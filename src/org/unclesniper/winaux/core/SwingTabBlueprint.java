package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;

public interface SwingTabBlueprint {

	SwingTab getSwingTab(AuxEngine engine, SwingWindow window);

	void setTabActivator(Runnable activator);

}
