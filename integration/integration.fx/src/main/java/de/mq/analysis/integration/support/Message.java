package de.mq.analysis.integration.support;

import java.util.function.Consumer;

interface Message {
	

	public enum Screne {
		DefiniteIntegral,
		Script;
	}
	
	

	void notifyObservers(Screne screne);

	void notifyObservers();

	String message(String code);

	void register(Screne scene, Consumer<Message> observer);

}