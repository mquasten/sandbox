package de.mq.profile.web;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;





import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;





import de.mq.profile.domain.Feature;
import de.mq.profile.domain.Feature.FeatureType;
import de.mq.profile.domain.ProfileTemplate;
import de.mq.profile.domain.ProfileTemplateRepository;

@Named
@Singleton
public class ProfileTemplateController {
	
	private static final String PROFILE_TEMPLATES_PAGE = "profileTemplates";


	private final ProfileTemplateRepository profileTemplateRepository ; 
	
	
   private final Validator validator;
	
	
	@Inject
	public ProfileTemplateController(ProfileTemplateRepository profileTemplateRepository, final Validator validator) {
	
		this.profileTemplateRepository = profileTemplateRepository;
		this.validator=validator;
	}

	public void init(final ProfileTemplateEdit profileTemplateEdit) {
		
	
		if( profileTemplateEdit.getProfileTemplateId() == null) {
			profileTemplateEdit.setProfileTemplate(new ProfileTemplate(""));
			return;
		}
		
	
		final Optional<ProfileTemplate> template = profileTemplateRepository.template(profileTemplateEdit.getProfileTemplateId());
		templateExistsGuard(profileTemplateEdit, template);
		profileTemplateEdit.setProfileTemplate(template.get());
	
		
	}
	
	private void templateExistsGuard(ProfileTemplateEdit templateEdit, final Optional<ProfileTemplate> result) {
		if (!result.isPresent()) {
			throw new IllegalArgumentException("ProfileTemplate not found id " + templateEdit.getProfileTemplateId());
		}
	}
	
	@Transactional(TxType.REQUIRED)
	public String save(ProfileTemplateEdit  profileTemplateEdit) {
		profileTemplateRepository.save(profileTemplateEdit.getProfileTemplate());
		return PROFILE_TEMPLATES_PAGE;
	}
	
	
	public List<FeatureType> featureTypes() {
		return Arrays.asList(FeatureType.values());
	}
	
	
	public void addFeature(ProfileTemplate profileTemplate) {
		profileTemplate.add(new Feature("???", FeatureType.String));
	}
	
	public void removeFeature(final ProfileTemplateEdit profileTemplateEdit) {
		if( profileTemplateEdit.getSelectedFeature() == null) {
			return ;
		} 
		
		
		
		profileTemplateEdit.getProfileTemplate().remove(profileTemplateEdit.getSelectedFeature());
		profileTemplateEdit.setSelectedFeature(null);
	}
	
	
	public void addService(final ProfileTemplateEdit profileTemplateEdit, final FacesContext facesContext) {
		if( ! validateFields(profileTemplateEdit, ServiceInputValidationGroup.class, facesContext, false)) {
			return;
		}
		
		profileTemplateEdit.getProfileTemplate().addService(profileTemplateEdit.getNewService());
		profileTemplateEdit.setNewService(null);
	}

	private boolean  validateFields(final ProfileTemplateEdit profileTemplateEdit, final Class<?> validationGroup, FacesContext facesContext, boolean isGlobal) {
		final Set<ConstraintViolation<ProfileTemplateEdit>>  errors = validator.validate(profileTemplateEdit, validationGroup);
		
		errors.stream().forEach(cv -> {
			 
			 final String clientId = isGlobal ? null  : clientId(facesContext, cv);
			 final FacesMessage facesMessage = new FacesMessage(cv.getMessage());
			 facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			
		
			
		
			
			 
			
			 facesContext.addMessage(clientId,facesMessage);
			 
		});
		return  errors.isEmpty();
	}

	private String clientId(FacesContext facesContext, ConstraintViolation<ProfileTemplateEdit> cv) {
		return ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getParameterMap().keySet().stream().filter(key -> key.endsWith(cv.getPropertyPath().toString())).findAny().orElse(cv.getPropertyPath().toString());
	}


	public void removeService(final ProfileTemplateEdit profileTemplateEdit) {
		profileTemplateEdit.getProfileTemplate().removeService(profileTemplateEdit.getSelectedService().toString());
		
		profileTemplateEdit.setSelectedService(null);
	}
	
	
	@Transactional()
	public String remove(ProfileTemplateEdit profileTemplateEdit, FacesContext facesContext) {
		profileTemplateEdit.setProfileCounter(profileTemplateRepository.usedByProfiles(profileTemplateEdit.getProfileTemplate()));
		
		if(! validateFields(profileTemplateEdit, ProfileCounterValidationGroup.class, facesContext, true) ) {
			return null;
		}
		profileTemplateRepository.remove(profileTemplateEdit.getProfileTemplate());
		return PROFILE_TEMPLATES_PAGE;
	}

}



interface  ServiceInputValidationGroup { }

interface ProfileCounterValidationGroup {}
