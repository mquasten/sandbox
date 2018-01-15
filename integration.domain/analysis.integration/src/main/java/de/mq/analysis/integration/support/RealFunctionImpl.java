package de.mq.analysis.integration.support;

import de.mq.analysis.integration.RealFunction;

class RealFunctionImpl  implements RealFunction {

	@Override
	public double f(final double x) {
		return Math.exp(Math.pow(x, 2));
	}

}
