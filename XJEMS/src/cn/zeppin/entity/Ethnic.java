package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Ethnic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ethnic")
public class Ethnic implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 6915108687348641826L;
	private Short id;
    private String name;
    private Short weight;

    // Constructors

    /** default constructor */
    public Ethnic()
    {
    }

    /** minimal constructor */
    public Ethnic(String name, Short weight)
    {
	this.name = name;
	this.weight = weight;
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

    @Column(name = "WEIGHT", nullable = false)
    public Short getWeight()
    {
	return this.weight;
    }

    public void setWeight(Short weight)
    {
	this.weight = weight;
    }

}