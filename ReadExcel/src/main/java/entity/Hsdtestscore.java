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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Hsdtestscore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "hsdtestscore", catalog = "xjtts")
public class Hsdtestscore implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4427612398599234810L;
	private Integer id;
	private Float plane;
	private Float organize;
	private Float assess;
	private Float skill;
	private Float learn;
	private Set<Hsdtest> hsdtestsForChange = new HashSet<Hsdtest>(0);
	private Set<Hsdtest> hsdtestsForOptimize = new HashSet<Hsdtest>(0);

	// Constructors

	/** default constructor */
	public Hsdtestscore() {
	}

	/** minimal constructor */
	public Hsdtestscore(Float plane, Float organize, Float assess, Float skill, Float learn) {
		this.plane = plane;
		this.organize = organize;
		this.assess = assess;
		this.skill = skill;
		this.learn = learn;
	}

	/** full constructor */
	public Hsdtestscore(Float plane, Float organize, Float assess, Float skill, Float learn, Set<Hsdtest> hsdtestsForChange, Set<Hsdtest> hsdtestsForOptimize) {
		this.plane = plane;
		this.organize = organize;
		this.assess = assess;
		this.skill = skill;
		this.learn = learn;
		this.hsdtestsForChange = hsdtestsForChange;
		this.hsdtestsForOptimize = hsdtestsForOptimize;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "plane", nullable = false, precision = 12, scale = 0)
	public Float getPlane() {
		return this.plane;
	}

	public void setPlane(Float plane) {
		this.plane = plane;
	}

	@Column(name = "organize", nullable = false, precision = 12, scale = 0)
	public Float getOrganize() {
		return this.organize;
	}

	public void setOrganize(Float organize) {
		this.organize = organize;
	}

	@Column(name = "assess", nullable = false, precision = 12, scale = 0)
	public Float getAssess() {
		return this.assess;
	}

	public void setAssess(Float assess) {
		this.assess = assess;
	}

	@Column(name = "skill", nullable = false, precision = 12, scale = 0)
	public Float getSkill() {
		return this.skill;
	}

	public void setSkill(Float skill) {
		this.skill = skill;
	}

	@Column(name = "learn", nullable = false, precision = 12, scale = 0)
	public Float getLearn() {
		return this.learn;
	}

	public void setLearn(Float learn) {
		this.learn = learn;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hsdtestscoreByChange")
	public Set<Hsdtest> getHsdtestsForChange() {
		return this.hsdtestsForChange;
	}

	public void setHsdtestsForChange(Set<Hsdtest> hsdtestsForChange) {
		this.hsdtestsForChange = hsdtestsForChange;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hsdtestscoreByOptimize")
	public Set<Hsdtest> getHsdtestsForOptimize() {
		return this.hsdtestsForOptimize;
	}

	public void setHsdtestsForOptimize(Set<Hsdtest> hsdtestsForOptimize) {
		this.hsdtestsForOptimize = hsdtestsForOptimize;
	}

}