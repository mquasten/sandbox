package de.mq.analysis.integration.support;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(  classes=DefiniteIntegrationApplicationTest.class)
public class DefiniteIntegrationApplicationTest {
	
	
	
	
	
	
	
	
	




	@Test
	public final void main() throws Exception {
		ReflectionTestUtils.setField(DefiniteIntegrationApplication.class, "applicationClass", TestApplicationDummy.class);
		DefiniteIntegrationApplication.main(null);
		
		final ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) DataAccessUtils.requiredSingleResult(Arrays.asList(DefiniteIntegrationApplication.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(ConfigurableApplicationContext.class)).map(field -> ReflectionTestUtils.getField(DefiniteIntegrationApplication.class, field.getName())).collect(Collectors.toList()));
		
		final Parent parent = applicationContext.getBean(DefiniteIntegrationApplication.DEFINITE_INTEGRAL_PARENT_BEAN, Parent.class) ;
		
		assertNotNull(parent);
		
		
		
		final Stage stage = TestApplicationDummy.stage();
		ArgumentCaptor<Scene> sceneCaptor= ArgumentCaptor.forClass(Scene.class);
		
		Mockito.verify(stage).show();
		Mockito.verify(stage).setScene(sceneCaptor.capture());
		Mockito.verify(stage).setTitle(DefiniteIntegrationApplication.TITLE);
		

		assertNotNull(sceneCaptor.getValue());
		
		
		
		
		
		
		
	}

}

