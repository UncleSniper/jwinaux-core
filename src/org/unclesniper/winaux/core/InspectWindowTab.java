package org.unclesniper.winaux.core;

public class InspectWindowTab extends AbstractSwingTab {

	private InspectWindowPanel panel;

	public InspectWindowTab(SwingWindow window, String tabTitle) {
		super(window, tabTitle);
	}

	public InspectWindowPanel getPanel() {
		return panel;
	}

	@Override
	protected InspectWindowPanel makeTabComponent() {
		return panel = new InspectWindowPanel();
	}

}
