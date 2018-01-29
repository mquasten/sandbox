package de.mq.analysis.integration.support;

import java.io.IOException;
import java.net.URL;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DefiniteIntegrationApplication extends Application {

	private static ConfigurableApplicationContext applicationContext; 
	
	@Override
	public void start(final Stage stage) throws IOException {

	
		final URL url = getClass().getResource("/definiteIntegral.fxml");
	
			final FXMLLoader formLoader = new FXMLLoader(url);
	
			formLoader.setController(getDefiniteIntegralFX() );
			Parent parent = formLoader.load();
			stage.setTitle("numerische Integration");
			stage.setScene(new Scene(parent));
			stage.show();

	}

	public static void main(final String[] args) {
		applicationContext=	new AnnotationConfigApplicationContext(DefiniteIntegralConfiguration.class);
		launch(args);
	}

	private  DefiniteIntegralFX  getDefiniteIntegralFX() {
		return applicationContext.getBean(DefiniteIntegralFX.class);
	}
	
	@Override
	  public void stop() throws Exception {
		  applicationContext.close();
	  }

}
