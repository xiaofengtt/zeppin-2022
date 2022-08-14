/**
 * 
 */
package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemMenuService {

	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void left(InputParams params, DataResult<Object> result);
}
