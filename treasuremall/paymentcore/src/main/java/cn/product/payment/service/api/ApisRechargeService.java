package cn.product.payment.service.api;

import cn.product.payment.controller.base.ApiResult;
import cn.product.payment.controller.base.InputParams;

public interface ApisRechargeService {
	
	public void createOrder(InputParams params, ApiResult result);

	public void queryOrder(InputParams params, ApiResult result);
	
	public void closeOrder(InputParams params, ApiResult result);
}
