package de.mq.pattern.gof.strategy;

public class Parabel implements RealFunction {

	@Override
	public double result(final double x) {
		return x * x;
	}

}
