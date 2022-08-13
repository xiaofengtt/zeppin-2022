package com.whaty.platform.entity.service.teaching.basicInfo;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.exception.EntityException;

public interface PeTchProgramService {

	public Map<String, String> save(PeTchProgram instance);
	
	public Map<String, String> del(List<String> idList);
	
	public String save_plant(String programId, String gradeId);
	/**
	 * 导入教学计划
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveTchProgram(File file) throws EntityException;
}
