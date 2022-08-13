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
 * 教师调入调出池
 * @author Administrator
 *
 */
@Entity
@Table(name = "teacher_adjust")
public class TeacherAdjust implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Teacher teacher;
	private Organization oorganization;
	private Organization norganization;
	private Integer creator;
	private Short creatorType;
	private Integer checker;
	private Short checkerType;
	private Timestamp createtime;
	private Short status;
	private Timestamp checktime;
	

	public TeacherAdjust(Integer id, Teacher teacher,
			Organization oorganization, Organization norganization,
			Integer creator, Short creatorType, Integer checker,
			Short checkerType, Timestamp createtime, Short status, Timestamp checktime) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.oorganization = oorganization;
		this.norganization = norganization;
		this.creator = creator;
		this.creatorType = creatorType;
		this.checker = checker;
		this.checkerType = checkerType;
		this.createtime = createtime;
		this.status = status;
		this.checktime = checktime;
	}

	public TeacherAdjust() {
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
	@JoinColumn(name = "TEACHER", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}


	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "O_ORGANIZATION", nullable = false)
	public Organization getOorganization() {
		return oorganization;
	}


	public void setOorganization(Organization oorganization) {
		this.oorganization = oorganization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ORGANIZATION")
	public Organization getNorganization() {
		return norganization;
	}


	public void setNorganization(Organization norganization) {
		this.norganization = norganization;
	}

	@Column(name = "CREATOR", nullable = false)
	public Integer getCreator() {
		return creator;
	}


	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_TYPE", nullable = false)
	public Short getCreatorType() {
		return creatorType;
	}


	public void setCreatorType(Short creatorType) {
		this.creatorType = creatorType;
	}

	@Column(name = "CHECKER")
	public Integer getChecker() {
		return checker;
	}


	public void setChecker(Integer checker) {
		this.checker = checker;
	}

	@Column(name = "CHECKER_TYPE")
	public Short getCheckerType() {
		return checkerType;
	}


	public void setCheckerType(Short checkerType) {
		this.checkerType = checkerType;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}

	
	@Column(name = "CHECKTIME")
	public Timestamp getChecktime() {
		return checktime;
	}
	

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}
	
	
}