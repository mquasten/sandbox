package de.mq.pattern.di.state;

public interface ArtistState {

	ArtistState continueLifecycle();

	boolean isFinal();

	double amount();

	String name();

}