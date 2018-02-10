package de.mq.analysis.integration.support;

import java.util.Collection;

import de.mq.analysis.integration.Script;

class ScriptServiceImpl implements ScriptService {
	
	private final ScriptRepository scriptRepository;
	
	ScriptServiceImpl(final ScriptRepository scriptRepository) {
		this.scriptRepository=scriptRepository;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.support.ScriptService#scripts()
	 */
	@Override
	public final Collection<Script> scripts() {
		return scriptRepository.find();
	}

	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.ScriptService#save(de.mq.analysis.integration.Script)
	 */
	@Override
	public void save(final Script script) {
		scriptRepository.save(script);
	}

	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.ScriptService#delete(de.mq.analysis.integration.Script)
	 */
	@Override
	public void delete(Script script) {
		scriptRepository.delete(script);
		
	}

}
