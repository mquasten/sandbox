package de.mq.jdbc.artist.support;

import de.mq.jdbc.util.Persistence;

@Persistence("INSERT INTO award (id,name) VALUES (:id,:name)" )
public class Award {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
