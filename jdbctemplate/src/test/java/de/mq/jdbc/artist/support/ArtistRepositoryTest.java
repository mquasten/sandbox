package de.mq.jdbc.artist.support;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
@Disabled
class ArtistRepositoryTest {
	
	@Autowired
	private ArtistRepositoryImpl artistRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher ;
	
	@Test
	void insert() {
		System.out.println(artistRepository);
		final long t1 = System.currentTimeMillis();
		IntStream.range(0, 1000).forEach( i -> {
		final Artist artist = new Artist();
		artist.setFirstname("Kylie");
		artist.setLastname("Minogue");
		artist.setId(Long.valueOf(i));
		artist.setHotscore(10);
		artistRepository.persist(artist);
		
		});
		
		long t2 = System.currentTimeMillis();
		
		System.out.println(t2 -t1);
	}

	@Test
	void insertBatch() {
		
		final long t1 = System.currentTimeMillis();
		List<Artist> artists = new ArrayList<>();
		IntStream.range(0, 1000).forEach( i -> {
			final Artist artist = new Artist();
			artist.setFirstname("Kylie");
			artist.setLastname("Minogue");
			artist.setId(Long.valueOf(i));
			artist.setHotscore(10);
			
			artists.add(artist);
			});
		
		
		artistRepository.persist2(artists);
		
		long t2 = System.currentTimeMillis();
		
		System.out.println(t2 -t1);
	}
	
	@Test
	void publish() throws InterruptedException {
		
		IntStream.range(0, 10).forEach(i -> {
		final Artist artist = new Artist();
		artist.setFirstname("Kylie");
		artist.setLastname("Minogue");
		artist.setId(Long.valueOf(i));
		artist.setHotscore(10);
		
		publisher.publishEvent(artist);
		});
		//Thread.sleep(5000);
	}

}
