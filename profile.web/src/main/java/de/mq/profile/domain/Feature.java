package de.mq.profile.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import de.mq.profile.domain.rule.CreateTypeRule;
import de.mq.profile.domain.rule.MandatoryRule;
import de.mq.profile.domain.rule.MinMaxRangeRule;



@Entity(name="Feature")
@Table(name="feature")
public class Feature {
	
	public enum FeatureType {
		String(String.class),
		Integer(Integer.class),
		Double(Double.class);
		
		private final Class<?> type;
		private FeatureType(final Class<?> type) {
			this.type=type;
		}
		
		public Class<?> getType() {
			return type;
		}
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	
	

	@Column(length=50, nullable=false)
	@NotNull(message="{mandatory}")
	@Pattern(regexp=".*[\\S]+.*" ,message="{mandatory}")
	@Size(min=1, message="{mandatory}")
	private  String name;
	

	@Enumerated(EnumType.STRING)
	@Column(length=50, nullable=false)
	
	@NotNull(message="{mandatory}")
	private  FeatureType type;
	
	
	@ElementCollection()
	@CollectionTable(name="rule", joinColumns={@JoinColumn(name="feature_id")})
	@Column(name="rules", length=100)
	private final Set<String> rules = new HashSet<>();

	

	@Column(name="min_range")
	private Double minRange;
	
	@Column(name="max_range")
	private Double maxRange;
	
	private boolean mandatory=false;
	

	

	@JoinColumn(name="template_id")
	private ProfileTemplate profileTemplate;
	
	
	

	@OneToMany(mappedBy="feature", targetEntity=FeatureValue.class)
	private Collection<FeatureValue> featureValues = new HashSet<>();
	

	@ElementCollection()
	@CollectionTable(name="permitted_value", joinColumns={@JoinColumn(name="feature_id")})
	@Column(name="permitted_value", length=50)
	private Set<String> permittedValues = new HashSet<>();


	public Feature(final String name, final FeatureType type  ) {
		this.name=name;
		this.type=type;
	}
	
	
	public Feature() {
		this.name=null;
		this.type=null;
	}
	
	public String getName() {
		return name;
	}
	
	
	public Collection<String> getPermittedValues() {
		return Collections.unmodifiableList(permittedValues.stream().sorted().collect(Collectors.toList()));
	}
	
	public Long getId() {
		return id;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
   
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public FeatureType getFeatureType() {
		return this.type;
	}
	
	public void setFeatureType(final FeatureType featureType) {
		 this.type=featureType;
	}
	
	@SuppressWarnings("unchecked")
	Collection<Class<? extends FeatureValueRule>> getFeatureValueRuleTargets() {
		final Collection<Class<? extends FeatureValueRule>> results = new HashSet<>();
		results.add(CreateTypeRule.class);
		if( mandatory) {
			results.add(MandatoryRule.class);
		}
		
		if ((( minRange != null) || (maxRange != null))&&(this.type != FeatureType.String)) {
			results.add(MinMaxRangeRule.class);
		}
		results.addAll(rules.stream().filter(value -> value != null ).filter(value -> ! value.trim().isEmpty()).map( value -> classForNmae(value)).filter(clazz ->  cast(clazz)).map(value -> (Class<? extends FeatureValueRule>)value).collect(Collectors.toSet()));
		return results;
	}

	private boolean  cast(Class<?> clazz) {
		try {
		clazz.asSubclass(FeatureValueRule.class);
		return  true;
		} catch( ClassCastException ex) {
			return false;
		}
	}

	private Class<?> classForNmae(final String value)  {
		try {
			return Class.forName(value.trim());
		} catch (final ClassNotFoundException ex) {
		throw  new IllegalArgumentException(ex);
		}
	} 
	
	public Double getMinRange() {
		return minRange;
	}

	public void setMinRange(Double minRange) {
		this.minRange = minRange;
	}

	public Double getMaxRange() {
		return maxRange;
	}
	
	public void setMaxRange(Double maxRange) {
		this.maxRange = maxRange;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Collection<FeatureValue> getFeatureValues() {
		return featureValues;
	}


	
	void setProfileTemplate(ProfileTemplate profileTemplate) {
		this.profileTemplate = profileTemplate;
	}
	
	public void removePermittedValue(final String value) {
		permittedValues.remove(value);
	}
	
	public void addPermittedValue(final String value) {
		
		permittedValues.add(value);
	}
	
	
	public Collection<String> getRules() {
		return Collections.unmodifiableCollection(rules);
	}
	
	public void removeRule(final String value) {
		rules.remove(value);
	}
	
	public void addRule(final String value) {
		rules.add(value);
	}
	
}
