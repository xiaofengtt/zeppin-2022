/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BkPayment;
import cn.zeppin.product.ntb.core.entity.BkPayment.BkPaymentStatus;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class BkPaymentVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String payment;
	private String paymentDes;
	private Boolean flagSwitch;
	private Timestamp createtime;
	private String creator;
	private String creatorCN;
	private String status;
	private String createtimeCN;
	private String statusCN;
	
	
	public BkPaymentVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BkPaymentVO(BkPayment bp) {
		this.uuid = bp.getUuid();
		this.payment = bp.getPayment();
		this.flagSwitch = bp.getFlagSwitch();
		this.createtime = bp.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToDateString(bp.getCreatetime());
		this.creator = bp.getCreator();
		this.status = bp.getStatus();
		if(BkPaymentStatus.NORMAL.equals(bp.getStatus())){
			this.statusCN = "正常";
		} else if (BkPaymentStatus.DELETED.equals(bp.getStatus())) {
			this.statusCN = "已删除";
		} else {
			this.statusCN = "";
		}
		this.paymentDes = bp.getPaymentDes();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPayment() {
		return payment;
	}
	
	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Boolean getFlagSwitch() {
		return flagSwitch;
	}

	public void setFlagSwitch(Boolean flagSwitch) {
		this.flagSwitch = flagSwitch;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCreatorCN() {
		return creatorCN;
	}

	public void setCreatorCN(String creatorCN) {
		this.creatorCN = creatorCN;
	}

	
	public String getPaymentDes() {
		return paymentDes;
	}
	

	public void setPaymentDes(String paymentDes) {
		this.paymentDes = paymentDes;
	}
	
}
