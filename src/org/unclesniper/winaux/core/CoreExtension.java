package org.unclesniper.winaux.core;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.AuxExtension;

public class CoreExtension implements AuxExtension {

	private final List<SwingWindowBlueprint> swingWindows = new LinkedList<SwingWindowBlueprint>();

	public CoreExtension() {}

	public void addSwingWindow(SwingWindowBlueprint blueprint) {
		if(blueprint != null)
			swingWindows.add(blueprint);
	}

	public boolean removeSwingWindow(SwingWindowBlueprint blueprint) {
		return blueprint != null && swingWindows.remove(blueprint);
	}

	public void clearSwingWindows() {
		swingWindows.clear();
	}

	public List<SwingWindowBlueprint> getSwingWindows() {
		return Collections.unmodifiableList(swingWindows);
	}

	@Override
	public void registerExtension(AuxEngine engine) {
		for(SwingWindowBlueprint blueprint : swingWindows)
			blueprint.getSwingWindow(engine);
	}

	@Override
	public void unregisterExtension(AuxEngine engine) {
		for(SwingWindowBlueprint blueprint : swingWindows)
			blueprint.destroySwingWindow(engine);
	}

}
