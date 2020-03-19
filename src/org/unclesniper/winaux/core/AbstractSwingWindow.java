package org.unclesniper.winaux.core;

import javax.swing.JFrame;
import java.security.SecureRandom;
import org.unclesniper.winwin.HWnd;
import java.util.function.Predicate;
import org.unclesniper.winwin.WinAPI;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;
import org.unclesniper.winwin.WindowsException;
import org.unclesniper.winaux.WindowCreationHook;
import org.unclesniper.winaux.core.util.SwingUtils;

public abstract class AbstractSwingWindow extends JFrame implements SwingWindow {

	private static final class WindowHolder {

		AbstractSwingWindow window;

		Boolean onCreateResult;

		WindowHolder() {}

	}

	private static final SecureRandom RANDOM = new SecureRandom();

	private static long nextWindowID = 0l;

	private static final String UNIQUE_PREFIX = "$$$" + AbstractSwingWindow.class.getName()
			+ '/' + WinAPI.getCurrentProcessId() + '/';

	private HWnd hwnd;

	private final Object hwndLock = new Object();

	private String slatedTitle;

	private volatile boolean slatedToForeground;

	private final WindowHolder theHolder;

	private final Predicate<KnownWindow> theOnCreate;

	private final AuxEngine theEngine;

	public AbstractSwingWindow(AuxEngine engine, String title, Predicate<KnownWindow> onCreate) {
		this(new WindowHolder(), engine, title, onCreate);
	}

	private AbstractSwingWindow(WindowHolder holder, AuxEngine engine, String title,
			Predicate<KnownWindow> onCreate) {
		super(AbstractSwingWindow.makeUniqeTitle(holder, engine, onCreate));
		holder.window = this;
		slatedTitle = title;
		theHolder = holder;
		theOnCreate = onCreate;
		theEngine = engine;
	}

	@Override
	public HWnd getHWnd() {
		return hwnd;
	}

	@Override
	public String getTitle() {
		return hwnd != null ? super.getTitle() : slatedTitle;
	}

	@Override
	public void setTitle(String title) {
		if(hwnd != null)
			super.setTitle(title);
		else
			slatedTitle = title;
	}

	@Override
	public void setVisible(boolean visible) {
		synchronized(hwndLock) {
			if(hwnd != null || !visible || isVisible()) {
				super.setVisible(visible);
				return;
			}
			super.setVisible(true);
			hwnd = HWnd.findWindow(null, getTitle());
			if(hwnd == null)
				throw new IllegalStateException("Couldn't determine native window handle");
			if(theOnCreate != null)
				theHolder.onCreateResult = theOnCreate.test(theEngine.internWindow(hwnd));
			if(slatedToForeground)
				HWnd.setForegroundWindow(hwnd, true);
			setTitle(slatedTitle);
		}
	}

	@Override
	public void activateWindow() {
		SwingUtils.invokeAndWait(() -> {
			setVisible(true);
			synchronized(hwndLock) {
				if(hwnd == null)
					slatedToForeground = true;
				else
					HWnd.setForegroundWindow(hwnd, true);
			}
		});
	}

	@Override
	public void hideWindow() {
		SwingUtils.invokeAndWait(() -> setVisible(false));
	}

	private static String makeUniqeTitle(WindowHolder holder, AuxEngine engine, Predicate<KnownWindow> onCreate) {
		long left = AbstractSwingWindow.RANDOM.nextLong();
		long center = AbstractSwingWindow.RANDOM.nextLong();
		long right = AbstractSwingWindow.RANDOM.nextLong();
		String title = AbstractSwingWindow.UNIQUE_PREFIX + '/' + AbstractSwingWindow.nextWindowID++
				+ left + '/' + center + '/' + right;
		engine.addWindowCreationHook((en, window) -> {
			int flags = 0;
			synchronized(holder.window.hwndLock) {
				if(holder.window.hwnd == null) {
					String wtitle;
					try {
						wtitle = window.getHWnd().getWindowText();
					}
					catch(WindowsException we) {
						return 0;
					}
					if(!title.equals(wtitle))
						return 0;
					holder.window.hwnd = window.getHWnd();
					if(holder.window.hwnd != null) {
						holder.window.setTitle(holder.window.slatedTitle);
						if(holder.window.slatedToForeground)
							HWnd.setForegroundWindow(holder.window.hwnd, true);
					}
				}
				else if(!window.getHWnd().equals(holder.window.hwnd))
					return 0;
				if(holder.onCreateResult != null) {
					if(!holder.onCreateResult.booleanValue())
						flags |= WindowCreationHook.FL_SWALLOW;
				}
				else if(onCreate != null) {
					boolean ocr = onCreate.test(window);
					if(!ocr)
						flags |= WindowCreationHook.FL_SWALLOW;
					holder.onCreateResult = ocr;
				}
			}
			return flags | WindowCreationHook.FL_REMOVE;
		});
		return title;
	}

	public static boolean defaultOnCreate(AuxEngine engine, KnownWindow window) {
		engine.grantTag(window, engine.getExemptionTag());
		return true;
	}

}
