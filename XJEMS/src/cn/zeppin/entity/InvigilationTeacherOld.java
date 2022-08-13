package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invigilation_teacher_old")
public class InvigilationTeacherOld {
	private int id;
	private String idcard;
	private String name;
	private String sex;
	private String mobile;
	private String type;
	private String inschooltime;
	private String integral;
	private String status;
	private String organization;
	private String major;
	private String ethnic;

	public InvigilationTeacherOld() {
		super();
	}

	public InvigilationTeacherOld(int id, String idcard, String name, String sex, String mobile, String type,
			String inschooltime, String integral, String status, String organization, String major, String ethnic) {
		super();
		this.id = id;
		this.idcard = idcard;
		this.name = name;
		this.sex = sex;
		this.mobile = mobile;
		this.type = type;
		this.inschooltime = inschooltime;
		this.integral = integral;
		this.status = status;
		this.organization = organization;
		this.major = major;
		this.ethnic = ethnic;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "IDCARD", length = 20)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "INSHCOOL_TIME")
	public String getInschooltime() {
		return inschooltime;
	}

	public void setInschooltime(String inschooltime) {
		this.inschooltime = inschooltime;
	}

	@Column(name = "INTEGRAL")
	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ORGANIZATION")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(name = "MAJOR")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "ETHNIC")
	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

}
