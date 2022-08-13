package com.whaty.platform.entity.service.teaching.basicInfo;

import java.io.File;

import com.whaty.platform.entity.exception.EntityException;


public interface PeTchCourseService {

	public int save_uploadCourse(File file) throws EntityException;
	
	/**
	 * 批量添加课件
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int save_uploadCourseware(File file) throws EntityException;
}
