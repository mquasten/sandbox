package de.mq.jdbc.artist;

import de.mq.jdbc.util.Persistence;

@Persistence("INSERT INTO artist (id,firstname,lastname,hotscore) VALUES (:id,:firstname,:lastname,:hotscore)")
public class Artist {

	
	private Long id;
	private String firstname;
	private String lastname;
	private Integer hotscore;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getHotscore() {
		return hotscore;
	}

	public void setHotscore(Integer hotscore) {
		this.hotscore = hotscore;
	}

	

}
