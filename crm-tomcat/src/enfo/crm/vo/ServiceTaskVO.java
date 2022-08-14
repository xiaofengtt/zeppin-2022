/*
 * 创建日期 2009-11-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author enfo
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ServiceTaskVO {
	private Integer serial_no;
	private Integer managerID;
	private Integer executor;	
	private String executorTime;
	private Integer serviceType; 
	private String serviceTypeName;
	private String serviceWay;	//服务途径
	private String serviceTitle;
	private Integer startDateTime;
	private Integer endDateTime;
	private Integer status;
	private Integer inputMan;
	private Integer insertMan;
	private String result;
	private Integer satisfaction;
	private String description; //描述信息
	private String serviceRemark;
	private Integer retlateId;
	private String feedbackContent;	
	private String startDate;
	private String endDate;
	//ServiceTaskDetail
	private Integer TargetCustID;
	private Integer needFeedBack; 
	private Integer TaskSerialNO;
	private Integer TaskDetailSerialNO;
	//TServiceTask_Ques
	private Integer serviceTaskId;
	private Integer productId;
	private Integer ques_id;
	private String ques_no;
	private String ques_title;
	private Integer autoFlag;
	private Integer tempID;
	private String tempTitle;
	
	/**
	 * @return
	 */
	public Integer getExecutor() {
		return executor;
	}
	
	public String getDescription() {
			return description;
		}

	/**
	 * @return
	 */
	public Integer getInputMan() {
		return inputMan;
	}

	/**
	 * @return
	 */
	public Integer getManagerID() {
		return managerID;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public String getServiceTitle() {
		return serviceTitle;
	}

	/**
	 * @return
	 */
	public Integer getServiceType() {
		return serviceType;
	}

	/**
	 * @return
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}

	/**
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param integer
	 */
	public void setExecutor(Integer integer) {
		executor = integer;
	}
	


	/**
	 * @param integer
	 */
	public void setInputMan(Integer integer) {
		inputMan = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerID(Integer integer) {
		managerID = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setServiceTitle(String string) {
		serviceTitle = string;
	}

	/**
	 * @param integer
	 */
	public void setServiceType(Integer integer) {
		serviceType = integer;
	}

	/**
	 * @param string
	 */
	public void setServiceTypeName(String string) {
		serviceTypeName = string;
	}

	/**
	 * @param integer
	 */
	public void setStatus(Integer integer) {
		status = integer;
	}

	/**
	 * @return
	 */
	public Integer getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @return
	 */
	public Integer getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param integer
	 */
	public void setEndDateTime(Integer integer) {
		endDateTime = integer;
	}

	/**
	 * @param integer
	 */
	public void setStartDateTime(Integer integer) {
		startDateTime = integer;
	}

	/**
	 * @return
	 */
	public String getServiceWay() {
		return serviceWay;
	}

	/**
	 * @param string
	 */
	public void setServiceWay(String string) {
		serviceWay = string;
	}
	/**
	 * @return
	 */
	public Integer getNeedFeedBack() {
		return needFeedBack;
	}

	/**
	 * @return
	 */
	public Integer getTargetCustID() {
		return TargetCustID;
	}

	/**
	 * @param integer
	 */
	public void setNeedFeedBack(Integer integer) {
		needFeedBack = integer;
	}

	/**
	 * @param integer
	 */
	public void setTargetCustID(Integer integer) {
		TargetCustID = integer;
	}

	/**
	 * @return
	 */
	public Integer getTaskSerialNO() {
		return TaskSerialNO;
	}

	/**
	 * @param integer
	 */
	public void setTaskSerialNO(Integer integer) {
		TaskSerialNO = integer;
	}

	/**
	 * @return
	 */
	public Integer getTaskDetailSerialNO() {
		return TaskDetailSerialNO;
	}

	/**
	 * @param integer
	 */
	public void setTaskDetailSerialNO(Integer integer) {
		TaskDetailSerialNO = integer;
	}

	/**
	 * @return
	 */
	public Integer getInsertMan() {
		return insertMan;
	}

	/**
	 * @param integer
	 */
	public void setInsertMan(Integer integer) {
		insertMan = integer;
	}

	/**
	 * @return
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param string
	 */
	public void setResult(String string) {
		result = string;
	}

	/**
	 * @return
	 */
	public Integer getSatisfaction() {
		return satisfaction;
	}

	/**
	 * @param integer
	 */
	public void setSatisfaction(Integer integer) {
		satisfaction = integer;
	}
	
//	public void setFeedback(String string) {
//		      feedback = string;
//		}
	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @return
	 */
	public String getFeedbackContent() {
		return feedbackContent;
	}

	/**
	 * @param string
	 */
	public void setFeedbackContent(String string) {
		feedbackContent = string;
	}

	/**
	 * @return
	 */
	public Integer getRetlateId() {
		return retlateId;
	}

	/**
	 * @return
	 */
	public String getServiceRemark() {
		return serviceRemark;
	}

	/**
	 * @param integer
	 */
	public void setRetlateId(Integer integer) {
		retlateId = integer;
	}

	/**
	 * @param string
	 */
	public void setServiceRemark(String string) {
		serviceRemark = string;
	}

	/**
	 * @return
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @return
	 */
	public Integer getQues_id() {
		return ques_id;
	}

	/**
	 * @return
	 */
	public Integer getServiceTaskId() {
		return serviceTaskId;
	}

	/**
	 * @param integer
	 */
	public void setProductId(Integer integer) {
		productId = integer;
	}

	/**
	 * @param integer
	 */
	public void setQues_id(Integer integer) {
		ques_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setServiceTaskId(Integer integer) {
		serviceTaskId = integer;
	}

	/**
	 * @return
	 */
	public String getQues_no() {
		return ques_no;
	}

	/**
	 * @return
	 */
	public String getQues_title() {
		return ques_title;
	}

	/**
	 * @param string
	 */
	public void setQues_no(String string) {
		ques_no = string;
	}

	/**
	 * @param string
	 */
	public void setQues_title(String string) {
		ques_title = string;
	}

	/**
	 * @return
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param string
	 */
	public void setEndDate(String string) {
		endDate = string;
	}

	/**
	 * @param string
	 */
	public void setStartDate(String string) {
		startDate = string;
	}

	/**
	 * @return
	 */
	public String getExecutorTime() {
		return executorTime;
	}

	/**
	 * @param string
	 */
	public void setExecutorTime(String string) {
		executorTime = string;
	}

	/**
	 * @return 返回 autoFlag。
	 */
	public Integer getAutoFlag() {
		return autoFlag;
	}
	/**
	 * @param autoFlag 要设置的 autoFlag。
	 */
	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}
	/**
	 * @return 返回 tempID。
	 */
	public Integer getTempID() {
		return tempID;
	}
	/**
	 * @param tempID 要设置的 tempID。
	 */
	public void setTempID(Integer tempID) {
		this.tempID = tempID;
	}
	/**
	 * @return 返回 tempTitle。
	 */
	public String getTempTitle() {
		return tempTitle;
	}
	/**
	 * @param tempTitle 要设置的 tempTitle。
	 */
	public void setTempTitle(String tempTitle) {
		this.tempTitle = tempTitle;
	}
}
