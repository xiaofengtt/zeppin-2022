package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserTextbook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_textbook", catalog = "cetv")
public class UserTextbook implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5332990937221594084L;
	private Integer id;
	private Grade grade;
	private Textbook textbook;
	private User user;
	private Subject subject;

	// Constructors

	/** default constructor */
	public UserTextbook() {
	}

	/** full constructor */
	public UserTextbook(Grade grade, Textbook textbook, User user, Subject subject) {
		this.grade = grade;
		this.textbook = textbook;
		this.user = user;
		this.subject = subject;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "textbook")
	public Textbook getTextbook() {
		return this.textbook;
	}

	public void setTextbook(Textbook textbook) {
		this.textbook = textbook;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}