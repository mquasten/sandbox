package de.mq.analysis.integration.support;

import java.util.UUID;

import de.mq.analysis.integration.Script;

class ScriptImpl implements Script {
	
	private final UUID id; 


	private final String code; 
	
	
	ScriptImpl(final UUID id, final String code) {
		this.id = id;
		this.code = code;
	}

	
	public UUID id() {
		return id;
	}


	public String code() {
		return code;
	}

	

}
