package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.ActivityInfoFirstchargeService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;


@Service("activityInfoFirstchargeService")
public class ActivityInfoFirstchargeServiceImpl extends BaseServiceImpl implements ActivityInfoFirstchargeService {

	@Override
	public void prizelist(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
