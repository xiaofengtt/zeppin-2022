package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public interface ILeaveMessageService {

	/**
	 * 列表
	 */	
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 改变状态
	 */	
	public void changeStatus(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
}
