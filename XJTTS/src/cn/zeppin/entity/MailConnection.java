package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mail_connection")
public class MailConnection implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private MailInformation mailInformation;
	private Integer addressee;
	private Short addresseeRole;
	private Short status;
	
	
	public MailConnection() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MailConnection(Integer id, MailInformation mailInformation,
			Integer addressee, Short addresseeRole, Short status) {
		super();
		this.id = id;
		this.mailInformation = mailInformation;
		this.addressee = addressee;
		this.addresseeRole = addresseeRole;
		this.status = status;
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
    @JoinColumn(name = "MAIL_INFORMATION")
	public MailInformation getMailInformation() {
		return mailInformation;
	}


	public void setMailInformation(MailInformation mailInformation) {
		this.mailInformation = mailInformation;
	}

	@Column(name = "ADDRESSEE", nullable = false)
	public Integer getAddressee() {
		return addressee;
	}


	public void setAddressee(Integer addressee) {
		this.addressee = addressee;
	}

	@Column(name = "ADDRESSEE_ROLE", nullable = false)
	public Short getAddresseeRole() {
		return addresseeRole;
	}


	public void setAddresseeRole(Short addresseeRole) {
		this.addresseeRole = addresseeRole;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}
	

}
