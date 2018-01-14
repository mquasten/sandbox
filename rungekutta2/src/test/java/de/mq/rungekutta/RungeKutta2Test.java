package de.mq.rungekutta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.ToDoubleFunction;

import org.junit.Ignore;
import org.junit.Test;

import de.mq.rungekutta.RungeKutta2;
import de.mq.rungekutta.SolutionVector;
import de.mq.rungekutta.SolutionVectorImpl;
import junit.framework.Assert;

public class RungeKutta2Test {
	
//	final double  t0=0.002157;
	
	@Test
	@Ignore
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
	
	public final void resolveSar() throws IOException {
		final double t0 = 0.002157;
		final RungeKutta2 rungeKutta4 = new RungeKutta2(new SarTest(t0) , 1e-6, 15e-3);
		final List<SolutionVector> results = rungeKutta4.resolve(new SolutionVectorImpl(0d, 1.5d, 771.10d));
		
		System.out.println(results.size());
		final DecimalFormat nf = RungeKutta1Test.formatter();
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter("results.csv"))) {
		results.forEach(v -> {
			
			doWrite(writer, v, nf, t0);
		
			//System.out.println( ("" +v.t()).replace('.', ',') +";" + ("" +v.y()).replace('.', ','));
			
		});
	}
	}
	
	@Test
		public final void resolveSar2() throws IOException {
			final double t0 = 7.7e-3;
			final RungeKutta2 rungeKutta4 = new RungeKutta2(new SarTest(t0) , 1e-6, 10e-3);
			final List<SolutionVector> results = rungeKutta4.resolve(new SolutionVectorImpl(0d, 2.35, -57));
			
			System.out.println(results.size());
			final DecimalFormat nf = RungeKutta1Test.formatter();
			try (final BufferedWriter writer = new BufferedWriter(new FileWriter("results.csv"))) {
			results.forEach(v -> {
				
				doWrite(writer, v, nf, 20e-3+ t0);
			
				//System.out.println( ("" +v.t()).replace('.', ',') +";" + ("" +v.y()).replace('.', ','));
				
			});
		}

}


	
	private void doWrite(final BufferedWriter writer, SolutionVector sv, final NumberFormat nf, final double t0 ){
		
		try {
			
			writer.append(nf.format(sv.t()));
			writer.append(";");
			writer.append(nf.format(sv.t()+t0));
			writer.append(";");
			writer.append(nf.format(sv.y()));
			writer.append(";");
			writer.append(nf.format(sv.s()));
			writer.newLine();
		} catch (IOException e) {
			throw new IllegalStateException(e);
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

	final double t0;

	final double omega = Math.PI*25*2d;
	final double u1=28.5d;
	final double uled = 1.5;
	final double r1=1e3d;
	final double r2=10e3;
	final double c1= 330e-9;
	final double c2= 1.5e-6;
	
	SarTest(final double t0) {
		this.t0 = t0;
	}
	
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
		return omega*u1*Math.abs(Math.cos(omega*(t + t0)))/r1/c2 + (uled / r1/r2/c1/c2);
	}

}	
	
}