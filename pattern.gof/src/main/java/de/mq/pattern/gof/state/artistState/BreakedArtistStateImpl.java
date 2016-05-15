package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

class BreakedArtistStateImpl  extends AbstractArtistState{


	protected BreakedArtistStateImpl(final Checker checker) {
		super(checker);
	}

	@Override
	protected final void setupTransitions() {
		assignTransition(CheckResult.Stay, ArtistStateFactory.Breaked.artistState());
		assignTransition(CheckResult.Failed, ArtistStateFactory.Out.artistState());
		assignTransition(CheckResult.Success, ArtistStateFactory.Hot.artistState());
	}

	@Override
	public final String name() {
		return "breaked";
	}

	
	
	

}
