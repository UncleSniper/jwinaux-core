package org.unclesniper.winaux.core;

import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.IdentityHashMap;
import org.unclesniper.winaux.Tag;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;

public class HasTagWindowPredicate extends AbstractTagWindowPredicate {

	private final Map<Tag, Void> tags = new IdentityHashMap<Tag, Void>();

	private AndOr junctor = AndOr.AND;

	public HasTagWindowPredicate() {}

	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags.keySet());
	}

	public void addTag(Tag tag) {
		if(tag != null)
			tags.put(tag, null);
	}

	public void removeTag(Tag tag) {
		if(tag != null)
			tags.remove(tag);
	}

	public AndOr getJunctor() {
		return junctor;
	}

	public void setJunctor(AndOr junctor) {
		this.junctor = junctor == null ? AndOr.AND : junctor;
	}

	@Override
	public boolean matches(AuxEngine engine, KnownWindow window) {
		boolean and = junctor.isConjunctive();
		for(Tag tag : tags.keySet()) {
			boolean has = window.hasTag(tag);
			if(has != and)
				return junctor.negate(has);
		}
		return junctor.negate(and);
	}

}
