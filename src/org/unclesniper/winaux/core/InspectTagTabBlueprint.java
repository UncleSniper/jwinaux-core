package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;

public class InspectTagTabBlueprint extends AbstractSwingTabBlueprint {

	private InspectTagTab inspectTab;

	public InspectTagTabBlueprint() {}

	@Override
	protected InspectTagTab makeSwingTab(AuxEngine engine, SwingWindow window) {
		return inspectTab = new InspectTagTab(window, getEffectiveTitle("Inspect tags"));
	}

	public void updateTagsAndActivate(AuxEngine engine) {
		if(inspectTab == null)
			throw new IllegalStateException("Tab has not been instantiated");
		inspectTab.getPanel().updateTags(engine);
		activateTab();
		this.window.activateWindow();
	}

}
