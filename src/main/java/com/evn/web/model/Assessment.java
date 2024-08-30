package com.evn.web.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Entity
@Table(name = "assessment")
public class Assessment  implements UserOwned {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "active")
	private Boolean active;
	@OneToOne
	@JsonIgnore
	private User createdBy;

	@Size(min = 2, max = 500, message = "The name must be between 2 and 500 messages.")
	@NotNull(message = "Please provide a name")
	private String name;

	@Size(max = 500, message = "The description can't be longer than 500 characters.")
	@NotNull(message = "Please, provide a description")
	private String description;

	@OneToOne
	private Idea idea;

	@OneToOne
	private Council council;
	@OneToOne
	private Level level;
	@OneToOne
	private Period period;

    @OneToMany(mappedBy = "assessment")
    List<AssessmentUser> assessmentUsers;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@Column(name = "submited_date")
	private Calendar submitedDate;

	@Column(name = "org1")
	private String org1;
	@Column(name = "org2")
	private String org2;
	private Boolean isPublished = false;

	
	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrg1() {
		return org1;
	}

	public void setOrg1(String org1) {
		this.org1 = org1;
	}

	public String getOrg2() {
		return org2;
	}

	public void setOrg2(String org2) {
		this.org2 = org2;
	}

	public Calendar getSubmitedDate() {
		if(submitedDate == null)
			submitedDate = Calendar.getInstance();
		return submitedDate;
	}

	public void setSubmitedDate(Calendar submitedDate) {
		this.submitedDate = submitedDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getName() {
		return name;
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

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Council getCouncil() {
		return council;
	}

	public void setCouncil(Council council) {
		this.council = council;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public List<AssessmentUser> getAssessmentUsers() {
		return assessmentUsers;
	}

	public void setAssessmentUsers(List<AssessmentUser> assessmentUsers) {
		this.assessmentUsers = assessmentUsers;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Assessment() {
		super();
	}

}
