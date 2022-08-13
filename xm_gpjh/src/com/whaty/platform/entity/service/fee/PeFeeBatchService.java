package com.whaty.platform.entity.service.fee;

import java.util.List;

import com.whaty.platform.entity.exception.EntityException;

public interface PeFeeBatchService {

	/**
	 * 上报交费批次
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForReport(List ids) throws EntityException;
		
	/**
	 * 取消上报交费批次
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateCancelReport(List ids) throws EntityException;
	
	/**
	 * 交费审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateForCheck(List ids) throws EntityException;
		
	/**
	 * 取消审核
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateCancelCheck(List ids) throws EntityException;

}