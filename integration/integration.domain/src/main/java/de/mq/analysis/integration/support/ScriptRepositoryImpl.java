package de.mq.analysis.integration.support;

import java.util.Collection;
import java.util.Collections;

import org.springframework.data.mongodb.core.MongoOperations;

import de.mq.analysis.integration.Script;

class ScriptRepositoryImpl implements ScriptRepository {
	
	private final MongoOperations mongoOperations;
	
	ScriptRepositoryImpl(final MongoOperations mongoOperations){
		this.mongoOperations=mongoOperations;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.support.ScriptRepository#find()
	 */
	@Override
	public final  Collection<Script> find() {
		return Collections.unmodifiableList(mongoOperations.findAll(ScriptImpl.class));
	}
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.support.ScriptRepository#save(de.mq.analysis.integration.Script)
	 */
	@Override
	public final void save(final Script script) {
		mongoOperations.save(script);
	}

	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.ScriptRepository#delete(de.mq.analysis.integration.Script)
	 */
	@Override
	public void delete(final Script script) {
		mongoOperations.remove(script);
	}

}
