package cn.zeppin.entity ;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SubjectRetrieve entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject_retrieve")
public class SubjectRetrieve implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1133389463713940481L;
	private Integer id;
	private Subject subject;
	private Retrieve retrieve;

	// Constructors

	/** default constructor */
	public SubjectRetrieve() {
	}

	/** full constructor */
	public SubjectRetrieve(Integer id, Subject subject, Retrieve retrieve) {
		this.id = id;
		this.subject = subject;
		this.retrieve = retrieve;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECTID", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RETRIEVEID", nullable = false)
	public Retrieve getRetrieve() {
		return this.retrieve;
	}

	public void setRetrieve(Retrieve retrieve) {
		this.retrieve = retrieve;
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