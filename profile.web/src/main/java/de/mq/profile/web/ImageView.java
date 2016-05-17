package de.mq.profile.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.mq.profile.domain.Image;
import de.mq.profile.domain.Profile;

@Named
@ViewScoped
public class ImageView {

	private Collection<Image> images = new ArrayList<>();
	
	@Inject
	private HttpSession session;
	
	private Long profileId;
	
	private String name;

	public String getName() {
		return name;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public Collection<Image> getImages() {
		session.setAttribute("images",images);
		return images;
	}

	public void assign(final Profile profile) {
		this.name=profile.getName();
		this.images = profile.getImages();
	}
	
	
	
}
