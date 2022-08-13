package entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user", catalog = "xjtts")
public class SysUser implements java.io.Serializable
{

    // Fields

    private SysUserId id;

    // Constructors

    /** default constructor */
    public SysUser()
    {
    }

    /** full constructor */
    public SysUser(SysUserId id)
    {
	this.id = id;
    }

    // Property accessors
    @EmbeddedId
    @AttributeOverrides({
	    @AttributeOverride(name = "id", column = @Column(
		    name = "ID",
		    nullable = false)),
	    @AttributeOverride(name = "idcard", column = @Column(
		    name = "IDCARD",
		    length = 20)),
	    @AttributeOverride(name = "mobile", column = @Column(
		    name = "MOBILE",
		    nullable = false,
		    length = 20)),
	    @AttributeOverride(name = "email", column = @Column(
		    name = "EMAIL",
		    length = 50)),
	    @AttributeOverride(name = "password", column = @Column(
		    name = "PASSWORD",
		    nullable = false,
		    length = 20)),
	    @AttributeOverride(name = "role", column = @Column(
		    name = "ROLE",
		    nullable = false)),
	    @AttributeOverride(name = "name", column = @Column(
		    name = "NAME",
		    nullable = false,
		    length = 50)),
	    @AttributeOverride(name = "organization", column = @Column(
		    name = "ORGANIZATION",
		    nullable = false)),
	    @AttributeOverride(name = "createuser", column = @Column(
		    name = "CREATEUSER",
		    nullable = false)),
	    @AttributeOverride(name = "level", column = @Column(
		    name = "LEVEL",
		    nullable = false)),
	    @AttributeOverride(name = "status", column = @Column(
		    name = "STATUS")),
	    @AttributeOverride(name = "creator", column = @Column(
		    name = "CREATOR")),
	    @AttributeOverride(name = "creattime", column = @Column(
		    name = "CREATTIME",
		    nullable = false,
		    length = 19)) })
    public SysUserId getId()
    {
	return this.id;
    }

    public void setId(SysUserId id)
    {
	this.id = id;
    }

}