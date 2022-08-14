package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemStatisticsChannelService {
	
	public void channelList(InputParams params, DataResult<Object> result);
	
	public void channelAccountList(InputParams params, DataResult<Object> result);
}
