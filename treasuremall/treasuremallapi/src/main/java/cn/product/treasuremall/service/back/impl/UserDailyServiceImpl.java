package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.UserDailyService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("userDailyService")
public class UserDailyServiceImpl extends BaseServiceImpl implements UserDailyService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
