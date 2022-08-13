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
 * ProjectLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_group")
public class ProjectGroup implements java.io.Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Set<Project> projects = new HashSet<Project>(0);
	
	
	public ProjectGroup(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public ProjectGroup() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name = "NAME", nullable = false, length = 200)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "projectGroup")
	public Set<Project> getProjects() {
		return projects;
	}


	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	

}