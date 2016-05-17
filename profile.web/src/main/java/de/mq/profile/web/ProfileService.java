package de.mq.profile.web;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import de.mq.profile.domain.Profile;
import de.mq.profile.domain.ProfileRepository;


@Path("service")
@Stateless
public class ProfileService {
	
	@Inject
	private ProfileRepository profileRepository;
	
	
	
	
	 @GET
	 @Path("profiles")
	 @Produces(MediaType.APPLICATION_JSON)
	public List<Profile> profiles() {
		return profileRepository.profiles();
		
	}
	 
	 
	 @GET
	 @Path("profiles/{profileId}")
	 @Produces(MediaType.APPLICATION_JSON)
	public Profile profile( @PathParam("profileId") final Long id) {
	
		
		final Optional<Profile> profile = profileRepository.profile(id);
		if(! profile.isPresent()){
			throw new WebApplicationException(404);
		}
		return profile.get();
		
	}
	 
}
	 

	 
	
