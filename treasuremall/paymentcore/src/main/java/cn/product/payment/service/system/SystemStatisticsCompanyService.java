package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemStatisticsCompanyService {
	
	public void dailyList(InputParams params, DataResult<Object> result);
	
	public void companyList(InputParams params, DataResult<Object> result);
}
