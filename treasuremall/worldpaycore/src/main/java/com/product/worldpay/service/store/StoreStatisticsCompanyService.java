package com.product.worldpay.service.store;

import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

public interface StoreStatisticsCompanyService {
	
	public void dailyList(InputParams params, DataResult<Object> result);
}
