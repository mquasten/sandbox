package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.RealFunction;

public class TrapezoidIntegrationTest {

	private final RealFunction realFunction = Mockito.mock(RealFunction.class);

	@Before
	public final void setup() {
		Mockito.doAnswer(answer -> Math.exp(-Math.pow(answer.getArgumentAt(0, Double.class), 2))).when(realFunction).f(Mockito.anyDouble());
	}

	// Papula MAthematik fuer INgenieure und Naturwissenschaftler Seite 480
	@Test
	public final void calculate() {
		final DefiniteIntegral definiteIntegral = new TrapezoidIntegrationImpl(100, realFunction);
		Assert.assertEquals(cut(0.7468d, 4), cut(definiteIntegral.calculate(boundsOfIntegrationMock()), 4));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void calculateWrongN() {
		final DefiniteIntegral definiteIntegral = new TrapezoidIntegrationImpl(1, realFunction);
		definiteIntegral.calculate(boundsOfIntegrationMock());
	}

	private Double cut(double x, int n) {
		final long y = (long) (x * Math.pow(10, n));
		return y / Math.pow(10, n);
	}

	private BoundsOfIntegration boundsOfIntegrationMock() {
		final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
		Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(0d);
		Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(1d);
		return boundsOfIntegration;
	}

}
