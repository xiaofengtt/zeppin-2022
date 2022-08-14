package cn.product.payment.service.api;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface TransferRechargeService {
	
	public void aliGet(InputParams params, DataResult<Object> result);

	public void aliFinish(InputParams params, DataResult<Object> result);

	public void wechatGet(InputParams params, DataResult<Object> result);

	public void wechatFinish(InputParams params, DataResult<Object> result);
}
