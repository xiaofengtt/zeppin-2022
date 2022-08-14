package com.product.worldpay.service.store;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface StoreCompanyAdminService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void password(InputParams params, DataResult<Object> result);
	
	public void getByMobile(InputParams params, DataResult<Object> result);
}
