package de.mq.jdbc.util.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import de.mq.jdbc.artist.support.Artist;

class PersistenceInfoTest {
	
	private static final String INSERT_SQL = "insert into...";

	@Test
	void insertSql() {
		final PersistenceInfo persistenceInfo = new PersistenceInfo(Artist.class);
		assertNull(persistenceInfo.getInsertSql());
		persistenceInfo.setInsertSql(INSERT_SQL);
		assertEquals(INSERT_SQL, persistenceInfo.getInsertSql());
	}
	
	@Test
	void hash() {
		assertEquals(Artist.class.hashCode(), new PersistenceInfo(Artist.class).hashCode());
	}
	
	
	@Test
	void  equals() {
		assertTrue(new PersistenceInfo(Artist.class).equals(new PersistenceInfo(Artist.class)));
		assertFalse(new PersistenceInfo(Artist.class).equals(new PersistenceInfo(Date.class)));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void  equalsWrongClass() {
		assertFalse(new PersistenceInfo(Artist.class).equals(Artist.class));
	}
	
	@Test
	void sqlParameterSource() {
		final PersistenceInfo persistenceInfo = new PersistenceInfo(Artist.class);
		assertFalse(persistenceInfo.getSqlParameterSource().isPresent());
		
		final Artist artist = new Artist();
		artist.setFirstname("Kylie");
		artist.setLastname("Minogue");
		artist.setHotscore(10);
		persistenceInfo.setEntities(Arrays.asList(artist));
		assertTrue(persistenceInfo.getSqlParameterSource().isPresent());
		
		assertEquals(1, persistenceInfo.getSqlParameterSource().get().length);
		
		final SqlParameterSource result = persistenceInfo.getSqlParameterSource().get()[0];
		
		assertEquals(artist.getFirstname(), result.getValue("firstname"));
		assertEquals(artist.getLastname(), result.getValue("lastname"));
		assertEquals(artist.getHotscore(), result.getValue("hotscore"));
	}
	
	@Test
	void sqlParameterSourceEmpty() {
		final PersistenceInfo persistenceInfo = new PersistenceInfo(Artist.class);
		
		persistenceInfo.setEntities(null);
		
		assertFalse(persistenceInfo.getSqlParameterSource().isPresent());
		
		persistenceInfo.setEntities(new ArrayList<>());
		
		assertFalse(persistenceInfo.getSqlParameterSource().isPresent());
	}

}
