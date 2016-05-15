package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

class BlessedArtistStateImpl  extends AbstractArtistState{


	BlessedArtistStateImpl(final Checker checker) {
		super(checker);
	}

	@Override
	protected final void setupTransitions() {
		assignTransition(CheckResult.Stay, ArtistStateFactory.Blessed.artistState());
		assignTransition(CheckResult.Failed, ArtistStateFactory.Out.artistState());
		assignTransition(CheckResult.Success, ArtistStateFactory.Hot.artistState());
	}

	@Override
	protected final double amount() {
		return 10e3;
	}

	@Override
	public final String name() {
		return "blessed";
	}

	
	
	

}
