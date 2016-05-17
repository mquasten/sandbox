package de.mq.profile.domain;

import java.util.List;
import java.util.Optional;

import javax.enterprise.inject.Typed;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Named("ProfileRepositiory")
@Singleton
@Typed(ProfileRepository.class)
public class ProfileRepositioryImpl implements ProfileRepository  {
	static final String PROFILES_FOR_CRITERIA = "profilesForCriteria";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	

	/* (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileRepository#profiles()
	 */
	@Override
	public final List<Profile> profiles() {
		final TypedQuery<Profile> query = entityManager.createNamedQuery(PROFILES_FOR_CRITERIA, Profile.class);
		return query.getResultList();
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileRepository#profile(java.lang.Long)
	 */
	@Override
	public final  Optional<Profile> profile(final Long id) {
		return Optional.ofNullable(entityManager.find(Profile.class, id));
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileRepository#save(de.mq.profile.domain.Profile)
	 */
	@Override
	public final  void save(Profile profile) {
		entityManager.merge(profile);
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.profile.domain.ProfileRepository#remove(de.mq.profile.domain.Profile)
	 */
	@Override
	public void remove(final Profile profile) {
		final Profile toBeRemoved = entityManager.find(Profile.class, profile.getId());
		if( toBeRemoved==null){
			return;
		}
		entityManager.remove(toBeRemoved);
	}

}
