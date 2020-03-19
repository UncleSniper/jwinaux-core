package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;

public class InspectWindowTabBlueprint extends AbstractSwingTabBlueprint {

	private InspectWindowTab inspectTab;

	public InspectWindowTabBlueprint() {}

	@Override
	protected InspectWindowTab makeSwingTab(AuxEngine engine, SwingWindow window) {
		return inspectTab = new InspectWindowTab(window, getEffectiveTitle("Inspect window"));
	}

	public void setInspectedWindowAndActivate(KnownWindow window) {
		if(inspectTab == null)
			throw new IllegalStateException("Tab has not been instantiated");
		inspectTab.getPanel().setInspectedWindow(window);
		activateTab();
		this.window.activateWindow();
	}

}
