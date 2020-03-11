package org.unclesniper.winaux.core;

import javax.swing.JFrame;
import java.security.SecureRandom;
import org.unclesniper.winwin.HWnd;
import java.util.function.Predicate;
import org.unclesniper.winwin.WinAPI;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;
import org.unclesniper.winaux.WindowCreationHook;

public class AbstractSwingWindow extends JFrame {

	private static final class WindowHolder {

		AbstractSwingWindow window;

		WindowHolder() {}

	}

	private static final SecureRandom RANDOM = new SecureRandom();

	private static long nextWindowID = 0l;

	private static final String UNIQUE_PREFIX = "$$$" + AbstractSwingWindow.class.getName()
			+ '/' + WinAPI.getCurrentProcessId() + '/';

	private HWnd hwnd;

	private String slatedTitle;

	public AbstractSwingWindow(AuxEngine engine, String title, Predicate<KnownWindow> onCreate) {
		this(new WindowHolder(), engine, title, onCreate);
	}

	private AbstractSwingWindow(WindowHolder holder, AuxEngine engine, String title,
			Predicate<KnownWindow> onCreate) {
		super(AbstractSwingWindow.makeUniqeTitle(holder, engine, onCreate));
		holder.window = this;
		this.slatedTitle = slatedTitle;
	}

	public HWnd getHwnd() {
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
		if(hwnd != null || !visible || isVisible()) {
			super.setVisible(visible);
			return;
		}
		super.setVisible(true);
		hwnd = HWnd.findWindow(null, getTitle());
		if(hwnd == null)
			throw new IllegalStateException("Couldn't determine native window handle");
		setTitle(slatedTitle);
	}

	private static String makeUniqeTitle(WindowHolder holder, AuxEngine engine, Predicate<KnownWindow> onCreate) {
		long left = AbstractSwingWindow.RANDOM.nextLong();
		long center = AbstractSwingWindow.RANDOM.nextLong();
		long right = AbstractSwingWindow.RANDOM.nextLong();
		String title = AbstractSwingWindow.UNIQUE_PREFIX + '/' + AbstractSwingWindow.nextWindowID++
				+ left + '/' + center + '/' + right;
		engine.addWindowCreationHook((en, window) -> {
			if(holder.window.hwnd == null) {
				holder.window.hwnd = window.getHWnd();
				if(holder.window.hwnd != null)
					holder.window.setTitle(holder.window.slatedTitle);
			}
			int flags = 0;
			if(onCreate != null && !onCreate.test(window))
				flags |= WindowCreationHook.FL_SWALLOW;
			return flags |= WindowCreationHook.FL_REMOVE;
		});
		return title;
	}

}
