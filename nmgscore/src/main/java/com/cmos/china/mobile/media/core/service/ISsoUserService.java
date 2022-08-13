package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface ISsoUserService {
	/**
	 * 登录
	 */	
	public void login(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 第三方平台验证
	 */	
	public void verify(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 加载用户详细信息
	 */	
	public void load(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 修改信息
	 */	
	public void editInfomation(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 修改密码
	 */	
	public void editPassword(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 停用用户
	 */	
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception;
}
