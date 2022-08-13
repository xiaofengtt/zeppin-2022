package cn.zeppin.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.zeppin.entity.base.BaseEntity;

@Entity
@Table(name = "keyword")
public class Keyword extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer id;
	private String word;
	private User user;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Keyword() {
	}

	/** full constructor */
	public Keyword(String word, User user, Timestamp createtime) {
		this.word = word;
		this.user = user;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "WORD", nullable = false, length = 200)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime()
	{
		return this.createtime;
	}
	
	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}