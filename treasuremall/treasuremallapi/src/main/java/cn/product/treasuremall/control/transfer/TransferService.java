package cn.product.treasuremall.control.transfer;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

/**
 *	 请求转移调度
 */
public interface TransferService {
	void execute(InputParams inputParams, DataResult<Object> result);
}
