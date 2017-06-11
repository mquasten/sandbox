package de.mq.rungekutta;

import java.util.List;
import java.util.function.ToDoubleFunction;

import org.junit.Test;

import junit.framework.Assert;

public class RungeKutta1Test {
	
	@Test
	public final void rungeKutta1() {
		final RungeKutta1  rungeKutta1 = new RungeKutta1(0.1d, new STest(), 0.3) ;
		
		final List<SolutionVector> results = rungeKutta1.resolve(new SolutionVectorImpl(0d, 0d, 0d));
		
		Assert.assertEquals(4, results.size());
		
		Assert.assertEquals(0d, results.get(0).t());
		Assert.assertEquals(0d, results.get(0).y());
		Assert.assertEquals(0d, results.get(0).s());
		
		
		Assert.assertEquals(0.1d, results.get(1).t());
		Assert.assertEquals(-5171L, scaledLong(results.get(1).y(),1e6));
		Assert.assertEquals(scaledLong(-0.005171 - 0.1,  1e6), scaledLong(results.get(1).s(),1e6)) ;
		
		Assert.assertEquals(0.2d, results.get(2).t());
		Assert.assertEquals(-21403L, scaledLong(results.get(2).y(),1e6));
		Assert.assertEquals(scaledLong(-0.021403 - 0.2,  1e6), scaledLong(results.get(2).s(),1e6)) ;
		
		Assert.assertTrue(Math.abs(results.get(3).t() -0.3)< 1e-12);
		Assert.assertEquals(-4986L, scaledLong(results.get(3).y(),1e5));
		Assert.assertEquals(scaledLong(-0.04986 - 0.3,  1e5), scaledLong(results.get(3).s(),1e5)) ;
		
		
	}
	
	
	private long  scaledLong(final double x, final double factor) {
		
		return (long) Math.rint(x*factor);
	}

}


class STest  implements ToDoubleFunction<SolutionVector>{

	@Override
	public double applyAsDouble(SolutionVector s) {
		return s.y() - s.t();
		
	}
	
}