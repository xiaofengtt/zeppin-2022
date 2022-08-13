package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.UniqueConstraint;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SsoKnowledgeDegree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sso_knowledge_degree",uniqueConstraints = @UniqueConstraint(columnNames = { "SSOID", "SUBJECT", "KNOWLEDGE"}))
public class SsoKnowledgeDegree implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 9204701070757665651L;
	private Long id;
	private SsoUser ssoUser;
	private Subject subject;
	private Knowledge knowledge;
	private Double degree;
	private Short level;

	// Constructors

	/** default constructor */
	public SsoKnowledgeDegree() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SSOID", nullable = false)
	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "KNOWLEDGE", nullable = false)
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	@Column(name = "DEGREE", precision = 8,nullable = false)
	public Double getDegree() {
		return this.degree;
	}

	public void setDegree(Double degree) {
		this.degree = degree;
	}


	@Column(name = "LEVEL", nullable = false)
	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}
	
	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}

}