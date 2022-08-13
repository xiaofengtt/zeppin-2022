package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Result entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "result", catalog = "xjtts")
public class Result implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Submit submit;
    private Answer answer;
    private Question question;
    private String content;
    private Integer score;

    // Constructors

    /** default constructor */
    public Result()
    {
    }

    /** full constructor */
    public Result(Submit submit, Answer answer, Question question,
	    String content, Integer score)
    {
	this.submit = submit;
	this.answer = answer;
	this.question = question;
	this.content = content;
	this.score = score;
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
    @JoinColumn(name = "SUBMIT", nullable = false)
    public Submit getSubmit()
    {
	return this.submit;
    }

    public void setSubmit(Submit submit)
    {
	this.submit = submit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANSWER", nullable = false)
    public Answer getAnswer()
    {
	return this.answer;
    }

    public void setAnswer(Answer answer)
    {
	this.answer = answer;
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

    @Column(name = "CONTENT", nullable = false, length = 2000)
    public String getContent()
    {
	return this.content;
    }

    public void setContent(String content)
    {
	this.content = content;
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

}