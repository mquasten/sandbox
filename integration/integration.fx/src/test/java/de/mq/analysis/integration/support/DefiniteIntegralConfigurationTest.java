package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import de.mq.analysis.integration.RealFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DefiniteIntegralConfigurationTest {
	
	
	private static final String CODE = "Math.exp(-x**2)";
	private final DefiniteIntegralConfiguration definiteIntegralConfiguration = Mockito.mock(DefiniteIntegralConfiguration.class, Mockito.CALLS_REAL_METHODS);

	private final FXMLLoader fxmlLoader = Mockito.mock(FXMLLoader.class);
	private final ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
	
	private final Parent parent = Mockito.mock(Parent.class);

	@BeforeEach
	void setup() throws IOException  {
		Mockito.when(definiteIntegralConfiguration.newFXMLLoader(Mockito.anyString())).thenReturn(fxmlLoader);
		Mockito.when(fxmlLoader.load()).thenReturn(parent);
		
	}
	
	@Test
	void trapezoidIntegration() {
		
		assertTrue(definiteIntegralConfiguration.trapezoidIntegration() instanceof TrapezoidIntegrationImpl);
		
	}
	
	@Test
	void simpsonIntegrationService() {
		assertTrue(definiteIntegralConfiguration.simpsonIntegrationService() instanceof SimpsonIntegrationServiceImpl);
	}
	
	@Test
	void  scriptEngine() throws ScriptException {
		final ScriptEngine scriptEngine = definiteIntegralConfiguration.scriptEngine();
		final Object receiver = scriptEngine.eval(String.format( "class RealFunctionImpl\ninclude Java::%s\ndef f(x)\n%s\nend\nend\nRealFunctionImpl.new", RealFunction.class.getName(), CODE));
		final RealFunction realFunction =  ((Invocable) scriptEngine).getInterface(receiver, RealFunction.class);
		assertEquals(1d/Math.E, realFunction.f(1d));
	}
	
	@Test
	void  realFunctionJRubyScriptEngineFactory() {
		final RealFunctionJRubyScriptEngineFactory realFunctionJRubyScriptEngineFactory = definiteIntegralConfiguration.realFunctionJRubyScriptEngineFactory(new ScriptEngineManager().getEngineByName("jruby"));
		assertEquals(1d/Math.E, realFunctionJRubyScriptEngineFactory.realFunction(CODE).f(1d));
		
	}
	
	@Test
	void scriptDialogParent() throws IOException {
		final ScriptFX scriptFX  = Mockito.mock(ScriptFX.class);
		
		assertEquals(parent, definiteIntegralConfiguration.scriptDialogParent(scriptFX));	
		
		Mockito.verify(definiteIntegralConfiguration).newFXMLLoader(urlCaptor.capture());
		assertEquals("/script.fxml", urlCaptor.getValue());
		
		Mockito.verify(fxmlLoader).setController(scriptFX);
		
	}
	
	@Test
	void definiteIntegralParent() throws IOException {
		final DefiniteIntegralFX definiteIntegralFX  = Mockito.mock(DefiniteIntegralFX.class);
		
		assertEquals(parent, definiteIntegralConfiguration.definiteIntegralParent(definiteIntegralFX));	
		Mockito.verify(definiteIntegralConfiguration).newFXMLLoader(urlCaptor.capture());
		assertEquals("/definiteIntegral.fxml", urlCaptor.getValue());
		
		Mockito.verify(fxmlLoader).setController(definiteIntegralFX);
		
	}

}
