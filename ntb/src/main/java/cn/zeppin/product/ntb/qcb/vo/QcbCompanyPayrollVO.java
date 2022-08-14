package cn.zeppin.product.ntb.qcb.vo;

import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll.QcbCompanyPayrollStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll.QcbCompanyPayrollType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyPayrollVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String qcbCompany;
	private String qcbCompanyName;

	private String title;
	private Timestamp payTime;
	private String payTimeCN;
	private String type;
	private String typeCN;
	private String remark;
	private Boolean flagSms;
	private Boolean flagHide;
	
	private String columnData;
	private List<String> columnList;
	private Integer reward;
	
	private String sourcefile;
	private String sourcefileName;
	private String sourcefileUrl;
	
	private Integer payrollCount;
	private Integer comfirmCount;
	private Integer feedbackCount; 
	
	private String status;
	private String statusCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
 	public QcbCompanyPayrollVO(){
		
	}
	
	public QcbCompanyPayrollVO(QcbCompanyPayroll qcp){
		this.uuid = qcp.getUuid();
		this.qcbCompany = qcp.getQcbCompany();
		
		this.title = qcp.getTitle();
		this.payTime = qcp.getPayTime();
		this.payTimeCN = Utlity.timeSpanToString(qcp.getPayTime());
		this.type = qcp.getType();
		if(QcbCompanyPayrollType.WAGE.equals(qcp.getType())){
			this.typeCN = "工资";
		}else if(QcbCompanyPayrollType.BONUS.equals(qcp.getType())){
			this.typeCN = "奖金";
		}else if(QcbCompanyPayrollType.SUBSIDY.equals(qcp.getType())){
			this.typeCN = "补贴";
		}else if(QcbCompanyPayrollType.REIMBURSEMENT.equals(qcp.getType())){
			this.typeCN = "报销";
		}else if(QcbCompanyPayrollType.COMMISSION.equals(qcp.getType())){
			this.typeCN = "提成";
		}else if(QcbCompanyPayrollType.REISSUE.equals(qcp.getType())){
			this.typeCN = "补发";
		}else{
			this.typeCN = "其他";
		}
		
		this.remark = qcp.getRemark();
		this.flagSms = qcp.getFlagSms();
		this.flagHide = qcp.getFlagHide();
		this.sourcefile = qcp.getSourcefile();
		this.columnData = qcp.getColumnData();
		this.reward = qcp.getReward();
		this.columnList = JSONUtils.json2list(qcp.getColumnData(), String.class);
		
		this.status = qcp.getStatus();
		if(QcbCompanyPayrollStatus.SUCCESS.equals(qcp.getStatus())){
			this.statusCN = "已发送";
		}else if(QcbCompanyPayrollStatus.SUBMIT.equals(qcp.getStatus())){
			this.statusCN = "待发送";
		}else if(QcbCompanyPayrollStatus.FAILED.equals(qcp.getStatus())){
			this.statusCN = "发送失败";
		}else if(QcbCompanyPayrollStatus.DELETED.equals(qcp.getStatus())){
			this.statusCN = "已删除";
		}else{
			this.statusCN = "草稿";
		}
		this.createtime = qcp.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qcp.getCreatetime());
		this.creator = qcp.getCreator();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getPayTimeCN() {
		return payTimeCN;
	}

	public void setPayTimeCN(String payTimeCN) {
		this.payTimeCN = payTimeCN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCN() {
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getFlagSms() {
		return flagSms;
	}

	public void setFlagSms(Boolean flagSms) {
		this.flagSms = flagSms;
	}

	public Boolean getFlagHide() {
		return flagHide;
	}

	public void setFlagHide(Boolean flagHide) {
		this.flagHide = flagHide;
	}

	public String getColumnData() {
		return columnData;
	}

	public void setColumnData(String columnData) {
		this.columnData = columnData;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public String getSourcefile() {
		return sourcefile;
	}

	public void setSourcefile(String sourcefile) {
		this.sourcefile = sourcefile;
	}

	public String getSourcefileName() {
		return sourcefileName;
	}

	public void setSourcefileName(String sourcefileName) {
		this.sourcefileName = sourcefileName;
	}

	public String getSourcefileUrl() {
		return sourcefileUrl;
	}

	public void setSourcefileUrl(String sourcefileUrl) {
		this.sourcefileUrl = sourcefileUrl;
	}

	public Integer getPayrollCount() {
		return payrollCount;
	}

	public void setPayrollCount(Integer payrollCount) {
		this.payrollCount = payrollCount;
	}

	public Integer getComfirmCount() {
		return comfirmCount;
	}

	public void setComfirmCount(Integer comfirmCount) {
		this.comfirmCount = comfirmCount;
	}

	public Integer getFeedbackCount() {
		return feedbackCount;
	}

	public void setFeedbackCount(Integer feedbackCount) {
		this.feedbackCount = feedbackCount;
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
	
	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
	}
}
