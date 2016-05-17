package de.mq.profile.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import de.mq.profile.domain.FeatureValue;
import de.mq.profile.domain.Profile;
import de.mq.profile.domain.ProfileRepository;
import de.mq.profile.domain.ProfileTemplate;
import de.mq.profile.domain.ProfileTemplateRepository;

@Named
@Singleton
public class ProfileController {

	private static final String PROFILES_PAGE = "profiles";

	private static final String MSG_BUNDLE_NAME = "msg";

	@ViewScoped
	@Inject
	private ProfileTemplateRepository profileTemplateRepository;

	@Inject
	private ProfileRepository profileRepository;

	public final Map<String, Long> templates() {
		final Map<String, Long> templates = new TreeMap<>();

		profileTemplateRepository.templates().forEach(t -> templates.put(t.getName(), t.getId()));
		return templates;
	}

	public final void init(final ProfileEdit profileEdit) {

		if (profileEdit.getProfileId() == null) {
			final Profile profile = newProfile();
			profileEdit.setProfile(profile);
			return;
		}

		final Optional<Profile> result = profileRepository.profile(profileEdit.getProfileId());
		profileExistsGuard(profileEdit, result);
		profileEdit.setProfile(result.get());

		final Collection<FeatureValue> featureValues = result.get().getFeatureValues();

		profileEdit.setFeatureValues(featureValues);

	}

	private Profile newProfile() { 
		final Profile profile = new Profile("");
		profile.setProfileTemplate(new ProfileTemplate(""));
		return profile;
	}

	private void profileExistsGuard(ProfileEdit profileEdit, final Optional<Profile> result) {
		if (!result.isPresent()) {
			throw new IllegalArgumentException("Profile not found id " + profileEdit.getProfileId());
		}
	}

	@Transactional(TxType.REQUIRED)
	public String save(final ProfileEdit profileEdit) {

		
		
		
		final Profile profile = profileEdit.getProfile();

		if (profileEdit.getProfileId() != null) {
			profile.assign(profileEdit.getFeatureValues());
		}
		
		profileRepository.save(profile);
		
	
		
		return PROFILES_PAGE;
	}

	public String message(final Collection<String> keys, final FacesContext ctx) {
	
		final StringBuilder builder = new StringBuilder();
		keys.forEach(key -> {
			if (builder.length() != 0) {
				builder.append("<br/>");
			}
			builder.append(message(key, ctx));
		});
		return builder.toString();
	}

	private String message(final String key, final FacesContext ctx) {
		final ResourceBundle bundle = ctx.getApplication().getResourceBundle(ctx, MSG_BUNDLE_NAME);
		if (bundle == null) {
			throw new IllegalArgumentException(String.format("Bundle %s not found", MSG_BUNDLE_NAME));
		}

		final String[] vars = key.split("[,]");
		final String text = fromBundle(key, bundle, vars);

		if (vars.length == 1) {
			return text;
		}

		final Object[] params = new Object[vars.length - 1];
		System.arraycopy(vars, 1, params, 0, vars.length - 1);
		return MessageFormat.format(text, params);
	}

	private String fromBundle(final String key, final ResourceBundle bundle, final String[] vars) {
		try {

			return bundle.getString(vars[0]);

		} catch (Exception ex) {

			return String.format("?%s?", key);
		}
	}
	
	public void upload(final ProfileEdit profileEdit) throws IOException{
  
         final byte[] content=new byte[(int)profileEdit.getFile().getSize()];
         final InputStream in=profileEdit.getFile().getInputStream();
         in.read(content);   
         
         profileEdit.getProfile().assign(profileEdit.getFile().getSubmittedFileName(),content );
         
        
  }
	
	

	

 @Transactional
 public String delete(Profile profile) {
	 profileRepository.remove(profile);
	 return PROFILES_PAGE;
 }

}
