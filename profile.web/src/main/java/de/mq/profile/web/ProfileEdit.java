package de.mq.profile.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import javax.validation.constraints.Max;

import de.mq.profile.domain.FeatureValue;
import de.mq.profile.domain.Profile;


@Named
@ViewScoped
public class ProfileEdit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Valid
	private Profile profile;
	
	private Part  file;  
	
	
	private Collection<FeatureValue> featureValues = new ArrayList<>();
	
	
	
	
	

	private Long profileId;
	
	@Inject
	private HttpSession session;

	
	
	
	public Long getProfileId() {
		return profileId;
	}


	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}


	
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Collection<FeatureValue> getFeatureValues() {
		return featureValues;
	}
	
	
	void setFeatureValues(final Collection<FeatureValue> featureValues) {
		
		
		this.featureValues =  (List<FeatureValue>) featureValues;
		
	}
	
	
	@Max(value = 0, message="{feature_errors}")
	public long getErrors() {
		this.featureValues.forEach(featureValue -> featureValue.validate());
		return featureValues.stream().filter(featureValues -> featureValues.getErrors().size() > 0 ).count();
		
	}
	
	public void setErrors(final long errors) {
		
	}
	
	public Part getFile() {
		return file;
	}


	public void setFile(final Part file) {
		this.file = file;
	}

	
	public final Object getImages() {
		
		session.setAttribute("images", profile.getImages());
		
		return session.getAttribute("images");
	}
	
	

}
