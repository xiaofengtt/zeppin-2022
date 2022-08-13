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
 * Information entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "information")
public class Information implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 979447958914344930L;
	private Integer id;
	private Subject subject;
	private Resource resource;
	private Short type;
	private String title;
	private String abstract_;
	private String content;
	private Timestamp createtime;
	private Timestamp releasetime;
	private Short status;
	private Integer favoritCount;
	private Integer retweetCount;

	// Constructors

	/** default constructor */
	public Information() {
	}

	/** minimal constructor */
	public Information(Short type, String title, String abstract_, Timestamp createtime, Short status, Integer favoritCount, Integer retweetCount) {
		this.type = type;
		this.title = title;
		this.abstract_ = abstract_;
		this.createtime = createtime;
		this.status = status;
		this.favoritCount = favoritCount;
		this.retweetCount = retweetCount;
	}

	/** full constructor */
	public Information(Subject subject, Resource resource, Short type, String title, String abstract_, String content, Timestamp createtime, Short status, Integer favoritCount, Integer retweetCount) {
		this.subject = subject;
		this.resource = resource;
		this.type = type;
		this.title = title;
		this.abstract_ = abstract_;
		this.content = content;
		this.createtime = createtime;
		this.status = status;
		this.favoritCount = favoritCount;
		this.retweetCount = retweetCount;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "thumb_pic")
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name = "type", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "abstract", nullable = false, length = 200)
	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "favorit_count", nullable = false)
	public Integer getFavoritCount() {
		return this.favoritCount;
	}

	public void setFavoritCount(Integer favoritCount) {
		this.favoritCount = favoritCount;
	}

	@Column(name = "retweet_count", nullable = false)
	public Integer getRetweetCount() {
		return this.retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	@Column(name = "release_time", length = 19)
	public Timestamp getReleasetime() {
		return releasetime;
	}

	public void setReleasetime(Timestamp releasetime) {
		this.releasetime = releasetime;
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