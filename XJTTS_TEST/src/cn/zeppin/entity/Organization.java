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
 * Organization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "organization")
public class Organization implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6417899019809628427L;
	private Integer id;
	private Grade grade;
	private Area area;
	private OrganizationLevel organizationLevel;
	private Organization organization;
	private String name;
	private String shortName;
	private Boolean isschool;
	private String adress;
	private String contacts;
	private String phone;
	private String fax;
	private Short status;
	private Integer creator;
	private Timestamp creattime;
	private String pinying;
	private String scode;
	private Set<Organization> organizations = new HashSet<Organization>(0);
	private Set<AssignTeacherTask> assignTeacherTasksForGOrganization = new HashSet<AssignTeacherTask>(0);
	private Set<AssignTeacherTask> assignTeacherTasksForPOrganization = new HashSet<AssignTeacherTask>(0);
	private Set<TeacherTrainingRecords> teacherTrainingRecordses = new HashSet<TeacherTrainingRecords>(0);
	private Set<ProjectAdmin> projectAdmins = new HashSet<ProjectAdmin>(0);
	private Set<Teacher> teachers = new HashSet<Teacher>(0);

	// Constructors

	/** default constructor */
	public Organization() {
	}

	/** minimal constructor */
	public Organization(Area area, OrganizationLevel organizationLevel, String name, String shortName, Boolean isschool, Short status, Timestamp creattime) {
		this.area = area;
		this.organizationLevel = organizationLevel;
		this.name = name;
		this.shortName = shortName;
		this.isschool = isschool;
		this.status = status;
		this.creattime = creattime;
	}

	/** full constructor */
	public Organization(Grade grade, Area area, OrganizationLevel organizationLevel, Organization organization, String name, String shortName, Boolean isschool, String adress, String contacts, String phone, String fax, Short status, Integer creator, Timestamp creattime, String pinying,
			String scode, Set<Organization> organizations, Set<AssignTeacherTask> assignTeacherTasksForGOrganization, Set<AssignTeacherTask> assignTeacherTasksForPOrganization, Set<TeacherTrainingRecords> teacherTrainingRecordses, Set<ProjectAdmin> projectAdmins, Set<Teacher> teachers) {
		this.grade = grade;
		this.area = area;
		this.organizationLevel = organizationLevel;
		this.organization = organization;
		this.name = name;
		this.shortName = shortName;
		this.isschool = isschool;
		this.adress = adress;
		this.contacts = contacts;
		this.phone = phone;
		this.fax = fax;
		this.status = status;
		this.creator = creator;
		this.creattime = creattime;
		this.pinying = pinying;
		this.scode = scode;
		this.organizations = organizations;
		this.assignTeacherTasksForGOrganization = assignTeacherTasksForGOrganization;
		this.assignTeacherTasksForPOrganization = assignTeacherTasksForPOrganization;
		this.teacherTrainingRecordses = teacherTrainingRecordses;
		this.projectAdmins = projectAdmins;
		this.teachers = teachers;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRADE")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AREA", nullable = false)
	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEVEL", nullable = false)
	public OrganizationLevel getOrganizationLevel() {
		return this.organizationLevel;
	}

	public void setOrganizationLevel(OrganizationLevel organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SHORT_NAME", nullable = false, length = 200)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "ISSCHOOL", nullable = false)
	public Boolean getIsschool() {
		return this.isschool;
	}

	public void setIsschool(Boolean isschool) {
		this.isschool = isschool;
	}

	@Column(name = "ADRESS", length = 200)
	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Column(name = "CONTACTS", length = 50)
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "FAX", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATOR")
	public Integer getCreator() {
		return this.creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CREATTIME", nullable = false, length = 19)
	public Timestamp getCreattime() {
		return this.creattime;
	}

	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}

	@Column(name = "PINYING", length = 50)
	public String getPinying() {
		return this.pinying;
	}

	public void setPinying(String pinying) {
		this.pinying = pinying;
	}

	@Column(name = "SCODE", length = 100)
	public String getScode() {
		return this.scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Organization> getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizationByGOrganization")
	public Set<AssignTeacherTask> getAssignTeacherTasksForGOrganization() {
		return this.assignTeacherTasksForGOrganization;
	}

	public void setAssignTeacherTasksForGOrganization(Set<AssignTeacherTask> assignTeacherTasksForGOrganization) {
		this.assignTeacherTasksForGOrganization = assignTeacherTasksForGOrganization;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizationByPOrganization")
	public Set<AssignTeacherTask> getAssignTeacherTasksForPOrganization() {
		return this.assignTeacherTasksForPOrganization;
	}

	public void setAssignTeacherTasksForPOrganization(Set<AssignTeacherTask> assignTeacherTasksForPOrganization) {
		this.assignTeacherTasksForPOrganization = assignTeacherTasksForPOrganization;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<TeacherTrainingRecords> getTeacherTrainingRecordses() {
		return this.teacherTrainingRecordses;
	}

	public void setTeacherTrainingRecordses(Set<TeacherTrainingRecords> teacherTrainingRecordses) {
		this.teacherTrainingRecordses = teacherTrainingRecordses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<ProjectAdmin> getProjectAdmins() {
		return this.projectAdmins;
	}

	public void setProjectAdmins(Set<ProjectAdmin> projectAdmins) {
		this.projectAdmins = projectAdmins;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

}