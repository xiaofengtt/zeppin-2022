/**
 * 
 */
package com.product.worldpay.service.store;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface StoreCompanyTradeService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);

	public void recharge(InputParams params, DataResult<Object> result);
	
	public void withdraw(InputParams params, DataResult<Object> result);
	
	public void close(InputParams params, DataResult<Object> result);
	
	public void retry(InputParams params, DataResult<Object> result);
}
