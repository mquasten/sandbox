package de.mq.jdbc.artist.support;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import de.mq.jdbc.artist.Artist;

@Component
public class PersistListener {

	@EventListener
	@Async
	void receive(Artist artist) {
		System.out.println(artist);
	}
	
}
