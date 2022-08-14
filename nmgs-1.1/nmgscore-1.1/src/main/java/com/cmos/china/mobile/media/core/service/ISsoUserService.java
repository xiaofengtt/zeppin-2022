package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public interface ISsoUserService {
	/**
	 * 登录
	 */	
	public void login(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 第三方平台验证
	 */	
	public void verify(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 加载用户详细信息
	 */	
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 修改信息
	 */	
	public void editInfomation(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

	/**
	 * 修改密码
	 */	
	public void editPassword(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 停用用户
	 */	
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	

	/**
	 * 获取短信
	 */	
	public void getSmsCode(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	

	/**
	 * 验证短信
	 */	
	public void authSmsCode(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

}
