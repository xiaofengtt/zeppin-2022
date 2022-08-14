/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class ShbxInsuredVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5094796942055794802L;
	
	private String uuid;
	private String name;
	private String idcard;
	private String mobile;
	private String householdtype;
	private String householdarea;
	private String householdareaName;
	private Timestamp worktime;
	private String worktimeCN;
	private String education;
	private String email;
	private String nationality;
	private String duty;
	private String status;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public ShbxInsuredVO(){
		
	}
	
	public ShbxInsuredVO(ShbxInsured si){
		this.uuid = si.getUuid();
		this.name = si.getName();
		this.idcard = si.getIdcard();
		this.householdarea = si.getHouseholdarea();
		this.householdtype = si.getHouseholdtype();
		this.worktime = si.getWorktime();
		this.worktimeCN = Utlity.timeSpanToDateString(si.getCreatetime());
		this.education = si.getEducation();
		this.email = si.getEmail();
		this.nationality = si.getNationality();
		this.duty = si.getDuty();
		this.status = si.getStatus();
		this.creator = si.getCreator();
		this.createtime = si.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(si.getCreatetime());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHouseholdtype() {
		return householdtype;
	}

	public void setHouseholdtype(String householdtype) {
		this.householdtype = householdtype;
	}

	public String getHouseholdarea() {
		return householdarea;
	}

	public void setHouseholdarea(String householdarea) {
		this.householdarea = householdarea;
	}

	public String getHouseholdareaName() {
		return householdareaName;
	}

	public void setHouseholdareaName(String householdareaName) {
		this.householdareaName = householdareaName;
	}

	public Timestamp getWorktime() {
		return worktime;
	}

	public void setWorktime(Timestamp worktime) {
		this.worktime = worktime;
	}

	public String getWorktimeCN() {
		return worktimeCN;
	}

	public void setWorktimeCN(String worktimeCN) {
		this.worktimeCN = worktimeCN;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
}
