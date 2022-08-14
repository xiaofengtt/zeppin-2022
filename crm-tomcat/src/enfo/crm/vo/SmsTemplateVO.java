/*
 * 创建日期 2010-9-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

/**
 * 短信平台模板
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
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
	 * @return 返回 content。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content 要设置的 content。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return 返回 tempId。
	 */
	public Integer getTempId() {
		return tempId;
	}
	/**
	 * @param tempId 要设置的 tempId。
	 */
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	/**
	 * @return 返回 tempType。
	 */
	public String getTempType() {
		return tempType;
	}
	/**
	 * @param tempType 要设置的 tempType。
	 */
	public void setTempType(String tempType) {
		this.tempType = tempType;
	}
	/**
	 * @return 返回 tempTypeName。
	 */
	public String getTempTypeName() {
		return tempTypeName;
	}
	/**
	 * @param tempTypeName 要设置的 tempTypeName。
	 */
	public void setTempTypeName(String tempTypeName) {
		this.tempTypeName = tempTypeName;
	}
	/**
	 * @return 返回 title。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title 要设置的 title。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	private String tempTypeName;
	
	
	/**
	 * @return 返回 autosendflag。
	 */
	public Integer getAutosendflag() {
		return autosendflag;
	}
	/**
	 * @param autosendflag 要设置的 autosendflag。
	 */
	public void setAutosendflag(Integer autosendflag) {
		this.autosendflag = autosendflag;
	}
	/**
	 * @return 返回 priority。
	 */
	public Integer getPriority() {
		return priority;
	}
	/**
	 * @param priority 要设置的 priority。
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
