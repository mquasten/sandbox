package de.mq.profile.web;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import de.mq.profile.domain.Profile;
import de.mq.profile.domain.ProfileRepository;

@Named
@Singleton
public class ImageController {
	
	@Inject
	private ProfileRepository profileRepository;
	
	public final void init(final ImageView imageView) {
		
		final Optional<Profile> profile = profileRepository.profile(imageView.getProfileId());
		if(! profile.isPresent()){
			return ;
		}
		imageView.assign(profile.get());
	}

}
