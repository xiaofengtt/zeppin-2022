package com.whaty.platform.entity.service.teaching.examScore;

import java.io.File;

import com.whaty.platform.entity.exception.EntityException;

public interface ExamScoreBatchService {
	/**
	 * 保存学生平时成绩
	 * @return
	 * @throws EntityException
	 */
	public int saveUsualScore(File file) throws EntityException;
	/**
	 * 保存学生作业成绩
	 * @return
	 * @throws EntityException
	 */
	public int saveHomeworkScore(File file) throws EntityException;
	
	/**
	 * 保存学位英语成绩
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveDegreeEnglishScore(File file) throws EntityException;
	
	/**
	 * 保存统考成绩
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveUnitExamScore(File file) throws EntityException;
}
