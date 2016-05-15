package de.mq.pattern.gof.state;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.mq.pattern.gof.state.artistState.ArtistState;



public class StupidGirls {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArtistState artistState = ArtistState.UNKOWN_ARTIST;
		final Map<String, Integer> states = new HashMap<String, Integer>();
		
		while(! artistState.isFinal()) {
			incStateInMap(artistState, states);
			artistState=artistState.continueLifecycle();
		}
	
		for(String name : states.keySet()){
			
			System.out.println( name + ": "  + states.get(name));
		}
		
		System.out.println();
		final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		System.out.println("*****************************");
		System.out.println("final state: " + artistState.name());
		System.out.println("salery:      " + nf.format(artistState.salery()));
		System.out.println("*****************************");

	}

	private static void incStateInMap(ArtistState artistState, final Map<String, Integer> states) {
		String name = artistState.name() ;
		if( ! states.containsKey(name) ){
			states.put(name, 0);
		}
		states.put(name, states.get(name)+1);
	}

}
