package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


import org.springframework.util.StringUtils;

import de.mq.analysis.integration.IntegrationService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


class DefiniteIntegralFX   implements Initializable, Observer {
	
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
	private ChoiceBox<IntegrationService.CalculationAlgorithm> algorithms;
	
	@FXML
	private TextField result;
	
	@FXML
	private Label resultLabel;
	
	private final DefiniteIntegralController definiteIntegralController; 
	
	private final DefiniteIntegralAO definiteIntegralAO = new DefiniteIntegralAO();

	DefiniteIntegralFX(DefiniteIntegralController definiteIntegralController) {
		this.definiteIntegralController = definiteIntegralController;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		
		definiteIntegralAO.addObserver(this);
		algorithms.setItems(FXCollections.observableArrayList(IntegrationService.CalculationAlgorithm.values()));
		
		lowerLimit.textProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setLowerLimit(null);
			if( ! validateDouble(newValue) ) {
				lowerLimitMessage.setText("reele Zahl");;
			} else {
				definiteIntegralAO.setLowerLimit(Double.valueOf(newValue)); 
				lowerLimitMessage.setText(null);
			}
			
		});
		
		upperLimit.textProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setUpperLimit(null);
			if( ! validateDouble(newValue) ) {
				upperLimitMessage.setText("reele Zahl");;
			} else {
				definiteIntegralAO.setUpperLimit(Double.valueOf(newValue)); 
				upperLimitMessage.setText(null);
			}
			
		});
		
		algorithms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setCalculationAlgorithm(null);
			if ( newValue == null) {
				algorithmenMessage.setText("Mußfeld");
			} else {	
				algorithmenMessage.setText(null);
				definiteIntegralAO.setCalculationAlgorithm(newValue);
			}
			
		});
		
		lowerLimit.setText(null);
		upperLimit.setText(null);
		algorithms.setValue(IntegrationService.CalculationAlgorithm.Simpson);
		algorithms.setValue(null);
		integrationButton.setOnAction(actionEvent -> {
			
			result.setVisible(false);
			resultLabel.setVisible(false);
		
			
			if( definiteIntegralAO.validate() ) {
					definiteIntegralController.integrate(definiteIntegralAO);
			}
			
		
			
		} );
		closeButton.setOnAction(actionEvent -> ((Stage) ((Node)actionEvent.getSource()).getScene().getWindow()).close() );
	}
	
	
	
	private boolean validateDouble(final String text ) {
	
		if( ! StringUtils.hasText(text)) {
			return false;
		}
		
		try {
		Double.valueOf(text);
		return true; 
		} catch ( NumberFormatException nfe){
			return false; 
		}
		

	}

	@Override
	public void update(final Observable o, Object arg) {
		result.setText(String.valueOf(definiteIntegralAO.getResult()));
		result.setVisible(true);
		resultLabel.setVisible(true);
	}
	
	


}
