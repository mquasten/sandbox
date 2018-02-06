package de.mq.analysis.integration.support;

import java.util.Observable;


import de.mq.analysis.integration.Script;

class ScriptAO extends Observable {
	
	private Script selectedScript; 
	
	private Script currentScript; 


	public Script getCurrentScript() {
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
	
	void setCurrentScript(final Script currentScript) {
		this.currentScript = currentScript;
		setChanged();
		notifyObservers();
	}

	
}
