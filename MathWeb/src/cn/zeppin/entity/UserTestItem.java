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
 * UserTestItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_test_item", catalog = "cetv")
public class UserTestItem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473023032211117777L;
	private Long id;
	private Item item;
	private UserTest userTest;
	private ItemAnswer itemAnswer;
	private Timestamp starttime;
	private Integer answertime;
	private Timestamp endtime;
	private String content;
	private Integer score;
	private Integer inx;
	private Boolean isFlag;

	// Constructors

	/** default constructor */
	public UserTestItem() {
	}

	/** minimal constructor */
	public UserTestItem(Item item, UserTest userTest, ItemAnswer itemAnswer, Integer answertime) {
		this.item = item;
		this.userTest = userTest;
		this.itemAnswer = itemAnswer;
		this.answertime=answertime;
	}

	/** full constructor */
	public UserTestItem(Item item, UserTest userTest, ItemAnswer itemAnswer, Timestamp starttime,Integer answertime, Timestamp endtime, String content, Integer score) {
		this.item = item;
		this.userTest = userTest;
		this.itemAnswer = itemAnswer;
		this.starttime = starttime;
		this.answertime=answertime;
		this.endtime = endtime;
		this.content = content;
		this.score = score;
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
	@JoinColumn(name = "ITEM", nullable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TEST", nullable = false)
	public UserTest getUserTest() {
		return this.userTest;
	}

	public void setUserTest(UserTest userTest) {
		this.userTest = userTest;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM_ANSWER", nullable = true)
	public ItemAnswer getItemAnswer() {
		return this.itemAnswer;
	}

	public void setItemAnswer(ItemAnswer itemAnswer) {
		this.itemAnswer = itemAnswer;
	}

	@Column(name = "STARTTIME",length = 19)
	public Timestamp getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	@Column(name = "ENDTIME",length = 19)
	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	@Column(name = "ANSWER_TIME", nullable = false)
	public Integer getAnswertime() {
		return this.answertime;
	}

	public void setAnswertime(Integer answertime) {
		this.answertime = answertime;
	}
	
	@Column(name = "CONTENT", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "SCORE")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "INX")
	public Integer getInx() {
		return inx;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	@Column(name = "FLAG")
	public Boolean getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Boolean isFlag) {
		this.isFlag = isFlag;
	}

}