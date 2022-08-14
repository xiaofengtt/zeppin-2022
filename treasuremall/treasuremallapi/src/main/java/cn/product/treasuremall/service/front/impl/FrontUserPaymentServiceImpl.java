package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontUserPaymentService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontUserPaymentService")
public class FrontUserPaymentServiceImpl extends BaseServiceImpl implements FrontUserPaymentService{

	@Override
	public void placeOrder(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

}
