package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontUserWithdrawService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontUserWithdrawService")
public class FrontUserWithdrawServiceImpl extends BaseServiceImpl implements FrontUserWithdrawService{

	@Override
	public void poundage(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
