package de.mq.pattern.di.state.support;

import org.junit.Test;
import org.mockito.Mockito;

import de.mq.pattern.di.state.ArtistState;
import junit.framework.Assert;

public class NonFinalArtistStateTest {
	
	private static final Double AMOUNT = 10e6;
	private static final String FAILED_STATE_NAME = "breaked";
	private static final String STATE_NAME = "famous";
	private final Checker checker = Mockito.mock(Checker.class);
	private ArtistState successState = Mockito.mock(ArtistState.class);
	private final ArtistState artistState = new NonFinalArtistStateImpl(checker, STATE_NAME ,AMOUNT, successState, FAILED_STATE_NAME) ;

	@Test
	public final void name() {
		Assert.assertEquals(STATE_NAME, artistState.name());
	}
	
	@Test
	public final void ammount() {
		Assert.assertEquals(AMOUNT, artistState.amount());
	}
	
	@Test
	public final void isFinal() {
		Assert.assertFalse(artistState.isFinal());
	}
	
	@Test
	public final void failed() {
		Assert.assertEquals(FAILED_STATE_NAME, ((NonFinalArtistStateImpl)artistState).failed());
	}
	
	@Test
	public final void continueLifecycle() {
		Mockito.when(checker.checkResult()).thenReturn(CheckResult.Success);
		
		Assert.assertEquals(successState, artistState.continueLifecycle());
		
	}
	
	@Test(expected=IllegalStateException.class)
	public final void continueLifecycleNotFound() {
		Mockito.when(checker.checkResult()).thenReturn(CheckResult.Failed);
		artistState.continueLifecycle();
	}
	
	@Test
	public final void continueLifecycleFailedState() {
		final ArtistState failedState = Mockito.mock(ArtistState.class);
		Mockito.when(checker.checkResult()).thenReturn(CheckResult.Failed);
		((NonFinalArtistStateImpl)artistState).assignFailedState(failedState);
		Assert.assertEquals(failedState, artistState.continueLifecycle());
	}
	
	@Test
	public final void continueLifecycleStay() {
		Mockito.when(checker.checkResult()).thenReturn(CheckResult.Stay);
		Assert.assertEquals(artistState, artistState.continueLifecycle());
	}
}
