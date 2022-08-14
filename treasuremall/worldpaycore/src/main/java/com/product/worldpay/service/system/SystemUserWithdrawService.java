/**
 * 
 */
package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemUserWithdrawService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void channelAccountList(InputParams params, DataResult<Object> result);
	
	public void check(InputParams params, DataResult<Object> result);
	
	public void fail(InputParams params, DataResult<Object> result);
	
	public void success(InputParams params, DataResult<Object> result);
	
	public void typeList(InputParams params, DataResult<Object> result);
}
