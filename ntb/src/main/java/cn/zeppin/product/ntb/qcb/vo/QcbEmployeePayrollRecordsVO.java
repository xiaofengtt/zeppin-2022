package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords.QcbCompanyPayrollRecordsStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class QcbEmployeePayrollRecordsVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8974151497856649618L;
	
	private String uuid;
	private String qcbCompanyPayroll;
	private String qcbCompany;
	private String qcbCompanyName;
	private String qcbEmployee;
	private String qcbEmployeeName;
	private String qcbEmployeeIdcard;
	private String qcbEmployeeMobile;
	
	private String title;
	private BigDecimal price;
	private String priceCN;
	private Timestamp payTime;
	private String payTimeCN;
	private String payTimeMonth;
	private String value;
	private List<String> valueList;
	private String column;
	private List<String> columnList;
	
	private Integer feedbackCount;
	
	private String status;
	private String statusCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	private String time;
	
 	public QcbEmployeePayrollRecordsVO(){
		
	}
	
	public QcbEmployeePayrollRecordsVO(QcbCompanyPayrollRecords qcpr){
		this.uuid = qcpr.getUuid();
		this.qcbCompanyPayroll = qcpr.getQcbCompanyPayroll();
		this.qcbCompany = qcpr.getQcbCompany();
		this.qcbEmployee = qcpr.getQcbEmployee();
		
		this.payTime = qcpr.getPayTime();
		this.payTimeCN = Utlity.timeSpanToString(qcpr.getPayTime());
		this.payTimeMonth = Utlity.timeSpanToChinaStringesLessMonth(qcpr.getPayTime());
		this.price = qcpr.getPrice();
		this.priceCN = Utlity.numFormat4UnitDetail(qcpr.getPrice());
		this.value = qcpr.getValue();
		this.valueList = JSONUtils.json2list(value, String.class);
		
		this.status = qcpr.getStatus();
		if(QcbCompanyPayrollRecordsStatus.SUCCESS.equals(qcpr.getStatus())){
			this.statusCN = "未确认";
		}else if(QcbCompanyPayrollRecordsStatus.CONFIRM.equals(qcpr.getStatus())){
			this.statusCN = "已确认";
		}else if(QcbCompanyPayrollRecordsStatus.DRAFT.equals(qcpr.getStatus())){
			this.statusCN = "待发送";
		}else{
			this.statusCN = "已删除";
		}
		this.createtime = qcpr.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qcpr.getCreatetime());
		this.creator = qcpr.getCreator();
		this.time = Utlity.timeSpanToChinaStringesLessYear(qcpr.getPayTime());
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

	public String getQcbEmployeeName() {
		return qcbEmployeeName;
	}

	public void setQcbEmployeeName(String qcbEmployeeName) {
		this.qcbEmployeeName = qcbEmployeeName;
	}

	public String getQcbEmployeeIdcard() {
		return qcbEmployeeIdcard;
	}

	public void setQcbEmployeeIdcard(String qcbEmployeeIdcard) {
		this.qcbEmployeeIdcard = qcbEmployeeIdcard;
	}

	public String getQcbEmployeeMobile() {
		return qcbEmployeeMobile;
	}

	public void setQcbEmployeeMobile(String qcbEmployeeMobile) {
		this.qcbEmployeeMobile = qcbEmployeeMobile;
	}

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
	}
	
	public String getQcbCompanyPayroll() {
		return qcbCompanyPayroll;
	}

	public void setQcbCompanyPayroll(String qcbCompanyPayroll) {
		this.qcbCompanyPayroll = qcbCompanyPayroll;
	}

	public String getQcbEmployee() {
		return qcbEmployee;
	}

	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getValueList() {
		return valueList;
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
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

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}

	public String getPayTimeMonth() {
		return payTimeMonth;
	}

	public void setPayTimeMonth(String payTimeMonth) {
		this.payTimeMonth = payTimeMonth;
	}


	public String getPriceCN() {
		return priceCN;
	}
	

	public void setPriceCN(String priceCN) {
		this.priceCN = priceCN;
	}


	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}


	public String getColumn() {
		return column;
	}
	

	public void setColumn(String column) {
		this.column = column;
		this.columnList = JSONUtils.json2list(column, String.class);
	}
	

	public List<String> getColumnList() {
		return columnList;
	}
	

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

}
