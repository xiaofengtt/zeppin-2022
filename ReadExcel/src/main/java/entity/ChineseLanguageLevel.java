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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ChineseLanguageLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chinese_language_level", catalog = "xjtts")
public class ChineseLanguageLevel implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private String level;
    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    // Constructors

    /** default constructor */
    public ChineseLanguageLevel()
    {
    }

    /** minimal constructor */
    public ChineseLanguageLevel(String name, String level)
    {
	this.name = name;
	this.level = level;
    }

    /** full constructor */
    public ChineseLanguageLevel(String name, String level, Set<Teacher> teachers)
    {
	this.name = name;
	this.level = level;
	this.teachers = teachers;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Short getId()
    {
	return this.id;
    }

    public void setId(Short id)
    {
	this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 50)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "LEVEL", nullable = false, length = 3)
    public String getLevel()
    {
	return this.level;
    }

    public void setLevel(String level)
    {
	this.level = level;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "chineseLanguageLevel")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

}