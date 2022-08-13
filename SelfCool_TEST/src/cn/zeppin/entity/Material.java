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
 * Material entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "material")
public class Material implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6698444980416840846L;
	private Integer id;
	private Grade grade;
	private Subject subject;
	private String content;
	private String contentBackup;

	// Constructors

	/** default constructor */
	public Material() {
	}

	/** minimal constructor */
	public Material(String content) {
		this.content = content;
	}

	/** full constructor */
	public Material(Grade grade, Subject subject, String content, String contentBackup) {
		this.grade = grade;
		this.subject = subject;
		this.content = content;
		this.contentBackup = contentBackup;
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
	@JoinColumn(name = "GRADE")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "CONTENT", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CONTENT_BACKUP", length = 65535)
	public String getContentBackup() {
		return contentBackup;
	}

	public void setContentBackup(String contentBackup) {
		this.contentBackup = contentBackup;
	}

	/**
	 * cacheKey
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}