package com.evn.web.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assessment_user")
public class AssessmentUser extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
    @ManyToOne
    @JoinColumn(name = "assessment_id")
	private Assessment assessment;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Assessment getAssessment() {
		return assessment;
	}
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
	
}
