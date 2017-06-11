package de.mq.rungekutta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class RungeKutta1 {
	
	private final double h;
	private final ToDoubleFunction<SolutionVector> s;
	
	private final double tmax;
	
	public RungeKutta1(double h, ToDoubleFunction<SolutionVector> s, double tmax) {
		this.h = h;
		this.s = s;
		this.tmax = tmax;
	}

	
	
	public  final List<SolutionVector> resolve(final SolutionVector startVector) {
		final List<SolutionVector> solutionVectors = new ArrayList<>();
		
		
		solutionVectors.add(new SolutionVectorImpl(startVector.t(), startVector.y(), s.applyAsDouble(startVector)));
		
		do {
			solutionVectors.add(calculateNext(solutionVectors, solutionVectors.get(solutionVectors.size()-1)));
		} while(solutionVectors.get(solutionVectors.size()-1).t() < tmax) ;
		return solutionVectors;
	}



	private SolutionVector calculateNext(List<SolutionVector> solutionVectors, SolutionVector solutionVector) {
		
		final double k1 = h * s.applyAsDouble(solutionVector);
		final double k2 = h*s.applyAsDouble(new SolutionVectorImpl(solutionVector.t() + h/2, solutionVector.y() + k1/2, 0d));
		final double k3 = h*s.applyAsDouble(new SolutionVectorImpl(solutionVector.t() + h/2, solutionVector.y() + k2/2, 0d));
		final double k4 = h*s.applyAsDouble(new SolutionVectorImpl(solutionVector.t() + h, solutionVector.y()+k3,0d));
		final double y = solutionVector.y() + 1/6d*(k1+2*k2+2*k3+k4);
		final double s = this.s.applyAsDouble(new SolutionVectorImpl(solutionVector.t() + h, y, 0d));
		return new SolutionVectorImpl(solutionVector.t() + h, y, s);
	}

}
