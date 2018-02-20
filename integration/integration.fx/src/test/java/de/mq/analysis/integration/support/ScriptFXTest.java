package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.util.ReflectionTestUtils;
import org.testfx.framework.junit.ApplicationTest;

import de.mq.analysis.integration.Script;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;

public class ScriptFXTest extends ApplicationTest {
	
	private static final String SCRIPT_INPUT_ID = "script";
	private static final String I18N_SCRIPT_TABLE_DATA_HEADLINE = "f(x)";
	private static final String SCRIPT_TABLE_ID = "scriptTable";
	private static final String CODE = "x**2";
	private static final String ID = UUID.randomUUID().toString();
	private final ScriptController scriptController = Mockito.mock(ScriptController.class);
	private final DefiniteIntegralFX definiteIntegralFX = Mockito.mock(DefiniteIntegralFX.class);
	private ScriptFX scriptFX;
	private final DefiniteIntegralAO definiteIntegralAO = Mockito.mock(DefiniteIntegralAO.class);
	
	private ScriptAO scriptAO;
	
	
	private final Script script = Mockito.mock(Script.class);
	
	private final Map<String, Control> controls = new HashMap<>();
	
	@Override
	public void start(final Stage primaryStage) throws Exception {
	
		Mockito.doReturn(definiteIntegralAO).when(definiteIntegralFX).getDefiniteIntegralAO();
		scriptFX = new ScriptFX(scriptController, definiteIntegralFX);
		scriptAO=(ScriptAO) DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptFX.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(ScriptAO.class)).map(field -> ReflectionTestUtils.getField(scriptFX, field.getName())).collect(Collectors.toList()));
		
		Mockito.doReturn(CODE).when(script).code();
		Mockito.doReturn(ID).when(script).id();
		
		Mockito.doReturn(Arrays.asList(script)).when(scriptController).scripts();
		
		
		
		
		Arrays.asList(ScriptFX.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(FXML.class)).forEach(field -> {
			final Control dependency = (Control) BeanUtils.instantiateClass(field.getType());
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
		
		assertEquals(I18N_SCRIPT_TABLE_DATA_HEADLINE, scriptTable.getColumns().get(0).getText());
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


}
