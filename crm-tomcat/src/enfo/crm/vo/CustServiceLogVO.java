/*
 * �������� 2011-7-12
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class CustServiceLogVO {
	private Integer serialNo;
	private Integer custId;
	private String custNo;
	private String custName;
	private String serviceTime;
	private String serviceInfo;
	private String serviceSummary;
	private Integer serviceMan;
	private String serviceManName;
	private Integer executor;
	private String  executorName;
	private Integer inputMan;
	
	private String content;
	private String subject;
	private String step_flag;
	private String step_flag_name;
	private String info_level;
	private String info_level_name;
	
	/**
	 * @return ���� serialNo��
	 */
	public Integer getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo Ҫ���õ� serialNo��
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return ���� custId��
	 */
	public Integer getCustId() {
		return custId;
	}
	/**
	 * @param custId Ҫ���õ� custId��
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	/**
	 * @return ���� custName��
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName Ҫ���õ� custName��
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return ���� custNo��
	 */
	public String getCustNo() {
		return custNo;
	}
	/**
	 * @param custNo Ҫ���õ� custNo��
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	/**
	 * @return ���� executor��
	 */
	public Integer getExecutor() {
		return executor;
	}
	/**
	 * @param executor Ҫ���õ� executor��
	 */
	public void setExecutor(Integer executor) {
		this.executor = executor;
	}
	/**
	 * @return ���� executorName��
	 */
	public String getExecutorName() {
		return executorName;
	}
	/**
	 * @param executorName Ҫ���õ� executorName��
	 */
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	/**
	 * @return ���� serviceInfo��
	 */
	public String getServiceInfo() {
		return serviceInfo;
	}
	/**
	 * @param serviceInfo Ҫ���õ� serviceInfo��
	 */
	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	/**
	 * @return ���� serviceMan��
	 */
	public Integer getServiceMan() {
		return serviceMan;
	}
	/**
	 * @param serviceMan Ҫ���õ� serviceMan��
	 */
	public void setServiceMan(Integer serviceMan) {
		this.serviceMan = serviceMan;
	}
	/**
	 * @return ���� serviceManName��
	 */
	public String getServiceManName() {
		return serviceManName;
	}
	/**
	 * @param serviceManName Ҫ���õ� serviceManName��
	 */
	public void setServiceManName(String serviceManName) {
		this.serviceManName = serviceManName;
	}
	/**
	 * @return ���� serviceSummary��
	 */
	public String getServiceSummary() {
		return serviceSummary;
	}
	/**
	 * @param serviceSummary Ҫ���õ� serviceSummary��
	 */
	public void setServiceSummary(String serviceSummary) {
		this.serviceSummary = serviceSummary;
	}
	/**
	 * @return ���� serviceTime��
	 */
	public String getServiceTime() {
		return serviceTime;
	}
	/**
	 * @param serviceTime Ҫ���õ� serviceTime��
	 */
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	
	/**
	 * @return ���� inputMan��
	 */
	public Integer getInputMan() {
		return inputMan;
	}
	/**
	 * @param inputMan Ҫ���õ� inputMan��
	 */
	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}
	/**
	 * @return ���� content��
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content Ҫ���õ� content��
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return ���� info_level��
	 */
	public String getInfo_level() {
		return info_level;
	}
	/**
	 * @param info_level Ҫ���õ� info_level��
	 */
	public void setInfo_level(String info_level) {
		this.info_level = info_level;
	}
	/**
	 * @return ���� info_level_name��
	 */
	public String getInfo_level_name() {
		return info_level_name;
	}
	/**
	 * @param info_level_name Ҫ���õ� info_level_name��
	 */
	public void setInfo_level_name(String info_level_name) {
		this.info_level_name = info_level_name;
	}
	/**
	 * @return ���� step_flag��
	 */
	public String getStep_flag() {
		return step_flag;
	}
	/**
	 * @param step_flag Ҫ���õ� step_flag��
	 */
	public void setStep_flag(String step_flag) {
		this.step_flag = step_flag;
	}
	/**
	 * @return ���� step_flag_name��
	 */
	public String getStep_flag_name() {
		return step_flag_name;
	}
	/**
	 * @param step_flag_name Ҫ���õ� step_flag_name��
	 */
	public void setStep_flag_name(String step_flag_name) {
		this.step_flag_name = step_flag_name;
	}
	/**
	 * @return ���� subject��
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject Ҫ���õ� subject��
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
