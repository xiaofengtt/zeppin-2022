package com.whaty.platform.entity.service.programApply;

import java.util.List;

import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.exception.EntityException;

public interface ProgramApplyService {
	
	/**
	 * 省厅师范处通过审核
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForProvincePass(List<String> ids)throws EntityException; 
	
	/**
	 * 省厅师范处不通过审核
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForProvinceNoPass(List<String> ids)throws EntityException; 
	
	/**
	 * 项目执行办通过审核
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForNationalPass(List<String> ids)throws EntityException; 
	
	/**
	 * 项目执行办不通过审核
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForNationalNoPass(List<String> ids)throws EntityException; 
	
	/**
	 * 指定评审专家
	 * @param ids
	 * @param applyIds
	 * @return
	 * @throws EntityException
	 */
	public abstract int distributeValueExpert(List<String> ids,List<String> applyIds)throws EntityException;

	/**
	 * 取消评审专家
	 * @param ids
	 * @param applyIds
	 * @return
	 * @throws EntityException
	 */
	public abstract int cancelValueExpert(List<String> idList, List<String> applyIdList)throws EntityException;
	
	
	public int savePrProExpert(PeProApply peProApply2,PeProApply peProApply);
}
