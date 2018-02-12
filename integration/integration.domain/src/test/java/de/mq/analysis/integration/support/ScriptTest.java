package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import de.mq.analysis.integration.Script;

class ScriptTest implements ArgumentsProvider {

	private static final String ID = UUID.randomUUID().toString();
	private static final String CODE = "MAT.exp(-x**2)";
	private Script script;

	@BeforeEach
	void setup() {
		script = scriptWithId();
	}

	private static Script scriptWithId() {
		final Script script = new ScriptImpl(CODE);
		Arrays.asList(script.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).forEach(field -> {
			field.setAccessible(true);
			ReflectionUtils.setField(field, script, ID.toString());
		});
		return script;
	}

	@Test
	final void code() {
		assertEquals(CODE, script.code());
	}

	@Test
	final void id() {
		assertEquals(ID, script.id());
	}

	@ParameterizedTest()
	@ArgumentsSource(ScriptTest.class)
	final void hash(final Script script) {
		if (script.id() != null) {
			assertEquals(ID.hashCode(), script.hashCode());
		} else {
			assertEquals(System.identityHashCode(script), script.hashCode());
		}

	}


	@SuppressWarnings("unlikely-arg-type")
	@ParameterizedTest
	@ArgumentsSource(ScriptTest.class)
	final void equals(final Script script) {
		final Script otherWithId = scriptWithId();

		if (script.id() != null) {
			assertTrue(otherWithId.equals(script));
		} else {
			assertFalse(otherWithId.equals(script));
		}

		final Script otherWithoutId = new ScriptImpl(CODE);
		assertFalse(otherWithoutId.equals(script));

		assertFalse(script.equals(CODE));
	}

	@Override
	public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
		
		return Stream.of(Arguments.of(scriptWithId(), new ScriptImpl(CODE)));
	}

}
