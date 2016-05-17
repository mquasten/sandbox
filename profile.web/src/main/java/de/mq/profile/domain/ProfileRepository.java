package de.mq.profile.domain;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

	
	List<Profile> profiles();

	void save(Profile profile);

	Optional<Profile> profile(Long id);
	
	void remove(final Profile profile);

}