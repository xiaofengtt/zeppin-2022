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

@Entity
@Table(name = "count_teacher_year")
public class CountTeacherYear implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	// Fields
    private Integer id;
    private String year;
    private Organization organization;
    private Subject subject;
    private Grade grade;
    private Integer teacherCount;
    private Short status;
    private Timestamp creattime;

    // Constructors
    public CountTeacherYear(){
    }

    public CountTeacherYear(String year, Organization organization, Subject subject, Grade grade,
    		Integer teacherCount, Short status, Timestamp creattime){
		this.year = year;
		this.organization = organization;
		this.teacherCount = teacherCount;
		this.status = status;
		this.creattime = creattime;
    }

    // Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId(){
		return this.id;
	}

    public void setId(Integer id){
    	this.id = id;
    }

	@Column(name = "YEAR", nullable = false, length = 4)
	public String getYear(){
    	return this.year;
	}

	public void setYear(String year) {
    	this.year = year;
	}
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATION", nullable = false)
	public Organization getOrganization(){
		return this.organization;
	}
	
	public void setOrganization(Organization organization){
		this.organization = organization;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBJECT")
	public Subject getSubject(){
		return this.subject;
	}
	
	public void setSubject(Subject subject){
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRADE")
	public Grade getGrade(){
		return this.grade;
	}
	
	public void setGrade(Grade grade){
		this.grade = grade;
	}
	
	@Column(name = "TEACHER_COUNT", nullable = false)
	public Integer getTeacherCount(){
		return this.teacherCount;
	}
	
	public void setTeacherCount(Integer teacherCount){
		this.teacherCount = teacherCount;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "CREATTIME", nullable = false, length = 19)
	public Timestamp getCreattime() {
		return this.creattime;
	}

	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}
}