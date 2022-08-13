package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkOperator.BkOperatorStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;

public class OperatorVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8380233637752469096L;
	
	private String uuid;
	private String role;
	private String roleName;
	private String name;
	private String realname;
	private String mobile;
	private String email;
	private String status;
	private String statusCN;
	private String creator;
	private String creatorName;
	private Timestamp lockedtime;
	private String lockedtimeCN;
	private Timestamp createtime;
	private String createtimeCN;
	
	public OperatorVO(){
		
	}
	
	public OperatorVO(BkOperator operator){
		this.uuid = operator.getUuid();
		this.role = operator.getRole();
		this.name = operator.getName();
		this.realname = operator.getRealname();
		this.mobile = operator.getMobile();
		this.email = operator.getEmail();
		this.status = operator.getStatus();
		this.creator = operator.getCreator();
		this.lockedtime = operator.getLockedtime();
		if(lockedtime != null){
			this.lockedtimeCN = DataTimeConvert.timespanToString(lockedtime,"yyyy-MM-dd HH:mm:ss");
		}
		this.createtime = operator.getCreatetime();
		this.createtimeCN = DataTimeConvert.timespanToString(createtime,"yyyy-MM-dd HH:mm:ss");
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getRealname() {
		return realname;
	}
	
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
		if(BkOperatorStatus.NORMAL.equals(status)){
			this.statusCN = "正常";
		}else if(BkOperatorStatus.DISABLE.equals(status)){
			this.statusCN = "停用";
		}else if(BkOperatorStatus.LOCKED.equals(status)){
			this.statusCN = "锁定";
		}else if(BkOperatorStatus.UNOPEN.equals(status)){
			this.statusCN = "未开通";
		}else if(BkOperatorStatus.DELETED.equals(status)){
			this.statusCN = "已删除";
		}
	}

	public String getStatusCN() {
		return statusCN;
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
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
	
	public Timestamp getLockedtime() {
		return lockedtime;
	}
	
	public void setLockedtime(Timestamp lockedtime) {
		this.lockedtime = lockedtime;
		if(lockedtime != null){
			this.lockedtimeCN = DataTimeConvert.timespanToString(lockedtime,"yyyy-MM-dd HH:mm:ss");
		}
	}
	
	public String getLockedtimeCN() {
		return lockedtimeCN;
	}
	
	public void setLockedtimeCN(String lockedtimeCN) {
		this.lockedtimeCN = lockedtimeCN;
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
