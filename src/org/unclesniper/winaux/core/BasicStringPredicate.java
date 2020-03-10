package org.unclesniper.winaux.core;

import org.unclesniper.winaux.Doom;
import org.unclesniper.winaux.AuxEngine;

public class BasicStringPredicate implements StringPredicate {

	public enum Mode {
		EQUALS,
		STARTS_WITH,
		ENDS_WITH
	}

	private Mode mode = Mode.EQUALS;

	private StringProvider string;

	public BasicStringPredicate() {}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode == null ? Mode.EQUALS : mode;
	}

	public StringProvider getString() {
		return string;
	}

	public void setString(StringProvider string) {
		this.string = string;
	}

	@Override
	public boolean matches(AuxEngine engine, String string) {
		if(this.string == null)
			return false;
		String ref = this.string.getString(engine);
		if(ref == null)
			return false;
		switch(mode) {
			case EQUALS:
				return string.equals(ref);
			case STARTS_WITH:
				return string.startsWith(ref);
			case ENDS_WITH:
				return string.endsWith(ref);
			default:
				throw new Doom("Unrecognized mode: " + mode.name());
		}
	}

}
