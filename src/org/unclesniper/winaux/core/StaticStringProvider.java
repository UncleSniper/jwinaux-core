package org.unclesniper.winaux.core;

import org.unclesniper.winaux.AuxEngine;

public class StaticStringProvider implements StringProvider {

	private String string;

	public StaticStringProvider() {}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public String getString(AuxEngine engine) {
		if(string == null)
			throw new IllegalStateException("No string was set");
		return string;
	}

}
