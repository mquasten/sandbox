package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.util.ReflectionTestUtils;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Application.class})
public class DefiniteIntegrationApplicationTest {
	
	
	final static  Parent PARENT = Mockito.mock(Parent.class); 	
	
	final static Message MESSAGE = Mockito.mock(Message.class);
	
	
	@Before
	public final  void setup() {
		PowerMockito.mockStatic(Application.class);		
		ReflectionTestUtils.setField(DefiniteIntegrationApplication.class, "springConfigurationClass", TestConfiguration.class);
	}
	




	@Test
	public final void main() throws Exception {
		DefiniteIntegrationApplication.main(null);
		
		final ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) DataAccessUtils.requiredSingleResult(Arrays.asList(DefiniteIntegrationApplication.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(ConfigurableApplicationContext.class)).map(field -> ReflectionTestUtils.getField(DefiniteIntegrationApplication.class, field.getName())).collect(Collectors.toList()));
		final Parent parent =  applicationContext.getBean(DefiniteIntegrationApplication.DEFINITE_INTEGRAL_PARENT_BEAN, Parent.class); 
		assertEquals(PARENT, parent);
	     
	}

	

	@Test
	public final void start() throws Exception {
		final Scene scene = Mockito.mock(Scene.class);
		final Stage stage = Mockito.mock(Stage.class);
		final DefiniteIntegrationApplication application = PowerMockito.mock(DefiniteIntegrationApplication.class);
		PowerMockito.when(application.newScene(Mockito.any())).thenReturn(scene);
		PowerMockito.doCallRealMethod().when(application).start(stage);;
		
		application.start(stage);
		
		Mockito.verify(stage).setScene(scene);
		Mockito.verify(stage).show();
		Mockito.verify(MESSAGE).notifyObservers(Message.Screne.DefiniteIntegral);;
	}
	
	@Test
	public final void close() throws Exception {
		final DefiniteIntegrationApplication application = new DefiniteIntegrationApplication();
		final ConfigurableApplicationContext applicationContext = Mockito.mock(ConfigurableApplicationContext.class);
		ReflectionTestUtils.setField(DefiniteIntegrationApplication.class, "applicationContext", applicationContext);
		
		application.stop();
		
		Mockito.verify(applicationContext).close();
	}
	

}

@Configuration()
class TestConfiguration {

	
	@Bean
	Parent definiteIntegralParent() {
		return DefiniteIntegrationApplicationTest.PARENT;
	}
	
	@Bean
	Message message() {
		return DefiniteIntegrationApplicationTest.MESSAGE;
	}
	
	
}



