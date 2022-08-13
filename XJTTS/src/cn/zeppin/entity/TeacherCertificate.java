package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProjectApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_certificate")
public class TeacherCertificate implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Teacher teacher;
	private String certificate;
	private Timestamp createtime;
	private Integer creator;
	private Timestamp gettime;
	private Short status;
	private String certificationBody;
	private String type;
	
	
	
	
	
	public TeacherCertificate(Integer id, Teacher teacher, String certificate,
			Timestamp createtime, Integer creator, Timestamp gettime,
			Short status, String certificationBody) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.certificate = certificate;
		this.createtime = createtime;
		this.creator = creator;
		this.gettime = gettime;
		this.status = status;
		this.certificationBody = certificationBody;
	}
	
	public TeacherCertificate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER")
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Column(name = "CERTIFICATE", nullable = false)
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "CREATOR", nullable = false)
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
	@Column(name = "GETTIME", nullable = false)
	public Timestamp getGettime() {
		return gettime;
	}
	public void setGettime(Timestamp gettime) {
		this.gettime = gettime;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "CERTIFICATION_BODY", nullable = false)
	public String getCertificationBody() {
		return certificationBody;
	}
	public void setCertificationBody(String certificationBody) {
		this.certificationBody = certificationBody;
	}

	@Column(name = "TYPE", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}