package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
@FunctionalInterface
public interface IVideoIframeService {
	
	/**
	 * 添加
	 */	
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil;

}
