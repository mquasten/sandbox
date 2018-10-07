package de.mq.jdbc.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})

class MetaDataRepositoryTest {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private NamedParameterJdbcOperations jdbcOperationsBeihilfe; 
	
	
	
	private  MetaDataRepositoryImpl metaDataRepository ;
	
	
	@BeforeEach
	void setup() {
		metaDataRepository = new MetaDataRepositoryImpl(jdbcOperationsBeihilfe.getJdbcOperations());
	
	}
	@Test
	@Disabled
	final void artist() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("artist"));
	
	}
	
	@Test
	@Disabled
	final void aristInsert() throws Exception {
		System.out.println(metaDataRepository.insertFor("artist"));
	
	}
	
	@Test
	@Disabled
	final void video() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("video"));
	
	}
	
	@Test
	@Disabled
	final void videoInsert() throws Exception {
		System.out.println(metaDataRepository.insertFor("video"));
	
	}
	
	@Test
	@Disabled
	final void award() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("award"));
	
	}
	
	@Test
	@Disabled
	final void awardInsert() throws Exception {
		System.out.println(metaDataRepository.insertFor("award"));
	
	}
	
	
}
