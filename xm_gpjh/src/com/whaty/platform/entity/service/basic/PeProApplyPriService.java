package com.whaty.platform.entity.service.basic;

import java.util.List;

import com.whaty.platform.entity.exception.EntityException;

public interface PeProApplyPriService {
	/**
	 * 指定项目管理员
	 * @param ids
	 * @param applyIds
	 * @return
	 * @throws EntityException
	 */
	public abstract int distributePrSsoPro(List<String> idList, List<String> applyIdList)throws EntityException;

	/**
	 * 取消项目管理员
	 * @param ids
	 * @param applyIds
	 * @return
	 * @throws EntityException
	 */
	public abstract int cancelPrSsoPro(List<String> idList, List<String> applyIdList)throws EntityException;
}
