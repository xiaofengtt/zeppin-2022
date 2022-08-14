package cn.zeppin.product.ntb.qcb.vo;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class QcbCompanyAccountLessVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4740659141418123237L;
	
	private String uuid;
	private String name;
	
	
	public QcbCompanyAccountLessVO(){
		
	}
	
	public QcbCompanyAccountLessVO(QcbCompanyAccount company){
		this.uuid = company.getUuid();
		this.name = company.getName();
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
	
}
