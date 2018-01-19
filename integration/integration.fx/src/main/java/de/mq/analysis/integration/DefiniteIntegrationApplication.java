package de.mq.analysis.integration;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DefiniteIntegrationApplication extends Application {

	@Override
	public void start(final Stage stage) {
		try {
			stage.setTitle("Nummerische Integration");

			final GridPane root = new GridPane();
			final GridPane grid = new GridPane();
			grid.setPadding(new Insets(10, 10, 10, 10));
			grid.setVgap(5);
			grid.setHgap(5);

			final Scene scene = new Scene(root, 400, 400);

			final TextField lowerLimit = new TextField();
			final Label lbLowerLimit = new Label("untere Grenze:");

			final TextField upperLimit = new TextField();
			final Label lbUpperLimit = new Label("obere Grenze:");

			final Button calculate = new Button();
			final Button close = new Button();
			close.setText("beenden");

			close.setOnAction(e -> stage.close());
			calculate.setOnAction(e -> {

				System.out.println(lowerLimit.getText());
				System.out.println(upperLimit.getText());

			});
			calculate.setText("berechnen");

			GridPane.setConstraints(lbLowerLimit, 0, 0);
			GridPane.setConstraints(lowerLimit, 1, 0);

			GridPane.setConstraints(lbUpperLimit, 0, 1);
			GridPane.setConstraints(upperLimit, 1, 1);

			grid.getChildren().add(lbLowerLimit);
			grid.getChildren().add(lowerLimit);
			grid.getChildren().add(lbUpperLimit);
			grid.getChildren().add(upperLimit);

			final HBox hbox = new HBox();
			hbox.setPadding(new Insets(15, 12, 15, 12));
			hbox.setSpacing(10);

			hbox.getChildren().addAll(close, calculate);

			root.getChildren().add(grid);
			root.getChildren().add(hbox);

			GridPane.setConstraints(grid, 0, 0);
			GridPane.setConstraints(hbox, 0, 1);

			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(final String[] args) {
		launch(args);
	}

}
