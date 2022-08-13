package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface IVideoPublishService {

	/**
	 * 列表
	 */	
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 加载
	 */	
	public void load(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 添加
	 */	
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 编辑
	 */	
	public void edit(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 删除
	 */	
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 改变状态
	 */	
	public void changeStatus(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 按状态取数量列表
	 */	
	public void statusList(InputObject inputObject, OutputObject outputObject) throws Exception;

}
