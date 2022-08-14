package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontUserService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontUserService")
public class FrontUserServiceImpl extends BaseServiceImpl implements FrontUserService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void certification(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
