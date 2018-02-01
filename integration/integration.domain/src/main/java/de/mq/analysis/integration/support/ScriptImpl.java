package de.mq.analysis.integration.support;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import de.mq.analysis.integration.Script;

@Document(collection="script")
class ScriptImpl implements Script {
	
	@Id
	private  String id; 


	private final String code; 



	ScriptImpl(final String code) {
		this.code = code;
	}

	
	public UUID id() {
		return id != null ? UUID.fromString(id): null;
	}


	public String code() {
		return code;
	}

	

}
