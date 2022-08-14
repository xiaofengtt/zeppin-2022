/**
 * 
 */
package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemRoleMethodService {

	public void all(InputParams params, DataResult<Object> result);
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void edit(InputParams params, DataResult<Object> result);
	
	public void getMethodListByRole(InputParams params, DataResult<Object> result);
}
