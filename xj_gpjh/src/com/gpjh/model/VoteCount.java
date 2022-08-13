package com.gpjh.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="votecount")
@Repository("votecount")
public class VoteCount implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
    @OneToOne
    @JoinColumn(name="loginkey")
    private LoginKey loninKey;
    
    @Column(name = "count", nullable = true)
    private int count;
    
	public LoginKey getLoninKey() {
		return loninKey;
	}

	public void setLoninKey(LoginKey loninKey) {
		this.loninKey = loninKey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
