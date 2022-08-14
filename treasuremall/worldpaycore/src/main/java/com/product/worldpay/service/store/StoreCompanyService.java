/**
 * 
 */
package com.product.worldpay.service.store;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface StoreCompanyService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void edit(InputParams params, DataResult<Object> result);
}
