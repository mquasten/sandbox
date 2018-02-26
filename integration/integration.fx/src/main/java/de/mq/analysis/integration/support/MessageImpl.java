package de.mq.analysis.integration.support;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
class MessageImpl implements Message  {
	
	
	private final MessageSource messageSource; 
	
	private final Locale locale = Locale.GERMAN;
	
	private final  Map<SceneType,Consumer<Message>> observers = new HashMap<>();
	
	MessageImpl(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Message#register(de.mq.analysis.integration.support.Message.Screne, java.util.function.Consumer)
	 */
	@Override
	public final void register(SceneType scene, Consumer<Message> observer) {
		observers.put(scene, observer);
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Message#notifyObservers(de.mq.analysis.integration.support.MessageImpl.Screne)
	 */
	@Override
	public final void notifyObservers(final SceneType screne) {
		if( ! observers.containsKey(screne) ) {
			return;
		}
		
		observers.get(screne).accept(this);
	}
	
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Message#notifyObservers()
	 */
	@Override
	public void notifyObservers() {
		observers.keySet().forEach(scene -> notifyObservers(scene) );
	}
	
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Message#message(java.lang.String)
	 */
	@Override
	public String message(final String code) {
		return messageSource.getMessage(code, new Object[] {}, "?" + code,  locale);
	}


	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Message#unRegister(de.mq.analysis.integration.support.Message.SceneType)
	 */
	@Override
	public void unRegister(final SceneType scene) {
		observers.remove(scene);
	}

}
