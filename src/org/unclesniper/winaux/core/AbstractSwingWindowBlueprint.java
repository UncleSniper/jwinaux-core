package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.core.util.SwingUtils;

public abstract class AbstractSwingWindowBlueprint implements SwingWindowBlueprint {

	private volatile AbstractSwingWindow window;

	protected String title;

	public AbstractSwingWindowBlueprint() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	protected abstract AbstractSwingWindow makeWindowInGUIThread(AuxEngine engine);

	@Override
	public AbstractSwingWindow getSwingWindow(AuxEngine engine) {
		synchronized(this) {
			if(window == null)
				SwingUtils.invokeAndWait(() -> window = makeWindowInGUIThread(engine));
			return window;
		}
	}

	@Override
	public void destroySwingWindow(AuxEngine engine) {
		synchronized(this) {
			if(window != null) {
				SwingUtils.invokeAndWait(() -> {
					window.dispose();
					window = null;
				});
			}
		}
	}

}
