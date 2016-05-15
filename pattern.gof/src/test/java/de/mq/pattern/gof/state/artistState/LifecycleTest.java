package de.mq.pattern.gof.state.artistState;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.mq.pattern.gof.state.artistState.ArtistState;
import de.mq.pattern.gof.state.artistState.ArtistStateFactory;

public class LifecycleTest {
	
	@Test
	public final void testUnkownState(){
		final Set<ArtistState> states = simmulateLifeCycle(ArtistStateFactory.Unkown.artistState());
		Assert.assertEquals(3, states.size());
		Assert.assertTrue(states.contains(ArtistStateFactory.Unkown.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Blessed.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Out.artistState()));
	}
	
	@Test
	public final void testBlessedState(){
		final Set<ArtistState> states = simmulateLifeCycle(ArtistStateFactory.Blessed.artistState());
		Assert.assertEquals(3, states.size());
		Assert.assertTrue(states.contains(ArtistStateFactory.Blessed.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Hot.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Out.artistState()));
	}
	
	@Test
	public final void testHotState(){
		final Set<ArtistState> states = simmulateLifeCycle(ArtistStateFactory.Hot.artistState());
		
		Assert.assertEquals(3, states.size());
		Assert.assertTrue(states.contains(ArtistStateFactory.Hot.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Famous.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Breaked.artistState()));
	}
	
	@Test
	public final void testFamousState(){
		final Set<ArtistState> states = simmulateLifeCycle(ArtistStateFactory.Famous.artistState());
		
		Assert.assertEquals(3, states.size());
		Assert.assertTrue(states.contains(ArtistStateFactory.Famous.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Inmortal.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Breaked.artistState()));
	}
	
	@Test
	public final void testBreakedState(){
		final Set<ArtistState> states = simmulateLifeCycle(ArtistStateFactory.Breaked.artistState());
		
		Assert.assertEquals(3, states.size());
		Assert.assertTrue(states.contains(ArtistStateFactory.Hot.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Breaked.artistState()));
		Assert.assertTrue(states.contains(ArtistStateFactory.Out.artistState()));

	}
	
	@Test(expected=IllegalStateException.class)
	public final void testInmortalState() {
		ArtistStateFactory.Inmortal.artistState().continueLifecycle();
	}
	
	@Test(expected=IllegalStateException.class)
	public final void testOutState() {
		ArtistStateFactory.Out.artistState().continueLifecycle();
	}
	
	
	
	

	private Set<ArtistState> simmulateLifeCycle(final ArtistState state) {
		final Set<ArtistState> states = new HashSet<ArtistState>();
		for(int i=0; i < 50; i++){
			
			states.add(state.continueLifecycle());
		}
		return states;
	}
	

}
