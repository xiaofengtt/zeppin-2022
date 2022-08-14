package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public interface IUserService {
	/**
	 * 登录
	 */	
	public void login(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
		
	public void login1(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
}
