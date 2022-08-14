package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.UserBuyfreeService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("userBuyfreeService")
public class UserBuyfreeServiceImpl extends BaseServiceImpl implements UserBuyfreeService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void receive(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void confirm(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}

	@Override
	public void reset(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}
}
