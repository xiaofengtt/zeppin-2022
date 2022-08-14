/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 * @author L
 *
 * 
 * @description 【数据对象】TOPERATOR入库
 */
@Entity
@Table(name = "TOPERATOR")
public class Toperator extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer opCode;
	private String opName;
	private Integer departId;
	private Integer roleId;
	private Integer status;
	private Integer online;
	private Integer pagesize;
	
	private String mobile;
	private String oTel;
	

	@Id
	@Column(name = "OP_CODE", unique = true, nullable = false)
	public Integer getOpCode() {
		return opCode;
	}

	public void setOpCode(Integer opCode) {
		this.opCode = opCode;
	}

	@Column(name = "OP_NAME", nullable = false, length = 20)
	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	@Column(name = "DEPART_ID", nullable = false)
	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	@Column(name = "ROLE_ID", nullable = false)
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "ONLINE", nullable = false)
	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	@Column(name = "PAGESIZE", nullable = false)
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	@Column(name = "MOBILE", length = 100)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "O_TEL", length = 40)
	public String getoTel() {
		return oTel;
	}

	public void setoTel(String oTel) {
		this.oTel = oTel;
	}
	
}
