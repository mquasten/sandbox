package de.mq.analysis.integration.support;

import java.util.function.Consumer;

interface Message {
	

	public enum SceneType {
		DefiniteIntegral,
		Script;
	}
	
	

	void notifyObservers(final SceneType sceneType);

	void notifyObservers();

	String message(final String code);

	void register(final SceneType scene, final Consumer<Message> observer);
	
	void unRegister(final SceneType scene);

}