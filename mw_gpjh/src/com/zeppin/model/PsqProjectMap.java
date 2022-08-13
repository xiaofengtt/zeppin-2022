package com.zeppin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "psqprojectmap")
@Repository("psqprojectmap")
@SequenceGenerator(name = "EMP_PSQPROJECTMAP", sequenceName = "EMP_PSQPROJECTMAP", allocationSize = 1)
public class PsqProjectMap implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_PSQPROJECTMAP")
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@OneToOne
	@JoinColumn(name = "project")
	private peProApplyNo project;

	@ManyToOne
	@JoinColumn(name = "psq")
	private Paper paper;

	@Column(name = "status")
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public peProApplyNo getProject() {
		return project;
	}

	public void setProject(peProApplyNo project) {
		this.project = project;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}
}
