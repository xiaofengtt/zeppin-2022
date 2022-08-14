package cn.zeppin.product.ntb.qcb.vo;

import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbAdmin.QcbAdminStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class QcbCompanyAdminVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7010872682032384372L;
	
	private String uuid;
	private String qcbAdmin;
	private String mobile;
	private String name;
	private String role;
	private String status;
	private String statusCN;
	
	private String menu;
	
	private boolean flagAdmin;
	
	public QcbCompanyAdminVO(){
		
	}
	
	public QcbCompanyAdminVO(QcbAdmin operator){
		this.qcbAdmin = operator.getUuid();
		this.name = operator.getName();
		this.mobile = operator.getMobile();
		this.status = operator.getStatus();
		if(QcbAdminStatus.NORMAL.equals(operator.getStatus())){
			this.statusCN = "正常";
		}else if(QcbAdminStatus.DISABLE.equals(operator.getStatus())){
			this.statusCN = "停用";
		}else if(QcbAdminStatus.DELETED.equals(operator.getStatus())){
			this.statusCN = "已删除";
		} else {
			this.statusCN = "--";
		}
		this.flagAdmin = false;
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

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
		if(QcbAdminStatus.NORMAL.equals(status)){
			this.statusCN = "正常";
		}else if(QcbAdminStatus.DISABLE.equals(status)){
			this.statusCN = "停用";
		}else if(QcbAdminStatus.DELETED.equals(status)){
			this.statusCN = "已删除";
		} else {
			this.statusCN = "--";
		}
	}

	public String getStatusCN() {
		return statusCN;
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	
	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getQcbAdmin() {
		return qcbAdmin;
	}

	public void setQcbAdmin(String qcbAdmin) {
		this.qcbAdmin = qcbAdmin;
	}

	public boolean isFlagAdmin() {
		return flagAdmin;
	}

	public void setFlagAdmin(boolean flagAdmin) {
		this.flagAdmin = flagAdmin;
	}
}
