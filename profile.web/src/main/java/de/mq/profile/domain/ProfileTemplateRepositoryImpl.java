package de.mq.profile.domain;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.inject.Typed;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;

@Named("ProfileTemlateRepositiory")
@Singleton
@Typed(ProfileTemplateRepository.class)

public class ProfileTemplateRepositoryImpl implements ProfileTemplateRepository {
	
	static final String ALL_TEMPLATES = "allTemplates";
	
	static final String USED_IN_PROFILES = "usedInProfiles";
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	/* (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileTemplateRepository#templates()
	 */
	@Override
	public final Collection<ProfileTemplate> templates() {
		final TypedQuery<ProfileTemplate> query = entityManager.createNamedQuery(ALL_TEMPLATES, ProfileTemplate.class);
		return query.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileTemplateRepository#save(de.mq.profile.domain.ProfileTemplate)
	 */
	@Override
	public final  void save(ProfileTemplate profileTemplate) {
		entityManager.merge(profileTemplate);
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileTemplateRepository#template(java.lang.Long)
	 */
	@Override
	public final  Optional<ProfileTemplate> template(final Long id) {
		return Optional.ofNullable(entityManager.find(ProfileTemplate.class, id));
		
	}

	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileTemplateRepository#usedByProfiles(de.mq.profile.domain.ProfileTemplate)
	 */
	@Override
	public Number usedByProfiles(final ProfileTemplate profileTemplate) {
		final TypedQuery<Number> counterQuery = entityManager.createNamedQuery(USED_IN_PROFILES,Number.class);
		counterQuery.setParameter("id", profileTemplate.getId());
		return counterQuery.getSingleResult();
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileTemplateRepository#remove(de.mq.profile.domain.ProfileTemplate)
	 */
	@Override
	public void remove(final ProfileTemplate profileTemplate) {
		ProfileTemplate toBeDeleted = entityManager.find(ProfileTemplate.class, profileTemplate.getId());
		if(toBeDeleted==null){
			return;
		}
		entityManager.remove(toBeDeleted);
		
	}

}
