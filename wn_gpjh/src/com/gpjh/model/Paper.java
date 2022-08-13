package com.gpjh.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "psq")
@Repository("paper")
@SequenceGenerator(name = "EMP_SEQUENCE", sequenceName = "EMP_SEQUENCE", allocationSize = 1)
public class Paper implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQUENCE")
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "title", unique = true, nullable = false)
	private String title;

	@Column(name = "about", unique = true, nullable = false)
	private String about;

	@OneToMany(mappedBy = "paper", fetch = FetchType.EAGER)
	@OrderBy("id,inx ASC")
	private Set<Question> questions;

	@Column(name = "type", unique = true, nullable = false)
	private short type;

	@Column(name = "status", unique = true, nullable = false)
	private short status;

	@Column(name = "closing")
	private String closing;

	@Column(name = "answernum", unique = true, nullable = false)
	private int answerNum;

	@Column(name = "gotourl")
	private String gotoUrl;

	@Column(name = "theway", unique = true, nullable = false)
	private short theWay;

	@Column(name = "creator", unique = true, nullable = false)
	private String creator;

	@Column(name = "creattime", unique = true, nullable = false)
	private Date creatTime = new Date();

	@Column(name = "surveydata", unique = true, nullable = false)
	private String surveydata;
	
	@Column(name = "PROJECTTYPE")
	private int projectType;

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	@Transient
	private List<ProjectAppNo> projects;

	@Transient
	private int editalbe;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSurveydata() {
		return surveydata;
	}

	public void setSurveydata(String surveydata) {
		this.surveydata = surveydata;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getClosing() {
		return closing;
	}

	public void setClosing(String closing) {
		this.closing = closing;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}

	public String getGotoUrl() {
		return gotoUrl;
	}

	public void setGotoUrl(String gotoUrl) {
		this.gotoUrl = gotoUrl;
	}

	public short getTheWay() {
		return theWay;
	}

	public void setTheWay(short theWay) {
		this.theWay = theWay;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public List<ProjectAppNo> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectAppNo> projects) {
		this.projects = projects;
	}

	public int getEditalbe() {
		return editalbe;
	}

	public void setEditalbe(int editalbe) {
		this.editalbe = editalbe;
	}

}
