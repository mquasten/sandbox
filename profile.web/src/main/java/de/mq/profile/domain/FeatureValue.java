package de.mq.profile.domain;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

import de.mq.profile.domain.Feature.FeatureType;

@Entity(name="FeatureValue")
@Table(name="feature_value")
public class FeatureValue {
	
	@Transient
	private RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().withSilentMode(true).build();
	
	@Transient
	private final Set<String> errors = new HashSet<>();
	
	@Id
	@JoinColumn(name="profile_id")
	private final Profile profile;
	
	
	
	
	@Id
	@JoinColumn(name="feature_id")
	private final  Feature feature;
	


	@Column(length=250,name="value_string")
	private String valueAsString; 
	



	
	
	
	
	@SuppressWarnings("unused")
	private FeatureValue() {
		this.profile = null;
		this.feature = null;
		
	}
	
	public FeatureValue(final Profile profile, final Feature feature) {
		this.profile = profile;
		this.feature = feature;
	
	}
	
	public FeatureValue(final Profile profile, final Feature feature, final String valueAsString) {
		this.profile = profile;
		this.feature = feature;
		this.valueAsString = valueAsString;
	
	}
	

	
	public final  Feature getFeature() {
		return feature;
	}
	
	
	public String getValueAsString() {
		return valueAsString;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}
	
	
	
	public void validate() {
		errors.clear();
		rulesEngine.clearRules();
		
		feature.getFeatureValueRuleTargets().stream().map(target -> newRule(target)).forEach(rule -> rulesEngine.registerRule(rule));
		
		rulesEngine.fireRules();
	}

	private Object newRule(Class<? extends FeatureValueRule> target)  {
		try {
		Constructor<?> constructor = target.getDeclaredConstructor(FeatureValue.class);
		constructor.setAccessible(true); 
		return constructor.newInstance(this);
		} catch( final Exception ex) {
			throw  new IllegalStateException(ex);
		}
	}
	
	public void addError(final String error) {
		this.errors.add(error);
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		if( valueAsString == null) {
			return null;
		}
		if( getFeature().getFeatureType()==FeatureType.String ) {
			return (T) valueAsString;
		}
		if( valueAsString.trim().isEmpty()){
			return null;
		}
	
		try {
			final Constructor<?>  constructor = getFeature().getFeatureType().getType().getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			return (T) constructor.newInstance(valueAsString);
		} catch (final Exception ex) {
			 throw new IllegalArgumentException(ex);
		}
		
		
	}
	
	public boolean canConvert() {
		try {
			final Constructor<?>  constructor = getFeature().getFeatureType().getType().getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			 constructor.newInstance(valueAsString);
			 return true;
		} catch (final Exception ex) {
			 return false;
		}
	}
	
	public Collection<String> getErrors() {
		return errors;
	}
}
