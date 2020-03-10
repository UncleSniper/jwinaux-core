package org.unclesniper.winaux.core;

import java.util.function.Consumer;
import org.unclesniper.winaux.WindowPredicate;
import org.unclesniper.winaux.ShellEventListener;

public abstract class AbstractShellEventWindowPredicate implements WindowPredicate {

	public AbstractShellEventWindowPredicate() {}

	@Override
	public void collectListenerTypes(Consumer<Class<?>> sink) {
		sink.accept(ShellEventListener.class);
	}

}
