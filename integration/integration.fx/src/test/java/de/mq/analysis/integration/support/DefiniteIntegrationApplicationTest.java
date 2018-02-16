package de.mq.analysis.integration.support;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.stage.Stage;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {TestConfiguration.class})
public class DefiniteIntegrationApplicationTest {
	
	final Parent parent = Mockito.mock(Parent.class);
	
	DefiniteIntegrationApplication definiteIntegrationApplication = Mockito.mock(DefiniteIntegrationApplication.class);
	
	
	
	
	




	@Test
	public final void main() throws IOException {
		//Mockito.doNothing().when(definiteIntegrationApplication).start(Mockito.any());
		//Mockito.doNothing().when(definiteIntegrationApplication).launch(Mockito.anyVararg());
	
	}

}

@Configuration
class TestConfiguration {
	private static Parent parent;
	
	Parent definiteIntegralParent() {
		return parent;
	}

}