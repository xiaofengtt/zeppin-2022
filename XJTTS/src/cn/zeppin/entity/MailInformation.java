package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mail_information")
public class MailInformation implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String text;
	private String inscription;
	private Short type;
	private Integer creator;
	private Short creatorRole;
	private Timestamp creattime;
	private Timestamp sendtime;
	private Short sendStatus;
	private Set<MailConnection> mailConnections = new HashSet<MailConnection>();
	private Set<ServiceApplyReply> serviceApplyReply = new HashSet<ServiceApplyReply>();
	private Set<MailAttachment> mailAttachment = new HashSet<MailAttachment>();
	
	
	public MailInformation() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MailInformation(Integer id, String title, String text,
			String inscription, Short type, Integer creator, Short creatorRole,
			Timestamp creattime, Timestamp sendtime, Short sendStatus) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.inscription = inscription;
		this.type = type;
		this.creator = creator;
		this.creatorRole = creatorRole;
		this.creattime = creattime;
		this.sendtime = sendtime;
		this.sendStatus = sendStatus;
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

	@Column(name = "TITLE", nullable = false, length = 200)
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "TEXT", nullable = false, length = 5000)
	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "INSCRIPTION", nullable = false, length = 200)
	public String getInscription() {
		return inscription;
	}


	public void setInscription(String inscription) {
		this.inscription = inscription;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return type;
	}


	public void setType(Short type) {
		this.type = type;
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

	@Column(name = "CREATTIME", nullable = false, length = 19)
	public Timestamp getCreattime() {
		return creattime;
	}


	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}

	@Column(name = "SENDTIME", nullable = false, length = 19)
	public Timestamp getSendtime() {
		return sendtime;
	}


	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "SENDSTATUS", nullable = false)
	public Short getSendStatus() {
		return sendStatus;
	}


	public void setSendStatus(Short sendStatus) {
		this.sendStatus = sendStatus;
	}

	@OneToMany(
		    cascade = CascadeType.ALL,
		    fetch = FetchType.LAZY,
		    mappedBy = "mailInformation")
	public Set<MailConnection> getMailConnections() {
		return mailConnections;
	}


	public void setMailConnections(Set<MailConnection> mailConnections) {
		this.mailConnections = mailConnections;
	}


	
	@OneToMany(
		    cascade = CascadeType.ALL,
		    fetch = FetchType.LAZY,
		    mappedBy = "serviceApply")
	public Set<ServiceApplyReply> getServiceApplyReply() {
		return serviceApplyReply;
	}


	public void setServiceApplyReply(Set<ServiceApplyReply> serviceApplyReply) {
		this.serviceApplyReply = serviceApplyReply;
	}
	
	@OneToMany(
		    cascade = CascadeType.ALL,
		    fetch = FetchType.LAZY,
		    mappedBy = "mailInformation")
	public Set<MailAttachment> getMailAttachment() {
		return mailAttachment;
	}


	public void setMailAttachment(Set<MailAttachment> mailAttachment) {
		this.mailAttachment = mailAttachment;
	}
	
	

}
