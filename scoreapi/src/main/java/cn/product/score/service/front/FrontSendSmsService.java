package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontSendSmsService {
	
	public void sendCode(InputParams params, DataResult<Object> result);

	public void sendCodeForUser(InputParams params, DataResult<Object> result);
}
