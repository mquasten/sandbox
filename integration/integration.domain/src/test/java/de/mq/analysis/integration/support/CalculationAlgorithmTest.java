package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Test;

public class CalculationAlgorithmTest {

	@Test
	public final void definiteIntegralCaculationTropezoid() {

		Assert.assertTrue(CalculationAlgorithm.Tropezoid.definiteIntegralCaculation() instanceof TrapezoidIntegrationImpl);

	}

}
