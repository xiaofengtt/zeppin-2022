package com.product.worldpay.service.api;

import com.product.worldpay.controller.base.ApiResult;
import com.product.worldpay.controller.base.InputParams;

public interface ApisWithdrawService {
	
	public void createOrder(InputParams params, ApiResult result);

	public void queryOrder(InputParams params, ApiResult result);
	
	public void closeOrder(InputParams params, ApiResult result);
}
