package org.unclesniper.winaux.core;

import javax.swing.SwingUtilities;
import org.unclesniper.winaux.AuxEngine;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractSwingWindowBlueprint implements SwingWindowBlueprint {

	private volatile AbstractSwingWindow window;

	public AbstractSwingWindowBlueprint() {}

	protected abstract AbstractSwingWindow makeWindowInGUIThread(AuxEngine engine);

	@Override
	public AbstractSwingWindow getSwingWindow(AuxEngine engine) {
		synchronized(this) {
			if(window == null) {
				if(SwingUtilities.isEventDispatchThread())
					window = makeWindowInGUIThread(engine);
				else {
					for(;;) {
						try {
							SwingUtilities.invokeAndWait(() -> window = makeWindowInGUIThread(engine));
							break;
						}
						catch(InterruptedException ie) {}
						catch(InvocationTargetException ite) {
							Throwable cause = ite.getCause();
							if(cause instanceof RuntimeException)
								throw (RuntimeException)cause;
							throw new RuntimeException(ite.getMessage(), ite);
						}
					}
				}
			}
			return window;
		}
	}

}
