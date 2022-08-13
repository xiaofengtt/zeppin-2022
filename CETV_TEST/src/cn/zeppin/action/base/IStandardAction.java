/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.action.base
 * IStandardAction
 */
package cn.zeppin.action.base;

/**
 * @author Clark
 * 下午4:24:52
 */
public interface IStandardAction {
	
	/**
	 * 添加数据（需要用户登录并判断权限）
	 * @author Clark
	 * @date 2014年7月30日下午4:31:28
	 */
	public void Add();
	
	/**
	 * 更新数据（需要用户登录并判断权限）
	 * @author Clark
	 * @date 2014年7月30日下午4:31:32
	 */
	public void Update();
	
	/**
	 * 删除数据（需要用户登录并判断权限）
	 * @author Clark
	 * @date 2014年7月30日下午4:31:35
	 */
	public void Delete();
	
	/**
	 * 数据管理列表（一般全状态结果返回，但需根据数据管理权限显示和操作）
	 * @author Clark
	 * @date 2014年7月30日下午4:30:40
	 */
	public void List();
	
	
	/**
	 * 数据查询接口（无需判断用户登录或权限，返回状态正常的数据）
	 * @author Clark
	 * @date 2014年7月30日下午4:30:45
	 */
	public void Search();
	
	
}
