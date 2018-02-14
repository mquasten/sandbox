package de.mq.analysis.integration.support;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.IntegrationService.CalculationAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class DefiniteIntegralFXTest {

	private final  DefiniteIntegralFX  definiteIntegralFX = Mockito.mock(DefiniteIntegralFX.class, Mockito.CALLS_REAL_METHODS);
	
	
	private final Map<String,Object> dependencies = new HashMap<>();
	
	private final DefiniteIntegralController definiteIntegralController = Mockito.mock(DefiniteIntegralController.class);
	
	private final DefiniteIntegralAO definiteIntegralAO = new DefiniteIntegralAO();
	
	private final ActionEvent event = Mockito.mock(ActionEvent.class);
	
	@BeforeAll
	 static void  setupFX() throws InterruptedException {
		
		System.out.printf("About to launch FX App\n");
	    final Thread t = new Thread("JavaFX Init Thread") {
	    	@Override
	        public void run() {
	            Application.launch(ApplicationDummy.class, new String[0]);
	          
	        }
	    };
	  
	    t.setDaemon(true);
	    t.start();
	    System.out.printf("FX App thread started\n");
	    
	    
	}
	
	@BeforeEach
	void setup() {
		
		
		
		Arrays.asList(DefiniteIntegralFX.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(FXML.class)).forEach(field -> {
			final Object dependency = BeanUtils.instantiateClass(field.getType());
			dependencies.put(field.getName(), dependency);
			ReflectionTestUtils.setField(definiteIntegralFX, field.getName(), dependency);
		});
		Arrays.asList(DefiniteIntegralFX.class.getDeclaredFields()).stream().filter(field ->field.getType().equals(DefiniteIntegralController.class)).forEach(field -> {
			ReflectionTestUtils.setField(definiteIntegralFX, field.getName(), definiteIntegralController);
		});
		
		Arrays.asList(DefiniteIntegralFX.class.getDeclaredFields()).stream().filter(field ->field.getType().equals(DefiniteIntegralAO.class)).forEach(field -> {
			ReflectionTestUtils.setField(definiteIntegralFX, field.getName(), definiteIntegralAO);
		});
		
		
		Mockito.doNothing().when(definiteIntegralFX).showScriptDialog(Mockito.any());
		Mockito.doNothing().when(definiteIntegralFX).closeWindow(Mockito.any());
		definiteIntegralFX.initialize(null, null);
	}
	
	@Test
	final void algorithms() {
		
		final ChoiceBox<?> choiceBox = (ChoiceBox<?>) dependencies.get("algorithms");
		assertNotNull(choiceBox);
		assertEquals(2, choiceBox.getItems().size());
		assertEquals(Arrays.asList(IntegrationService.CalculationAlgorithm.values()),  choiceBox.getItems());
		assertNull(choiceBox.getValue());
		
		
		
		
		
	}
	
	@Test
	final void samples() {
		 final ChoiceBox<?> choiceBox = (ChoiceBox<?>) dependencies.get("samples");
		 assertNotNull(choiceBox);
		 assertEquals(5, choiceBox.getItems().size());
		 assertNull(choiceBox.getValue());
		 assertEquals(Arrays.asList(1000L, 10000L, 100000L, 1000000L, 10000000L), choiceBox.getItems());
	}
	
	@Test
	final void messages() {
		Arrays.asList("lowerLimitMessage" , "upperLimitMessage").forEach( control -> {
			final Label label = (Label) dependencies.get(control);
			assertNotNull(label);
			assertEquals("reelle Zahl", label.getText() );
		});
		
		Arrays.asList( "algorithmenMessage" , "codeMessage", "samplesMessage" ).forEach( control -> {
			final Label label = (Label) dependencies.get(control);
			assertNotNull(label);
			assertEquals("Mußfeld", label.getText() );
		});;
	}
	
	@Test
	final void visible() {
		Arrays.asList("result" , "resultLabel").forEach( control -> assertFalse(((Control) dependencies.get(control)).isVisible()));
		
	}
	
	@Test
	final void lowerLimitChanged() {
		final TextField  lowerlimit = (TextField) dependencies.get("lowerLimit");
		assertNotNull(lowerlimit);
		assertNull(lowerlimit.getText());
		final Label label = (Label) dependencies.get("lowerLimitMessage");
		assertEquals("reelle Zahl", label.getText() );
		assertNull(getLowerLimit());
		
		lowerlimit.setText("0");
		
		assertNull(label.getText() );
		assertEquals(0d, getLowerLimit());
	}

	private Object getLowerLimit() {
		return ReflectionTestUtils.getField(definiteIntegralAO, "lowerLimit");
	}
	
	@Test
	final void upperLimitChanged() {
		final TextField  upperLimit = (TextField) dependencies.get("upperLimit");
		assertNotNull(upperLimit);
		assertNull(upperLimit.getText());
		final Label label = (Label) dependencies.get("upperLimitMessage");
		assertEquals("reelle Zahl", label.getText() );
		assertNull(getUpperLimit());
		
		upperLimit.setText("1");
		
		assertNull(label.getText() );
		assertEquals(1d, getUpperLimit());
		
	}
	
	private Object getUpperLimit() {
		return ReflectionTestUtils.getField(definiteIntegralAO, "upperLimit");
	}
	private Object getcalculationAlgorithm() {
		return ReflectionTestUtils.getField(definiteIntegralAO, "calculationAlgorithm");
	}
	
	
	
	
	@Test
	final void codeChanged() {
		final TextArea  code = (TextArea) dependencies.get("code");
		assertNotNull(code);
		assertNull(code.getText());
		final Label label = (Label) dependencies.get("codeMessage");
		assertEquals("Mußfeld", label.getText() );

		
		code.setText("x**2");
		
		
		assertNull(label.getText() );
		
	}
	
	@Test
	final void algorithmsChanged() {
		@SuppressWarnings("unchecked")
		final ChoiceBox<IntegrationService.CalculationAlgorithm>  algorithms = (ChoiceBox<CalculationAlgorithm>) dependencies.get("algorithms");
		assertNotNull(algorithms);
		assertNull(algorithms.getValue());
		final Label label = (Label) dependencies.get("algorithmenMessage");
		assertEquals("Mußfeld", label.getText() );
		assertNull(getcalculationAlgorithm());
		assertNull(algorithms.getValue());
		
		
		algorithms.setValue(IntegrationService.CalculationAlgorithm.Simpson);
		
		
		assertNull(label.getText() );
		assertEquals(IntegrationService.CalculationAlgorithm.Simpson, getcalculationAlgorithm());
		assertEquals(IntegrationService.CalculationAlgorithm.Simpson, algorithms.getValue());
	}
	
	@Test
	final void samplesChanged() {
		@SuppressWarnings("unchecked")
		final ChoiceBox<Long>  samples = (ChoiceBox<Long>) dependencies.get("samples");
		assertNotNull(samples);
		assertNull(samples.getValue());
		final Label label = (Label) dependencies.get("samplesMessage");
		assertEquals("Mußfeld", label.getText() );
		assertNull(getcalculationAlgorithm());
		assertNull(samples.getValue());
		
		
		samples.setValue(1000L);
		
		
		assertNull(label.getText() );
		assertEquals(Long.valueOf(1000L), Long.valueOf(definiteIntegralAO.getNumberOfSamples()));
		assertEquals(Long.valueOf(1000L), Long.valueOf(samples.getValue()));
	}
	
	@Test
	final void showScriptDialog( ) {
		final Hyperlink   scriptLink =  (Hyperlink) dependencies.get("script");
		assertNotNull(scriptLink);
		
		
		scriptLink.getOnAction().handle(event);
		
		Mockito.verify(definiteIntegralFX).showScriptDialog(event);
	}
	
	@Test
	final void closewindow( ) {
		final Button   button =  (Button) dependencies.get("closeButton");
		assertNotNull(button);
		
		button.getOnAction().handle(event);
		
		Mockito.verify(definiteIntegralFX).closeWindow(event);
	}


}




 