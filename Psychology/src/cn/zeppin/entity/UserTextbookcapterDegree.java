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

/**
 * UserTextbookcapterDegree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_textbookcapter_degree", catalog = "cetv")
public class UserTextbookcapterDegree implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8245626593030961584L;
	private Long id;
	private TextbookCapter textbookCapter;
	private User user;
	private UserTest userTest;
	private Timestamp recordtime;
	private Integer degree;

	// Constructors

	/** default constructor */
	public UserTextbookcapterDegree() {
	}

	/** minimal constructor */
	public UserTextbookcapterDegree(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

	/** full constructor */
	public UserTextbookcapterDegree(TextbookCapter textbookCapter, User user, UserTest userTest, Timestamp recordtime, Integer degree) {
		this.textbookCapter = textbookCapter;
		this.user = user;
		this.userTest = userTest;
		this.recordtime = recordtime;
		this.degree = degree;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "textbookcapter")
	public TextbookCapter getTextbookCapter() {
		return this.textbookCapter;
	}

	public void setTextbookCapter(TextbookCapter textbookCapter) {
		this.textbookCapter = textbookCapter;
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
	@JoinColumn(name = "user_test")
	public UserTest getUserTest() {
		return this.userTest;
	}

	public void setUserTest(UserTest userTest) {
		this.userTest = userTest;
	}

	@Column(name = "recordtime", nullable = false, length = 19)
	public Timestamp getRecordtime() {
		return this.recordtime;
	}

	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

	@Column(name = "degree")
	public Integer getDegree() {
		return this.degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

}