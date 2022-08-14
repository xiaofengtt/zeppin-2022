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
 * @author hehe 2016年2月8日
 * @description 【数据对象】支行信息
 */

@Entity
@Table(name = "investor_idcard_img")
public class InvestorIdcardImg extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String imgface;
	private String imgback;
	private String reason;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	private String checker;
	private Timestamp checktime;
	
	public class InvestorIdcardImgStatus{
		public final static String NOTUPLOAD = "notupload";
		public final static String UNCHECKED = "unchecked";
		public final static String CHECKED = "checked";
		public final static String UNPASSED = "unpassed";
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
	
	@Column(name = "imgface", nullable = false, length = 36)
	public String getImgface() {
		return imgface;
	}
	

	public void setImgface(String imgface) {
		this.imgface = imgface;
	}
	
	@Column(name = "imgback", nullable = false, length = 36)
	public String getImgback() {
		return imgback;
	}
	

	public void setImgback(String imgback) {
		this.imgback = imgback;
	}
	
	@Column(name = "reason", length = 50)
	public String getReason() {
		return reason;
	}
	

	public void setReason(String reason) {
		this.reason = reason;
	}
	

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	@Column(name = "checker", length = 36)
	public String getChecker() {
		return checker;
	}
	

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@Column(name = "checktime", length = 36)
	public Timestamp getChecktime() {
		return checktime;
	}
	

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}
	
}
