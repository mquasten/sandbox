package de.mq.analysis.integration.support;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Observer;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;


public class ScriptAOTest {
	
	private static final String ID = UUID.randomUUID().toString();

	private static final String CODE = "Math.exp(-x^2)";

	private final ScriptAO scriptAO = new ScriptAO(); 
	
	private final Script script = Mockito.mock(Script.class);
	
	
	private final Observer observer = Mockito.mock(Observer.class);
	
	
	
	@Before
	public final void setup() {
		scriptAO.addObserver(observer);
	}
	
	
	
	@Test
	public final void selectedScript() {
		assertNull(scriptAO.getSelectedScript());
		
		scriptAO.setSelectedScript(script);
		
		assertEquals(script, scriptAO.getSelectedScript());
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void currentScript() {
		assertNull(scriptAO.getCurrentScript());
		
		scriptAO.setCurrentScript(script);
		
		assertEquals(script, scriptAO.getCurrentScript());
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
		
	}
	
	
	@Test
	public void setCurrentScriptCode() {
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
	public void setCurrentScriptCodeWithoutId() {
	
		assertNull( scriptAO.getCurrentScript());
		scriptAO.setCurrentScript(CODE);
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
		final Script result = scriptAO.getCurrentScript();
		assertNull(result.id());
		assertEquals(CODE, result.code());
		assertTrue((result instanceof ScriptImpl));
		
	}
	
	

}


