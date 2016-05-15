package de.mq.pattern.gof.strategy;

public class SimpsonIntegrationServiceImpl implements IntegrationService {

	private RealFunction realFunction;

	protected SimpsonIntegrationServiceImpl() {

	}

	public SimpsonIntegrationServiceImpl(final RealFunction realFunction) {
		this.realFunction = realFunction;
	}

	@Override
	public double area(double min, double max, int n) {
		if ((n % 2) != 0) {
			throw new IllegalArgumentException("n  muß durch 2 teilbar sein");
		}
		final double result = calculate(min, max, n);
		if ((n % 4) != 0) {
			return result;
		}

		double half = calculate(min, max, n / 2);
		return half + (half - result) / 15;
	}

	private double calculate(final double min, final double max, final int n) {
		final double h = (max - min) / n;
		return ((realFunction.result(min) + realFunction.result(max)) + 4 * summe(max, h, min + h) + 2 * summe(max, h, min + 2 * h)) * h / 3;
	}

	private double summe(final double max, final double h, final double min) {
		double sum = 0;
		double x = min;
		while (x < max) {
			sum += realFunction.result(x);
			x += 2 * h;
		}
		return sum;
	}

}
