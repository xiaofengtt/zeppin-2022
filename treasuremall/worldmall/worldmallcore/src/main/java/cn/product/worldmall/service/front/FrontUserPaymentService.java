package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontUserPaymentService {
	
	public void placeOrder(InputParams params, DataResult<Object> result);
	
}
