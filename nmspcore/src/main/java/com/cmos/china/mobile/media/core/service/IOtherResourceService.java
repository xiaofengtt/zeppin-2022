package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public interface IOtherResourceService {
	
	/**
	 * 添加
	 */	
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;
	
	/**
	 * 校验文件
	 */	
	public void verifyFile(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

}
