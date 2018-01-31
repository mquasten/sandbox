package de.mq.analysis.integration.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
class ScriptController {
	
	private final ScriptService scriptService;
	
	@Autowired
	ScriptController(final ScriptService scriptService) {
		this.scriptService=scriptService;
	}

}
