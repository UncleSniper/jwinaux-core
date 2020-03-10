package org.unclesniper.winaux.core;

public enum AndOr {

	AND(true, false),
	OR(false, false),
	NAND(true, true),
	NOR(false, true);

	private final boolean conjunctive;

	private final boolean negating;

	private AndOr(boolean conjunctive, boolean negating) {
		this.conjunctive = conjunctive;
		this.negating = negating;
	}

	public boolean isConjunctive() {
		return conjunctive;
	}

	public boolean isNegating() {
		return negating;
	}

	public boolean negate(boolean value) {
		return negating ? !value : value;
	}

}
