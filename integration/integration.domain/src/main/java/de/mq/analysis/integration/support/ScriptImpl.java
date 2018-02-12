package de.mq.analysis.integration.support;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import de.mq.analysis.integration.Script;

@Document(collection = "script")
class ScriptImpl implements Script {

	@Id
	private String id;

	private final String code;

	ScriptImpl(final String code) {
		this.code = code;
	}

	public String id() {
		return id;
	}

	public String code() {
		return code;
	}

	@Override
	public int hashCode() {
		if (id == null) {
			return super.hashCode();
		}
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Script)) {
			return super.equals(obj);
		}
		final Script other = (Script) obj;
		if (id == null) {
			return super.equals(obj);
		}
		if (other.id() == null) {
			return super.equals(obj);
		}
		return id.equals(other.id());
	}

}
