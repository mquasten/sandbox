package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

class OutArtistStateImpl  extends AbstractArtistState{


	protected OutArtistStateImpl(final Checker checker) {
		super(checker);
	}

	@Override
	protected final void setupTransitions() {
		assignTransition(CheckResult.Stay, ArtistStateFactory.Out.artistState());
	}

	@Override
	public final boolean isFinal() {
		return true;
	}

	@Override
	public final String name() {
		return "out";
	}

	
	
	
	

}
