/*
 * �������� 2010-9-16
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * ����ƽ̨ģ��
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class SmsTemplateVO {
	private Integer tempId;
	private String title;
	private String content;
	private String tempType;
	private Integer input_man;
	private Integer autosendflag;
	private Integer priority;
	
	
	
	/**
	 * @return ���� input_man��
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
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
	 * @return ���� tempId��
	 */
	public Integer getTempId() {
		return tempId;
	}
	/**
	 * @param tempId Ҫ���õ� tempId��
	 */
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	/**
	 * @return ���� tempType��
	 */
	public String getTempType() {
		return tempType;
	}
	/**
	 * @param tempType Ҫ���õ� tempType��
	 */
	public void setTempType(String tempType) {
		this.tempType = tempType;
	}
	/**
	 * @return ���� tempTypeName��
	 */
	public String getTempTypeName() {
		return tempTypeName;
	}
	/**
	 * @param tempTypeName Ҫ���õ� tempTypeName��
	 */
	public void setTempTypeName(String tempTypeName) {
		this.tempTypeName = tempTypeName;
	}
	/**
	 * @return ���� title��
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title Ҫ���õ� title��
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	private String tempTypeName;
	
	
	/**
	 * @return ���� autosendflag��
	 */
	public Integer getAutosendflag() {
		return autosendflag;
	}
	/**
	 * @param autosendflag Ҫ���õ� autosendflag��
	 */
	public void setAutosendflag(Integer autosendflag) {
		this.autosendflag = autosendflag;
	}
	/**
	 * @return ���� priority��
	 */
	public Integer getPriority() {
		return priority;
	}
	/**
	 * @param priority Ҫ���õ� priority��
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
