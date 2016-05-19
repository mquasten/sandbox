package de.mq.pattern.di.state;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("/beans.xml")
public class StupidGirls {

	static final String START_STATE_NAME = "unkownArtist";


	public static void main(final String[] args) {
		lifecycle(SpringApplication.run(StupidGirls.class, args), new HashMap<String, Integer>());

	}

	static void lifecycle(final ApplicationContext ctx,  final Map<String, Integer> states) {
		ArtistState artistState = ctx.getBean(START_STATE_NAME, ArtistState.class);

		
		double amount = 0;
		while (!artistState.isFinal()) {
			incStateInMap(artistState, states);
			amount += artistState.amount();
			artistState = artistState.continueLifecycle();

		}

		states.keySet().forEach(name -> System.out.println(name + ": " + states.get(name)));

		final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		System.out.println("*****************************");
		System.out.println("final state: " + artistState.name());
		System.out.println("salery:      " + nf.format(amount));
		System.out.println("*****************************");
	}

	private static void incStateInMap(ArtistState artistState, final Map<String, Integer> states) {
		String name = artistState.name();
		if (!states.containsKey(name)) {
			states.put(name, 0);
		}
		states.put(name, states.get(name) + 1);
	}

}
