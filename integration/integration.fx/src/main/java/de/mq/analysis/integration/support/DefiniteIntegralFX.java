package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.ResourceBundle;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
	private Label lowerLimitMessage;
	
	@FXML
	private TextField upperLimit;
	@FXML
	private Label upperLimitMessage;
	@FXML
	private Label errorMessage; 
	
	@FXML
	private Label algorithmenMessage;
	
	@FXML
	private ChoiceBox<CalculationAlgorithm> algorithms;
	
	@FXML
	private TextField result;
	
	@FXML
	private Label resultLabel;
	
	private final DefiniteIntegralController definiteIntegralController; 

	DefiniteIntegralFX(DefiniteIntegralController definiteIntegralController) {
		this.definiteIntegralController = definiteIntegralController;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		
		algorithms.setItems(FXCollections.observableArrayList(CalculationAlgorithm.values()));
		
		integrationButton.setOnAction(actionEvent -> {
			
			result.setVisible(false);
			resultLabel.setVisible(false);
			
			if( !validate()) {
				return;
			}
			
			final BoundsOfIntegration boundsOfIntegration = BoundsOfIntegration.of(Double.valueOf(lowerLimit.getText()), Double.valueOf(upperLimit.getText()));
			final DefiniteIntegral definiteIntegral= new  DefiniteIntegralImpl(boundsOfIntegration, new RealFunctionImpl(), algorithms.getValue(), 100000);
			final double definiteIntegralResult = definiteIntegralController.integrate(definiteIntegral);
			
			
			result.setText(String.valueOf(definiteIntegralResult));
			result.setVisible(true);
			resultLabel.setVisible(true);
		
			
		} );
		closeButton.setOnAction(actionEvent -> ((Stage) ((Node)actionEvent.getSource()).getScene().getWindow()).close() );
	}
	
	
	private boolean validate() {
		boolean result = true;
		lowerLimitMessage.setText("");
		upperLimitMessage.setText("");
		algorithmenMessage.setText("");
		if(! validateDouble(lowerLimit.getText())) {
			result=false;
			lowerLimitMessage.setText("reele Zahl");;
		}
		
		if( ! validateDouble(upperLimit.getText())) {
			result=false;
			upperLimitMessage.setText("reele Zahl");;
		}
		
		if ( algorithms.getValue() == null) {
			result=false;
			algorithmenMessage.setText("Mußfeld");
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
