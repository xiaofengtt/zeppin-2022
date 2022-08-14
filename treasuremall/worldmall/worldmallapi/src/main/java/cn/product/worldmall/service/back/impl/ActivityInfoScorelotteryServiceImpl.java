package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.ActivityInfoScorelotteryService;
import cn.product.worldmall.service.impl.BaseServiceImpl;


@Service("activityInfoScorelotteryService")
public class ActivityInfoScorelotteryServiceImpl extends BaseServiceImpl implements ActivityInfoScorelotteryService {

	@Override
	public void prizelist(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
