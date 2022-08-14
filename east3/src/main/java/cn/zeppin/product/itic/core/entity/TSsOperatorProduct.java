/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

@Entity
@Table(name = "T_SS_OPERATOR_PRODUCT")
public class TSsOperatorProduct extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String opcode;
	private String opname;
	private String productcode;
	private String productname;
	private String departid;
	private String departname;
	
	@Id
	public String getOpcode() {
		return opcode;
	}
	
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}
	
	@Id
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}
}
