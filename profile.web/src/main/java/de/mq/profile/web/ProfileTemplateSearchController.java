package de.mq.profile.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


import de.mq.profile.domain.ProfileTemplate;
import de.mq.profile.domain.ProfileTemplateRepository;

@Named
@Singleton
public class ProfileTemplateSearchController {
	
	
	private final ProfileTemplateRepository profileTemplateRepository;
	
	@Inject
	public ProfileTemplateSearchController(ProfileTemplateRepository profileTemplateRepository) {
	
		this.profileTemplateRepository = profileTemplateRepository;
	}

	
	
	public List<ProfileTemplate> templates( ) {
		return profileTemplateRepository.templates().stream().sorted(((x1,x2) -> x1.getName().compareTo(x2.getName()))).collect(Collectors.toList());
		
	}

}
