package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.UserWithdrawService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

/**
 */
@Service("userWithdrawService")
public class UserWithdrawServiceImpl extends BaseServiceImpl implements UserWithdrawService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void firstCheck(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void finalCheck(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void cancel(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);				
	}
}
