package de.mq.jdbc.util.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import de.mq.jdbc.artist.support.Artist;
import de.mq.jdbc.artist.support.Award;
import de.mq.jdbc.artist.support.Video;
import de.mq.jdbc.util.Persistence;

class UnitOfWorkTest {
	
	
	private static final String AWARD_NAME_PREFIX = "award";
	private static final String NAME_FIELDNAME = "name";
	private static final String VIDEO_NAME_PREFIX = "video";
	private static final String LASTNAME_FIELDNAME = "lastname";
	private static final String ID_FIELDNAME = "id";
	private static final String ARTISTNAME_PREFIX = "artist";

	
	final Map<Long, Optional<Entry<List<Long>, List<PersistenceInfo>>>  >	batchUpdates = new HashMap<>();
	final UnitOfWork<Long> unitOfWork = new UnitOfWork<>(10);
	
	@BeforeEach
	void setup() {
		IntStream.range(0, 12).forEach(i -> {
			
			long id = 1+ Long.valueOf(i);
			
			final Artist artist = new Artist();
			artist.setLastname(ARTISTNAME_PREFIX +id );
			
			artist.setId(id);
			
			final Video video = new Video();
			video.setId(id);
			video.setName(VIDEO_NAME_PREFIX +id );
			
			final Award award = new Award();
			award.setName(AWARD_NAME_PREFIX +id );
			award.setId(id);
			
			batchUpdates.put(id, unitOfWork.add(id, Arrays.asList(artist, video, award)));
		});
	}
	
	
	@Test
	void add() {
	
		assertEquals(12, batchUpdates.size());
		final Collection<Optional<Entry<List<Long>, List<PersistenceInfo>>>>  emptyResults =  batchUpdates.entrySet().stream().filter(entry -> entry.getKey() != 10 ).map(entry -> entry.getValue()).distinct().collect(Collectors.toSet());
	    assertEquals(1, emptyResults.size());
	    assertEquals(Optional.empty(), emptyResults.stream().findFirst().get());
	    
	    final Entry<List<Long>, List<PersistenceInfo>>  persistentResult = batchUpdates.get(10l).get();
	    
	    
	 
	    assertEquals(10, persistentResult.getKey().size());
	    assertEquals(  LongStream.range(1, 11).mapToObj(i -> Long.valueOf(i)).collect(Collectors.toList()), persistentResult.getKey());
	    
	    final List<PersistenceInfo>  persistenceInfos = persistentResult.getValue();
	    
	    assertEquals(3, persistenceInfos.size());
	    
	    final PersistenceInfo persistenceInfoArtsists =  persistenceInfos.get(0);
	    assertEquals(Artist.class.getAnnotation(Persistence.class).value(), persistenceInfoArtsists.getInsertSql());
	    assertEquals(0, persistenceInfoArtsists.getOrder());
	    
	    final SqlParameterSource[]   sqlParameterSourceArtsist = persistenceInfoArtsists.getSqlParameterSource().get();
	    assertEquals(10, sqlParameterSourceArtsist.length);
	    
	    IntStream.range(0, 10).forEach(i -> assertArtistSource(sqlParameterSourceArtsist, i));
	    
	    final PersistenceInfo persistenceInfoVideos =  persistenceInfos.get(1);
	    assertEquals(Video.class.getAnnotation(Persistence.class).value(), persistenceInfoVideos.getInsertSql());
	    assertEquals(1, persistenceInfoVideos.getOrder());
	    
	    final SqlParameterSource[]   sqlParameterSourceVideo = persistenceInfoVideos.getSqlParameterSource().get();
	    assertEquals(10, sqlParameterSourceVideo.length);
	    
	    IntStream.range(0, 10).forEach(i -> assertVideoSource(sqlParameterSourceVideo, i));
	    
	    
	    final PersistenceInfo persistenceInfoAwards =  persistenceInfos.get(2);
	    assertEquals(Award.class.getAnnotation(Persistence.class).value(), persistenceInfoAwards.getInsertSql());
	    assertEquals(2, persistenceInfoAwards.getOrder());
	    
	    final SqlParameterSource[]   sqlParameterSourceAward = persistenceInfoAwards.getSqlParameterSource().get();
	    assertEquals(10, sqlParameterSourceAward.length);
	    
	    IntStream.range(0, 10).forEach(i -> assertAwardSource(sqlParameterSourceAward, i));
		
	}

	

	private void assertArtistSource(final SqlParameterSource[] sqlParameterSourceArtsist, final int i) {
		assertEquals(ARTISTNAME_PREFIX + (1 +i) , sqlParameterSourceArtsist[i].getValue(LASTNAME_FIELDNAME));
		assertEquals( Long.valueOf(1+i),  sqlParameterSourceArtsist[i].getValue(ID_FIELDNAME));
	}
	
	private void assertVideoSource(final SqlParameterSource[] sqlParameterSourceVideo, final int i) {
		assertEquals(VIDEO_NAME_PREFIX + (1 +i) , sqlParameterSourceVideo[i].getValue(NAME_FIELDNAME));
		assertEquals( Long.valueOf(1+i),  sqlParameterSourceVideo[i].getValue(ID_FIELDNAME));
	
	}
	
	private void assertAwardSource(final SqlParameterSource[] sqlParameterSourceAward, final int i) {
		assertEquals(AWARD_NAME_PREFIX + (1 +i) , sqlParameterSourceAward[i].getValue(NAME_FIELDNAME));
		assertEquals( Long.valueOf(1+i),  sqlParameterSourceAward[i].getValue(ID_FIELDNAME));
	}

}
