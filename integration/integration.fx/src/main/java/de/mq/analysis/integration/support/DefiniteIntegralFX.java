package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.ResourceBundle;

import de.mq.analysis.integration.BoundsOfIntegration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


class DefiniteIntegralFX  implements Initializable{
	
	@FXML
	private Button integrationButton; 
	
	@FXML
	private Button closeButton; 
	
	@FXML
	private TextField lowerLimit;
	
	@FXML
	private TextField upperLimit;
	
	private final DefiniteIntegralController definiteIntegralController; 

	DefiniteIntegralFX(DefiniteIntegralController definiteIntegralController) {
		this.definiteIntegralController = definiteIntegralController;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		integrationButton.setOnAction(actionEvent -> {
			
			if( !validate()) {
				return;
			}
			
			final BoundsOfIntegration boundsOfIntegration = BoundsOfIntegration.of(Double.valueOf(lowerLimit.getText()), Double.valueOf(upperLimit.getText()));
			final double result = definiteIntegralController.integrate(boundsOfIntegration);
			
			System.out.println(result);
			
		} );
		closeButton.setOnAction(actionEvent -> ((Stage) ((Node)actionEvent.getSource()).getScene().getWindow()).close() );
	}
	
	
	private boolean validate() {
		boolean result = true;
		if(! validateDouble(lowerLimit.getText())) {
			result=false;
			
		}
		
		if( ! validateDouble(upperLimit.getText())) {
			result=false;
		}
				
		return result;	
			
	}
	
	private boolean validateDouble(final String text ) {
	
		try {
		Double.valueOf(text);
		return true; 
		} catch ( NumberFormatException nfe){
			return false; 
		}
		

	}

}
