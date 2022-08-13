package com.whaty.platform.entity.service.exam.mainCourse;

import java.util.List;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrExamOpenMaincourse;
import com.whaty.platform.entity.exception.EntityException;

public interface PrExamOpenMaincourseService {
	
	/**
	 * 保存学生主干课程预约考试课程的记录
	 * @param stuIds
	 * @param courseIds
	 * @return
	 * @throws EntityException
	 */
	public int saveBooking(String stuIds,String courseIds)throws EntityException;
	
	/**
	 * 学生工作室 主干课预约
	 * @param peStudentr
	 * @param prExamOpenMaincourseList
	 * @return
	 * @throws EntityException
	 */
	public int saveReserver(PeStudent peStudentr , List<PrExamOpenMaincourse> prExamOpenMaincourseList)throws EntityException;
	
	/**
	 * 主干课考试自动分配考场
	 * @return
	 * @throws EntityException
	 */
	public String saveMainExamRoom() throws EntityException;
}
