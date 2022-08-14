package com.cmos.china.mobile.media.core.service;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public interface IResourceService {
	
	/**
	 * 添加
	 */	
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 添加360压缩文件
	 */	
	public void displayAdd(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 上传apk
	 */	
	public void updateApk(InputObject inputObject, OutputObject outputObject) throws Exception;

}
