package com.whaty.platform.entity.util;

/**
 * column配置类
 */
public class ColumnConfig {

	private String name;

	private String dataIndex;

	private boolean search;
	
	private boolean add;
	
	private boolean list;
	
	private String type;
	
	private boolean allowBlank;
	
	private int maxLength;
	
	private String textFieldParameters;
	
	private String comboSQL;
	
	//添加三种ComboBox（下拉框）属性供 选择  czc
	public final static String COMBOBOX ="ComboBox" ;	//extjs原始属性
	public final static String WHATYCOMBOBOX ="WhatyComboBox" ;	// 下拉框宽度自适应内容宽度
	public final static String MULTISELECT ="MultiSelect" ;		// 下拉框添加复选框，可多选
	
	private String comboboxType = MULTISELECT;		//设置该属性的值可以选择列表页面所要的combobox类型，以上三种供选择;可以在此设置默认选项
	/**
	 * minimal constructor
	 */
	public ColumnConfig(String name, String dataIndex) {
		
		this.name = name;
		this.dataIndex = dataIndex;
		
		this.search = true;
		this.add = true;
		this.list = true;
		this.type = "TextField";
		this.allowBlank = false;
		this.maxLength = 25;
		this.textFieldParameters = null;
	}
	
	/**
	 * @param name
	 * @param dataIndex
	 * @param search
	 * @param add
	 * @param list
	 * @param type
	 * @param allowBlank
	 * @param maxLength
	 * @param textFieldParameters
	 */
	public ColumnConfig(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength, String textFieldParameters) {
		this.name = name;
		this.dataIndex = dataIndex;
		this.search = search;
		this.add = add;
		this.list = list;
		this.type = type;
		this.allowBlank = allowBlank;
		this.maxLength = maxLength;
		this.textFieldParameters = textFieldParameters;
	}
	
	/**
	 * full constructor
	 * @param name
	 * @param dataIndex
	 * @param search
	 * @param add
	 * @param list
	 * @param type
	 * @param allowBlank
	 * @param maxLength
	 * @param textFieldParameters
	 * @param comboboxType 下拉菜单类型
	 */
	public ColumnConfig(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength, String textFieldParameters,String comboboxType) {
		this.name = name;
		this.dataIndex = dataIndex;
		this.search = search;
		this.add = add;
		this.list = list;
		this.type = type;
		this.allowBlank = allowBlank;
		this.maxLength = maxLength;
		this.textFieldParameters = textFieldParameters;
		this.comboboxType = comboboxType;
	}
	public String getName() {
		return name;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public String getDataIndex_() {
		return dataIndex.replace(".", "_");
	}

	public boolean isSearch() {
		return search;
	}

	public boolean isAdd() {
		return add;
	}

	public boolean isList() {
		return list;
	}
	
	public String getType() {
		return type;
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getTextFieldParameters() {
		return textFieldParameters;
	}

	public void setTextFieldParameters(String textFieldParameters) {
		this.textFieldParameters = textFieldParameters;
	}

	public String getComboSQL() {
		return comboSQL;
	}

	/**
	 * 部分特殊符号不支持
	 * 已支持：'
	 * @param comboSQL
	 */
	public void setComboSQL(String comboSQL) {
		this.comboSQL = comboSQL;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComboboxType() {
		return comboboxType;
	}

	public void setComboboxType(String comboboxType) {
		this.comboboxType = comboboxType;
	}

}
