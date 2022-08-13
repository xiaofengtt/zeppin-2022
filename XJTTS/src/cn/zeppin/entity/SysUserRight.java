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
 * SysUserRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user_right")
public class SysUserRight implements java.io.Serializable
{

    // Fields

    private Integer id;
    private ProjectType projectType;
    private Integer sysUser;

    // Constructors

    /** default constructor */
    public SysUserRight()
    {
    }

    /** full constructor */
    public SysUserRight(ProjectType projectType, Integer sysUser)
    {
	this.projectType = projectType;
	this.sysUser = sysUser;
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
    @JoinColumn(name = "PROJECT_TYPE", nullable = false)
    public ProjectType getProjectType()
    {
	return this.projectType;
    }

    public void setProjectType(ProjectType projectType)
    {
	this.projectType = projectType;
    }

    @Column(name = "SYS_USER", nullable = false)
    public Integer getSysUser()
    {
	return this.sysUser;
    }

    public void setSysUser(Integer sysUser)
    {
	this.sysUser = sysUser;
    }

}