package com.gpjh.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="question")
@Repository("question")
@SequenceGenerator(name = "EMP_QUESTION", sequenceName = "EMP_QUESTION", allocationSize = 1)
public class Question implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_QUESTION")
	@Column(name = "id", unique = true, nullable = false)
	private int id;

    @ManyToOne
    @JoinColumn(name="psq")
    private Paper paper;
    
    @OneToMany(mappedBy="question", fetch=FetchType.EAGER )
    @OrderBy("id ASC")
    private Set<Answer> answers;
    
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "inx", unique = true, nullable = false)
	private int inx;
	
	@Column(name = "type", unique = true, nullable = false)
	private short type;
	
	@Column(name = "ismust", unique = true, nullable = false)
	private boolean isMust;
	
	@Column(name = "arrange", unique = true, nullable = false)
	private int arrange;
	
	@Column(name = "hint", unique = true, nullable = false)
	private String hint;
	
	@Column(name = "scale", unique = true, nullable = false)
	private int scale;

	@Column(name = "istongji", unique = true, nullable = false)
	private int isTongji;
	
	public Question(){}
	
	public Question(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInx() {
		return inx;
	}

	public void setInx(int inx) {
		this.inx = inx;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public boolean getIsMust() {
		return isMust;
	}

	public void setIsMust(boolean isMust) {
		this.isMust = isMust;
	}

	public int getArrange() {
		return arrange;
	}

	public void setArrange(int arrange) {
		this.arrange = arrange;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getIsTongji() {
		return isTongji;
	}

	public void setIsTongji(int isTongji) {
		this.isTongji = isTongji;
	}
	
}
