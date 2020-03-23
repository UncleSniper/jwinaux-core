package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.AuxAction;

public class InspectTagsAction implements AuxAction {

	private InspectTagTabBlueprint tab;

	public InspectTagsAction() {}

	public InspectTagTabBlueprint getTab() {
		return tab;
	}

	public void setTab(InspectTagTabBlueprint tab) {
		this.tab = tab;
	}

	@Override
	public void perform(AuxEngine engine) {
		if(tab == null)
			throw new IllegalStateException("No tab has been set");
		tab.updateTagsAndActivate(engine);
	}

}
