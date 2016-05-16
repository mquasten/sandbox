package de.mq.pattern.di.state.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/beans.xml" })
public class ArtistContestIntegrationTest {
	
	@Autowired
	private Checker checker;
	
	@Test
	public final void test() {
		System.out.println("****");
		System.out.println(checker);
	}

}
