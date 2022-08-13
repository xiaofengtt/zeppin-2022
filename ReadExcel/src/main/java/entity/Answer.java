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
import javax.persistence.UniqueConstraint;

/**
 * Answer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "answer",
	catalog = "xjtts",
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "QUESTION", "INX" }))
public class Answer implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Psq psq;
    private Question question;
    private Integer inx;
    private String name;
    private Boolean isright;
    private Boolean isdefault;
    private String pic;
    private Integer score;
    private Integer jump;
    private String about;
    private Set<Result> results = new HashSet<Result>(0);

    // Constructors

    /** default constructor */
    public Answer()
    {
    }

    /** minimal constructor */
    public Answer(Psq psq, Question question, Integer inx, String name,
	    Boolean isright, Boolean isdefault, Integer score)
    {
	this.psq = psq;
	this.question = question;
	this.inx = inx;
	this.name = name;
	this.isright = isright;
	this.isdefault = isdefault;
	this.score = score;
    }

    /** full constructor */
    public Answer(Psq psq, Question question, Integer inx, String name,
	    Boolean isright, Boolean isdefault, String pic, Integer score,
	    Integer jump, String about, Set<Result> results)
    {
	this.psq = psq;
	this.question = question;
	this.inx = inx;
	this.name = name;
	this.isright = isright;
	this.isdefault = isdefault;
	this.pic = pic;
	this.score = score;
	this.jump = jump;
	this.about = about;
	this.results = results;
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
    @JoinColumn(name = "PSQ", nullable = false)
    public Psq getPsq()
    {
	return this.psq;
    }

    public void setPsq(Psq psq)
    {
	this.psq = psq;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION", nullable = false)
    public Question getQuestion()
    {
	return this.question;
    }

    public void setQuestion(Question question)
    {
	this.question = question;
    }

    @Column(name = "INX", nullable = false)
    public Integer getInx()
    {
	return this.inx;
    }

    public void setInx(Integer inx)
    {
	this.inx = inx;
    }

    @Column(name = "NAME", nullable = false, length = 2000)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "ISRIGHT", nullable = false)
    public Boolean getIsright()
    {
	return this.isright;
    }

    public void setIsright(Boolean isright)
    {
	this.isright = isright;
    }

    @Column(name = "ISDEFAULT", nullable = false)
    public Boolean getIsdefault()
    {
	return this.isdefault;
    }

    public void setIsdefault(Boolean isdefault)
    {
	this.isdefault = isdefault;
    }

    @Column(name = "PIC", length = 200)
    public String getPic()
    {
	return this.pic;
    }

    public void setPic(String pic)
    {
	this.pic = pic;
    }

    @Column(name = "SCORE", nullable = false)
    public Integer getScore()
    {
	return this.score;
    }

    public void setScore(Integer score)
    {
	this.score = score;
    }

    @Column(name = "JUMP")
    public Integer getJump()
    {
	return this.jump;
    }

    public void setJump(Integer jump)
    {
	this.jump = jump;
    }

    @Column(name = "ABOUT", length = 500)
    public String getAbout()
    {
	return this.about;
    }

    public void setAbout(String about)
    {
	this.about = about;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "answer")
    public Set<Result> getResults()
    {
	return this.results;
    }

    public void setResults(Set<Result> results)
    {
	this.results = results;
    }

}