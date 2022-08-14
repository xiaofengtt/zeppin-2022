package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.ActivityInfoFirstchargeService;
import cn.product.worldmall.service.impl.BaseServiceImpl;


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
