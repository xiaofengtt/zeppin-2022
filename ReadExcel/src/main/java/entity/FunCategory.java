package entity;

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
 * FunCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fun_category", catalog = "xjtts")
public class FunCategory implements java.io.Serializable
{

    // Fields

    private Integer id;
    private FunCategory funCategory;
    private String name;
    private String path;
    private Integer level;
    private Integer code;
    private Set<OrgaCateMap> orgaCateMaps = new HashSet<OrgaCateMap>(0);
    private Set<FunCategory> funCategories = new HashSet<FunCategory>(0);

    // Constructors

    /** default constructor */
    public FunCategory()
    {
    }

    /** full constructor */
    public FunCategory(FunCategory funCategory, String name, String path,
	    Integer level, Integer code, Set<OrgaCateMap> orgaCateMaps,
	    Set<FunCategory> funCategories)
    {
	this.funCategory = funCategory;
	this.name = name;
	this.path = path;
	this.level = level;
	this.code = code;
	this.orgaCateMaps = orgaCateMaps;
	this.funCategories = funCategories;
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
    @JoinColumn(name = "PID")
    public FunCategory getFunCategory()
    {
	return this.funCategory;
    }

    public void setFunCategory(FunCategory funCategory)
    {
	this.funCategory = funCategory;
    }

    @Column(name = "NAME", length = 50)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "PATH", length = 200)
    public String getPath()
    {
	return this.path;
    }

    public void setPath(String path)
    {
	this.path = path;
    }

    @Column(name = "LEVEL")
    public Integer getLevel()
    {
	return this.level;
    }

    public void setLevel(Integer level)
    {
	this.level = level;
    }

    @Column(name = "CODE")
    public Integer getCode()
    {
	return this.code;
    }

    public void setCode(Integer code)
    {
	this.code = code;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "funCategory")
    public Set<OrgaCateMap> getOrgaCateMaps()
    {
	return this.orgaCateMaps;
    }

    public void setOrgaCateMaps(Set<OrgaCateMap> orgaCateMaps)
    {
	this.orgaCateMaps = orgaCateMaps;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "funCategory")
    public Set<FunCategory> getFunCategories()
    {
	return this.funCategories;
    }

    public void setFunCategories(Set<FunCategory> funCategories)
    {
	this.funCategories = funCategories;
    }

}