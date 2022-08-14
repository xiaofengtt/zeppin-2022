package cn.zeppin.product.ntb.qcb.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyPayrollFeedbackVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5055488672416921543L;
	
	private String uuid;
	private String qcbCompanyPayroll;
	private String qcbCompanyPayrollRecords;
	private String content;
	private String status;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
 	public QcbCompanyPayrollFeedbackVO(){
		
	}
	
	public QcbCompanyPayrollFeedbackVO(QcbCompanyPayrollFeedback qcpf){
		this.uuid = qcpf.getUuid();
		this.qcbCompanyPayroll = qcpf.getQcbCompanyPayroll();
		this.qcbCompanyPayrollRecords = qcpf.getQcbCompanyPayrollRecords();
		this.content = qcpf.getContent();
		
		this.status = qcpf.getStatus();
		this.createtime = qcpf.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qcpf.getCreatetime());
		this.creator = qcpf.getCreator();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQcbCompanyPayroll() {
		return qcbCompanyPayroll;
	}

	public void setQcbCompanyPayroll(String qcbCompanyPayroll) {
		this.qcbCompanyPayroll = qcbCompanyPayroll;
	}

	public String getQcbCompanyPayrollRecords() {
		return qcbCompanyPayrollRecords;
	}

	public void setQcbCompanyPayrollRecords(String qcbCompanyPayrollRecords) {
		this.qcbCompanyPayrollRecords = qcbCompanyPayrollRecords;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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
