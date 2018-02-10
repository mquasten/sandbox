package de.mq.analysis.integration.support;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.mq.analysis.integration.Script;

@Controller
class ScriptController {
	
	private final ScriptService scriptService;
	
	@Autowired
	ScriptController(final ScriptService scriptService) {
		this.scriptService=scriptService;
	}
	
	
	final Collection<Script> scripts() {
		return new ArrayList<Script> (scriptService.scripts());
		
	}
	
	final Script save(final ScriptAO scriptAO) {
		final Script currentScript = scriptAO.getCurrentScript();
		scriptService.save(currentScript);
		return currentScript;
	}
	
	final void delete(final ScriptAO scriptAO){
		scriptService.delete(scriptAO.getCurrentScript());
	}
	
	

}
