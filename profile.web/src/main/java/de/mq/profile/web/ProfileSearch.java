package de.mq.profile.web;


import java.io.Serializable;

import javax.enterprise.context.ViewScoped;

import javax.inject.Named;

import de.mq.profile.domain.Profile;

@Named()
@ViewScoped
public class ProfileSearch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private  Profile selectedProfile; 
	
	
	
	public Profile getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}
	
	
	
	
}
