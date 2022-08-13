package com.gpjh.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="answer")
@Repository("answer")
@SequenceGenerator(name = "EMP_ANSWER", sequenceName = "EMP_ANSWER", allocationSize = 1)
public class Answer implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_ANSWER")
	@Column(name = "id", unique = true, nullable = false)
	private int id;

    @ManyToOne
    @JoinColumn(name="psq")
    private Paper paper;
    
    @ManyToOne
    @JoinColumn(name="question")
    private Question question;
    
	@Column(name = "inx", unique = true, nullable = false)
	private int inx;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "isright", unique = true, nullable = false)
	private boolean isRight;
	
	@Column(name = "isdefault", unique = true, nullable = false)
	private boolean isDefault;
	
	@Column(name = "pic", unique = true, nullable = false)
	private String pic="";
	
	@Column(name = "score", unique = true, nullable = false)
	private int score;
	
	@Column(name = "jump", unique = true, nullable = false)
	private int jump;
	
	@Column(name = "about", unique = true, nullable = false)
	private String about;

	public Answer(){}
	
	public Answer(int id){
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getInx() {
		return inx;
	}

	public void setInx(int inx) {
		this.inx = inx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
}
