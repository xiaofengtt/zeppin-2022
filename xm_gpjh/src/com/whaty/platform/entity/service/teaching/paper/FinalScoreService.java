package com.whaty.platform.entity.service.teaching.paper;

import java.io.File;

import com.whaty.platform.entity.exception.EntityException;

public interface FinalScoreService {

	public String update_compose() throws EntityException;
	
	/**
	 * 保存答辩成绩
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveReplyScore(File file) throws EntityException;
	
	/**
	 * 随机抽取答辩名单
	 * @return
	 * @throws EntityException
	 */
	public String savePaperRejoin() throws EntityException;
	
	/**
	 * 自动安排答辩时间和教室
	 * @return
	 * @throws EntityException
	 */
	public String saveRejoinSection() throws EntityException;
}
