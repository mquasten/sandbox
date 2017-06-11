package de.mq.rungekutta;

public class SolutionVectorImpl implements SolutionVector {
	
	private final double t;

	private final double y;
	private final double s;
	
	public SolutionVectorImpl(final double t, final double y, final double s) {
		this.t = t;
		this.y = y;
		this.s = s;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.rungekutta2.SolutionVector#t()
	 */
	public double t() {
		return t;
	}

	/* (non-Javadoc)
	 * @see de.mq.rungekutta2.SolutionVector#y()
	 */
	public double y() {
		return y;
	}

	/* (non-Javadoc)
	 * @see de.mq.rungekutta2.SolutionVector#s()
	 */
	public double s() {
		return s;
	}

	

}
