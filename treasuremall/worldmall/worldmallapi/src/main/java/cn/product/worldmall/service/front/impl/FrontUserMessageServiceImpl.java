package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontUserMessageService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontUserMessageService")
public class FrontUserMessageServiceImpl extends BaseServiceImpl implements FrontUserMessageService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}

	@Override
	public void readAll(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}

	@Override
	public void unRead(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}
}
