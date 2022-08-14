package com.cmos.china.mobile.media.core.service;

import com.cmos.china.mobile.media.core.util.ExceptionUtil;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface ICommodityService {

	/**
	 * 列表
	 */	
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 加载
	 */	
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 添加
	 */	
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 编辑
	 */	
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 删除
	 */	
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 搜索
	 */	
	public void search(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
}
