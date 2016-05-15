package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

class UnknownArtistStateImpl  extends AbstractArtistState{


	protected UnknownArtistStateImpl(final Checker checker) {
		super(checker);
	}

	@Override
	protected final void setupTransitions() {
		assignTransition(CheckResult.Stay, ArtistStateFactory.Unkown.artistState());
		assignTransition(CheckResult.Failed, ArtistStateFactory.Out.artistState());
		assignTransition(CheckResult.Success, ArtistStateFactory.Blessed.artistState());
	}

	@Override
	public String name() {
		return "unkown";
	}

	
	
	

}
