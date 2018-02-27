package de.mq.analysis.integration.support;

import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.mq.analysis.integration.IntegrationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;



@Component
abstract class DefiniteIntegralFX implements Initializable, Observer {

	static final String I18N_DEFINITEINTEGRAL_PREFIX = "definiteintegral_";

	static final String I18N_DEFINITEINTEGRAL_TITLE = I18N_DEFINITEINTEGRAL_PREFIX+"title";

	static final String I18N__MANDATORY = I18N_DEFINITEINTEGRAL_PREFIX+"mandatory";

	static final String I18N_REAL_NUMBER_EXPECTED = I18N_DEFINITEINTEGRAL_PREFIX+"realnumber";

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
	
	@FXML
	private TextField error;

	@FXML
	private Label errorLabel;

	@FXML
	private TextArea code;

	@FXML
	private Label codeMessage;

	@FXML
	private ChoiceBox<Long> samples;

	@FXML
	private Label samplesMessage;

	@FXML
	private Hyperlink script;
	
	
	@FXML
	private Label lowerLimitLabel;
	@FXML
	private Label upperLimitLabel;
	
	@FXML
	private Label algorithmLabel;
	
	@FXML
	private Label samplesLabel;
	

	private final DefiniteIntegralController definiteIntegralController;

	private final DefiniteIntegralAO definiteIntegralAO = new DefiniteIntegralAO();
	private final Message message;

	DefiniteIntegralFX(final DefiniteIntegralController definiteIntegralController, final Message message) {
		this.definiteIntegralController = definiteIntegralController;
		this.message=message;

	}

	@Override
	public final void initialize(final URL location, final ResourceBundle resources) {

		
		definiteIntegralAO.addObserver(this);
		code.setEditable(false);
		algorithms.setItems(FXCollections.observableArrayList(IntegrationService.CalculationAlgorithm.values()));
		samples.setItems(FXCollections.observableArrayList(1000L, 10000L, 100000L, 1000000L));
		lowerLimit.textProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setLowerLimit(null);
			if (!validateDouble(newValue)) {
				lowerLimitMessage.setText(realNumberExpectedMessage());
			} else {
				definiteIntegralAO.setLowerLimit(Double.valueOf(newValue));
				lowerLimitMessage.setText(null);
			}

		});

		upperLimit.textProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setUpperLimit(null);
			if (!validateDouble(newValue)) {
				upperLimitMessage.setText(realNumberExpectedMessage());
			} else {
				definiteIntegralAO.setUpperLimit(Double.valueOf(newValue));
				upperLimitMessage.setText(null);
			}

		});

		algorithms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setCalculationAlgorithm(null);
			if (newValue == null) {
				algorithmenMessage.setText(mandatoryMessage());
			} else {
				algorithmenMessage.setText(null);
				definiteIntegralAO.setCalculationAlgorithm(newValue);
			}

		});

		samples.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			definiteIntegralAO.setNumberOfSamples(null);
			if (newValue == null) {
				samplesMessage.setText(mandatoryMessage());
			} else {
				samplesMessage.setText(null);
				definiteIntegralAO.setNumberOfSamples(newValue);
			}
		});

		code.textProperty().addListener((observable, oldValue, newValue) -> {

			if (!StringUtils.hasText(newValue)) {
				codeMessage.setText(mandatoryMessage());
			} else {
				codeMessage.setText(null);
			}

		});
		script.setOnAction(actionEvent -> showScriptDialog(actionEvent));

		lowerLimit.setText(null);
		upperLimit.setText(null);
		algorithms.setValue(IntegrationService.CalculationAlgorithm.Simpson);
		algorithms.setValue(null);
		code.setText(null);
		samples.setValue(1000L);
		samples.setValue(null);
		definiteIntegralAO.setResult(null);
	
		integrationButton.setOnAction(actionEvent -> {

			result.setVisible(false);
			resultLabel.setVisible(false);
			
			if (definiteIntegralAO.validate()) {
				resolveIntegral();
			}
		});
		closeButton.setOnAction(actionEvent -> closeWindow(actionEvent));
	
		
		message.register(Message.SceneType.DefiniteIntegral, message -> {
			final Stage stage = (Stage) lowerLimit.getScene().getWindow();
			
			stage.setTitle(message.message(I18N_DEFINITEINTEGRAL_TITLE));
			
			Arrays.asList(lowerLimitLabel, upperLimitLabel,algorithmLabel,samplesLabel,script,resultLabel, errorLabel, closeButton, integrationButton).forEach(label -> label.setText(message.message(I18N_DEFINITEINTEGRAL_PREFIX + label.getId().toLowerCase())));
			
		});
	}

	protected String mandatoryMessage() {
		return message.message(I18N__MANDATORY);
	}

	protected String realNumberExpectedMessage() {
		return message.message(I18N_REAL_NUMBER_EXPECTED);
	}
	
	

	private void closeWindow(final ActionEvent actionEvent) {
		((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
	}

	private void resolveIntegral() {
		try {
			definiteIntegralController.integrate(definiteIntegralAO);
		} catch(final Exception ex){
			definiteIntegralAO.setErrorMessage(ex.getMessage());
		}
	}

	private void showScriptDialog(ActionEvent actionEvent) {
		
		
		
		try {
			final Stage scriptDialog =  newStage();	
			scriptDialog.setScene(newScene(scriptDialogParent()));
			//scriptDialog.setTitle("Script auswählen");
			scriptDialog.initModality(Modality.WINDOW_MODAL);
			scriptDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
			definiteIntegralAO.setErrorMessage(null);
			message.notifyObserver(Message.SceneType.Script);
			scriptDialog.show();
		} catch (final NestedRuntimeException ex) {
			definiteIntegralAO.setErrorMessage(ex.getMostSpecificCause().getMessage());

		}
	}

	final Stage newStage() {
		return new Stage();
	}
	  
	final Scene newScene(Parent parent ) {
		return new Scene(parent);
	}



	private boolean validateDouble(final String text) {

		if (!StringUtils.hasText(text)) {
			return false;
		}

		try {
			Double.valueOf(text);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}

	}

	@Override
	public void update(final Observable o, Object arg) {
		result.setText(definiteIntegralAO.hasResult() ? "" + definiteIntegralAO.getResult().value() : null);
		result.setVisible(definiteIntegralAO.hasResult());
		error.setText(definiteIntegralAO.hasResult() ? "" + definiteIntegralAO.getResult().error() : null);
		error.setVisible(definiteIntegralAO.hasResult());
		resultLabel.setVisible(definiteIntegralAO.hasResult());
		errorLabel.setVisible(definiteIntegralAO.hasResult());
		code.setText(definiteIntegralAO.hasScript() ? definiteIntegralAO.getScript().code() : null);
		errorMessage.setText(definiteIntegralAO.getErrorMessage());

	}

	@Lookup("scriptDialogParent")
	abstract Parent scriptDialogParent();
	
	DefiniteIntegralAO getDefiniteIntegralAO(){
		return definiteIntegralAO;
	}

}
