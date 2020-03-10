package org.unclesniper.winaux.core;

import java.util.function.Consumer;
import org.unclesniper.winaux.TagListener;
import org.unclesniper.winaux.WindowPredicate;

public abstract class AbstractTagWindowPredicate implements WindowPredicate {

	public AbstractTagWindowPredicate() {}

	@Override
	public void collectListenerTypes(Consumer<Class<?>> sink) {
		sink.accept(TagListener.class);
	}

}
