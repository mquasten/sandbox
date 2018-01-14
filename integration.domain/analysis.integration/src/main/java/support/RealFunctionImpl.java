package support;

import de.mq.analysis.integration.RealFunction;

public class RealFunctionImpl  implements RealFunction {

	@Override
	public double f(final double x) {
		return Math.exp(Math.pow(x, 2));
	}

}
