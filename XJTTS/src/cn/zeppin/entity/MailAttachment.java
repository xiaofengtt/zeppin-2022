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

/**
 * ProjectApplyWorkReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mail_attachment")
public class MailAttachment implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private MailInformation mailInformation;
    private Document document;

    // Constructors

    /** default constructor */
    public MailAttachment()
    {
    }

    /** full constructor */
    public MailAttachment(MailInformation mailInformation, Document document)
    {
	this.mailInformation = mailInformation;
	this.document = document;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIL_INFORMATION", nullable = false)
    public MailInformation getMailInformation() {
		return mailInformation;
	}

	public void setMailInformation(MailInformation mailInformation) {
		this.mailInformation = mailInformation;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCUMENT", nullable = false)
    public Document getDocument()
    {
	return this.document;
    }


	public void setDocument(Document document)
    {
	this.document = document;
    }

}