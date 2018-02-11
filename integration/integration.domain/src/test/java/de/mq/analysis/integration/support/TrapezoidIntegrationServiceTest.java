package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;

public class TrapezoidIntegrationServiceTest {

	private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);
	
	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
	
	private final AbstractIntegrationService integrationService = new TrapezoidIntegrationImpl();

	@BeforeEach
	public final void setup() {
		
		Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(0d);
		Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(1d);
		
		Mockito.doAnswer(answer -> Math.exp(-Math.pow((Double) answer.getArgument(0), 2))).when(realFunction).f(Mockito.anyDouble());
	
	   Mockito.when(definiteIntegral.boundsOfIntegration()).thenReturn(boundsOfIntegration);
	   Mockito.when(definiteIntegral.realFunction()).thenReturn(realFunction);
	   Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(5l);
	   Mockito.when(definiteIntegral.stepSize()).thenCallRealMethod();
	}

	// Papula Mathematik fuer Ingenieure und Naturwissenschaftler Seite 480
	@Test
	public final void resolveIntegral() {
		assertEquals(cut(0.7444d, 4), cut(integrationService.resolveIntegral(definiteIntegral), 4));
	}

	
	private Double cut(double x, int n) {
		final long y = (long) (x * Math.pow(10, n));
		return y / Math.pow(10, n);
	}

	@Test
	public final void calculationAlgorithm() {
		assertEquals(IntegrationService.CalculationAlgorithm.Trapezoid, integrationService.calculationAlgorithm());
	}
	
	@Test
	public final void quality() {
		assertEquals(2, integrationService.quality());
	}

}
