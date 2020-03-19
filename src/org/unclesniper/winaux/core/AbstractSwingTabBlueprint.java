package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;

public abstract class AbstractSwingTabBlueprint implements SwingTabBlueprint {

	protected Runnable tabActivator;

	private String overrideTitle;

	private SwingTab tab;

	protected SwingWindow window;

	public AbstractSwingTabBlueprint() {}

	protected abstract SwingTab makeSwingTab(AuxEngine engine, SwingWindow window);

	public String getOverrideTitle() {
		return overrideTitle;
	}

	public void setOverrideTitle(String overrideTitle) {
		this.overrideTitle = overrideTitle;
	}

	public Runnable getTabActivator() {
		return tabActivator;
	}

	@Override
	public void setTabActivator(Runnable tabActivator) {
		this.tabActivator = tabActivator;
	}

	@Override
	public SwingTab getSwingTab(AuxEngine engine, SwingWindow window) {
		if(tab == null) {
			tab = makeSwingTab(engine, window);
			this.window = window;
		}
		return tab;
	}

	public void activateTab() {
		if(tabActivator == null)
			throw new IllegalStateException("No tab activator set");
		tabActivator.run();
	}

	protected String getEffectiveTitle(String fallback) {
		return overrideTitle == null ? fallback : overrideTitle;
	}

}
