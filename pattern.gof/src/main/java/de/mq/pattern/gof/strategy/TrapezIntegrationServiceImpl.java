package de.mq.pattern.gof.strategy;

public class TrapezIntegrationServiceImpl implements IntegrationService {

	private RealFunction function;

	protected TrapezIntegrationServiceImpl() {

	}

	public TrapezIntegrationServiceImpl(final RealFunction function) {
		this.function = function;
	}

	@Override
	public double area(double min, double max, int n) {
		final double h = (max - min) / n;

		double sum = 0.5 * (function.result(min) + function.result(max));

		double x = min + h;
		while (x < max) {
			sum += function.result(x);
			x += h;
		}

		return sum * h;
	}

}
