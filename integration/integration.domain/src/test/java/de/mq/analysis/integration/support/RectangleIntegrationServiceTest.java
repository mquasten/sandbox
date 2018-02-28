package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;

public class RectangleIntegrationServiceTest {
	
	private final RealFunction realFunction = Mockito.mock(RealFunction.class);

	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);

	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);

	private final AbstractIntegrationService integrationService = new RectangleIntegrationServiceImpl();

	@Before
	public final void setup() {

		Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(0d);
		Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(1d);

		Mockito.doAnswer(answer -> Math.exp(-Math.pow((Double) answer.getArgument(0), 2))).when(realFunction).f(Mockito.anyDouble());

		Mockito.when(definiteIntegral.boundsOfIntegration()).thenReturn(boundsOfIntegration);
		Mockito.when(definiteIntegral.realFunction()).thenReturn(realFunction);
		Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(5l);
		Mockito.when(definiteIntegral.stepSize()).thenCallRealMethod();
	}

	
	@Test
	public final void resolveIntegral() {
		assertEquals(Double.valueOf(0.7481d), cut(Double.valueOf( integrationService.resolveIntegral(definiteIntegral)), 4));
	}
	@Test
	public final void quality() {
		assertEquals(1, integrationService.quality());
	}
	
	@Test
	public final void calculationAlgorithm() {
		assertEquals(IntegrationService.CalculationAlgorithm.Rectangle, integrationService.calculationAlgorithm());
	}
	
	
	
	private Double cut(final double x, final int n) {
		final long y = Math.round((x * Math.pow(10, n)));
		return y / Math.pow(10, n);
	}

}
