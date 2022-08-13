package com.zeppin.entiey;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MessageId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MessageId implements java.io.Serializable
{

    // Fields

    private Integer id;
    private short type;

    // Constructors

    /** default constructor */
    public MessageId()
    {
    }

    /** full constructor */
    public MessageId(Integer id, short type)
    {
	this.id = id;
	this.type = type;
    }

    // Property accessors

    @Column(name = "id", nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
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

    public boolean equals(Object other)
    {
	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof MessageId))
	    return false;
	MessageId castOther = (MessageId) other;

	return ((this.getId() == castOther.getId()) || (this.getId() != null
		&& castOther.getId() != null && this.getId().equals(
		castOther.getId())))
		&& (this.getType() == castOther.getType());
    }

    public int hashCode()
    {
	int result = 17;

	result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
	result = 37 * result + this.getType();
	return result;
    }

}