package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontSendSmsService {
	
	public void sendCode(InputParams params, DataResult<Object> result);

	public void sendCodeForUser(InputParams params, DataResult<Object> result);
}
