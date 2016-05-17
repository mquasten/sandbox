package de.mq.profile.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="profile" , uniqueConstraints= {@UniqueConstraint(columnNames={"name" , "profile_template_id" },name="profile_name_template_id")})
@NamedQueries({@NamedQuery(name=ProfileRepositioryImpl.PROFILES_FOR_CRITERIA, query = "select p from Profile p")})
public class Profile {
	
	
	private static final int MAX_INDEX = 99;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length=50, nullable=false)
	@Pattern(regexp=".*[\\S]+.*" ,message="{mandatory}")
	@NotNull(message="{mandatory}")
	@Size(min=1, message="{mandatory}")
	private String name;
	
	@Column(length=250)
	private String description;
	

	

	@ElementCollection
	@CollectionTable(joinColumns={@JoinColumn(name="profile_id")} , name="service")
	@Column(length=50)
	private List<String> services = new ArrayList<>();
	
	

	@OneToMany(mappedBy="profile", targetEntity=FeatureValue.class ,cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<FeatureValue> featureValues = new HashSet<>();
	
	

	@ElementCollection
	@CollectionTable(joinColumns={@JoinColumn(name="profile_id")} , name="profile_image")
	@Valid
	private Collection<Image> images = new ArrayList<>();
	
	
	public Collection<Image> getImages() {
	
		
		return images.stream().sorted((x1,x2)->  nvl(x1.getIndex()) - nvl(x2.getIndex())).collect(Collectors.toList());
	}

	

	private int nvl(final Integer x) {
		return x==null?MAX_INDEX:x;
	}

	@ManyToOne(optional=false, targetEntity=ProfileTemplate.class)
	@JoinColumn(name="profile_template_id")
	@Valid
	private ProfileTemplate profileTemplate;
	
	
	
	@SuppressWarnings("unused")
	private Profile() {
		
	}
	
	public Profile(final String name) {
		this.name=name;
	}
	
	public final String getName() {
		return name;
	}
	
	public final Long getId() {
		return  this.id;
	}
	
	public final ProfileTemplate getProfileTemplate() {
		return this.profileTemplate;
	}

	
	public final void setProfileTemplate(final ProfileTemplate profileTemplate) {
	  this.profileTemplate=profileTemplate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Collection<FeatureValue> getFeatureValues() {
		final Map<Long,FeatureValue> results = new HashMap<>();
		
		this.featureValues.forEach(v -> results.put(v.getFeature().getId(), v));
		
		profileTemplate.getFetures().forEach(f -> {
			if( ! results.containsKey(f.getId()) ) {
				results.put(f.getId(), new FeatureValue(this, f));
			}
			
		});
		
		return Collections.unmodifiableList(results.values().stream().sorted(( x1, x2) -> x1.getFeature().getName().compareToIgnoreCase(x2.getFeature().getName())).collect(Collectors.toList()) );
	}
	
	public final void assign(final Collection<FeatureValue> values) {
		
		final Map<Long,FeatureValue> featureValueMap = new HashMap<Long, FeatureValue>();
		featureValues.forEach(v -> featureValueMap.put(v.getFeature().getId(), v));
		
		values.forEach(v -> {
			if( ! featureValueMap.containsKey(v.getFeature().getId()) ) {
				final FeatureValue value = new FeatureValue(this, v.getFeature());
				featureValues.add(value);
				featureValueMap.put(value.getFeature().getId(), value);
			}
			featureValueMap.get(v.getFeature().getId()).setValueAsString(v.getValueAsString());
		});
	}
	
	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}
	
	public void assign(final String  name, final byte[] content) {
	
		final Optional<Image> existing = images.stream().filter(i -> i.getName().equals(name)).findAny();
		
		if( existing.isPresent()){
			existing.get().setContent(content);
		} else {
			images.add(new Image(name, content));
		}
	
	}

	public  void remove(final Image image) {
		images.remove(image);
	}

}
