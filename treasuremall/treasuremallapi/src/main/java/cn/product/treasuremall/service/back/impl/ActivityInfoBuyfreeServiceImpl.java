package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.ActivityInfoBuyfreeService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;


@Service("activityInfoBuyfreeService")
public class ActivityInfoBuyfreeServiceImpl extends BaseServiceImpl implements ActivityInfoBuyfreeService {

	@Override
	public void goodslist(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
