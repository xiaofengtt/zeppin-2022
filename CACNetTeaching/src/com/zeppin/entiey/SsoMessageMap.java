package com.zeppin.entiey;

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
 * SsoMessageMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sso_message_map", catalog = "cac")
public class SsoMessageMap implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Message message;
    private Sso sso;
    private boolean isRead;
    private boolean isValue;

    // Constructors

    /** default constructor */
    public SsoMessageMap()
    {
    }

    /** minimal constructor */
    public SsoMessageMap(Sso sso, boolean isRead, boolean isValue)
    {
	this.sso = sso;
	this.isRead = isRead;
	this.isValue = isValue;
    }

    /** full constructor */
    public SsoMessageMap(Message message, Sso sso, boolean isRead,
	    boolean isValue)
    {
	this.message = message;
	this.sso = sso;
	this.isRead = isRead;
	this.isValue = isValue;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageId")
    public Message getMessage()
    {
	return this.message;
    }

    public void setMessage(Message message)
    {
	this.message = message;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    public Sso getSso()
    {
	return this.sso;
    }

    public void setSso(Sso sso)
    {
	this.sso = sso;
    }

    @Column(name = "isRead", nullable = false)
    public boolean getIsRead()
    {
	return this.isRead;
    }

    public void setIsRead(boolean isRead)
    {
	this.isRead = isRead;
    }

    @Column(name = "isValue", nullable = false)
    public boolean getIsValue()
    {
	return this.isValue;
    }

    public void setIsValue(boolean isValue)
    {
	this.isValue = isValue;
    }

}