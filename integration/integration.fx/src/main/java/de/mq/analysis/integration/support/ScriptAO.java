package de.mq.analysis.integration.support;

import java.util.Arrays;
import java.util.Observable;

import org.springframework.data.annotation.Id;
import org.springframework.data.util.ReflectionUtils;

import de.mq.analysis.integration.Script;

class ScriptAO extends Observable {
	
	private Script selectedScript; 
	
	private Script currentScript; 


	Script getCurrentScript() {
		return currentScript;
	}

	Script getSelectedScript() {
	   return selectedScript; 	
	}
	
	void setSelectedScript(final Script selectedScript) {
		this.selectedScript = selectedScript;
		setChanged();
		notifyObservers();
	}
	
	void setCurrentScript(final String code) {
		
		final Script script = new ScriptImpl(code);
	   Arrays.asList(ScriptImpl.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).forEach(field-> {
		   field.setAccessible(true);
		   ReflectionUtils.setField(field, script, getCurrentScript().id());
	   });;
		
	}
	
	void setCurrentScript(final Script currentScript) {
		this.currentScript = currentScript;
		setChanged();
		notifyObservers();
	}

	
}
