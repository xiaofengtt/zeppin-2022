package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface ILeaveMessageService {

	/**
	 * 列表
	 */	
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 改变状态
	 */	
	public void changeStatus(InputObject inputObject, OutputObject outputObject) throws Exception;
}
