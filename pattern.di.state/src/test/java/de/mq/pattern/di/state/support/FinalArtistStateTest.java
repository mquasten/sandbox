package de.mq.pattern.di.state.support;

import org.junit.Test;

import de.mq.pattern.di.state.ArtistState;
import junit.framework.Assert;

public class FinalArtistStateTest {
	
	private static final String STATE_NAME = "famous";
	private ArtistState artistState = new FinalArtistStateImpl(STATE_NAME);
	
	@Test
	public final void  name() {
		Assert.assertEquals(STATE_NAME, artistState.name());
	}
	
	@Test
	public final void rate() {
		Assert.assertEquals(0d, artistState.amount());
	}
	@Test
	public final void isFinal() {
		Assert.assertTrue(artistState.isFinal());
	}
	@Test(expected=IllegalStateException.class)
	public final void continueLifecycle() {
		artistState.continueLifecycle();
	}

}
