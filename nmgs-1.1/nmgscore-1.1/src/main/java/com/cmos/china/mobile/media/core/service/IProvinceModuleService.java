package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public interface IProvinceModuleService {

	/**
	 * 列表
	 */	
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 加载
	 */	
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 加载VO
	 */	
	public void loadVO(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
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
	 *	查询其他模块
	 */	
	public void searchOtherModules(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
}
