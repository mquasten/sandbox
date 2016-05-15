package de.mq.pattern.gof.state.artistState;

import java.util.HashMap;
import java.util.Map;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

abstract class AbstractArtistState implements ArtistState{
	
	
	private final Map<CheckResult,ArtistState> transitions = new HashMap<CheckResult,ArtistState>(); 
	
	private final Checker checker;
	
	private double salery;
	
	
	AbstractArtistState(final Checker checker) {
		this.checker=checker;
		
		
	}
	
	
	public final double salery() {
		return this.salery;
	} 
	
	
	protected abstract void setupTransitions();
	
	
	public abstract String name();
	
	
	protected double amount() {
		return 0d;
	}
	
	public boolean isFinal(){
		return false;
	}
	
	public final ArtistState continueLifecycle(){
		transitions.clear();
		setupTransitions();
		finalStateGuard();
		final CheckResult checkResult = checker.checkResult();
		transitionNotFoundGuard(checkResult);
	
		final AbstractArtistState result = (AbstractArtistState) transitions.get(checkResult);
		
	   result.salery=salery+amount();
		
		return result;
	}


	private void finalStateGuard() {
		if( isFinal()){
			throw new IllegalStateException("method should not be called at a final state");
		}
	}

	private void transitionNotFoundGuard(final CheckResult checkResult) {
		if( ! transitions.containsKey(checkResult)){
			throw new IllegalStateException("No Transistion for " + checkResult.name() + " found: " + getClass() );
		}
	}
	
	protected void assignTransition(CheckResult checkResult, AbstractArtistState artistState){
		transitions.put(checkResult, artistState);
	}

}
