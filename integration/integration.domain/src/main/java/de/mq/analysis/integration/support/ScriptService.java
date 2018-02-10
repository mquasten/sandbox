package de.mq.analysis.integration.support;

import java.util.Collection;

import de.mq.analysis.integration.Script;

interface ScriptService {

	Collection<Script> scripts();
	
	void save(final Script script);

	void delete(Script currentScript);

}