package com.whaty.platform.entity.service.fee;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;

public interface PrFeeDetailReduceService {
	/**
	 * 批量添加学生减免费用信息
	 * @param 
	 * @return
	 * @throws EntityException
	 */
	public int saveBatch(File file) throws EntityException;
	
	/**
	 * 教师减免费用
	 * @param 
	 * @return
	 * @throws EntityException
	 */
	public int saveTeacherReduce(List<String> idList) throws EntityException;
	
	/**
	 * 添加学生减免费用信息
	 * @param prFeeDetail
	 * @return
	 * @throws EntityException
	 */
	public abstract PrFeeDetail save(PrFeeDetail prFeeDetail) throws EntityException;

	/**
	 * 批量审核学生减免费用(通过或者不通过)
	 * @param ids
	 * @param pass
	 * 		是否通过
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForCheck(java.util.List<String> ids,boolean pass)throws EntityException;

	/**
	 * 取消审核通过或者不通过
	 * @param ids
	 * @param pass
	 * 		是否通过
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForCancel(java.util.List<String> ids,boolean pass)throws EntityException;

}
