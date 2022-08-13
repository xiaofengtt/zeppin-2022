package com.gpjh.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="trainingunit")
@Repository("trainingUnit")
public class TrainingUnit implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "area", unique = true, nullable = false)
	private String area;
	
	@Column(name = "level", unique = true, nullable = false)
	private int level;
	
/*	@Column(name = "pid", unique = false, nullable = true)
	private int pid;*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

/*	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}*/

}
