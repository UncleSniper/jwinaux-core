package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;
import org.unclesniper.winaux.WindowAction;

public class InspectInTabWindowAction implements WindowAction {

	private InspectWindowTabBlueprint tab;

	public InspectInTabWindowAction() {}

	public InspectWindowTabBlueprint getTab() {
		return tab;
	}

	public void setTab(InspectWindowTabBlueprint tab) {
		this.tab = tab;
	}

	@Override
	public void perform(AuxEngine engine, KnownWindow window) {
		if(tab == null)
			throw new IllegalStateException("No tab has been set in which to inspect the window");
		tab.setInspectedWindowAndActivate(window);
	}

}
