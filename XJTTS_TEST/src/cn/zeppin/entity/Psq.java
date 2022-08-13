package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Psq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "psq")
public class Psq implements java.io.Serializable
{

    // Fields

    private Integer id;
    private String title;
    private String about;
    private Short type;
    private Short status;
    private String closing;
    private String gotourl;
    private Short theway;
    private Integer creator;
    private Timestamp creattime;
    private String surveydata;
    private Set<Submit> submits = new HashSet<Submit>(0);
    private Set<Answer> answers = new HashSet<Answer>(0);
    private Set<Project> projectsForProjectJudgePsq = new HashSet<Project>(0);
    private Set<Project> projectsForEvaluationTrainingPsq = new HashSet<Project>(0);
    private Set<Question> questions = new HashSet<Question>(0);
    private Set<Project> projectsForEvaluationTeacherPsq = new HashSet<Project>(0);

    // Constructors

    /** default constructor */
    public Psq()
    {
    }

    /** minimal constructor */
    public Psq(String title, String about, Short type, Short status,
	    String closing, Short theway, Integer creator, Timestamp creattime)
    {
	this.title = title;
	this.about = about;
	this.type = type;
	this.status = status;
	this.closing = closing;
	this.theway = theway;
	this.creator = creator;
	this.creattime = creattime;
    }

    /** full constructor */
    public Psq(String title, String about, Short type, Short status,
	    String closing, String gotourl, Short theway, Integer creator,
	    Timestamp creattime, String surveydata, Set<Submit> submits,
	    Set<Answer> answers, Set<Project> projectsForProjectJudgePsq,
	    Set<Project> projectsForEvaluationTrainingPsq,
	    Set<Question> questions,
	    Set<Project> projectsForEvaluationTeacherPsq)
    {
	this.title = title;
	this.about = about;
	this.type = type;
	this.status = status;
	this.closing = closing;
	this.gotourl = gotourl;
	this.theway = theway;
	this.creator = creator;
	this.creattime = creattime;
	this.surveydata = surveydata;
	this.submits = submits;
	this.answers = answers;
	this.projectsForProjectJudgePsq = projectsForProjectJudgePsq;
	this.projectsForEvaluationTrainingPsq = projectsForEvaluationTrainingPsq;
	this.questions = questions;
	this.projectsForEvaluationTeacherPsq = projectsForEvaluationTeacherPsq;
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

    @Column(name = "TITLE", nullable = false, length = 1000)
    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    @Column(name = "ABOUT", nullable = false, length = 2000)
    public String getAbout()
    {
	return this.about;
    }

    public void setAbout(String about)
    {
	this.about = about;
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

    @Column(name = "STATUS", nullable = false)
    public Short getStatus()
    {
	return this.status;
    }

    public void setStatus(Short status)
    {
	this.status = status;
    }

    @Column(name = "CLOSING", nullable = false, length = 2000)
    public String getClosing()
    {
	return this.closing;
    }

    public void setClosing(String closing)
    {
	this.closing = closing;
    }

    @Column(name = "GOTOURL", length = 400)
    public String getGotourl()
    {
	return this.gotourl;
    }

    public void setGotourl(String gotourl)
    {
	this.gotourl = gotourl;
    }

    @Column(name = "THEWAY", nullable = false)
    public Short getTheway()
    {
	return this.theway;
    }

    public void setTheway(Short theway)
    {
	this.theway = theway;
    }

    @Column(name = "CREATOR", nullable = false)
    public Integer getCreator()
    {
	return this.creator;
    }

    public void setCreator(Integer creator)
    {
	this.creator = creator;
    }

    @Column(name = "CREATTIME", nullable = false, length = 19)
    public Timestamp getCreattime()
    {
	return this.creattime;
    }

    public void setCreattime(Timestamp creattime)
    {
	this.creattime = creattime;
    }

    @Column(name = "SURVEYDATA", length = 10000)
    public String getSurveydata()
    {
	return this.surveydata;
    }

    public void setSurveydata(String surveydata)
    {
	this.surveydata = surveydata;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "psq")
    public Set<Submit> getSubmits()
    {
	return this.submits;
    }

    public void setSubmits(Set<Submit> submits)
    {
	this.submits = submits;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "psq")
    public Set<Answer> getAnswers()
    {
	return this.answers;
    }

    public void setAnswers(Set<Answer> answers)
    {
	this.answers = answers;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "psqByProjectJudgePsq")
    public Set<Project> getProjectsForProjectJudgePsq()
    {
	return this.projectsForProjectJudgePsq;
    }

    public void setProjectsForProjectJudgePsq(
	    Set<Project> projectsForProjectJudgePsq)
    {
	this.projectsForProjectJudgePsq = projectsForProjectJudgePsq;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "psqByEvaluationTrainingPsq")
    public Set<Project> getProjectsForEvaluationTrainingPsq()
    {
	return this.projectsForEvaluationTrainingPsq;
    }

    public void setProjectsForEvaluationTrainingPsq(
	    Set<Project> projectsForEvaluationTrainingPsq)
    {
	this.projectsForEvaluationTrainingPsq = projectsForEvaluationTrainingPsq;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "psq"
	    )
    @OrderBy("id ASC")
    public Set<Question> getQuestions()
    {
	return this.questions;
    }

    public void setQuestions(Set<Question> questions)
    {
	this.questions = questions;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "psqByEvaluationTeacherPsq")
    public Set<Project> getProjectsForEvaluationTeacherPsq()
    {
	return this.projectsForEvaluationTeacherPsq;
    }

    public void setProjectsForEvaluationTeacherPsq(
	    Set<Project> projectsForEvaluationTeacherPsq)
    {
	this.projectsForEvaluationTeacherPsq = projectsForEvaluationTeacherPsq;
    }

}