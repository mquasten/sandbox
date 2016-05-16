package de.mq.pattern.di.state.support;

import de.mq.pattern.di.state.ArtistState;

class FinalArtistStateImpl extends AbstractArtistState {

	FinalArtistStateImpl(final String name) {
		super(name);
	}

	@Override
	final boolean finalState() {
		return true;
	}

	@Override
	public final ArtistState continueLifecycle() {
		throw new IllegalStateException("Method should not be called at a final state");
	}

	@Override
	final double rate() {
		return 0;
	}

}
