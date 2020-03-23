package org.unclesniper.winaux.core;

public class InspectTagTab extends AbstractSwingTab {

	private InspectTagPanel panel;

	public InspectTagTab(SwingWindow window, String tabTitle) {
		super(window, tabTitle);
	}

	public InspectTagPanel getPanel() {
		return panel;
	}

	@Override
	protected InspectTagPanel makeTabComponent() {
		return panel = new InspectTagPanel();
	}

}
