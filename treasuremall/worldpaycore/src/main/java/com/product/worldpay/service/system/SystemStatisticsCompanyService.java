package com.product.worldpay.service.system;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface SystemStatisticsCompanyService {
	
	public void dailyList(InputParams params, DataResult<Object> result);
}
