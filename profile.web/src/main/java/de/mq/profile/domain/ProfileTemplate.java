package de.mq.profile.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name="Template")
@Table(name="profile_template")
@NamedQueries({@NamedQuery(name=ProfileTemplateRepositoryImpl.ALL_TEMPLATES , query="select t from Template t"), @NamedQuery(name=ProfileTemplateRepositoryImpl.USED_IN_PROFILES , query="select count(p.id) from Profile p where p.profileTemplate.id= :id")})
public class ProfileTemplate {
	
	
	@Id
	@GeneratedValue
	private Long id;
	

	@Column(length=50, nullable=false,unique=true)
	@Pattern(regexp=".*[\\S]+.*" ,message="{mandatory}")
	@NotNull(message="{mandatory}")
	@Size(min=1, message="{mandatory}")
	private String name;
	
	
	@Column(length=250)
	private String description;
	


	@OneToMany(mappedBy="profileTemplate", targetEntity=Feature.class, cascade={CascadeType.ALL}, orphanRemoval=true)
	@Valid
	private Collection<Feature> features = new HashSet<>();
	
	

	@ElementCollection
	@CollectionTable(joinColumns={@JoinColumn(name="profile_template_id")} , name="service")
	@Column(length=50)
	private Set<String> services = new HashSet<>();
	

	
	
	@SuppressWarnings("unused")
	private ProfileTemplate() {
		this.name = null;
	}
	
	public ProfileTemplate(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	
	
	public Long getId() {
		return id;
	}

	
	public final Collection<Feature> getFetures() {
		return Collections.unmodifiableCollection(features);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Collection<String> getServices() {
		return Collections.unmodifiableCollection(services.stream().sorted().collect(Collectors.toList()));
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
	
	
	public Collection<Feature> getFeatures() {
		return features;
	}


	public  void add(Feature feature) {
		this.features.add(feature);
		feature.setProfileTemplate(this);
	}
	
	public  void remove(Feature feature) {
		if(  feature.getFeatureValues().size() > 0 ){
			throw new IllegalArgumentException("Feature can not be removed, it is used");
		}
		this.features.remove(feature);
	
	}
	
	
	public final void addService(final String service ){
		this.services.add(service);
	}
	
	public final void removeService(final String service ){
		
		this.services.remove(service);
	}
	
}
