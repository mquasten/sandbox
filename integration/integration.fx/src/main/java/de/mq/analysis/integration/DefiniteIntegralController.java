package de.mq.analysis.integration;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


class DefiniteIntegralController  implements Initializable{
	
	@FXML
	private Button integrationButton; 
	
	@FXML
	private Button closeButton; 

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		integrationButton.setOnAction(this::integration );
		
	
		closeButton.setOnAction(actionEvent -> ((Stage) ((Node)actionEvent.getSource()).getScene().getWindow()).close() );
		
		
	}
	
	private void integration(final ActionEvent actionEvent) {
		System.out.println("integral berechnen...");
		
	
	 
	    

	}

}
