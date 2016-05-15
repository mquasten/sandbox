package de.mq.pattern.gof.strategy;

public class Client {

	private IntegrationService integrationService;

	
	protected Client() {

	}

	public Client(final IntegrationService integrationService) {
		this.integrationService = integrationService;
	}

	public final double calculateArea(final double min, final double max, int n) {
		return integrationService.area(min, max, n);
	}

}
