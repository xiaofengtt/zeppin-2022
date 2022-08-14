/**
 * 
 */
package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemResourceService{
	
	public void add(InputParams params, DataResult<Object> result);
}
