package de.mq.pattern.gof.observer;

public class PositionCalculatorImpl implements PositionCalculator {

	private static final double RE = 6371e3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.mq.observer.PositionCalculator#position(de.mq.observer.Location,
	 * de.mq.observer.Location)
	 */
	public final Position position(final Location gunPosition, final Location targetPosition) {
		final double e12 = Math.acos((Math.sin(gunPosition.latitude().rad()) * Math.sin(targetPosition.latitude().rad()) + Math.cos(gunPosition.latitude().rad()) * Math.cos(targetPosition.latitude().rad()) * Math.cos(gunPosition.longitude().rad() - targetPosition.longitude().rad())));

		final double sin = Math.sin(targetPosition.longitude().rad() - gunPosition.longitude().rad()) * Math.cos(targetPosition.latitude().rad()) / Math.sin(e12);
		final double cos = (Math.sin(targetPosition.latitude().rad()) - (Math.sin(gunPosition.latitude().rad()) * Math.cos(e12))) / (Math.cos(gunPosition.latitude().rad()) * Math.sin(e12));

		return new Position() {

			@Override
			public Angle azimuth() {
				return  new Angle(Math.atan2(sin, cos));
			}

			@Override
			public double distanceOverGround() {
				return RE * e12;
			}

			@Override
			public Angle elevation() {
			
				return new Angle(Math.PI/2 - Math.asin((RE+targetPosition.altitude())*Math.sin(e12)/distanceToTarget(targetPosition, e12)));
				
			}

			private double distanceToTarget(final Location targetPosition, final double e12) {
				return Math.sqrt(Math.pow(RE, 2) + Math.pow(RE+targetPosition.altitude(), 2) -2* RE*(RE+targetPosition.altitude()) * Math.cos(e12));
			}

			@Override
			public double distanceToTarget() {
				return distanceToTarget(targetPosition, e12);
			}

			
		};

	}

}
