package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.analysis.integration.support.Message.SceneType;

public class MessageTest {
	private static final String VALUE = "value";
	private static final String CODE = "code";
	private final MessageSource messageSource = Mockito.mock(MessageSource.class);
	private final Message message = new MessageImpl(messageSource);

	@SuppressWarnings("unchecked")
	private final Consumer<Message> observer = Mockito.mock(Consumer.class);

	@Test
	public void register() {
		message.register(Message.SceneType.DefiniteIntegral, observer);

		final Map<Message.SceneType, Consumer<Message>> results = observers();
		assertEquals(1, results.size());
		assertEquals(Message.SceneType.DefiniteIntegral, results.keySet().stream().findAny().get());
		assertEquals(observer, results.values().stream().findAny().get());
	}

	@SuppressWarnings("unchecked")
	private Map<SceneType, Consumer<Message>> observers() {
		return (Map<SceneType, Consumer<Message>>) DataAccessUtils.requiredSingleResult(Arrays.asList(MessageImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(Map.class)).map(field -> ReflectionTestUtils.getField(message, field.getName())).collect(Collectors.toList()));
	}

	@Test
	public void notifyObserver() {
		observers().put(Message.SceneType.Script, observer);

		Arrays.asList(Message.SceneType.values()).forEach(sceneType -> message.notifyObserver(sceneType));

		Mockito.verify(observer).accept(message);

	}

	@Test
	public void notifyObservers() {

		@SuppressWarnings("unchecked")
		final Consumer<Message> otherObserver = Mockito.mock(Consumer.class);
		observers().put(Message.SceneType.Script, observer);
		observers().put(Message.SceneType.DefiniteIntegral, otherObserver);

		message.notifyObservers();

		Mockito.verify(observer).accept(message);
		Mockito.verify(otherObserver).accept(message);

	}

	@Test
	public void unRegister() {
		observers().put(Message.SceneType.DefiniteIntegral, observer);

		message.unRegister(Message.SceneType.DefiniteIntegral);

		assertTrue(observers().isEmpty());

	}

	@Test
	public void message() {
		Mockito.doReturn(VALUE).when(messageSource).getMessage(CODE, new Object[] {}, MessageImpl.NOTFOUND_PREFIX + CODE, Locale.GERMAN);

		assertEquals(VALUE, message.message(CODE));
	}

}
