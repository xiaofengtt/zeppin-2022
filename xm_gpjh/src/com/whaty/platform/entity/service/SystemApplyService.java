package com.whaty.platform.entity.service;

import java.util.List;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;

public interface SystemApplyService {
	
	/**
	 * 审核通过或者驳回
	 * @param ids
	 * @param check
	 * 		是否审核通过
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForCheck(List<String> ids,boolean check)throws EntityException;
	
	/**
	 * 取消审核通过或者驳回
	 * @param ids
	 * @param check
	 * 		是否审核通过
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForCancel(List<String> ids,boolean check)throws EntityException;
	
	/**
	 * 毕业学位审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForGraduation(List<String> ids,boolean degree)throws EntityException;
	
	/**
	 * 取消毕业学位审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForCancelGraduation(List<String> ids,boolean degree)throws EntityException;

	/**
	 * 申请毕业和学位时保存申请记录
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean saveForApplyGraduationAndDegree(PeStudent peStudent)throws EntityException;

	
	/**
	 * 毕业论文重修申请审核
	 * @param ids
	 * @param apply
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForPaperReapply(List<String> ids,boolean apply) throws EntityException;

	
	
	/**
	 * 评优申请审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentPass(List<String> ids)throws EntityException;
	
	/**
	 * 评优申请审核 取消通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentCancelPass(List<String> ids)throws EntityException;
	
	/**
	 * 评优申请审核不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentNoPass(List<String> ids)throws EntityException;
	
	/**
	 * 评优申请审核 取消不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentCancelNoPass(List<String> ids)throws EntityException;
	
	/**
	 * 统考免试申请审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamPass(List<String> ids,String type)throws EntityException;
	
	/**
	 * 统考免试申请审核取消通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamCancelPass(List<String> ids,String type)throws EntityException;
	
	/**
	 * 统考免试申请审核不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamNoPass(List<String> ids)throws EntityException;
	
	/**
	 * 统考免试申请审核取消不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamCancelNoPass(List<String> ids)throws EntityException;
	
	/**
	 * 课程免考申请审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidPass(List<String> ids)throws EntityException;
	
	/**
	 * 课程免考申请审核 取消通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidCancelPass(List<String> ids)throws EntityException;
	
	/**
	 * 课程免考申请审核不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidNoPass(List<String> ids)throws EntityException;
	
	/**
	 * 课程免考申请审核 取消不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidCancelNoPass(List<String> ids)throws EntityException;

	public abstract int updateForChangeStudentApply(List<String> ids,boolean apply) throws EntityException;  

	/**
	 * 学位英语申请审核
	 * @param ids
	 * @param apply
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForDegreeEnglishApply(List<String> ids,boolean apply) throws EntityException;
}
