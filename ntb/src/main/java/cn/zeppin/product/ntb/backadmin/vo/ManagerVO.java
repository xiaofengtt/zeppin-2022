package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Manager;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;

public class ManagerVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8380233637752469096L;
	
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
	private String photoUrl;
	private String status;
	private Timestamp createtime;
	private String createtimeCN;
	private String creator;
	private String creatorName;
	
	public ManagerVO(){
		
	}
	
	public ManagerVO(Manager manager){
		this.uuid = manager.getUuid();
		this.name = manager.getName();
		this.type = manager.getType();
		this.graduation = manager.getGraduation();
		this.education = manager.getEducation();
		this.score = manager.getScore();
		this.resume = manager.getResume();
		this.workage = manager.getWorkage();
		this.mobile = manager.getMobile();
		this.idcard = manager.getIdcard();
		this.email = manager.getEmail();
		this.photo = manager.getPhoto();
		this.status = manager.getStatus();
		this.createtime = manager.getCreatetime();
		this.creator = manager.getCreator();
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getGraduation() {
		return graduation;
	}
	
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	public String getEducation() {
		return education;
	}
	
	public void setEducation(String education) {
		this.education = education;
	}

	public BigDecimal getScore() {
		return score;
	}
	
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getResume() {
		return resume;
	}
	
	public void setResume(String resume) {
		this.resume = resume;
	}

	public Integer getWorkage() {
		return workage;
	}
	
	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdcard() {
		return idcard;
	}
	
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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
		this.createtimeCN = DataTimeConvert.timespanToString(createtime,"yyyy-MM-dd HH:mm:ss");
	}
	public String getCreatetimeCN() {
		return createtimeCN;
	}
	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
}
