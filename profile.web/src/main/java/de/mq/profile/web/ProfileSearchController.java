package de.mq.profile.web;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import de.mq.profile.domain.Profile;
import de.mq.profile.domain.ProfileRepository;

@Named
@Singleton
public class ProfileSearchController {
	
	@Inject
	private ProfileRepository profileRepository;
	
	public final List<Profile> profiles() {
		
		return profileRepository.profiles();
	}
	
	public final void view(final Profile profile) {
		System.out.println(profile.getId());
	}
	
	

}
