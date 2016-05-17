package de.mq.profile.web;

import java.io.Serializable;

import javax.enterprise.context.ViewScoped;
import javax.inject.Named;

import de.mq.profile.domain.ProfileTemplate;

@Named()
@ViewScoped
public class ProfileTemplateSearch  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private  ProfileTemplate selectedProfileTemplate;
	
	public ProfileTemplate getSelectedProfileTemplate() {
		return selectedProfileTemplate;
	}
	public void setSelectedProfileTemplate(ProfileTemplate selectedProfileTemplate) {
		this.selectedProfileTemplate = selectedProfileTemplate;
	} 

}
