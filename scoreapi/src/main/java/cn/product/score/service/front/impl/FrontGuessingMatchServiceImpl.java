package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontGuessingMatchService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontGuessingMatchService")
public class FrontGuessingMatchServiceImpl extends BaseServiceImpl implements FrontGuessingMatchService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void categoryList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
