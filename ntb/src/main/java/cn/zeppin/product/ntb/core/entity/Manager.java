/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author hehe 2017年2月7日
 * @description 【数据对象】主理人信息
 */

@Entity
@Table(name = "manager", uniqueConstraints = {@UniqueConstraint(columnNames = "idcard"),@UniqueConstraint(columnNames = "mobile")})
public class Manager extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String name;
	private String type;
	private String graduation;
	private String education;
	private BigDecimal score;
	private String resume;
	private Integer workage;
	private String mobile;
	private String idcard;
	private String email;
	private String photo;
	private String status;
	private Timestamp createtime;
	private String creator;
	
	public class ManagerStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "graduation", length = 50)
	public String getGraduation() {
		return graduation;
	}
	
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	@Column(name = "education", length = 20)
	public String getEducation() {
		return education;
	}
	
	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "score")
	public BigDecimal getScore() {
		return score;
	}
	
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Column(name = "resume")
	public String getResume() {
		return resume;
	}
	
	public void setResume(String resume) {
		this.resume = resume;
	}

	@Column(name = "workage")
	public Integer getWorkage() {
		return workage;
	}
	
	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	@Column(name = "mobile", unique = true, nullable = false, length = 50)
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "idcard", nullable = false, length = 100)
	public String getIdcard() {
		return idcard;
	}
	
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "photo", length = 36)
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
