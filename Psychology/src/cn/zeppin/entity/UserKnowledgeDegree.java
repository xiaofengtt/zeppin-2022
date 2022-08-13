package cn.zeppin.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserKnowledgeDegree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_knowledge_degree", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = { "USER", "KNOWLEDGE", "USER_TEST" }))
public class UserKnowledgeDegree implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6934392142775015206L;
	private Long id;
	private User user;
	private Knowledge knowledge;
	private UserTest userTest;
	private Timestamp recordtime;
	private Integer degree;

	// Constructors

	/** default constructor */
	public UserKnowledgeDegree() {
	}

	/** full constructor */
	public UserKnowledgeDegree(User user, Knowledge knowledge, UserTest userTest, Timestamp recordtime, Integer degree) {
		this.user = user;
		this.knowledge = knowledge;
		this.userTest = userTest;
		this.recordtime = recordtime;
		this.degree = degree;
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
	@JoinColumn(name = "USER", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "KNOWLEDGE", nullable = false)
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TEST", nullable = false)
	public UserTest getUserTest() {
		return this.userTest;
	}

	public void setUserTest(UserTest userTest) {
		this.userTest = userTest;
	}

	@Column(name = "RECORDTIME", nullable = false, length = 19)
	public Timestamp getRecordtime() {
		return this.recordtime;
	}

	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

	@Column(name = "DEGREE", nullable = false)
	public Integer getDegree() {
		return this.degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

}