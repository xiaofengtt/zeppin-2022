package com.whaty.platform.entity.service.recruit.registration;

import java.io.File;

import com.whaty.platform.entity.exception.EntityException;

public interface PeRecStudentAddService {
	/**
	 * 批量上传学生报名信息
	 * 
	 * @param file
	 * @return
	 */
	public int saveUploadStudent(File file)throws EntityException;
}
