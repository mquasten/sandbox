package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;

public class ScriptAOTest {
	
	private final ScriptAO scriptAO = new ScriptAO(); 
	
	private final Script script = Mockito.mock(Script.class);
	
	@Test
	public final void selectedScript() {
		Assert.assertNull(scriptAO.getSelectedScript());
		
		scriptAO.setSelectedScript(script);
		
		Assert.assertEquals(script, scriptAO.getSelectedScript());
	}

}
