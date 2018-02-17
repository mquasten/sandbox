package de.mq.analysis.integration.support;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DefiniteIntegrationApplication extends Application {

	private static Class<DefiniteIntegralConfiguration> springConfigurationClass = DefiniteIntegralConfiguration.class;

	static final String TITLE = "numerische Integration";

	static final String DEFINITE_INTEGRAL_PARENT_BEAN = "definiteIntegralParent";

	private static ConfigurableApplicationContext applicationContext;

	private static Class<? extends Application> applicationClass = DefiniteIntegrationApplication.class;

	@Override
	public void start(final Stage stage) throws IOException {
		stage.setTitle(TITLE);
		stage.setScene(newScene(getDefiniteIntegralParent()));
		stage.show();

	}

	Scene newScene(Parent parent) {
		return new Scene(parent);
	}

	public static void main(final String[] args) {
		applicationContext = new AnnotationConfigApplicationContext(springConfigurationClass);
		launch(applicationClass, args);
	}

	private Parent getDefiniteIntegralParent() {
		return applicationContext.getBean(DEFINITE_INTEGRAL_PARENT_BEAN, Parent.class);
	}

	@Override
	public void stop() throws Exception {
		applicationContext.close();
	}

}
