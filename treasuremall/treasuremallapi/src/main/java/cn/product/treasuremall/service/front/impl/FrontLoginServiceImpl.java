package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontLoginService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontLoginService")
public class FrontLoginServiceImpl extends BaseServiceImpl implements FrontLoginService{

	@Override
	public void loginByCode(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void loginByPwd(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void auth(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void resetPwd(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void resetCheck(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}
	
}
