package cn.product.payment.service.store;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface StoreStatisticsCompanyService {
	
	public void dailyList(InputParams params, DataResult<Object> result);
}
