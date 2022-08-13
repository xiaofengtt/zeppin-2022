package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.entity.base.BaseEntity;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "NAME")})
public class User extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7332970541949961101L;
	private Integer id;
	private Role role;
	private String email;
	private String phone;
	private String name;
	private String password;
	private Integer status;
	private String company;
	private String job;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Role role, String email, String phone, String name, String password, Integer status, String company, String job) {
		this.role = role;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.password = password;
		this.status = status;
		this.company = company;
		this.job = job;
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
	@JoinColumn(name = "ROLE", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "EMAIL", unique = true, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", unique = true, nullable = false, length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "JOB", unique = true, length = 100)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Column(name = "COMPANY", unique = true, length = 200)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}

}