package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.ActivityInfoBuyfreeService;
import cn.product.worldmall.service.impl.BaseServiceImpl;


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
