package de.mq.analysis.integration.support;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DefiniteIntegrationApplication extends Application {

	private static ConfigurableApplicationContext applicationContext; 
	
	@Override
	public void start(final Stage stage) throws IOException {

			stage.setTitle("numerische Integration");
			stage.setScene(new Scene( getDefiniteIntegralParent()));
			stage.show();

	}

	public static void main(final String[] args) {
		applicationContext=	new AnnotationConfigApplicationContext(DefiniteIntegralConfiguration.class);
		launch(args);
	}

	private  Parent  getDefiniteIntegralParent() {
		return applicationContext.getBean("definiteIntegralParent", Parent.class);
	}
	
	@Override
	  public void stop() throws Exception {
		  applicationContext.close();
	  }
	
	
	
}
