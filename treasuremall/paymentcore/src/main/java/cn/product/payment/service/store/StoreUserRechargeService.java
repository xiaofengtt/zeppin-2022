/**
 * 
 */
package cn.product.payment.service.store;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface StoreUserRechargeService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void close(InputParams params, DataResult<Object> result);
}
