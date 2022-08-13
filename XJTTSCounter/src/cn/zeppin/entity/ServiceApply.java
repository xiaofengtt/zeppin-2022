package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 下级申请
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "service_apply")
public class ServiceApply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String countent;
	private String replyText;
	private ProjectAdmin creator;
	private Short cretorType;
	private Timestamp createtime;
	private ProjectAdmin checker;
	private Timestamp checktime;
	private Short status;
	private Short type;

	public ServiceApply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceApply(Integer id, String countent, String replyText,
			ProjectAdmin creator, Short cretorType, Timestamp createtime,
			ProjectAdmin checker, Timestamp checktime, Short status, Short type) {
		super();
		this.id = id;
		this.countent = countent;
		this.replyText = replyText;
		this.creator = creator;
		this.cretorType = cretorType;
		this.createtime = createtime;
		this.checker = checker;
		this.checktime = checktime;
		this.status = status;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "COUNTENT", nullable = false)
	public String getContent() {
		return countent;
	}

	public void setContent(String content) {
		this.countent = content;
	}

	@Column(name = "REPLY_TEXT")
	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR", nullable = false)
	public ProjectAdmin getCreator() {
		return creator;
	}

	public void setCreator(ProjectAdmin creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_TYPE", nullable = false)
	public Short getCretorType() {
		return cretorType;
	}

	public void setCretorType(Short cretorType) {
		this.cretorType = cretorType;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKER")
	public ProjectAdmin getChecker() {
		return checker;
	}

	public void setChecker(ProjectAdmin checker) {
		this.checker = checker;
	}

	@Column(name = "CHECKTIME")
	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "TYPE")
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

}