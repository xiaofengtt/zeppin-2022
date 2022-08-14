/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】社保熊参保人
 */

@Entity
@Table(name = "shbx_insured")
public class ShbxInsured extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8785839802975628452L;
	
	private String uuid;
	private String name;
	private String idcard;
	private String mobile;
	private String householdtype;
	private String householdarea;
	private Timestamp worktime;
	private String education;
	private String email;
	private String nationality;
	private String duty;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class ShbxInsuredStatus{
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

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "idcard", nullable = false, length = 20)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "mobile", nullable = false, length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "householdtype", nullable = false, length = 30)
	public String getHouseholdtype() {
		return householdtype;
	}

	public void setHouseholdtype(String householdtype) {
		this.householdtype = householdtype;
	}

	@Column(name = "householdarea", nullable = false, length = 36)
	public String getHouseholdarea() {
		return householdarea;
	}

	public void setHouseholdarea(String householdarea) {
		this.householdarea = householdarea;
	}

	@Column(name = "worktime")
	public Timestamp getWorktime() {
		return worktime;
	}

	public void setWorktime(Timestamp worktime) {
		this.worktime = worktime;
	}

	@Column(name = "education", length = 20)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "nationality", length = 20)
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "duty", length = 20)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
