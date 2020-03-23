package org.unclesniper.winaux.core.util;

import java.awt.Color;

public final class ColorBlend {

	private ColorBlend() {}

	public static Color alphaBlend(Color bottom, Color top, int alphaTop) {
		double br = (double)bottom.getRed() / 255.0;
		double bg = (double)bottom.getGreen() / 255.0;
		double bb = (double)bottom.getBlue() / 255.0;
		double ba = 1.0;
		double tr = (double)top.getRed() / 255.0;
		double tg = (double)top.getGreen() / 255.0;
		double tb = (double)top.getBlue() / 255.0;
		double ta = (double)alphaTop / 255.0;
		double numR = ba * (1.0 - ta);
		double den = ta + ba * (1.0 - ta);
		double r = (tr * ta + br * numR) / den;
		double g = (tg * ta + bg * numR) / den;
		double b = (tb * ta + bb * numR) / den;
		return new Color((float)r, (float)g, (float)b);
	}

}
