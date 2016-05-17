package de.mq.profile.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
public class Image {
	private final String name;
	

	@Lob()
	@Basic(fetch=FetchType.LAZY)
	@Column(/*columnDefinition="bytea"*/ columnDefinition="blob")
	private byte[] content;
	
	
	@Column(name="image_index")
	@Min(value=0, message="{invalid_range_min}")
	@Max(value=99, message="{invalid_range_max}")
	private Integer index;
	
	
	


	public String getName() {
		return name;
	}



	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public Image(final String name, final byte[] content) {
		this.name = name;
		this.content = content;
	}
	
	public Image(final String name) {
		this.name = name;
	}
	
	@SuppressWarnings("unused")
	private Image() {
		this.name=null;
	}
	
	
	public Integer getIndex() {
		return index;
	}



	public void setIndex(Integer index) {
		this.index = index;
	}


}
