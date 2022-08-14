/**
 * 
 */
package cn.zeppin.product.itic.backadmin.vo;

import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author L
 */
public class ToperatorMobileVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233988491797513339L;
	
	private Integer id;
	
	private String status;
	private String statusCN;
	
	private Integer opCode;
	private String opName;
	
	private String toMobile;
	private String toTel;
	
	public ToperatorMobileVO(ToperatorMobile tr) {
		this.id = tr.getId();
		
		this.opCode = tr.getFkToperator();
		this.opName = "";
		
		this.toMobile = tr.getToMobile() == null ? "" : tr.getToMobile();
		this.toTel = tr.getToTel() == null ? "" : tr.getToTel();
		
		if(!Utlity.checkStringNull(this.toMobile) && !Utlity.checkStringNull(this.toTel)) {
			this.status = "normal";
			this.statusCN = "正常";
		} else {
			this.status = "error";
			this.statusCN = "异常";
		}
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusCN() {
		return statusCN;
	}


	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	
	public Integer getOpCode() {
		return opCode;
	}


	public void setOpCode(Integer opCode) {
		this.opCode = opCode;
	}


	public String getOpName() {
		return opName;
	}


	public void setOpName(String opName) {
		this.opName = opName;
	}


	public String getToMobile() {
		return toMobile;
	}


	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}


	public String getToTel() {
		return toTel;
	}


	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

}
