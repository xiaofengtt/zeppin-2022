package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Log entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "log")
public class Log implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Short userRole;
    private Integer userId;
    private Short type;
    private String tableName;
    private String tableId;
    private String remark;
    private Timestamp time;
    
    // Constructors

    /** default constructor */
    public Log()
    {
    }

    /** full constructor */
    public Log(Short userRole,Integer userId,Short type,String tableName,String tableId,String remark,Timestamp time)
    {
	this.userRole = userRole;
	this.userId = userId;
	this.type = type;
	this.tableName = tableName;
	this.tableId = tableId;
	this.remark = remark;
	this.time = time;
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

    @Column(name = "USER_ROLE", nullable = false)
    public Short getUserRole()
    {
	return this.userRole;
    }

    public void setUserRole(Short userRole)
    {
	this.userRole = userRole;
    }
    
    @Column(name = "USER_ID", nullable = false)
    public Integer getUserId()
    {
	return this.userId;
    }

    public void setUserId(Integer userId)
    {
	this.userId = userId;
    }
    
    @Column(name = "TYPE", nullable = false)
    public Short getType()
    {
	return this.type;
    }

    public void setType(Short type)
    {
	this.type = type;
    }
    
    @Column(name = "TABLE_NAME", nullable = false , length = 50)
    public String getTableName()
    {
	return this.tableName;
    }

    public void setTableName(String tableName)
    {
	this.tableName = tableName;
    }

    @Column(name = "TABLE_ID", nullable = false , length = 50)
    public String getTableId()
    {
	return this.tableId;
    }

    public void setTableId(String tableId)
    {
	this.tableId = tableId;
    }
    
    @Column(name = "REMARK", nullable = false)
    public String getRemark()
    {
	return this.remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
    }
    
    @Column(name = "TIME", nullable = false, length = 19)
    public Timestamp getTime()
    {
	return this.time;
    }

    public void setTime(Timestamp time)
    {
	this.time = time;
    }
}