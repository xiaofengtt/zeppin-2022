package cn.product.worldmall.control.transfer;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

/**
 *	 请求转移调度
 */
public interface TransferService {
	void execute(InputParams inputParams, DataResult<Object> result);
}
