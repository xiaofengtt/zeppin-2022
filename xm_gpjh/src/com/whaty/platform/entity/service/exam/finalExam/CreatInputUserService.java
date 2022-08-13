package com.whaty.platform.entity.service.exam.finalExam;

import com.whaty.platform.entity.exception.EntityException;

/**
 * 等分帐号管理
 * @author Administrator
 *
 */
public interface CreatInputUserService {
	
	/**
	 * 自动生成登分帐号
	 * @return
	 * @throws EntityException
	 */
	public String save_CreatInputUser() throws EntityException;
	
	/**
	 * 将考试成绩同步到学生选课表
	 * @return
	 * @throws EntityException
	 */
	public String saveExamScore() throws EntityException;
}
