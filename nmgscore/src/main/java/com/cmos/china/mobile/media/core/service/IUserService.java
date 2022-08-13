package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface IUserService {
	/**
	 * 登录
	 */	
	public void login(InputObject inputObject, OutputObject outputObject) throws Exception;
		
	public void login1(InputObject inputObject, OutputObject outputObject) throws Exception;
}
