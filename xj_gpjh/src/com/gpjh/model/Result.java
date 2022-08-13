package com.gpjh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="result")
@Repository("result")
public class Result implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
    @ManyToOne
    @JoinColumn(name="loginkey")
    private LoginKey loginkey;
    
    @ManyToOne
    @JoinColumn(name="question")
    private Question question;
    
    @ManyToOne
    @JoinColumn(name="answer")
    private Answer answer;
    
    @Column(name = "content", nullable = true)
    private String content;
    
    @Column(name = "score", nullable = true)
    private int score;
    
	@Column(name = "creattime", unique = true, nullable = false)
	private Date creatTime = new Date();

	@Transient
	@Column(name = "question", unique = true, nullable = false)
	private int qid;
	
	@OneToOne
    @JoinColumn(name="submit")
    private Submit submit;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LoginKey getLoginkey() {
		return loginkey;
	}

	public void setLoginkey(LoginKey loginkey) {
		this.loginkey = loginkey;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public Submit getSubmit() {
		return submit;
	}

	public void setSubmit(Submit submit) {
		this.submit = submit;
	}
	
}
