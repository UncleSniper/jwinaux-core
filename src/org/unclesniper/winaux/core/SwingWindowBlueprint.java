package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;

public interface SwingWindowBlueprint {

	SwingWindow getSwingWindow(AuxEngine engine);

	void destroySwingWindow(AuxEngine engine);

}
