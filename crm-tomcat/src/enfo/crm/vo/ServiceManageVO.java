/*
 * 创建日期 2009-11-26
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
public class ServiceManageVO {
	
	private Integer serial_no;
	private Integer serviceType; //服务类型
	private String serviceTypeName;
	private Integer offsetDays; //天偏移量
	private String serviceWay;	//服务途径
	private String description; //描述信息
	private String noticeCaption; // 提示文字
	private Integer isValid; //是否有效
	private Integer executor;	
	private Integer autoFlag;
	private Integer tempID;
	private String tempTilte;
	private Integer inputMan;
	
	
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
	 * @return 返回 tempTilte。
	 */
	public String getTempTilte() {
		return tempTilte;
	}
	/**
	 * @param tempTilte 要设置的 tempTilte。
	 */
	public void setTempTilte(String tempTilte) {
		this.tempTilte = tempTilte;
	}
	
	
	/**
	 * @return
	 */
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
	public String getServiceWay() {
		return serviceWay;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
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
	 * @param string
	 */
	public void setServiceWay(String string) {
		serviceWay = string;
	}

	/**
	 * @return
	 */
	public Integer getIsValid() {
		return isValid;
	}

	/**
	 * @param integer
	 */
	public void setIsValid(Integer integer) {
		isValid = integer;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getOffsetDays() {
		return offsetDays;
	}

	/**
	 * @param integer
	 */
	public void setOffsetDays(Integer integer) {
		offsetDays = integer;
	}

	/**
	 * @return
	 */
	public String getNoticeCaption() {
		return noticeCaption;
	}

	/**
	 * @param string
	 */
	public void setNoticeCaption(String string) {
		noticeCaption = string;
	}

	/**
	 * @return
	 */
	public Integer getExecutor() {
		return executor;
	}

	/**
	 * @param integer
	 */
	public void setExecutor(Integer integer) {
		executor = integer;
	}

}
