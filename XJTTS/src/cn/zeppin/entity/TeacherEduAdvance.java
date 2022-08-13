package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ProjectApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_edu_advance")
public class TeacherEduAdvance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Teacher teacher;
	private String graduation;
	private String major;
	private Timestamp starttime;
	private Timestamp endtime;
	private String certificate;
	private Short finalStatus;
	private Short status;
	private Timestamp createtime;
	private EductionBackground educationBackground;
	private String oldEducationBack;
	
	private Set<TeacherEduAdvanceAdu> teacherEduAdvanceAdus = new HashSet<TeacherEduAdvanceAdu>();
	private Set<TeacherEduEvidence> teacherEduEvidences = new HashSet<TeacherEduEvidence>();
	
	
	public TeacherEduAdvance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEduAdvance(Integer id, Teacher teacher, String graduation,
			String major, Timestamp starttime, Timestamp endtime,
			String certificate, Short finalStatus, Short status,
			Timestamp createtime, EductionBackground educationBackground,
			String oldEducationBack) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.graduation = graduation;
		this.major = major;
		this.starttime = starttime;
		this.endtime = endtime;
		this.certificate = certificate;
		this.finalStatus = finalStatus;
		this.status = status;
		this.createtime = createtime;
		this.educationBackground = educationBackground;
		this.oldEducationBack = oldEducationBack;
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


	@Column(name = "GRADUATION", nullable = false)
	public String getGraduation() {
		return graduation;
	}


	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}


	@Column(name = "MAJOR", nullable = false)
	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	@Column(name = "STARTTIME", length = 19, nullable = false)
	public Timestamp getStarttime() {
		return starttime;
	}


	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}


	@Column(name = "ENDTIME", length = 19, nullable = false)
	public Timestamp getEndtime() {
		return endtime;
	}


	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}


	@Column(name = "CERTIFICATE", nullable = false)
	public String getCertificate() {
		return certificate;
	}


	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}


	@Column(name = "FINAL_STATUS", nullable = false)
	public Short getFinalStatus() {
		return finalStatus;
	}


	public void setFinalStatus(Short finalStatus) {
		this.finalStatus = finalStatus;
	}


	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}


	@Column(name = "CREATETIME", length = 19, nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EDUCATION_BACKGROUND")
	public EductionBackground getEducationBackground() {
		return educationBackground;
	}


	public void setEducationBackground(EductionBackground educationBackground) {
		this.educationBackground = educationBackground;
	}


	@Column(name = "OLD_EDUCATION_BACKGROUND")
	public String getOldEducationBack() {
		return oldEducationBack;
	}


	public void setOldEducationBack(String oldEducationBack) {
		this.oldEducationBack = oldEducationBack;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacherEduAdvance")
	public Set<TeacherEduEvidence> getTeacherEduEvidences() {
		return teacherEduEvidences;
	}

	public void setTeacherEduEvidences(Set<TeacherEduEvidence> teacherEduEvidences) {
		this.teacherEduEvidences = teacherEduEvidences;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacherEduAdvance")
	public Set<TeacherEduAdvanceAdu> getTeacherEduAdvanceAdus() {
		return teacherEduAdvanceAdus;
	}

	public void setTeacherEduAdvanceAdus(
			Set<TeacherEduAdvanceAdu> teacherEduAdvanceAdus) {
		this.teacherEduAdvanceAdus = teacherEduAdvanceAdus;
	}
	
	
}