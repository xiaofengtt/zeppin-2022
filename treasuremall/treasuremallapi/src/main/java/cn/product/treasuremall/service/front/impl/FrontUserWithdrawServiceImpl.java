package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontUserWithdrawService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontUserWithdrawService")
public class FrontUserWithdrawServiceImpl extends BaseServiceImpl implements FrontUserWithdrawService{

	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
