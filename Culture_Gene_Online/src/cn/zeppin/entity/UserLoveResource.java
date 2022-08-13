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
@Table(name = "user_love_resource")
public class UserLoveResource extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer id;
	private User user;
	private Resource resource;
	private Timestamp createtime;
	// Constructors

	/** default constructor */
	public UserLoveResource() {
	}

	/** full constructor */
	public UserLoveResource(User user, Resource resource, Timestamp createtime) {
		this.user = user;
		this.resource = resource;
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
	@JoinColumn(name = "USER", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RESOURCE", nullable = false)
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
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