package de.mq.rungekutta;

import java.util.List;
import java.util.function.ToDoubleFunction;

import org.junit.Ignore;
import org.junit.Test;

import de.mq.rungekutta.RungeKutta2;
import de.mq.rungekutta.SolutionVector;
import de.mq.rungekutta.SolutionVectorImpl;
import junit.framework.Assert;

public class RungeKutta2Test {
	
	@Test
	
	public final void resolve() {
		final RungeKutta2 rungeKutta4 = new RungeKutta2(new SToSquareTest() , 0.1d, 0.2);
		
		final List<SolutionVector> results = rungeKutta4.resolve(new SolutionVectorImpl(0d, 0d, 4d));
		
		//results.forEach(v -> System.out.println((v.t()+ ","+ v.y()+ "," +v.s())));
		Assert.assertEquals(3, results.size());
		
		Assert.assertEquals(0d, results.get(0).t());
		Assert.assertEquals(0d, results.get(0).y());
		Assert.assertEquals(4d, results.get(0).s());
		
		Assert.assertEquals(1L, scaledLong(results.get(1).t(), 10d));
		Assert.assertEquals(364333L, scaledLong(results.get(1).y(), 1e6));
		Assert.assertEquals(3327683L, scaledLong(results.get(1).s(), 1e6));
		
		Assert.assertEquals(2L, scaledLong(results.get(2).t(), 10d));
		Assert.assertEquals(672562, scaledLong(results.get(2).y(), 1e6));
		Assert.assertEquals(286792, scaledLong(results.get(2).s(), 1e5));
		
	}
	
	private long  scaledLong(final double x, final double factor) {
		return (long) Math.floor(x*factor);
	}
	
	
	@Test
	@Ignore
	public final void resolveSar() {
		final RungeKutta2 rungeKutta4 = new RungeKutta2(new SarTest() , 0.1e-3, 0.2);
		final List<SolutionVector> results = rungeKutta4.resolve(new SolutionVectorImpl(0d, 0d, 0d));
		
		System.out.println(results.size());
		
		results.forEach(v -> {
			System.out.println( ("" +v.t()).replace('.', ',') +";" + ("" +v.y()).replace('.', ','));
			
		});
	}

}




class SToSquareTest implements ToDoubleFunction<SolutionVector>{

	@Override
	public double applyAsDouble(final SolutionVector solutionVector) {
		final double result =  -2d*solutionVector.s() + 3d* solutionVector.y();
		return result;
	}

	
	
}

class SarTest implements ToDoubleFunction<SolutionVector>{

	final double omega = Math.PI*25*2d;
	final double u1=30d;
	final double uled = 1.5;
	final double r1=1e3d;
	final double r2=10e3;
	final double c1= 330e-9;
	final double c2= 1.5e-6;
	
	@Override
	public double applyAsDouble(SolutionVector solutionVector) {
		
		return u(solutionVector.t()) - s(solutionVector.s())- y(solutionVector.y());
		
	}

	private double y(final double y) {
		return y/r1/r2/c1/c2;
	}

	private double s(final double s ) {
		return (r1*c1 +r2*c2+c1*r2)/r1/r2/c1/c2*s;
	}

	private double u(double t) {
		return omega*u1*Math.abs(Math.cos(omega*t))/r1/c2 + (uled / r1/r2/c1/c2);
	}

	
	
}