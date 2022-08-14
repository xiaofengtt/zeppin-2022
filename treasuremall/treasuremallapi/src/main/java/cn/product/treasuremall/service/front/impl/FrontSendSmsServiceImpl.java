package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontSendSmsService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontSendSmsService")
public class FrontSendSmsServiceImpl extends BaseServiceImpl implements FrontSendSmsService{

	@Override
	public void sendCode(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void sendCodeForUser(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

}
