package org.unclesniper.winaux.core;

import java.awt.Component;

public abstract class AbstractSwingTab implements SwingTab {

	private final SwingWindow window;

	private Component tabComponent;

	private final String tabTitle;

	public AbstractSwingTab(SwingWindow window, String tabTitle) {
		this.window = window;
		this.tabTitle = tabTitle;
	}

	protected abstract Component makeTabComponent();

	@Override
	public Component getTabComponent() {
		if(tabComponent == null)
			tabComponent = makeTabComponent();
		return tabComponent;
	}

	@Override
	public String getTabTitle() {
		return tabTitle;
	}

}
