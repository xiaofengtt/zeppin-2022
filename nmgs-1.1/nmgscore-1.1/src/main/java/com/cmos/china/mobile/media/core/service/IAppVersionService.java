package com.cmos.china.mobile.media.core.service;

import com.cmos.china.mobile.media.core.util.ExceptionUtil;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface IAppVersionService {
	
	/**
	 * 添加apk
	 */	
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	/**
	 * 获取app信息
	 */	
	public void getApps(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	/**
	 * 修改app信息
	 */	
	public void editApp(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
}
