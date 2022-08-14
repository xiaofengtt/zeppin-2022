/*
 * 创建日期 2010-2-24
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author taochen
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ActivityTaskVO {
	private  Integer serial_no;
	private String taskTitle;
	private  Integer activitySerial_no;
	private  String activityTaskType;
	private  String activityTaskTypeName;
	private  String content;
	private  String beginDate;
	private  String endDate;
	private  Integer executor;
	private  Integer managerCode;
	private  String completeTime;
	private  String remark;
	private  Integer taskFlag;
	private  Integer checkMan;
	private  String checkOptions;
	private Integer inputMan;
	//查询条件
	private Integer beginDateQuery;
	private Integer endDateQuery;
	private Integer completeDateUp;
	private Integer completeDateDown;

	/**
	 * @return
	 */
	public String getActivityTaskTypeName() {
		return activityTaskTypeName;
	}

	/**
	 * @return
	 */
	public Integer getCheckMan() {
		return checkMan;
	}

	/**
	 * @return
	 */
	public String getCheckOptions() {
		return checkOptions;
	}

	/**
	 * @return
	 */
	public String getCompleteTime() {
		return completeTime;
	}

	/**
	 * @return
	 */
	public Integer getExecutor() {
		return executor;
	}

	/**
	 * @return
	 */
	public String getRemark() {
		return remark;
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
	public Integer getTaskFlag() {
		return taskFlag;
	}

	/**
	 * @param string
	 */
	public void setActivityTaskTypeName(String string) {
		activityTaskTypeName = string;
	}


	/**
	 * @param integer
	 */
	public void setCheckMan(Integer integer) {
		checkMan = integer;
	}

	/**
	 * @param string
	 */
	public void setCheckOptions(String string) {
		checkOptions = string;
	}

	/**
	 * @param string
	 */
	public void setCompleteTime(String string) {
		completeTime = string;
	}
	/**
	 * @param integer
	 */
	public void setExecutor(Integer integer) {
		executor = integer;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string) {
		remark = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param integer
	 */
	public void setTaskFlag(Integer integer) {
		taskFlag = integer;
	}

	/**
	 * @return
	 */
	public String getActivityTaskType() {
		return activityTaskType;
	}

	/**
	 * @param string
	 */
	public void setActivityTaskType(String string) {
		activityTaskType = string;
	}

	/**
	 * @return
	 */
	public Integer getInputMan() {
		return inputMan;
	}

	/**
	 * @param integer
	 */
	public void setInputMan(Integer integer) {
		inputMan = integer;
	}

	/**
	 * @return
	 */
	public Integer getActivitySerial_no() {
		return activitySerial_no;
	}

	/**
	 * @param integer
	 */
	public void setActivitySerial_no(Integer integer) {
		activitySerial_no = integer;
	}

	/**
	 * @return
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @return
	 */
	public Integer getBeginDateQuery() {
		return beginDateQuery;
	}

	/**
	 * @return
	 */
	public Integer getCompleteDateDown() {
		return completeDateDown;
	}

	/**
	 * @return
	 */
	public Integer getCompleteDateUp() {
		return completeDateUp;
	}

	/**
	 * @return
	 */
	public String getContent() {
		return content;
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
	public Integer getEndDateQuery() {
		return endDateQuery;
	}

	/**
	 * @param string
	 */
	public void setBeginDate(String string) {
		beginDate = string;
	}

	/**
	 * @param integer
	 */
	public void setBeginDateQuery(Integer integer) {
		beginDateQuery = integer;
	}

	/**
	 * @param integer
	 */
	public void setCompleteDateDown(Integer integer) {
		completeDateDown = integer;
	}

	/**
	 * @param integer
	 */
	public void setCompleteDateUp(Integer integer) {
		completeDateUp = integer;
	}

	/**
	 * @param string
	 */
	public void setContent(String string) {
		content = string;
	}

	/**
	 * @param string
	 */
	public void setEndDate(String string) {
		endDate = string;
	}

	/**
	 * @param integer
	 */
	public void setEndDateQuery(Integer integer) {
		endDateQuery = integer;
	}

	/**
	 * @return
	 */
	public Integer getManagerCode() {
		return managerCode;
	}

	/**
	 * @param integer
	 */
	public void setManagerCode(Integer integer) {
		managerCode = integer;
	}

	/**
	 * @return
	 */
	public String getTaskTitle() {
		return taskTitle;
	}

	/**
	 * @param string
	 */
	public void setTaskTitle(String string) {
		taskTitle = string;
	}

}
