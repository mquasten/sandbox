package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;
import org.testfx.framework.junit.ApplicationTest;

import de.mq.analysis.integration.Script;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScriptFXTest extends ApplicationTest   {
	
	private static final String I18N_SCRIPT_TITLE_VALUE = "Script auswählen";
	
	private static final String I18N_SCRIPT_CODE_COLUMN_VALUE  = "f(x)";
	
	private static final String I18N_FUNCTION_INVALID = "Funktion ist ungültig.";
	private static final String ERROR_MESSAGE_LABEL_ID = "errorMessage";
	private static final String ERROR_MESSAGE_VALUE = "ErrorMessage";
	private static final String CANCEL_BUTTON_ID = "cancelButton";
	private static final String SELECT_BUTTON_ID = "selectButton";
	private static final String SAVE_SCRIPT_BUTTON_ID = "saveScript";
	private static final String DELETE_SCRIPT_BUTTON_ID = "deleteScript";
	private static final String ADD_SCRIPT_BUTTON_ID = "addScript";
	private static final String SCRIPT_INPUT_ID = "script";
	
	private static final String SCRIPT_TABLE_ID = "scriptTable";
	private static final String CODE = "x**2";
	private static final String ID = UUID.randomUUID().toString();

	private static final Object ADD_SCRIPT_BUTTON_VALUE = "hinzufügen";

	private static final Object DELETE_SCRIPT_BUTTON_VALUE = "löschen";

	private static final Object SAVE_SCRIPT_BUTTON_VALUE = "speichern";

	private static final Object CANCEL_BUTTON_VALUE = "abbrechen";

	private static final Object SELECT_BUTTON_VALUE = "auswählen";
	private final ScriptController scriptController = Mockito.mock(ScriptController.class);
	private final DefiniteIntegralFX definiteIntegralFX = Mockito.mock(DefiniteIntegralFX.class);
	private ScriptFX scriptFX;
	private final DefiniteIntegralAO definiteIntegralAO = Mockito.mock(DefiniteIntegralAO.class);
	
	private ScriptAO scriptAO;
	
	
	private final Script script = Mockito.mock(Script.class);
	
	private final Map<String, Node> controls = new HashMap<>();


	private final Message message = Mockito.mock(Message.class);


	@Override
	public void start(final Stage primaryStage) throws Exception {
	
		Mockito.doReturn(definiteIntegralAO).when(definiteIntegralFX).getDefiniteIntegralAO();
		scriptFX = new ScriptFX(scriptController, definiteIntegralFX, message);
		scriptAO=(ScriptAO) DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptFX.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(ScriptAO.class)).map(field -> ReflectionTestUtils.getField(scriptFX, field.getName())).collect(Collectors.toList()));
		
		Mockito.doReturn(CODE).when(script).code();
		Mockito.doReturn(ID).when(script).id();
		
		Mockito.doReturn(Arrays.asList(script)).when(scriptController).scripts();
		
		
		
		
		Arrays.asList(ScriptFX.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(FXML.class)).forEach(field -> {
			final Node dependency = (Node) BeanUtils.instantiateClass(field.getType());
			dependency.setId(field.getName());
			ReflectionTestUtils.setField(scriptFX, field.getName(), dependency);
			controls.put(field.getName(), dependency);

		});
		
		scriptAO.setSelectedScript(script);
		scriptFX.initialize(null, null);
		
		
	}
	
	

	
	@Test
	public final void callDefiniteIntegralObserver() {
		scriptAO.setSelectedScript(null);
		Mockito.verify(definiteIntegralAO, Mockito.atLeastOnce()).update(Mockito.any(), Mockito.any());
	}
	
	@Test
	public final void scriptTableValues() {
		final TableView<Script>  scriptTable = scriptTableView();
		
		assertEquals(1, scriptTable.getItems().size());
		assertEquals(script, scriptTable.getItems().stream().findAny().get());
		assertEquals(script, scriptTable.getSelectionModel().getSelectedItem());
		assertEquals(script, scriptAO.getSelectedScript());
		assertEquals(script.id(),scriptAO.getCurrentScript().id());
		assertEquals(script.code(),scriptAO.getCurrentScript().code());
		
		
		assertEquals(1, scriptTable.getColumns().size());
		
		assertEquals(script, scriptTable.getColumns().get(0).getCellData(0));
	}


	@SuppressWarnings("unchecked")
	private  TableView<Script> scriptTableView() {
		assertNotNull(controls.containsKey(SCRIPT_TABLE_ID));
		return (TableView<Script>) controls.get(SCRIPT_TABLE_ID);
		
	}
	
	@Test
	public final void  scriptValue() {
		assertEquals(script.code(), textInput(SCRIPT_INPUT_ID).getText());
		assertEquals(CODE, scriptAO.getCurrentScript().code());
		assertEquals(ID, scriptAO.getCurrentScript().id());
		
		scriptTableView().getSelectionModel().clearSelection();
		
		assertNull(textInput(SCRIPT_INPUT_ID).getText());
		assertNull(ID, scriptAO.getCurrentScript().id());
		
	}
	
	private TextInputControl textInput(final String id) {
		assertTrue(controls.containsKey(id));
		return (TextInputControl) controls.get(id);
	}
	
	private ButtonBase buttonBase(final String id) {
		assertTrue(controls.containsKey(id));
		return (ButtonBase) controls.get(id);
	}
	
	@Test
	public final void buttonsEditor() {
		 assertFalse(buttonBase(ADD_SCRIPT_BUTTON_ID).isDisable());
		 assertFalse(buttonBase(DELETE_SCRIPT_BUTTON_ID).isDisabled());
		 assertFalse(buttonBase(SAVE_SCRIPT_BUTTON_ID).isDisabled());
		 
		 scriptTableView().getSelectionModel().clearSelection();
		 
		 assertFalse(buttonBase(ADD_SCRIPT_BUTTON_ID).isDisable());
		 assertTrue(buttonBase(DELETE_SCRIPT_BUTTON_ID).isDisabled());
		 assertTrue(buttonBase(SAVE_SCRIPT_BUTTON_ID).isDisabled());
		
	}
	
	@Test
	public final void selectButton() {
		 assertFalse(buttonBase(SELECT_BUTTON_ID).isDisable());
		 
		 scriptTableView().getSelectionModel().clearSelection();
		 
		 assertTrue(buttonBase(SELECT_BUTTON_ID).isDisable());	
	}
	
	@Test
	public void cancelAction() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		
		 buttonBase(CANCEL_BUTTON_ID).getOnAction().handle(event);
		 
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
	public void selectAction() {
		scriptAO.setSelectedScript(null);
		
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		
		 buttonBase(SELECT_BUTTON_ID).getOnAction().handle(event);
		 
		 Mockito.verify(window).close();
		 assertEquals(script, scriptAO.getSelectedScript());
	}
	
	@Test
	public void addScriptAction() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		assertEquals(script.id(), scriptAO.getCurrentScript().id());
		assertEquals(script.code(), scriptAO.getCurrentScript().code());
		
		 buttonBase(ADD_SCRIPT_BUTTON_ID).getOnAction().handle(event);
		 
		 assertNull(script.id(), scriptAO.getCurrentScript().id());
		 assertNull(script.code(), scriptAO.getCurrentScript().code());
	}
	
	@Test
	public void  saveScriptAction() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		
		scriptTableView().getSelectionModel().clearSelection();
		assertNull(scriptTableView().getSelectionModel().getSelectedItem());
		
		final Script newScript = Mockito.mock(Script.class);
		scriptAO.setCurrentScript(newScript);
		
		Mockito.doReturn(Arrays.asList(script, newScript)).when(scriptController).scripts();
		Mockito.doReturn(newScript).when(scriptController).save(scriptAO);
		Mockito.doReturn(true).when(scriptController).check(scriptAO);
		
		
		buttonBase(SAVE_SCRIPT_BUTTON_ID).getOnAction().handle(event);
		
		
		assertEquals(newScript, scriptTableView().getSelectionModel().getSelectedItem());
		assertEquals(newScript, scriptAO.getCurrentScript());
		Mockito.verify(scriptController).save(scriptAO);
	}
	
	@Test
	public void deleteScriptAction() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		assertEquals(script, scriptTableView().getSelectionModel().getSelectedItem());
		assertEquals(ID, scriptAO.getCurrentScript().id());
		assertEquals(CODE, scriptAO.getCurrentScript().code());
		Mockito.doReturn(Arrays.asList()).when(scriptController).scripts();
		
		buttonBase(DELETE_SCRIPT_BUTTON_ID).getOnAction().handle(event);
		
		assertNull(scriptTableView().getSelectionModel().getSelectedItem());
		assertNull(scriptAO.getCurrentScript().id());
		assertNull(scriptAO.getCurrentScript().code());
		Mockito.verify(scriptController).delete(scriptAO);
	}
	
	private Label label(final String id) {
		assertTrue(controls.containsKey(id));
		return (Label) controls.get(id);
	}

	
	
	@Test
	public void deleteScriptActionWithException() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		Mockito.doThrow(new IllegalStateException(ERROR_MESSAGE_VALUE)).when(scriptController).delete(scriptAO);
		assertTrue(StringUtils.isEmpty(label(ERROR_MESSAGE_LABEL_ID).getText()));
		
		buttonBase(DELETE_SCRIPT_BUTTON_ID).getOnAction().handle(event);
		
		assertEquals(ERROR_MESSAGE_VALUE , label(ERROR_MESSAGE_LABEL_ID).getText());
	}
	
	@Test
	public void saveScriptActionWithException() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
		Mockito.doReturn(true).when(scriptController).check(scriptAO);
		Mockito.doThrow(new IllegalStateException(ERROR_MESSAGE_VALUE)).when(scriptController).save(scriptAO);
		assertTrue(StringUtils.isEmpty(label(ERROR_MESSAGE_LABEL_ID).getText()));
		
		buttonBase(SAVE_SCRIPT_BUTTON_ID).getOnAction().handle(event);
		
		assertEquals(ERROR_MESSAGE_VALUE , label(ERROR_MESSAGE_LABEL_ID).getText());
	}
	
	@Test
	public void saveScriptActionCodeIsInvalid() {
		final Stage window = Mockito.mock(Stage.class);
		final ActionEvent event = actionEventForStageClose(window);
	
		Mockito.doReturn(false).when(scriptController).check(scriptAO);
		assertTrue(StringUtils.isEmpty(label(ERROR_MESSAGE_LABEL_ID).getText()));
		
		buttonBase(SAVE_SCRIPT_BUTTON_ID).getOnAction().handle(event);
		
		assertEquals(I18N_FUNCTION_INVALID , label(ERROR_MESSAGE_LABEL_ID).getText());
	} 
	
	@Test
	public void cellValueFactory() {
		
		@SuppressWarnings("rawtypes")
		final TableColumn tableColumn = new TableColumn<Script,Script>();
		tableColumn.setUserData(script);
		@SuppressWarnings("unchecked")
		final TableCell<Script,Script> result =  scriptTableView().getColumns().get(0).getCellFactory().call(tableColumn);
		assertNull(result.getText());
		
	
		ReflectionTestUtils.invokeMethod(result, "updateItem", script, false);
		assertEquals(CODE, result.getText());
		
		
		ReflectionTestUtils.invokeMethod(result, "updateItem", null, false);
		assertNull(CODE, result.getText());
		
	}
	
	
	@Test
	public final void i18N() {
		
		final Pane pane = Mockito.mock(Pane.class);
		final Scene scene = Mockito.mock(Scene.class);
		Mockito.doReturn(scene).when(pane).getScene();
		final Stage stage = Mockito.mock(Stage.class);
		Mockito.doReturn(stage).when(scene).getWindow();
		
		
		Mockito.doReturn(I18N_SCRIPT_TITLE_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_TITLE);
		Mockito.doReturn(I18N_SCRIPT_CODE_COLUMN_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_CODE_COLUMN);
		Mockito.doReturn(ADD_SCRIPT_BUTTON_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_PREFIX + ADD_SCRIPT_BUTTON_ID.toLowerCase());
		Mockito.doReturn(DELETE_SCRIPT_BUTTON_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_PREFIX + DELETE_SCRIPT_BUTTON_ID.toLowerCase());
		Mockito.doReturn(SAVE_SCRIPT_BUTTON_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_PREFIX + SAVE_SCRIPT_BUTTON_ID.toLowerCase());
		Mockito.doReturn(CANCEL_BUTTON_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_PREFIX + CANCEL_BUTTON_ID.toLowerCase());
		Mockito.doReturn(SELECT_BUTTON_VALUE).when(message).message(ScriptFX.I18N_SCRIPT_PREFIX + SELECT_BUTTON_ID.toLowerCase());
		
		ReflectionTestUtils.setField(scriptFX, "parent", pane);
		final ArgumentCaptor<Message.SceneType> sceneTypeCaptor = ArgumentCaptor.forClass(Message.SceneType.class);
		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Consumer<Message>> observerCaptor = ArgumentCaptor.forClass(Consumer.class);
		
		Mockito.verify(message).register(sceneTypeCaptor.capture(), observerCaptor.capture());
		
		assertEquals(Message.SceneType.Script, sceneTypeCaptor.getValue());
		
		observerCaptor.getValue().accept(message);
		
	
		
		Mockito.verify(stage).setTitle(I18N_SCRIPT_TITLE_VALUE);
		
		assertEquals(1, scriptTableView().getColumns().size());
		assertEquals(I18N_SCRIPT_CODE_COLUMN_VALUE, scriptTableView().getColumns().stream().findAny().get().getText());
		
		assertEquals(ADD_SCRIPT_BUTTON_VALUE, buttonBase(ADD_SCRIPT_BUTTON_ID).getText());
		assertEquals(DELETE_SCRIPT_BUTTON_VALUE, buttonBase(DELETE_SCRIPT_BUTTON_ID).getText());
		assertEquals(SAVE_SCRIPT_BUTTON_VALUE, buttonBase(SAVE_SCRIPT_BUTTON_ID).getText());
		assertEquals(CANCEL_BUTTON_VALUE, buttonBase(CANCEL_BUTTON_ID).getText());
		assertEquals(SELECT_BUTTON_VALUE, buttonBase(SELECT_BUTTON_ID).getText());
		
		Mockito.verify(message).unRegister(Message.SceneType.Script);
		
	}
	

















}
