package entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area", catalog = "xjtts")
public class Area implements java.io.Serializable {

	// Fields

	private Integer id;
	private Area area;
	private String name;
	private Short level;
	private Short weight;
	private String code;
	private String parentcode;
	private Set<Teacher> teachers = new HashSet<Teacher>(0);
	private Set<ProjectExpert> projectExperts = new HashSet<ProjectExpert>(0);
	private Set<TrainingAdmin> trainingAdmins = new HashSet<TrainingAdmin>(0);
	private Set<TrainingCollege> trainingColleges = new HashSet<TrainingCollege>(0);
	private Set<ProjectType> projectTypes = new HashSet<ProjectType>(0);
	private Set<Area> areas = new HashSet<Area>(0);
	private Set<ProjectAdmin> projectAdmins = new HashSet<ProjectAdmin>(0);
	private Set<Organization> organizations = new HashSet<Organization>(0);
	private Set<Teachexpert> teachexperts = new HashSet<Teachexpert>(0);

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** minimal constructor */
	public Area(String name, Short level, Short weight, String code, String parentcode) {
		this.name = name;
		this.level = level;
		this.weight = weight;
		this.code = code;
		this.parentcode = parentcode;
	}

	/** full constructor */
	public Area(Area area, String name, Short level, Short weight, String code, String parentcode, Set<Teacher> teachers, Set<ProjectExpert> projectExperts,
			Set<TrainingAdmin> trainingAdmins, Set<TrainingCollege> trainingColleges, Set<ProjectType> projectTypes, Set<Area> areas,
			Set<ProjectAdmin> projectAdmins, Set<Organization> organizations, Set<Teachexpert> teachexperts) {
		this.area = area;
		this.name = name;
		this.level = level;
		this.weight = weight;
		this.code = code;
		this.parentcode = parentcode;
		this.teachers = teachers;
		this.projectExperts = projectExperts;
		this.trainingAdmins = trainingAdmins;
		this.trainingColleges = trainingColleges;
		this.projectTypes = projectTypes;
		this.areas = areas;
		this.projectAdmins = projectAdmins;
		this.organizations = organizations;
		this.teachexperts = teachexperts;
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
	@JoinColumn(name = "PID")
	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LEVEL", nullable = false)
	public Short getLevel() {
		return this.level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	@Column(name = "WEIGHT", nullable = false)
	public Short getWeight() {
		return this.weight;
	}

	public void setWeight(Short weight) {
		this.weight = weight;
	}

	@Column(name = "CODE", nullable = false, length = 30)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "PARENTCODE", nullable = false, length = 45)
	public String getParentcode() {
		return this.parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<ProjectExpert> getProjectExperts() {
		return this.projectExperts;
	}

	public void setProjectExperts(Set<ProjectExpert> projectExperts) {
		this.projectExperts = projectExperts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<TrainingAdmin> getTrainingAdmins() {
		return this.trainingAdmins;
	}

	public void setTrainingAdmins(Set<TrainingAdmin> trainingAdmins) {
		this.trainingAdmins = trainingAdmins;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<TrainingCollege> getTrainingColleges() {
		return this.trainingColleges;
	}

	public void setTrainingColleges(Set<TrainingCollege> trainingColleges) {
		this.trainingColleges = trainingColleges;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<ProjectType> getProjectTypes() {
		return this.projectTypes;
	}

	public void setProjectTypes(Set<ProjectType> projectTypes) {
		this.projectTypes = projectTypes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<ProjectAdmin> getProjectAdmins() {
		return this.projectAdmins;
	}

	public void setProjectAdmins(Set<ProjectAdmin> projectAdmins) {
		this.projectAdmins = projectAdmins;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<Organization> getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<Teachexpert> getTeachexperts() {
		return this.teachexperts;
	}

	public void setTeachexperts(Set<Teachexpert> teachexperts) {
		this.teachexperts = teachexperts;
	}

}