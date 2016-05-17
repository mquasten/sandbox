package de.mq.profile.web;

import java.util.Collection;

import javax.enterprise.context.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.primefaces.event.CloseEvent;

import de.mq.profile.domain.Feature;
import de.mq.profile.domain.ProfileTemplate;

@Named
@ViewScoped
public class ProfileTemplateEdit {
	
	
	interface Operation<T> {
		
		 T execute(Feature feature, String value);
	}
	

	
	public enum ValueOperations {
		PermittedValues( (feature, value) -> feature.getPermittedValues(),(feature, value) -> {   feature.addPermittedValue(value); return null;}, (feature, value) -> {  feature.removePermittedValue(value); return null;}),
		Rules((feature, value) -> feature.getRules(),(feature, value) -> {  feature.addRule(value); return null;}, (feature, value) -> {  feature.removeRule(value); return null;});
		
		private  final Operation<Collection<String>> collectionOperation; 
		
		private  final Operation<Void> deleteOperation; 
		
		private  final Operation<Void> addOperation; 
		
		
		
		private ValueOperations(final Operation<Collection<String>> collectionOperation, final Operation<Void> addOperation, final Operation<Void> deleteOperation) {
			this.collectionOperation=collectionOperation;
			this.addOperation=addOperation;
			this.deleteOperation=deleteOperation;
		}
		
		Collection<String> getValues(final Feature feature) {
			return collectionOperation.execute(feature, null);
		}
		
		void remove(final Feature feature, final String value) {
			 deleteOperation.execute(feature, value);
		}
		
		void add(final Feature feature, final String value) {
			addOperation.execute(feature, value);
		}
		
	}
	
	
	private Long profileTemplateId;
	
	private Feature selectedFeature;
	
	
	private ProfileTemplate profileTemplate ;
	
	
	private String selectedValue;
	
	private ValueOperations valueOperations;

	

	private Feature feature;

	@NotNull(message="{mandatory}")
	@Pattern(regexp=".*[\\S]+.*" ,message="{mandatory}")
	@Size(min=1, message="{mandatory}")
	private String newValue;
	
	
	private String selectedService;
	
	@Pattern(regexp=".*[\\S]+.*" ,message="{mandatory}" , groups={ServiceInputValidationGroup.class})
	@NotNull(message="{mandatory}" , groups={ServiceInputValidationGroup.class})
	@Size(min=1, message="{mandatory}" , groups={ServiceInputValidationGroup.class})
	private String newService;
	
	@Max(value=0 , message="{profiles_exist}" , groups={ProfileCounterValidationGroup.class})
	private Number profileCounter;
	

	

	public String getNewService() {
		return newService;
	}

	public void setNewService(String newService) {
		this.newService = newService;
	}

	public void setProfileTemplateId(Long profileTemplateId) {
		this.profileTemplateId = profileTemplateId;
	}

	public ProfileTemplate getProfileTemplate() {
		return profileTemplate;
	}

	void setProfileTemplate(ProfileTemplate profileTemplate) {
		this.profileTemplate = profileTemplate;
	}

	public Long getProfileTemplateId() {
		return profileTemplateId;
	}
	
	
	public Feature getSelectedFeature() {
		return selectedFeature;
	}

	public void setSelectedFeature(Feature selectedFeature) {
		this.selectedFeature = selectedFeature;
	} 

	
	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	
	public void assignFeatureOperation(final Feature feature, final String operation) {
		this.selectedValue=null;
		this.newValue=null;
		this.feature=feature;
		this.valueOperations=ValueOperations.valueOf(operation);
		
	}
	
	public final Collection<String> getValues() {
		if( valueOperations==null) {
			return null;
		}
		
		if( feature==null) {
			return null;
		}
		
		return valueOperations.getValues(feature);
		
	}
	
	public final void removeValue() {		
		this.valueOperations.remove(feature, selectedValue);
		selectedValue=null;
	}
	
	public final void addValue() {		
		this.valueOperations.add(feature, newValue);
		newValue=null;
	}
	
	
	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public ValueOperations getValueOperations() {
		return valueOperations;
	}
	
	public  void closeDialog(CloseEvent e) {
		
		this.feature=null;
	}
	
	
	public Object getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(final Object selectedService) {
		this.selectedService = (String) selectedService;
	}
	
	void setProfileCounter(Number profileCounter) {
		this.profileCounter = profileCounter;
	}
	
	
}
