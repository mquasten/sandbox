package de.mq.pattern.di.state.support;

import de.mq.pattern.di.state.ArtistState;

abstract class AbstractArtistState implements ArtistState {

	private final String name;

	AbstractArtistState(final String name) {
		this.name = name;

	}

	abstract boolean finalState();

	@Override
	public final boolean isFinal() {
		return finalState();
	}

	abstract double rate();

	public final double amount() {
		return rate();
	}

	@Override
	public final String name() {
		return name;
	}

}
