package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

class HotArtistStateImpl  extends AbstractArtistState{


	protected HotArtistStateImpl(final Checker checker) {
		super(checker);
	}

	@Override
	protected final void setupTransitions() {
		assignTransition(CheckResult.Stay, ArtistStateFactory.Hot.artistState());
		assignTransition(CheckResult.Failed, ArtistStateFactory.Breaked.artistState());
		assignTransition(CheckResult.Success, ArtistStateFactory.Famous.artistState());
	}

	@Override
	protected final double amount() {
		return 500e3;
	}

	@Override
	public final String name() {
		return "hot";
	}
	
	

}
