package com.product.worldpay.service.support;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SupportStripeService {
	
	public void webhook(InputParams params, DataResult<Object> result);
}
