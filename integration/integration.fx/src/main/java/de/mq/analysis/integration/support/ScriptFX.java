package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@Controller
class ScriptFX implements Initializable{
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Button selectButton;
	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		cancelButton.setOnAction(actionEvent -> cancel(actionEvent));
		selectButton.setOnAction(actionEvent -> {
			cancel(actionEvent);
		});
	}

	protected void cancel(final ActionEvent actionEvent) {
		((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
	}
	
}
