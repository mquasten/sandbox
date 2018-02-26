package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class DefiniteIntegralConfigurationFXTest extends ApplicationTest  {
	
	private Map<Class<?>,Parent > parents = new HashMap<>();

	
	

	@Override
	public void start(Stage stage) {
		
		final DefiniteIntegralFX definiteIntegralFX = Mockito.mock(DefiniteIntegralFX.class);
		final ScriptFX scriptFX = Mockito.mock(ScriptFX.class);
		final DefiniteIntegralConfigurationFX configuration = new DefiniteIntegralConfigurationFX();	
		try {
			parents.put(DefiniteIntegralConfigurationFX.class, configuration.definiteIntegralParent(definiteIntegralFX));
		    parents.put(ScriptFX.class, configuration.scriptDialogParent(scriptFX));
			
		} catch (IOException ex) {
			fail(ex.getMessage());
		
		}
		
		
		
	}
	
	@Test
	public final void definiteIntegralParent() {
		assertTrue(parents.containsKey(DefiniteIntegralConfigurationFX.class));
		
		final Parent parent = parents.get(DefiniteIntegralConfigurationFX.class);
		assertNotNull(from(parent).nth(0));
		final List<String> ids = idsFrom(DefiniteIntegralFX.class);
		assertEquals(22, ids.size());
		
		ids.stream().forEach(id -> assertNotNull(from(from(parent).nth(0)).lookup("#"+id).query()));
	}
	
	@Test
	public final void scriptDialogParent() {
		assertTrue(parents.containsKey(ScriptFX.class));
		
		
		final Parent parent = parents.get(ScriptFX.class);
		assertNotNull(from(parent).nth(0));
		final List<String> ids = idsFrom(ScriptFX.class);

		assertEquals(9, ids.size());
	
		ids.stream().forEach(id -> assertNotNull(from(from(parent).nth(0)).lookup("#" +id)));
		
			
	}

	private  List<String> idsFrom(Class<?> target) {
		return Arrays.asList(target.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(FXML.class)).map(field ->field.getName()).collect(Collectors.toList());
	}

}
