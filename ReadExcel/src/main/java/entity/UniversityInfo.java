package entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UniversityInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "university_info", catalog = "xjtts")
public class UniversityInfo implements java.io.Serializable
{

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer code;
    private Integer type;
    private String typeName;
    private Integer xzlb;
    private String xzlbName;
    private String pinyin;

    // Constructors

    /** default constructor */
    public UniversityInfo()
    {
    }

    /** full constructor */
    public UniversityInfo(String name, Integer code, Integer type,
	    String typeName, Integer xzlb, String xzlbName, String pinyin)
    {
	this.name = name;
	this.code = code;
	this.type = type;
	this.typeName = typeName;
	this.xzlb = xzlb;
	this.xzlbName = xzlbName;
	this.pinyin = pinyin;
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

    @Column(name = "name", nullable = false)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "code", nullable = false)
    public Integer getCode()
    {
	return this.code;
    }

    public void setCode(Integer code)
    {
	this.code = code;
    }

    @Column(name = "type", nullable = false)
    public Integer getType()
    {
	return this.type;
    }

    public void setType(Integer type)
    {
	this.type = type;
    }

    @Column(name = "type_name", nullable = false)
    public String getTypeName()
    {
	return this.typeName;
    }

    public void setTypeName(String typeName)
    {
	this.typeName = typeName;
    }

    @Column(name = "xzlb", nullable = false)
    public Integer getXzlb()
    {
	return this.xzlb;
    }

    public void setXzlb(Integer xzlb)
    {
	this.xzlb = xzlb;
    }

    @Column(name = "xzlb_name", nullable = false)
    public String getXzlbName()
    {
	return this.xzlbName;
    }

    public void setXzlbName(String xzlbName)
    {
	this.xzlbName = xzlbName;
    }

    @Column(name = "pinyin", nullable = false)
    public String getPinyin()
    {
	return this.pinyin;
    }

    public void setPinyin(String pinyin)
    {
	this.pinyin = pinyin;
    }

}