/*
 * 创建日期 2009-12-1
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * 客户群组对象对应CustGroupVO对象
 * @author dingyj
 * @since 2009-12-1
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustGroupVO {
	
	private Integer groupId;//分组ID
	private String groupName;//分组名称
	private Integer leftId;//左ID
	private Integer rightId;//右ID
	private Integer levelId;//层次
	private Integer inputMan;//操作员
	
	/**
	 * @return
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @return
	 */
	public Integer getLeftId() {
		return leftId;
	}

	/**
	 * @return
	 */
	public Integer getLevelId() {
		return levelId;
	}

	/**
	 * @return
	 */
	public Integer getRightId() {
		return rightId;
	}

	/**
	 * @param integer
	 */
	public void setGroupId(Integer integer) {
		groupId = integer;
	}

	/**
	 * @param string
	 */
	public void setGroupName(String string) {
		groupName = string;
	}

	/**
	 * @param integer
	 */
	public void setLeftId(Integer integer) {
		leftId = integer;
	}

	/**
	 * @param integer
	 */
	public void setLevelId(Integer integer) {
		levelId = integer;
	}

	/**
	 * @param integer
	 */
	public void setRightId(Integer integer) {
		rightId = integer;
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

}
