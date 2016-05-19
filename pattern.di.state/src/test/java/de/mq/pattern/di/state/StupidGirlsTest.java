package de.mq.pattern.di.state;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import junit.framework.Assert;

public class StupidGirlsTest {
	
	
	@Test
	public final void lifecycle() {
		final ApplicationContext ctx = Mockito.mock(ApplicationContext.class);
		final ArtistState unkownState = Mockito.mock(ArtistState.class);
		Mockito.when(unkownState.name()).thenReturn(StupidGirls.START_STATE_NAME);
		final ArtistState outState = Mockito.mock(ArtistState.class);
		Mockito.when(outState.name()).thenReturn("outArtist");
		Mockito.when(outState.isFinal()).thenReturn(true);
		Mockito.when(unkownState.continueLifecycle()).thenReturn(unkownState, outState);
		Mockito.when(ctx.getBean(StupidGirls.START_STATE_NAME, ArtistState.class)).thenReturn(unkownState);
		
		final Map<String, Integer> states = new HashMap<>();
		
		StupidGirls.lifecycle(ctx, states);
		
		Mockito.verify(unkownState, Mockito.times(2)).continueLifecycle();
		Mockito.verify(unkownState,Mockito.times(2)).isFinal();
		Mockito.verify(outState).isFinal();
		
		Assert.assertEquals(1, states.size());
		Assert.assertTrue( states.keySet().stream().findAny().isPresent());
		Assert.assertEquals(StupidGirls.START_STATE_NAME, states.keySet().stream().findAny().get());
		Assert.assertTrue( states.values().stream().findAny().isPresent());
		Assert.assertEquals(2, (int) states.values().stream().findAny().get());
		
	}

}
