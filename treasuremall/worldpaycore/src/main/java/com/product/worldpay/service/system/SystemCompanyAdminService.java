/**
 * 
 */
package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemCompanyAdminService {

	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void add(InputParams params, DataResult<Object> result);
	
	public void edit(InputParams params, DataResult<Object> result);
	
	public void changeStatus(InputParams params, DataResult<Object> result);
}
