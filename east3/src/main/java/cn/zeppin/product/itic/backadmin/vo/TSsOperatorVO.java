package cn.zeppin.product.itic.backadmin.vo;

import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperator.TSsOperatorStatus;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;
import cn.zeppin.product.itic.core.entity.base.Entity;

public class TSsOperatorVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8380233637752469096L;
	
	private String id;
	private String name;
	private String code;
	private String role;
	private String roleName;
	private String department;
	private String departmentName;
	private String status;
	private String statusCN;
	
	public TSsOperatorVO(){
		
	}
	
	public TSsOperatorVO(TSsOperator operator){
		this.id = operator.getId();
		this.role = operator.getRole();
		if(RoleId.user.equals(operator.getRole())){
			this.roleName = "user";
		}else if(RoleId.superAdmin.equals(operator.getRole())){
			this.roleName = "superAdmin";
		}else if(RoleId.leader.equals(operator.getRole())){
			this.roleName = "leader";
		}
		this.name = operator.getName();
		this.status = operator.getStatus();
		if(TSsOperatorStatus.NORMAL.equals(operator.getStatus())){
			this.statusCN = "正常";
		}else if(TSsOperatorStatus.DISABLE.equals(operator.getStatus())){
			this.statusCN = "停用";
		}
		this.department = operator.getDepartment();
		this.code = operator.getCode();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
		if(TSsOperatorStatus.NORMAL.equals(status)){
			this.statusCN = "正常";
		}else if(TSsOperatorStatus.DISABLE.equals(status)){
			this.statusCN = "停用";
		}
	}

	public String getStatusCN() {
		return statusCN;
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
