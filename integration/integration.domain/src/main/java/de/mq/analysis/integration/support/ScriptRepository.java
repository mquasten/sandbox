package de.mq.analysis.integration.support;

import java.util.Collection;

import de.mq.analysis.integration.Script;

interface ScriptRepository {

	Collection<Script> find();

	void save(Script script);

}