package de.mq.analysis.integration.support;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import de.mq.analysis.integration.Script;

public class ScriptTest {
	
	private static final String ID = UUID.randomUUID().toString();
	private static final String CODE = "MAT.exp(-x**2)";
	private final Script script = new ScriptImpl(CODE);
	
	@Before
	public void setup() {
		Arrays.asList(script.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).forEach(field -> {
			field.setAccessible(true);
			ReflectionUtils.setField(field, script, ID.toString());
			
		});
	}

	
	@Test
	public final void code(){
		Assert.assertEquals(CODE, script.code());
	}
	
	@Test
	public final void id() {
		Assert.assertEquals(ID, script.id());
	}
	
	@Test
	public final void idNotPersistent() {
		Assert.assertNull(new ScriptImpl(CODE).id());
	}

}
