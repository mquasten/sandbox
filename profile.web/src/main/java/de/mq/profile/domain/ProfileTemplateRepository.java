package de.mq.profile.domain;

import java.util.Collection;
import java.util.Optional;

public interface ProfileTemplateRepository {

	public abstract Collection<ProfileTemplate> templates();

	void save(final ProfileTemplate profileTemplate);
	
	void remove(final ProfileTemplate profileTemplate);

	Optional<ProfileTemplate> template(final Long id);

	Number usedByProfiles(ProfileTemplate profileTemplate);

}