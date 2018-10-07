package de.mq.jdbc.artist.support;

import java.time.LocalDate;

import de.mq.jdbc.util.Persistence;

@Persistence("INSERT INTO video (id,publication_date,name) VALUES (:id,:publicationDate,:name)")
public class Video {

	private Long id;
	private LocalDate publicationDate;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
