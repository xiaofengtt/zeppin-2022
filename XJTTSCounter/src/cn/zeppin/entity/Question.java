package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Question entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "question")
public class Question implements java.io.Serializable {

	// Fields

	private Integer id;
	private Psq psq;
	private String name;
	private Integer inx;
	private Short type;
	private Boolean ismust;
	private Short arrange;
	private String hint;
	private Integer scale;
	private Boolean isCount;
	private Set<Result> results = new HashSet<Result>(0);
	private Set<Answer> answers = new HashSet<Answer>(0);

	// Constructors

	/** default constructor */
	public Question() {
	}

	/** minimal constructor */
	public Question(Psq psq, String name, Integer inx, Short type, Boolean ismust, Short arrange, Integer scale, Boolean isCount) {
		this.psq = psq;
		this.name = name;
		this.inx = inx;
		this.type = type;
		this.ismust = ismust;
		this.arrange = arrange;
		this.scale = scale;
		this.isCount = isCount;
	}

	/** full constructor */
	public Question(Psq psq, String name, Integer inx, Short type, Boolean ismust, Short arrange, String hint, Integer scale, Boolean isCount, Set<Result> results, Set<Answer> answers) {
		this.psq = psq;
		this.name = name;
		this.inx = inx;
		this.type = type;
		this.ismust = ismust;
		this.arrange = arrange;
		this.hint = hint;
		this.scale = scale;
		this.isCount = isCount;
		this.results = results;
		this.answers = answers;
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
	@JoinColumn(name = "PSQ", nullable = false)
	public Psq getPsq() {
		return this.psq;
	}

	public void setPsq(Psq psq) {
		this.psq = psq;
	}

	@Column(name = "NAME", nullable = false, length = 2000)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "INX", nullable = false)
	public Integer getInx() {
		return this.inx;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "ISMUST", nullable = false)
	public Boolean getIsmust() {
		return this.ismust;
	}

	public void setIsmust(Boolean ismust) {
		this.ismust = ismust;
	}

	@Column(name = "ARRANGE", nullable = false)
	public Short getArrange() {
		return this.arrange;
	}

	public void setArrange(Short arrange) {
		this.arrange = arrange;
	}

	@Column(name = "HINT", length = 2000)
	public String getHint() {
		return this.hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Column(name = "SCALE", nullable = false)
	public Integer getScale() {
		return this.scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Column(name = "IS_COUNT", nullable = false)
	public Boolean getIsCount() {
		return this.isCount;
	}

	public void setIsCount(Boolean isCount) {
		this.isCount = isCount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
	@OrderBy("id ASC")
	public Set<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

}