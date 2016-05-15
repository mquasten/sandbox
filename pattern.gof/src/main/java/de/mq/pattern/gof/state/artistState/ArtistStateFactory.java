package de.mq.pattern.gof.state.artistState;

import de.mq.pattern.gof.state.checker.Checker;


enum ArtistStateFactory  {
	
	
	Unkown(new UnknownArtistStateImpl(Checker.Checkers.RandomChecker.checker())),
	Blessed(new BlessedArtistStateImpl(Checker.Checkers.RandomChecker.checker())),
	Hot(new HotArtistStateImpl(Checker.Checkers.RandomChecker.checker())),
	Breaked(new BreakedArtistStateImpl(Checker.Checkers.RandomChecker.checker())),
	Out(new OutArtistStateImpl(Checker.Checkers.StayOnStateChecker.checker())),
	Famous(new FamousArtistStateImpl(Checker.Checkers.RandomChecker.checker())),
	Inmortal(new InmortalArtistStateImpl(Checker.Checkers.StayOnStateChecker.checker())); 
	
	
	private final AbstractArtistState artistStateProcessor;

	
	ArtistStateFactory(final AbstractArtistState artistState) {
		this.artistStateProcessor=artistState;
	}
	
	public final AbstractArtistState artistState(){
		return artistStateProcessor;
		
	}
}
