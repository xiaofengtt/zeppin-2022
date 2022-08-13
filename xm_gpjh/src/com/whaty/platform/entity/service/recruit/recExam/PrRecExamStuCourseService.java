package com.whaty.platform.entity.service.recruit.recExam;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.exception.EntityException;

/**
 * 入学考试学生-课程 关系 自动分配功能, 批量上传入学考试成绩
 * 
 * @author 李冰
 * 
 */
public interface PrRecExamStuCourseService {
	/**
	 * 添加学生课程关系记录
	 * 
	 * @param list
	 *            学生课程关系的集合
	 * @return
	 * @throws EntityException
	 */

	public String save(List list) throws EntityException;

	/**
	 * 保存批量上传的学生入学考试成绩
	 * 
	 * @param file
	 *            所包含的列：考生姓名，准考证号，考试课程名称，考试成绩
	 * @return 操作的结果信息
	 * @throws EntityException
	 */

	public int saveUploadScore(File file) throws EntityException;
	/**
	 * 自动分配考场及准考证号
	 * 分配方式：以学习中心和层次分组，根据考试课程是排考场的课程查询出学生，按照课程分组。
	 * 相同课程的考生在同一个考场。
	 * @return
	 */
	public String saveExamRoom();
	/**
	 * 自动分配巡考
	 * @return
	 */
	public String saveInspector();
	/**
	 * 自动分配监考
	 * @return
	 */
	public String saveInvigilator();
}
