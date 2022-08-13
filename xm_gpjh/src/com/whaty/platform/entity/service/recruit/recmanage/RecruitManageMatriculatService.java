package com.whaty.platform.entity.service.recruit.recmanage;

import java.util.List;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;

/**
 * 录取状态设置。 涉及到PrStudentInfo，PeStudent表的添加删除
 * 
 * @author 李冰
 * 
 */
public interface RecruitManageMatriculatService {
	/**
	 * 学生录取时候将PrStudentInfo，PeStudent 中插入数据，并且设置录取状态
	 * 
	 * @param peRecStudentList
	 *            所操作的学生报名信息集合
	 * @param value
	 *            录取状态位需要设置的值
	 * @return 操作信息
	 * @throws EntityException
	 */
	public String saveMatriculat(List<PeRecStudent> peRecStudentList,
			String value) throws EntityException;

	/**
	 * 学生录取状态改为不录取或者等待的时候将PrStudentInfo，PeStudent表中相应内容删除，并且设置录取状态
	 * 
	 * @param idList
	 *            所操作的学生报名信息id
	 * @param column
	 *            录取状态位
	 * @param value录取状态位需要设置的值
	 * @return
	 * @throws EntityException
	 */
	public String deleMatriculat(List<String> idList, String column,
			String value) throws EntityException;
}
