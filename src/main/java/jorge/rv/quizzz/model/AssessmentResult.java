package jorge.rv.quizzz.model;

import java.math.BigDecimal;
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

import jorge.rv.quizzz.model.support.Response;

@Entity
@Table(name = "assessment_result")
public class AssessmentResult extends BaseModel implements UserOwned {

	private String text;

	@ManyToOne
	@JsonIgnore
	private Assessment assessment;
	@ManyToOne
	@JsonIgnore
	private User user;


	private String v_2_1;
	private int v_3_1;
	private int v_3_2_1;
	private int v_3_2_2;
	private int v_3_2_3;
	private int v_3_2_4;
	private int v_3_3;
	private int v_3_4;
	private int v_4;
	private int v_4_4;
	private String v_4_4_2;
	private float v_4_5;
	private float v_4_6;
	private int v_4_7;
	private String v_4_8;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@JsonIgnore
	private Boolean isValid = false;

	public Calendar getCreatedDate() {
		return createdDate;
	}


	public String getV_2_1() {
		return v_2_1;
	}


	public void setV_2_1(String v_2_1) {
		this.v_2_1 = v_2_1;
	}


	public int getV_3_1() {
		return v_3_1;
	}


	public void setV_3_1(int v_3_1) {
		this.v_3_1 = v_3_1;
	}


	public int getV_3_2_1() {
		return v_3_2_1;
	}


	public void setV_3_2_1(int v_3_2_1) {
		this.v_3_2_1 = v_3_2_1;
	}


	public int getV_3_2_2() {
		return v_3_2_2;
	}


	public void setV_3_2_2(int v_3_2_2) {
		this.v_3_2_2 = v_3_2_2;
	}


	public int getV_3_2_3() {
		return v_3_2_3;
	}


	public void setV_3_2_3(int v_3_2_3) {
		this.v_3_2_3 = v_3_2_3;
	}


	public int getV_3_2_4() {
		return v_3_2_4;
	}


	public void setV_3_2_4(int v_3_2_4) {
		this.v_3_2_4 = v_3_2_4;
	}


	public int getV_3_3() {
		return v_3_3;
	}


	public void setV_3_3(int v_3_3) {
		this.v_3_3 = v_3_3;
	}


	public int getV_3_4() {
		return v_3_4;
	}


	public void setV_3_4(int v_3_4) {
		this.v_3_4 = v_3_4;
	}


	public int getV_4() {
		return v_4;
	}


	public void setV_4(int v_4) {
		this.v_4 = v_4;
	}


	public int getV_4_4() {
		return v_4_4;
	}


	public void setV_4_4(int v_4_4) {
		this.v_4_4 = v_4_4;
	}


	public String getV_4_4_2() {
		return v_4_4_2;
	}


	public void setV_4_4_2(String v_4_4_2) {
		this.v_4_4_2 = v_4_4_2;
	}





	public float getV_4_5() {
		return v_4_5;
	}


	public void setV_4_5(float v_4_5) {
		this.v_4_5 = v_4_5;
	}


	public float getV_4_6() {
		return v_4_6;
	}


	public void setV_4_6(float v_4_6) {
		this.v_4_6 = v_4_6;
	}


	public int getV_4_7() {
		return v_4_7;
	}


	public void setV_4_7(int v_4_7) {
		this.v_4_7 = v_4_7;
	}


	public String getV_4_8() {
		return v_4_8;
	}


	public void setV_4_8(String v_4_8) {
		this.v_4_8 = v_4_8;
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

	public AssessmentResult(Response resp) {
		this.v_2_1 = resp.getV_2_1();
		this.v_3_1 = resp.getV_3_1();
		this.v_3_2_1 = resp.getV_3_2_1();
		this.v_3_2_2 = resp.getV_3_2_2();
		this.v_3_2_3 = resp.getV_3_2_3();
		this.v_3_2_4 = resp.getV_3_2_4();
		this.v_3_3 = resp.getV_3_3();
		this.v_3_4 = resp.getV_3_4();
		this.v_4 =resp.getV_4();
		this.v_4_4 = resp.getV_4_4();
		this.v_4_4_2 = resp.getV_4_4_2();
		this.setV_4_5(Float.valueOf(resp.getV_4_5()));
		this.setV_4_6(Float.valueOf(resp.getV_4_6()));
		this.setV_4_7(resp.getV_4_7());
		this.setV_4_8(resp.getV_4_8());
	}

	public AssessmentResult() {
	}

}
