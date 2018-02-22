package de.mq.analysis.integration.support;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.Script;

@Controller
class ScriptController {

	private final ScriptService scriptService;

	private final RealFunctionJRubyScriptEngineFactory realFunctionJRubyScriptEngineFactory;

	@Autowired
	ScriptController(final ScriptService scriptService, final RealFunctionJRubyScriptEngineFactory realFunctionJRubyScriptEngineFactory) {
		this.scriptService = scriptService;
		this.realFunctionJRubyScriptEngineFactory = realFunctionJRubyScriptEngineFactory;
	}

	final Collection<Script> scripts() {
		return new ArrayList<Script>(scriptService.scripts());

	}

	final Script save(final ScriptAO scriptAO) {
		final Script currentScript = scriptAO.getCurrentScript();
		scriptService.save(currentScript);
		return currentScript;
	}

	final void delete(final ScriptAO scriptAO) {
		scriptService.delete(scriptAO.getCurrentScript());
	}

	final boolean check(final ScriptAO scriptAO) {
		if (scriptAO.getCurrentScript() == null) {
			return false;
		}
		if (!StringUtils.hasText(scriptAO.getCurrentScript().code())) {
			return false;
		}
		try {
			final RealFunction realFunction = realFunctionJRubyScriptEngineFactory.realFunction(scriptAO.getCurrentScript().code());
			Assert.notNull(realFunction, "RealFunction is mandatory.");
			realFunction.f(Math.random());
			return true;
		} catch (final Exception ex) {
			return false;
		}
	}

}
