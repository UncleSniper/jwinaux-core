package org.unclesniper.winaux.core.util;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

public final class SwingUtils {

	private SwingUtils() {}

	public static void invokeAndWait(Runnable task) {
		if(SwingUtilities.isEventDispatchThread()) {
			task.run();
			return;
		}
		for(;;) {
			try {
				SwingUtilities.invokeAndWait(task);
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

	public static void invokeLater(Runnable task) {
		if(SwingUtilities.isEventDispatchThread())
			task.run();
		else
			SwingUtilities.invokeLater(task);
	}

}
