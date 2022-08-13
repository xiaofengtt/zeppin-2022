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
 * Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "task")
public class Task implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6909785807725622542L;
	private Integer id;
	private Task task;
	private String name;
	private String description;
	private Short type;
	private Short answerType;
	private Integer answerQuantity;
	private Integer condition;
	private Short status;

	// Constructors

	/** default constructor */
	public Task() {
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
	@JoinColumn(name = "PID")
	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "ANSWER_TYPE", nullable = false)
	public Short getAnswerType() {
		return this.answerType;
	}

	public void setAnswerType(Short answerType) {
		this.answerType = answerType;
	}

	@Column(name = "ANSWER_QUANTITY", nullable = false)
	public Integer getAnswerQuantity() {
		return this.answerQuantity;
	}

	public void setAnswerQuantity(Integer answerQuantity) {
		this.answerQuantity = answerQuantity;
	}

	@Column(name = "CONDITION", nullable = false)
	public Integer getCondition() {
		return this.condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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