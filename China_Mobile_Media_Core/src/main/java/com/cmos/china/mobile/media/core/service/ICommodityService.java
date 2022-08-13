package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface ICommodityService {

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
	 * 搜索
	 */	
	public void search(InputObject inputObject, OutputObject outputObject) throws Exception;
}
