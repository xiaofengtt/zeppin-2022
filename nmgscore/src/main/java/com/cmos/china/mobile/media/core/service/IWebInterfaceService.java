package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface IWebInterfaceService {

	/**
	 * 栏目列表
	 */	
	public void categoryList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 发布列表
	 */	
	public void publishList(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 加载视频信息
	 */	
	public void videoInfo(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 加载商品信息
	 */	
	public void commodityInfo(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 获取省份业务信息
	 */	
	public void provinceTemplateInfo(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 获取留言信息
	 */	
	public void leaveMessageInfo(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 添加留言
	 */	
	public void addLeaveMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 获取组件信息
	 */	
	public void checkComponent(InputObject inputObject, OutputObject outputObject) throws Exception;
	/**
	 * 获取总发布列表
	 */	
	public void totalPublishList(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 获取最新app信息
	 */	
	public void getApps(InputObject inputObject, OutputObject outputObject) throws Exception;

}
