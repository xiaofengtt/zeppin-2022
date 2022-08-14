package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontUserBetService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontUserBetService")
public class FrontUserBetServiceImpl extends BaseServiceImpl implements FrontUserBetService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void bet(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
