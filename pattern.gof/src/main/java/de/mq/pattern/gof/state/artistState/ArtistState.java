package de.mq.pattern.gof.state.artistState;



public interface ArtistState {

	ArtistState continueLifecycle();

	boolean isFinal();

	double salery();
	
	String name();
	
	public static ArtistState UNKOWN_ARTIST = ArtistStateFactory.Unkown.artistState();

}