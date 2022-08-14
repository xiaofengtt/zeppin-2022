/**
 * 
 */
package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemCompanyTradeService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void add(InputParams params, DataResult<Object> result);
	
	public void check(InputParams params, DataResult<Object> result);
	
	public void fail(InputParams params, DataResult<Object> result);
	
	public void success(InputParams params, DataResult<Object> result);
	
	public void close(InputParams params, DataResult<Object> result);
}
