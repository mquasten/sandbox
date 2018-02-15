package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import de.mq.analysis.integration.Script;

public class ScriptTest {

	private static final String ID = UUID.randomUUID().toString();
	private static final String CODE = "MAT.exp(-x**2)";
	private Script script;

	@Before
	public void setup() {
		script = scriptWithId();
	}

	private Script scriptWithId() {
		final Script script = new ScriptImpl(CODE);
		Arrays.asList(script.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).forEach(field -> {
			field.setAccessible(true);
			ReflectionUtils.setField(field, script, ID.toString());
		});
		return script;
	}

	@Test
	public final void code() {
		assertEquals(CODE, script.code());
	}

	@Test
	public final void id() {
		assertEquals(ID, script.id());
	}

	@Test
	public final void hash() {
		scripts().forEach(script -> hash(script));

	}

	private void hash(Script script) {
		if (script.id() != null) {
			assertEquals(ID.hashCode(), script.hashCode());
		} else {
			assertEquals(System.identityHashCode(script), script.hashCode());
		}
	}

	@Test
	public final void equals() {
		scripts().forEach(script -> equals(script));
	}

	private void equals(Script script) {
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

	public Collection<Script> scripts() {

		return Arrays.asList(scriptWithId(), new ScriptImpl(CODE));
	}

}
