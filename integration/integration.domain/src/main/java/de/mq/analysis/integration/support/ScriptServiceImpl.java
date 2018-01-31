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

}
