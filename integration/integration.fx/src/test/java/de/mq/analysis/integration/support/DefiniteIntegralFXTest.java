package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.util.ReflectionTestUtils;
import org.testfx.framework.junit.ApplicationTest;

import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.Script;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DefiniteIntegralFXTest extends ApplicationTest {

	private static final String ERROR_MESSAGE_ID = "errorMessage";

	private static final String INTEGRATION_BUTTON_ID = "integrationButton";

	private static final String ERROR_MESSAGE_VALUE = "It sucks...";

	private static final String I18N_SCRIPT_DIALOG_TITLE = "Script auswählen";

	private static final String SCRIPT_LINK_ID = "script";

	private static final long NUMBER_OF_SAMPLES_VALUE = 10000L;

	private static final String CODE_VALUE = "x**2";

	private static final String LOWER_LIMIT_INPUT_ID = "lowerLimit";

	private static final String RESULT_INPUT_ID = "result";

	private static final String RESULT_LABEL_ID = "resultLabel";

	private static final String NUMBER_OFSAMPLES_INPUT_ID = "samples";

	private static final String ALGORITHMS_INPUT_ID = "algorithms";

	private static final String CODE_INPUT_ID = "code";

	private static final String I18N_REAL_NUMBER_MESSAGE = "reelle Zahl";

	private static final String UPPER_LIMIT_MESSAGE_ID = "upperLimitMessage";

	private static final String LOWER_LIMIT_MESSAGE_ID = "lowerLimitMessage";

	private static final String ALGORITHM_MESSAGE_ID = "algorithmenMessage";

	private static final String NUMBER_OF_SAMPLES_MESSAGE_ID = "samplesMessage";

	private static final String CODE_MESSAGE_ID = "codeMessage";

	private static final String I18N_MANDATORY_MESSAGE = "Mußfeld";

	private static final Double REAL_NUMBER_VALUE = Math.random();

	private static final String UPPER_LIMIT_INPUT_ID = "upperLimit";

	private static final String ERROR_INPUT_ID = "error";

	private final DefiniteIntegralController definiteIntegralController = Mockito.mock(DefiniteIntegralController.class);

	private final Map<String, Control> controls = new HashMap<>();
	private final DefiniteIntegralAO definiteIntegralAO = BeanUtils.instantiateClass(DefiniteIntegralAO.class);

	private final DefiniteIntegralFX definiteIntegralFX = Mockito.mock(DefiniteIntegralFX.class, Mockito.CALLS_REAL_METHODS);

	private final Parent root = new Parent() {};
	
	private Stage stage;
	
	
	@Override
	public void start(final Stage primaryStage) throws Exception {

		Arrays.asList(DefiniteIntegralFX.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(FXML.class)).forEach(field -> {
			final Control dependency = (Control) BeanUtils.instantiateClass(field.getType());
			dependency.setId(field.getName());
			ReflectionTestUtils.setField(definiteIntegralFX, field.getName(), dependency);
			controls.put(field.getName(), dependency);

		});

		final Map<Class<?>, Object> dependencies = new HashMap<>();
		dependencies.put(DefiniteIntegralFX.class, definiteIntegralFX);
		dependencies.put(DefiniteIntegralController.class, definiteIntegralController);
		dependencies.put(DefiniteIntegralAO.class, definiteIntegralAO);

		Arrays.asList(DefiniteIntegralFX.class.getDeclaredFields()).stream().filter(field -> dependencies.containsKey(field.getType())).forEach(field -> ReflectionTestUtils.setField(definiteIntegralFX, field.getName(), dependencies.get(field.getType())));

		definiteIntegralFX.initialize(null, null);

		
		stage = definiteIntegralFX.newStage();
		
		
		stage.setScene(definiteIntegralFX.newScene(root));
		
	}

	@Test
	public final void defaultLabelsMandatory() {
		Arrays.asList(CODE_MESSAGE_ID, NUMBER_OF_SAMPLES_MESSAGE_ID, ALGORITHM_MESSAGE_ID).forEach(id -> assertEquals(I18N_MANDATORY_MESSAGE, label(id).getText()));
	}

	@Test
	public final void defaultLabelsRealNumberExpected() {
		Arrays.asList(LOWER_LIMIT_MESSAGE_ID, UPPER_LIMIT_MESSAGE_ID).forEach(id -> assertEquals(I18N_REAL_NUMBER_MESSAGE, label(id).getText()));
	}

	private Label label(final String id) {
		assertTrue(controls.containsKey(id));
		return (Label) controls.get(id);
	}

	private TextInputControl textInput(final String id) {
		assertTrue(controls.containsKey(id));
		return (TextInputControl) controls.get(id);
	}

	private ButtonBase buttonBase(final String id) {
		assertTrue(controls.containsKey(id));
		return (ButtonBase) controls.get(id);
	}

	@SuppressWarnings("unchecked")
	private <T> ChoiceBox<T> choiceBox(final String id, Class<T> target) {
		assertTrue(controls.containsKey(id));
		return (ChoiceBox<T>) controls.get(id);
	}

	@Test
	public final void defaultCodeField() {
		final TextInputControl code = textInput(CODE_INPUT_ID);
		assertFalse(code.isEditable());
		assertNull(code.getText());

	}

	@Test
	public final void defaultAlgorithms() {
		final ChoiceBox<IntegrationService.CalculationAlgorithm> choiceBox = choiceBox(ALGORITHMS_INPUT_ID, IntegrationService.CalculationAlgorithm.class);
		assertEquals(Arrays.asList(IntegrationService.CalculationAlgorithm.values()), choiceBox.getItems());
	}

	@Test
	public final void defaultSamples() {
		final ChoiceBox<Long> choiceBox = choiceBox(NUMBER_OFSAMPLES_INPUT_ID, Long.class);
		assertEquals(Arrays.asList(1000L, 10000L, 100000L, 1000000L, 10000000L), choiceBox.getItems());
	}

	@Test
	public final void defaultResults() {
		assertFalse(label(RESULT_LABEL_ID).isVisible());
		assertFalse(textInput(RESULT_INPUT_ID).isVisible());
	}

	@Test
	public final void lowerLimitChanged() {
		final Label messageLabel = label(LOWER_LIMIT_MESSAGE_ID);
		assertEquals(I18N_REAL_NUMBER_MESSAGE, messageLabel.getText());
		assertNull(fieldFromModel(LOWER_LIMIT_INPUT_ID));

		textInput(LOWER_LIMIT_INPUT_ID).setText("" + REAL_NUMBER_VALUE);

		assertNull(messageLabel.getText());
		assertEquals(REAL_NUMBER_VALUE, fieldFromModel(LOWER_LIMIT_INPUT_ID));
	}

	private final Object fieldFromModel(final String fieldName) {
		return ReflectionTestUtils.getField(definiteIntegralAO, fieldName);
	}

	@Test
	public final void upperLimitChanged() {
		final Label messageLabel = label(UPPER_LIMIT_MESSAGE_ID);
		assertEquals(I18N_REAL_NUMBER_MESSAGE, messageLabel.getText());
		assertNull(fieldFromModel(UPPER_LIMIT_INPUT_ID));

		textInput(UPPER_LIMIT_INPUT_ID).setText("" + REAL_NUMBER_VALUE);

		assertNull(messageLabel.getText());
		assertEquals(REAL_NUMBER_VALUE, fieldFromModel(UPPER_LIMIT_INPUT_ID));
	}

	@Test
	public final void codeChanged() {
		final Label messageLabel = label(CODE_MESSAGE_ID);
		assertEquals(I18N_MANDATORY_MESSAGE, messageLabel.getText());

		textInput(CODE_INPUT_ID).setText(CODE_VALUE);

		assertNull(messageLabel.getText());
	}

	@Test
	public final void algorithmsChanged() {
		final Label messageLabel = label(ALGORITHM_MESSAGE_ID);
		assertEquals(I18N_MANDATORY_MESSAGE, messageLabel.getText());
		assertNull(calculationAlgorithmModelField());

		choiceBox(ALGORITHMS_INPUT_ID, IntegrationService.CalculationAlgorithm.class).setValue(IntegrationService.CalculationAlgorithm.Simpson);

		assertNull(messageLabel.getText());
		assertEquals(IntegrationService.CalculationAlgorithm.Simpson, calculationAlgorithmModelField());

	}

	private Object calculationAlgorithmModelField() {
		return fieldFromModel("calculationAlgorithm");
	}

	private Object numberOfSamplesModelField() {
		return fieldFromModel("numberOfSamples");
	}

	@Test
	public final void samplesChanged() {
		final Label messageLabel = label(NUMBER_OF_SAMPLES_MESSAGE_ID);
		assertEquals(I18N_MANDATORY_MESSAGE, messageLabel.getText());
		assertNull(numberOfSamplesModelField());

		choiceBox(NUMBER_OFSAMPLES_INPUT_ID, Long.class).setValue(NUMBER_OF_SAMPLES_VALUE);

		assertNull(messageLabel.getText());
		assertEquals(NUMBER_OF_SAMPLES_VALUE, numberOfSamplesModelField());
	}

	@Test
	public final void closeButtonAction() {
		final ButtonBase closeButton = buttonBase("closeButton");

		final Stage window = Mockito.mock(Stage.class);

		final ActionEvent event = actionEventForStageClose(window);

		closeButton.getOnAction().handle(event);

		Mockito.verify(window).close();
	}

	private ActionEvent actionEventForStageClose(final Stage stage) {
		final ActionEvent event = Mockito.mock(ActionEvent.class);
		final Node node = Mockito.mock(Node.class);
		final Scene scene = Mockito.mock(Scene.class);
		Mockito.when(node.getScene()).thenReturn(scene);

		Mockito.when(scene.getWindow()).thenReturn(stage);
		Mockito.when(event.getSource()).thenReturn(node);
		return event;
	}

	@Test
	public final void showScriptDialogAction() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		final ArgumentCaptor<Parent> parentCaptor = ArgumentCaptor.forClass(Parent.class);

		final Parent parent = Mockito.mock(Parent.class);

		final Scene scene = Mockito.mock(Scene.class);
		final Stage stage = Mockito.mock(Stage.class);
		Mockito.doReturn(stage).when(definiteIntegralFX).newStage();
		Mockito.doReturn(scene).when(definiteIntegralFX).newScene(parentCaptor.capture());
		Mockito.doReturn(parent).when(definiteIntegralFX).scriptDialogParent();

		buttonBase(SCRIPT_LINK_ID).getOnAction().handle(event);

		Mockito.verify(stage).setScene(scene);
		assertEquals(parent, parentCaptor.getValue());

		Mockito.verify(stage).setTitle(I18N_SCRIPT_DIALOG_TITLE);
		Mockito.verify(stage).initModality(Modality.WINDOW_MODAL);
		Mockito.verify(stage).initOwner(window);
		Mockito.verify(stage).show();
	}

	@Test
	public final void showScriptDialogActionException() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		final Stage stage = Mockito.mock(Stage.class);
		Mockito.doReturn(stage).when(definiteIntegralFX).newStage();
		Mockito.doThrow(new BeanCreationException(ERROR_MESSAGE_VALUE)).when(definiteIntegralFX).scriptDialogParent();

		buttonBase(SCRIPT_LINK_ID).getOnAction().handle(event);

		assertEquals(ERROR_MESSAGE_VALUE, definiteIntegralAO.getErrorMessage());

	}
	
	@Test
	public final void  validateDouble() {
		final Label message = label(LOWER_LIMIT_MESSAGE_ID);
		final TextInputControl lowerLimit = textInput(LOWER_LIMIT_INPUT_ID);
		assertEquals(I18N_REAL_NUMBER_MESSAGE, message.getText());
		
		lowerLimit.setText(" ");
		
		assertEquals(I18N_REAL_NUMBER_MESSAGE, message.getText());
		
		lowerLimit.setText(ERROR_MESSAGE_VALUE);
		
		assertEquals(I18N_REAL_NUMBER_MESSAGE, message.getText());
		
		lowerLimit.setText("" + REAL_NUMBER_VALUE);
		
		assertNull(message.getText());
		
	}
	
	@Test
	public final void integrationButtonAction() {
		final ActionEvent event = Mockito.mock(ActionEvent.class);
		//X**2: primitive 1/3 x**3 |0,1 = 1/3
		final  Double resultValue = 1d/3d;
		final  Double resultError = 1e-6;
		final Result result = Mockito.mock(Result.class);
		Mockito.doReturn(resultError).when(result).error();
		Mockito.doReturn(resultValue).when(result).value();
		Mockito.doAnswer(answer -> {
			final DefiniteIntegralAO definiteIntegralAO = answer.getArgument(0);
			definiteIntegralAO.setResult(result);
			return null;
		}).when(definiteIntegralController).integrate(Mockito.any());
		prepareForIntegration();
		
		buttonBase(INTEGRATION_BUTTON_ID).getOnAction().handle(event);
		
		assertEquals(""+ resultValue, textInput(RESULT_INPUT_ID).getText());
		assertEquals(""+ resultError, textInput(ERROR_INPUT_ID).getText());
		assertNull(label(ERROR_MESSAGE_ID).getText());
		
		assertTrue(textInput(RESULT_INPUT_ID).isVisible());
		assertTrue(textInput(ERROR_INPUT_ID).isVisible());
		
	}
	
	@Test
	public final void integrationButtonActionException() {
		final ActionEvent event = Mockito.mock(ActionEvent.class);
		Mockito.doThrow(new IllegalArgumentException(ERROR_MESSAGE_VALUE)).when(definiteIntegralController).integrate(Mockito.any());
		prepareForIntegration();
		
		buttonBase(INTEGRATION_BUTTON_ID).getOnAction().handle(event);
		
		assertEquals(ERROR_MESSAGE_VALUE, label(ERROR_MESSAGE_ID).getText());
		assertFalse(textInput(RESULT_INPUT_ID).isVisible());
		assertFalse(textInput(ERROR_INPUT_ID).isVisible());
		
	}
	
	@Test
	public final void integrationButtonActionValidationFailed() {
		final ActionEvent event = Mockito.mock(ActionEvent.class);
		Mockito.doThrow(new IllegalArgumentException(ERROR_MESSAGE_VALUE)).when(definiteIntegralController).integrate(Mockito.any());
		
		
		buttonBase(INTEGRATION_BUTTON_ID).getOnAction().handle(event);
		
		assertNull(label(ERROR_MESSAGE_ID).getText());
		assertFalse(textInput(RESULT_INPUT_ID).isVisible());
		assertFalse(textInput(ERROR_INPUT_ID).isVisible());
		Mockito.verify(definiteIntegralController, Mockito.never()).integrate(Mockito.any());
		
	}
	
	
	

	private void prepareForIntegration() {
		final Script script = Mockito.mock(Script.class);
		definiteIntegralAO.setScript(script);
		textInput(LOWER_LIMIT_INPUT_ID).setText("0");
		textInput(UPPER_LIMIT_INPUT_ID).setText("1");
		choiceBox(ALGORITHMS_INPUT_ID, IntegrationService.CalculationAlgorithm.class).setValue(IntegrationService.CalculationAlgorithm.Trapezoid); 
		choiceBox(NUMBER_OFSAMPLES_INPUT_ID, Long.class).setValue(NUMBER_OF_SAMPLES_VALUE);
	}
	
	@Test
	public final void getDefiniteIntegralAO() {
		assertEquals(definiteIntegralAO, definiteIntegralFX.getDefiniteIntegralAO());
	}
	
	@Test
	public final void create() throws Exception {
		final Constructor<?>   constructor = definiteIntegralFX.getClass().getDeclaredConstructor(DefiniteIntegralController.class);
		final Object definiteIntegralFX = BeanUtils.instantiateClass(constructor, definiteIntegralController);
		
		assertEquals(definiteIntegralController, DataAccessUtils.requiredSingleResult(Arrays.asList(DefiniteIntegralFX.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(DefiniteIntegralController.class)).map(field -> ReflectionTestUtils.getField(definiteIntegralFX, field.getName())).collect(Collectors.toList())));
	}
	@Test
	public final void scene() {
		assertEquals(root, stage.getScene().rootProperty().get());
	}
	

}