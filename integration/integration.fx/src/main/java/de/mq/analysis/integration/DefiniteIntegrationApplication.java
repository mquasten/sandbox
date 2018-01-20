package de.mq.analysis.integration;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DefiniteIntegrationApplication extends Application {

	@Override
	public void start(final Stage stage) throws IOException {
	
	
		final URL url = getClass().getResource("/definiteIntegral.fxml");
	
			final FXMLLoader formLoader = new FXMLLoader(url);
	
			formLoader.setController(new DefiniteIntegralController() );
			Parent parent = formLoader.load();
			stage.setTitle("numerische Integration");
			stage.setScene(new Scene(parent));
			stage.show();

	}

	public static void main(final String[] args) {
		launch(args);
	}

}
