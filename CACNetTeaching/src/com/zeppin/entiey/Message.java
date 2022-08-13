package com.zeppin.entiey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "message", catalog = "cac")
public class Message implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacher;
    private String title;
    private String content;
    private short type;
    private Date sendTime;
    private boolean isTop;
    private Set<SsoMessageMap> ssoMessageMaps = new HashSet<SsoMessageMap>(0);

    // Constructors

    /** default constructor */
    public Message()
    {
    }

    /** minimal constructor */
    public Message(Teacher teacher, String content, short type, Date sendTime,
	    boolean isTop)
    {
	this.teacher = teacher;
	this.content = content;
	this.type = type;
	this.sendTime = sendTime;
	this.isTop = isTop;
    }

    /** full constructor */
    public Message(Teacher teacher, String title, String content, short type,
	    Date sendTime, boolean isTop, Set<SsoMessageMap> ssoMessageMaps)
    {
	this.teacher = teacher;
	this.title = title;
	this.content = content;
	this.type = type;
	this.sendTime = sendTime;
	this.isTop = isTop;
	this.ssoMessageMaps = ssoMessageMaps;
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
    @JoinColumn(name = "senderId", nullable = false)
    public Teacher getTeacher()
    {
	return this.teacher;
    }

    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }

    @Column(name = "title", length = 100)
    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    @Column(name = "content", nullable = false, length = 65535)
    public String getContent()
    {
	return this.content;
    }

    public void setContent(String content)
    {
	this.content = content;
    }

    @Column(name = "type", nullable = false)
    public short getType()
    {
	return this.type;
    }

    public void setType(short type)
    {
	this.type = type;
    }

    @Column(name = "sendTime", nullable = false, length = 19)
    public Date getSendTime()
    {
	return this.sendTime;
    }

    public void setSendTime(Date sendTime)
    {
	this.sendTime = sendTime;
    }

    @Column(name = "isTop", nullable = false)
    public boolean getIsTop()
    {
	return this.isTop;
    }

    public void setIsTop(boolean isTop)
    {
	this.isTop = isTop;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "message")
    public Set<SsoMessageMap> getSsoMessageMaps()
    {
	return this.ssoMessageMaps;
    }

    public void setSsoMessageMaps(Set<SsoMessageMap> ssoMessageMaps)
    {
	this.ssoMessageMaps = ssoMessageMaps;
    }

}