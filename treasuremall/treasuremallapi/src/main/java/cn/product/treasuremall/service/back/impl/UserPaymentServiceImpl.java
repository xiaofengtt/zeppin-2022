package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.UserPaymentService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("userPaymentService")
public class UserPaymentServiceImpl extends BaseServiceImpl implements UserPaymentService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}
	
	@Override
	public void getStatistics(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}
}
