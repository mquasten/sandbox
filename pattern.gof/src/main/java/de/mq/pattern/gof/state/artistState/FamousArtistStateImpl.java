package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

class FamousArtistStateImpl  extends AbstractArtistState{


	protected FamousArtistStateImpl(final Checker checker) {
		super(checker);
	}

	@Override
	protected final void setupTransitions() {
		assignTransition(CheckResult.Stay, ArtistStateFactory.Famous.artistState());
		assignTransition(CheckResult.Failed, ArtistStateFactory.Breaked.artistState());
		assignTransition(CheckResult.Success, ArtistStateFactory.Inmortal.artistState());
	}

	@Override
	protected final double amount() {
		return 10e6;
	}

	@Override
	public final String name() {
		return "famous";
	}
	
	

}
