package com.whaty.platform.entity.service.recruit.recExam;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.exception.EntityException;

public interface RecruitManageService {
	/**
	 * 保存单科分数线
	 * 
	 * @param courseId
	 *            课程的id 组合成的字符串
	 * @param score
	 *            课程对应的分数线组合成的字符串
	 * @throws EntityException
	 */
	public void saveScoreLine(String courseId, String score)
			throws EntityException;

	/**
	 * 复制招生计划
	 * @param plan1name 源招生计划
	 * @param plan2name 目标招生计划
	 * @throws EntityException
	 */
	public void saveCopyPlan(String plan1name, String plan2name)throws EntityException;
}
