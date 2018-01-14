package de.mq.rungekutta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.ToDoubleFunction;

import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

public class RungeKutta1Test {
	
	@Test
	@Ignore
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
	@Test
	@Ignore
	public final void phase1() throws IOException {
		final RungeKutta1  rungeKutta1 = new RungeKutta1(1e-6, new SPhase01(), 2.5e-3) ;
		final List<SolutionVector> results = rungeKutta1.resolve(new SolutionVectorImpl(0d, 0d, 0d));
		
		final DecimalFormat nf = formatter();
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter("results.csv"))) {
		
		
		
		results.forEach(sv -> doWrite(writer, sv, nf, 0d));
		}
		
	

	}
	
@Test
public final void phase3() throws IOException {
	final RungeKutta1  rungeKutta1 = new RungeKutta1(1e-6, new SPhase03(), 20e-3) ;
	final List<SolutionVector> results = rungeKutta1.resolve(new SolutionVectorImpl(0d, 4.27, 0d));
	
	final DecimalFormat nf = formatter();
	try (final BufferedWriter writer = new BufferedWriter(new FileWriter("results.csv"))) {
	
	
	
	results.forEach(sv -> doWrite(writer, sv, nf, 10e-3));
	}
	


}
	static DecimalFormat formatter() {
		final DecimalFormat  nf  = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.GERMAN);
		
		DecimalFormatSymbols symbols = nf.getDecimalFormatSymbols();
		symbols.setCurrencySymbol(""); 
		nf.setRoundingMode(RoundingMode.HALF_DOWN);
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(99);
		nf.setDecimalFormatSymbols(symbols);
		return nf;
	}
	private void doWrite(final BufferedWriter writer, SolutionVector sv, final NumberFormat nf, final double t0 ){
		try {
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

class SPhase01  implements ToDoubleFunction<SolutionVector>{

	@Override
	public double applyAsDouble(SolutionVector vector) {
		
		final double r1=1e3d;
		final double u1=28.5;
		final double c1= 330e-9;
		final double c2= 1.5e-6;
		return  u1/(r1*c2)*Math.sin(2d*Math.PI*25*vector.t()) - (c1+c2)/(r1*c1*c2)*vector.y();
	
	}
	
}

class SPhase03  implements ToDoubleFunction<SolutionVector>{
	
	final double r2=10e3d;
	final double uled=1.5; 
	final double c2= 1.5e-6;

	@Override
	public double applyAsDouble(final SolutionVector vector) {
		return uled/(r2*c2) - vector.y() / (r2*c2); 
	
	}
	
	
}