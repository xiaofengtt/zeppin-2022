package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface IWebInterfaceService {

	/**
	 * 栏目列表
	 */	
	public void categoryList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 发布列表
	 */	
	public void publishList(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 加载视频信息
	 */	
	public void videoInfo(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 加载商品信息
	 */	
	public void commodityInfo(InputObject inputObject, OutputObject outputObject) throws Exception;

}
