package com.whaty.platform.entity.service.exam.finalExam;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;

public interface PrExamBookingService {
	/**
	 * 保存学生预约考试记录
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int save_BookingData(List ids) throws EntityException;
	
	/**
	 * 录入学生成绩
	 * @param bean
	 * @return
	 * @throws EntityException
	 */
	public int save_Score(PrExamBooking bean) throws EntityException;
	
	/**
	 * 保存批量上传的学生考试成绩
	 * 
	 * @param file
	 *            所包含的列：考生姓名，准考证号，考试课程名称，考试成绩
	 * @return 操作的结果信息
	 * @throws EntityException
	 */

	public int saveUploadScore(File file) throws EntityException;
	
	/**
	 * 自动分配考场
	 * @return
	 * @throws EntityException
	 */
	public String saveAutoExamRoom() throws Exception;
	
	/**
	 * 重新生成考场编号
	 * 
	 */
	public void saveModifyRoomNo();
	
	/**
	 * 保存登分
	 * @param isa
	 * 	是否A登分人
	 * @param ids
	 * 	预约表ids
	 * @param scores
	 * 	成绩
	 * @param scorestatus
	 *  成绩状态
	 * @return
	 * @throws EntityException
	 */
	public abstract String saveScore(boolean isa,String[] ids,String[] scores,String[] scorestatus) throws EntityException;
}
