package org.unclesniper.winaux.core;

import java.util.regex.Pattern;
import org.unclesniper.winaux.AuxEngine;

public class RegexStringPredicate implements StringPredicate {

	private String regex;

	private Pattern pattern;

	public RegexStringPredicate() {}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
		pattern = regex == null ? null : Pattern.compile(regex);
	}

	@Override
	public boolean matches(AuxEngine engine, String string) {
		if(pattern == null)
			return false;
		return pattern.matcher(string).matches();
	}

}
