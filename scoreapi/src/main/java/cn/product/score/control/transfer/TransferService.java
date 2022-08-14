package cn.product.score.control.transfer;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

/**
 *	 请求转移调度
 */
public interface TransferService {
	void execute(InputParams inputParams, DataResult<Object> result);
}
