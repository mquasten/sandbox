package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Observer;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;


class ScriptAOTest {
	
	private static final String ID = UUID.randomUUID().toString();

	private static final String CODE = "Math.exp(-x^2)";

	private final ScriptAO scriptAO = new ScriptAO(); 
	
	private final Script script = Mockito.mock(Script.class);
	
	
	private final Observer observer = Mockito.mock(Observer.class);
	
	
	
	@BeforeEach
	final void setup() {
		scriptAO.addObserver(observer);
	}
	
	
	
	@Test
	final void selectedScript() {
		assertNull(scriptAO.getSelectedScript());
		
		scriptAO.setSelectedScript(script);
		
		assertEquals(script, scriptAO.getSelectedScript());
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	@Test
	void currentScript() {
		assertNull(scriptAO.getCurrentScript());
		
		scriptAO.setCurrentScript(script);
		
		assertEquals(script, scriptAO.getCurrentScript());
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
		
	}
	
	
	@Test
	void setCurrentScriptCode() {
		scriptAO.setCurrentScript(script);
		assertEquals(script, scriptAO.getCurrentScript());
		Mockito.reset(observer);
		Mockito.when(script.id()).thenReturn(ID);
		scriptAO.setCurrentScript(CODE);
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
		final Script result = scriptAO.getCurrentScript();
		assertEquals(ID, result.id());
		assertEquals(CODE, result.code());
		assertTrue((result instanceof ScriptImpl));
		
	}
	
	@Test
	void setCurrentScriptCodeWithoutId() {
	
		assertNull( scriptAO.getCurrentScript());
		scriptAO.setCurrentScript(CODE);
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
		final Script result = scriptAO.getCurrentScript();
		assertNull(result.id());
		assertEquals(CODE, result.code());
		assertTrue((result instanceof ScriptImpl));
		
	}
	
	

}


