package com.whaty.platform.entity.service.fee;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;

public interface FeeRefundService {

	/**
	 * 学生退费
	 * @param ids
	 * 		学生的ids
	 * @param feeDetail
	 * 		存储退费信息的时间和备注
	 * @return
	 * @throws EntityException
	 */
	public abstract int saveForRefund(java.util.List<String> ids,PrFeeDetail feeDetail)throws EntityException;
	
	/**
	 * 取消退费
	 * @param ids
	 * 		退费明细的ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int delRefund(java.util.List<String> ids)throws EntityException;
	
}
