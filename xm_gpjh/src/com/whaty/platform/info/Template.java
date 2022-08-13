package com.whaty.platform.info;

/**
 * 模板对象
 * 
 * @author Terry
 */
public abstract class Template implements com.whaty.platform.Items {

	private String id;

	private String name;

	private String content;

	private String type ;

	private String pub_type;

	private String note ;

	private String mark;

	public abstract Template getTemplateByPub_type(String pub_type);

	/**
	 * 属性 name 的获取方法。
	 * 
	 * @return 属性 name 的值。
	 */
	public String getName() {
		return name;
	}

	/**
	 * 属性 name 的设置方法。
	 * 
	 * @param name
	 *            属性 address 的新值。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 属性 content 的获取方法。
	 * 
	 * @return 属性 content 的值。
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 属性 content 的设置方法。
	 * 
	 * @param email
	 *            属性 content 的新值。
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 属性 type 的获取方法。
	 * 
	 * @return 属性 type 的值。
	 */
	public String getType() {
		return type;
	}

	/**
	 * 属性 type 的设置方法。
	 * 
	 * @param type
	 *            属性 type 的新值。
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 属性 pub_Type 的获取方法。
	 * 
	 * @return 属性 pub_Type 的值。
	 */
	public String getPub_Type() {
		return pub_type;
	}

	/**
	 * 属性 pub_Type 的设置方法。
	 * 
	 * @param pub_Type
	 *            属性 pub_Type 的新值。
	 */
	public void setPub_type(String pub_type) {
		this.pub_type = pub_type;
	}

	/**
	 * 属性 note 的获取方法。
	 * 
	 * @return 属性 note 的值。
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 属性 note 的设置方法。
	 * 
	 * @param note
	 *            属性 note 的新值。
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 属性 id 的获取方法。
	 * 
	 * @return 属性 id 的值。
	 */
	public String getId() {
		return id;
	}

	/**
	 * 属性 id 的设置方法。
	 * 
	 * @param id
	 *            属性 id 的新值。
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 属性 mark 的获取方法。
	 * 
	 * @return 属性 mark 的值。
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * 属性 mark 的设置方法。
	 * 
	 * @param mark
	 *            属性 mark 的新值。
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * 该模板id是否存在
	 * 
	 * @return 0为不存在；大于0则存在
	 */
	public abstract int isIdExist();
}