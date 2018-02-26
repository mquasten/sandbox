package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DefiniteIntegrationApplicationFXTest extends ApplicationTest {

	private final Stage stage = Mockito.mock(Stage.class);
	
	private final Message message = Mockito.mock(Message.class);

	private final Parent parent = new Parent() {
	};

	@Override
	public void start(final Stage primaryStage) throws IOException {
		final DefiniteIntegrationApplication application = Mockito.mock(DefiniteIntegrationApplication.class, Mockito.CALLS_REAL_METHODS);
		final ConfigurableApplicationContext applicationContext = Mockito.mock(ConfigurableApplicationContext.class);
		Mockito.when(applicationContext.getBean(DefiniteIntegrationApplication.DEFINITE_INTEGRAL_PARENT_BEAN, Parent.class)).thenReturn(parent);
		Mockito.when(applicationContext.getBean(Message.class)).thenReturn(message);
		ReflectionTestUtils.setField(DefiniteIntegrationApplication.class, "applicationContext", applicationContext);

		application.start(stage);


	}

	@Before
	public final void setup() {

	}

	@Test
	public final void start() throws IOException {

		final ArgumentCaptor<Scene> argumentCaptor = ArgumentCaptor.forClass(Scene.class);
		Mockito.verify(stage).show();
		Mockito.verify(stage).setScene(argumentCaptor.capture());
		Mockito.verify(message).notifyObserver(Message.SceneType.DefiniteIntegral);
		assertEquals(parent, argumentCaptor.getValue().rootProperty().getValue());

	}

}
