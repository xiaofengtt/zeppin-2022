package com.whaty.platform.entity.service.teaching.basicInfo;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.exception.EntityException;

public interface PeTeacherService {

	public PeTeacher save(PeTeacher instance) throws EntityException;
	
	public int deleteByIds(List ids) throws EntityException;
	
	public PeTeacher update(PeTeacher instance) throws EntityException;
	
	/**
	 * 批量添加教师信息
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveBatch(File file) throws EntityException;
		
}
