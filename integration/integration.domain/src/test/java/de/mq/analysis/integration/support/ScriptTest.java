package de.mq.analysis.integration.support;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import de.mq.analysis.integration.Script;

public class ScriptTest {
	
	private static final UUID ID = UUID.randomUUID();
	private static final String CODE = "MAT.exp(-x**2)";
	private final Script script = new ScriptImpl(ID, CODE);
	
	@Test
	public final void id(){
		Assert.assertEquals(ID, script.id());
	}
	
	@Test
	public final void code(){
		Assert.assertEquals(CODE, script.code());
	}

}
