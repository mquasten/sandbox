package de.mq.rungekutta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * 
 * @author mq
 * E. gamma: Design Patterns. Elements of Reusable Object-Oriented Software: strategy
 * and basic principles of analysis ... 
 */
public class RungeKutta2 {
	
	private final double h;
	private final ToDoubleFunction<SolutionVector> sToSquare;
	
	private final double tmax;
	
	public RungeKutta2(final ToDoubleFunction<SolutionVector> sToSquare, final double h, final double tmax) {
		this.sToSquare=sToSquare;
		this.h = h;	
		this.tmax=tmax;
	}

	public  final List<SolutionVector> resolve(final SolutionVector startVector) {
		final List<SolutionVector> solutionVectors = new ArrayList<>();
		solutionVectors.add(startVector);
		
		do {
			solutionVectors.add(calculateNext(solutionVectors, solutionVectors.get(solutionVectors.size()-1)));
		} while(solutionVectors.get(solutionVectors.size()-1).t() < tmax) ;
		return solutionVectors;
	}

	private SolutionVector calculateNext(final List<SolutionVector> solutionVectors, final SolutionVector lastSolutionVector) {
		final double k1 = h* lastSolutionVector.s(); 
		final double  m1 = h * sToSquare.applyAsDouble(lastSolutionVector);
		final  double k2= h*(lastSolutionVector.s() + m1/2d);
		final double m2 = h*sToSquare.applyAsDouble(new SolutionVectorImpl(lastSolutionVector.t()+h/2d,  lastSolutionVector.y()+ k1/2d ,  lastSolutionVector.s()+m1/2d));
		final double k3=h*(lastSolutionVector.s() + m2/2);
		final double m3 = h * sToSquare.applyAsDouble(new SolutionVectorImpl(lastSolutionVector.t()+h/2d, lastSolutionVector.y()+ +k2/2d, lastSolutionVector.s()+m2/2d));
		final double k4 = h * (lastSolutionVector.s() + m3);
		
		final double m4 = h * sToSquare.applyAsDouble(new SolutionVectorImpl(lastSolutionVector.t()+h, lastSolutionVector.y()+k3, lastSolutionVector.s() +m3));
		double t = lastSolutionVector.t()+h;
		
		double y = lastSolutionVector.y()+1/6d *(k1+2*k2+2d*k3 +k4);
		double s = lastSolutionVector.s()+1/6d *(m1+2*m2+2d*m3 +m4);
		
		return new SolutionVectorImpl(t, y, s);
	}

}
