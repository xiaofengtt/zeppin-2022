package cn.zeppin.product.ntb.qcb.vo;

import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee.QcbCompanyEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class QcbCompanyEmployeeVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7010872682032384372L;
	
	private String uuid;
	private String qcbCompany;
	private String qcbEmployee;
	private String department;
	private String duty;
	private String status;
	private String statusCN;
	
	private String realname;
	private String idcard;
	private String mobile;
	
	public QcbCompanyEmployeeVO(){
		
	}
	
	public QcbCompanyEmployeeVO(QcbCompanyEmployee operator){
		this.uuid = operator.getUuid();
		this.qcbCompany = operator.getQcbCompany();
		this.qcbEmployee = operator.getQcbEmployee();
		this.department = operator.getDepartment();
		this.duty = operator.getDuty();
		this.status = operator.getStatus();
		if(QcbCompanyEmployeeStatus.NORMAL.equals(operator.getStatus())){
			this.statusCN = "在职";
		}else if(QcbCompanyEmployeeStatus.DISABLE.equals(operator.getStatus())){
			this.statusCN = "离职";
		}else if(QcbCompanyEmployeeStatus.DELETED.equals(operator.getStatus())){
			this.statusCN = "已删除";
		} else {
			this.statusCN = "--";
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
		if(QcbCompanyEmployeeStatus.NORMAL.equals(status)){
			this.statusCN = "在职";
		}else if(QcbCompanyEmployeeStatus.DISABLE.equals(status)){
			this.statusCN = "离职";
		}else if(QcbCompanyEmployeeStatus.DELETED.equals(status)){
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

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	public String getQcbEmployee() {
		return qcbEmployee;
	}

	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
}
