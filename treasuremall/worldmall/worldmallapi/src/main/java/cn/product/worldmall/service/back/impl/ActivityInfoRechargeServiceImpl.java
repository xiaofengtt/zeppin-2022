package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.ActivityInfoRechargeService;
import cn.product.worldmall.service.impl.BaseServiceImpl;


@Service("activityInfoRechargeService")
public class ActivityInfoRechargeServiceImpl extends BaseServiceImpl implements ActivityInfoRechargeService {

	@Override
	public void configlist(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
