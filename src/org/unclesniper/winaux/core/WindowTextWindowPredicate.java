package org.unclesniper.winaux.core;

import java.util.Set;
import java.util.Map;
import java.util.Collections;
import java.util.IdentityHashMap;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;
import org.unclesniper.winwin.WindowsException;

public class WindowTextWindowPredicate extends AbstractShellEventWindowPredicate {

	private final Map<StringPredicate, Void> predicates = new IdentityHashMap<StringPredicate, Void>();

	private AndOr junctor = AndOr.AND;

	public WindowTextWindowPredicate() {}

	public Set<StringPredicate> getPredicates() {
		return Collections.unmodifiableSet(predicates.keySet());
	}

	public void addPredicate(StringPredicate predicate) {
		if(predicate != null)
			predicates.put(predicate, null);
	}

	public void removePredicate(StringPredicate predicate) {
		if(predicate != null)
			predicates.remove(predicate);
	}

	public AndOr getJunctor() {
		return junctor;
	}

	public void setJunctor(AndOr junctor) {
		this.junctor = junctor == null ? AndOr.AND : junctor;
	}

	@Override
	public boolean matches(AuxEngine engine, KnownWindow window) {
		String windowText;
		try {
			windowText = window.getHWnd().getWindowText();
		}
		catch(WindowsException e) {
			return junctor.isNegating();
		}
		boolean and = junctor.isConjunctive();
		for(StringPredicate predicate : predicates.keySet()) {
			boolean has = predicate.matches(engine, windowText);
			if(has != and)
				return junctor.negate(has);
		}
		return junctor.negate(and);
	}

}
