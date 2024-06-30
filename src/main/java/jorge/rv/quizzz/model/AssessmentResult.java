package jorge.rv.quizzz.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "assessment_result")
public class AssessmentResult extends BaseModel implements UserOwned {

	@Size(min = 2, max = 3000, message = "The question should be between 2 and 3000 characters")
	@NotNull(message = "Question text not provided")
	private String text;

	@ManyToOne
	@JsonIgnore
	private Assessment assessment;
	@ManyToOne
	@JsonIgnore
	private User user;



	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@JsonIgnore
	private Boolean isValid = false;

	public Calendar getCreatedDate() {
		return createdDate;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	@JsonIgnore
	public User getUser() {
		return assessment.getUser();
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}


	public Assessment getAssessment() {
		return assessment;
	}


	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}



}
