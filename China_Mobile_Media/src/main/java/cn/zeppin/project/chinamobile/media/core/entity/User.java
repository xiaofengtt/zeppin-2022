/**
 * 
 */
package cn.zeppin.project.chinamobile.media.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Entity
@Table(name = "sys_user", uniqueConstraints = { @UniqueConstraint(columnNames = "phone"), @UniqueConstraint(columnNames = "email")})
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -552158329761307133L;
	
	private String id;
	private String name;
	private String role;
	private String status;
	private String password;
	private String phone;
	private String email;
	private String department;
	private String jobtitle;
	private String creator;
	private Timestamp createtime;
	
	// Constructors

	/** default constructor */
	public User() {
		
	}
	
	
	// Property accessors
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator") 
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@NotBlank(message="用户名不能为空")   
	@Column(name = "name", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "role", nullable = false, length = 20)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@NotNull(message="用户密码不能为空") 
	@Column(name = "password", nullable = false,  length = 128)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone", nullable = false,  length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "department", length = 200)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "jobtitle", length = 100)
	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}
