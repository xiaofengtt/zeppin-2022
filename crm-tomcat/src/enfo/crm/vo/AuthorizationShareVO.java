/*
 * 创建日期 2011-05-21
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class AuthorizationShareVO {
	private Integer serial_no;
	private Integer shareType;
	private String  shareDescription;
	private Integer status;
	private Integer sourceManagerID;
	private Integer ca_id;
	private Integer managerID;
	private Integer input_man;
	private Integer cust_id;
	private String  invalid_time;
	private String  start_time;
	
	/**
	 * @return 返回 ca_id。
	 */
	public Integer getCa_id() {
		return ca_id;
	}
	/**
	 * @param ca_id 要设置的 ca_id。
	 */
	public void setCa_id(Integer ca_id) {
		this.ca_id = ca_id;
	}
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 managerID。
	 */
	public Integer getManagerID() {
		return managerID;
	}
	/**
	 * @param managerID 要设置的 managerID。
	 */
	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
	}
	/**
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 shareDescription。
	 */
	public String getShareDescription() {
		return shareDescription;
	}
	/**
	 * @param shareDescription 要设置的 shareDescription。
	 */
	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}
	/**
	 * @return 返回 shareType。
	 */
	public Integer getShareType() {
		return shareType;
	}
	/**
	 * @param shareType 要设置的 shareType。
	 */
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}
	/**
	 * @return 返回 sourceManagerID。
	 */
	public Integer getSourceManagerID() {
		return sourceManagerID;
	}
	/**
	 * @param sourceManagerID 要设置的 sourceManagerID。
	 */
	public void setSourceManagerID(Integer sourceManagerID) {
		this.sourceManagerID = sourceManagerID;
	}
	/**
	 * @return 返回 status。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status 要设置的 status。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
    /**
     * @return 返回 cust_id。
     */
    public Integer getCust_id() {
        return cust_id;
    }
    /**
     * @param cust_id 要设置的 cust_id。
     */
    public void setCust_id(Integer cust_id) {
        this.cust_id = cust_id;
    }
    /**
     * @return 返回 invalid_time。
     */
    public String getInvalid_time() {
        return invalid_time;
    }
    /**
     * @param invalid_time 要设置的 invalid_time。
     */
    public void setInvalid_time(String invalid_time) {
        this.invalid_time = invalid_time;
    }
    /**
     * @return 返回 start_time。
     */
    public String getStart_time() {
        return start_time;
    }
    /**
     * @param start_time 要设置的 start_time。
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
