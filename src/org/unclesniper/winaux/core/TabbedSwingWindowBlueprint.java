package org.unclesniper.winaux.core;

import java.util.List;
import java.awt.Component;
import java.util.LinkedList;
import java.util.Collections;
import org.unclesniper.winaux.AuxEngine;

public class TabbedSwingWindowBlueprint extends AbstractSwingWindowBlueprint {

	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;

	private final List<SwingTabBlueprint> tabs = new LinkedList<SwingTabBlueprint>();

	private int width = TabbedSwingWindowBlueprint.DEFAULT_WIDTH;

	private int height = TabbedSwingWindowBlueprint.DEFAULT_HEIGHT;

	public TabbedSwingWindowBlueprint() {}

	public void addTab(SwingTabBlueprint tab) {
		if(tab != null)
			tabs.add(tab);
	}

	public boolean removeTab(SwingTabBlueprint tab) {
		return tab != null && tabs.remove(tab);
	}

	public void clearTabs() {
		tabs.clear();
	}

	public List<SwingTabBlueprint> getTabs() {
		return Collections.unmodifiableList(tabs);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width > 0 ? width : TabbedSwingWindowBlueprint.DEFAULT_WIDTH;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height > 0 ? height : TabbedSwingWindowBlueprint.DEFAULT_HEIGHT;
	}

	@Override
	protected TabbedSwingWindow makeWindowInGUIThread(AuxEngine engine) {
		TabbedSwingWindow window = new TabbedSwingWindow(engine, title == null ? "JWinAux" : title,
				win -> AbstractSwingWindow.defaultOnCreate(engine, win), width, height);
		for(SwingTabBlueprint tabBP : tabs) {
			SwingTab tab = tabBP.getSwingTab(engine, window);
			String title = tab.getTabTitle();
			if(title == null)
				throw new IllegalStateException("Tab cannot have null title");
			Component component = tab.getTabComponent();
			if(component == null)
				throw new IllegalStateException("Tab cannot have null component");
			window.addTab(title, component, tabBP);
			tabBP.setTabActivator(TabbedSwingWindowBlueprint.makeTabActivator(window, tabBP));
		}
		window.pack();
		window.setLocationRelativeTo(null);
		return window;
	}

	private static Runnable makeTabActivator(TabbedSwingWindow window, SwingTabBlueprint tabBP) {
		return () -> window.setActiveTab(tabBP);
	}

}
