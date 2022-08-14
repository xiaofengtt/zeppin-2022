package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyOperateCheck;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperateCheck.QcbCompanyOperateCheckStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyOperateCheckVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2403315505808040611L;
	private String uuid;
	private String reason;
	private String status;
	private String statusCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
 	public QcbCompanyOperateCheckVO(){
		
	}
	
	public QcbCompanyOperateCheckVO(QcbCompanyOperateCheck qcoc){
		this.uuid = qcoc.getUuid();
		this.reason = qcoc.getReason();
		this.status = qcoc.getStatus();
		if(QcbCompanyOperateCheckStatus.CHECKED.equals(qcoc.getStatus())){
			this.statusCN = "审核通过";
		}else if(QcbCompanyOperateCheckStatus.UNPASSED.equals(qcoc.getStatus())){
			this.statusCN = "审核不通过";
		}
		this.createtime = qcoc.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qcoc.getCreatetime());
		this.creator = qcoc.getCreator();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	
}
