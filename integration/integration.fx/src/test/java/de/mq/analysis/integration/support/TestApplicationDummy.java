package de.mq.analysis.integration.support;



import javafx.application.Platform;
import javafx.stage.Stage;

import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import javafx.application.Application; 
public  class TestApplicationDummy extends javafx.application.Application {
	
	
	
	

	private final Application application = BeanUtils.instantiateClass(DefiniteIntegrationApplication.class);
	private final static  Stage stage = Mockito.mock(Stage.class); 

	@Override
	public void start(final Stage primaryStage) throws Exception {
		application.start(stage);
		Platform.exit();
	}
	
	public static  final Stage stage() {
		return stage;
	}
	
	
}
