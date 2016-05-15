package de.mq.pattern.gof.state.artistState;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.state.artistState.AbstractArtistState;
import de.mq.pattern.gof.state.artistState.ArtistState;
import de.mq.pattern.gof.state.artistState.ArtistStateFactory;
import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;
import de.mq.pattern.gof.state.checker.Checker.Checkers;

public class StateTest {
	
	private final Checker stayOnStateChecker = Checkers.StayOnStateChecker.checker();
	private final Checker successChecker =  new Checker(){

		@Override
		public final CheckResult checkResult() {
			
			return CheckResult.Success;
		} };
	
		private final Checker failedChecker =  new Checker(){

			@Override
			public final CheckResult checkResult() {
				
				return CheckResult.Failed;
			} };
			
	@Test		
	public final void testUnkownState() {
		final AbstractArtistState state = ArtistStateFactory.Unkown.artistState();
		
		Assert.assertEquals(0d, state.amount());
		Assert.assertEquals("unkown" , state.name());
		Assert.assertFalse(state.isFinal());
		
      assignChecker(state, stayOnStateChecker);
		Assert.assertEquals(ArtistStateFactory.Unkown.artistState(),state.continueLifecycle());
		assignChecker(state, failedChecker);
		Assert.assertEquals(ArtistStateFactory.Out.artistState(),state.continueLifecycle());
		assignChecker(state, successChecker);
		Assert.assertEquals(ArtistStateFactory.Blessed.artistState(),state.continueLifecycle());
		
	}
	
	@Test		
	public final void testBlessedState() {
		final AbstractArtistState state = ArtistStateFactory.Blessed.artistState();
		
		Assert.assertEquals(10e3d, state.amount());
		Assert.assertEquals("blessed" , state.name());
		Assert.assertFalse(state.isFinal());
      assignChecker(state, stayOnStateChecker);
		Assert.assertEquals(ArtistStateFactory.Blessed.artistState(),state.continueLifecycle());
		assignChecker(state, failedChecker);
		Assert.assertEquals(ArtistStateFactory.Out.artistState(),state.continueLifecycle());
		assignChecker(state, successChecker);
		Assert.assertEquals(ArtistStateFactory.Hot.artistState(),state.continueLifecycle());
		
	}
	
	@Test		
	public final void testHotState() {
		final AbstractArtistState state = ArtistStateFactory.Hot.artistState();
		Assert.assertEquals(500e3d, state.amount());
		Assert.assertEquals("hot" , state.name());
		Assert.assertFalse(state.isFinal());
		
      assignChecker(state, stayOnStateChecker);
		Assert.assertEquals(ArtistStateFactory.Hot.artistState(),state.continueLifecycle());
		assignChecker(state, failedChecker);
		Assert.assertEquals(ArtistStateFactory.Breaked.artistState(),state.continueLifecycle());
		assignChecker(state, successChecker);
		Assert.assertEquals(ArtistStateFactory.Famous.artistState(),state.continueLifecycle());
		
	}
	
	@Test		
	public final void testFamousState() {
		final AbstractArtistState state = ArtistStateFactory.Famous.artistState();
		
		Assert.assertEquals(10e6, state.amount());
		Assert.assertEquals("famous" , state.name());
		Assert.assertFalse(state.isFinal());
      assignChecker(state, stayOnStateChecker);
		Assert.assertEquals(ArtistStateFactory.Famous.artistState(),state.continueLifecycle());
		assignChecker(state, failedChecker);
		Assert.assertEquals(ArtistStateFactory.Breaked.artistState(),state.continueLifecycle());
		assignChecker(state, successChecker);
		Assert.assertEquals(ArtistStateFactory.Inmortal.artistState(),state.continueLifecycle());
		
	}
	
	@Test		
	public final void testBreakedState() {
		final AbstractArtistState state = ArtistStateFactory.Breaked.artistState();
		Assert.assertEquals(0d, state.amount());
		Assert.assertEquals("breaked" , state.name());
		Assert.assertFalse(state.isFinal());
      assignChecker(state, stayOnStateChecker);
		Assert.assertEquals(ArtistStateFactory.Breaked.artistState(),state.continueLifecycle());
		assignChecker(state, failedChecker);
		Assert.assertEquals(ArtistStateFactory.Out.artistState(),state.continueLifecycle());
		assignChecker(state, successChecker);
		Assert.assertEquals(ArtistStateFactory.Hot.artistState(),state.continueLifecycle());
		
	}
	
	@Test(expected=IllegalStateException.class)		
	public final void testOutState() {
		final AbstractArtistState state = ArtistStateFactory.Out.artistState();
		Assert.assertEquals(0d, state.amount());
		Assert.assertEquals("out" , state.name());
		Assert.assertTrue(state.isFinal());
		state.continueLifecycle();
	}
	
	@Test(expected=IllegalStateException.class)		
	public final void testInmortalState() {
		final AbstractArtistState state = ArtistStateFactory.Inmortal.artistState();
		Assert.assertEquals(0d, state.amount());
		Assert.assertEquals("inmortal" , state.name());
		Assert.assertTrue(state.isFinal());
		state.continueLifecycle();
	}
	
	@Test
	public final void testSuccessLifecycle() {
		ArtistState state = ArtistStateFactory.Unkown.artistState();
		assignChecker(state, successChecker);
		int i=0;
		
		while ( ! state.isFinal() ){
			
			state=state.continueLifecycle();
			assignChecker(state, successChecker);
			i++;
			if( i > 4){
				Assert.fail("To mutch iterations:" + i );
			}
		}
		Assert.assertEquals(1.051E7, ((AbstractArtistState)state).salery());
		Assert.assertEquals(ArtistStateFactory.Inmortal.artistState(), state);
	
		
	}
	

	private void assignChecker(ArtistState state, Checker checker	)  {
		
		try {
			final Field field = state.getClass().getSuperclass().getDeclaredField("checker");
			field.setAccessible(true);
		   field.set(state, checker);
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to set checker" , ex);
		} 
	}
		

}
