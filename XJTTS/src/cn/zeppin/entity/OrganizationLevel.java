package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * OrganizationLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "organization_level")
public class OrganizationLevel implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Short level;
    private Set<OrgaCateMap> orgaCateMaps = new HashSet<OrgaCateMap>(0);
    private Set<Organization> organizations = new HashSet<Organization>(0);

    // Constructors

    /** default constructor */
    public OrganizationLevel()
    {
    }

    /** minimal constructor */
    public OrganizationLevel(String name, Short level)
    {
	this.name = name;
	this.level = level;
    }

    /** full constructor */
    public OrganizationLevel(String name, Short level,
	    Set<OrgaCateMap> orgaCateMaps, Set<Organization> organizations)
    {
	this.name = name;
	this.level = level;
	this.orgaCateMaps = orgaCateMaps;
	this.organizations = organizations;
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

    @Column(name = "NAME", nullable = false, length = 80)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "LEVEL", nullable = false)
    public Short getLevel()
    {
	return this.level;
    }

    public void setLevel(Short level)
    {
	this.level = level;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "organizationLevel")
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
	    mappedBy = "organizationLevel")
    public Set<Organization> getOrganizations()
    {
	return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations)
    {
	this.organizations = organizations;
    }

}