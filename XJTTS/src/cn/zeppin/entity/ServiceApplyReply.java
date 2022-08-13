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
 * 下级申请回复
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "service_apply_reply")
public class ServiceApplyReply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private MailInformation serviceApply;
	private String countent;
	private Integer creator;
	private Timestamp createtime;
	private Short status;
	private Short creatorRole;

	public ServiceApplyReply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceApplyReply(Integer id, MailInformation serviceApply,
			String countent, Integer creator, Timestamp createtime,
			Short status, Short creatorRole) {
		super();
		this.id = id;
		this.serviceApply = serviceApply;
		this.countent = countent;
		this.creator = creator;
		this.createtime = createtime;
		this.status = status;
		this.creatorRole = creatorRole;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_APPLY", nullable = false)
	public MailInformation getServiceApply() {
		return serviceApply;
	}

	public void setServiceApply(MailInformation serviceApply) {
		this.serviceApply = serviceApply;
	}

	@Column(name = "COUNTENT", nullable = false)
	public String getCountent() {
		return countent;
	}

	public void setCountent(String countent) {
		this.countent = countent;
	}

	@Column(name = "CREATOR", nullable = false)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_ROLE", nullable = false)
	public Short getCreatorRole() {
		return creatorRole;
	}

	public void setCreatorRole(Short creatorRole) {
		this.creatorRole = creatorRole;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}