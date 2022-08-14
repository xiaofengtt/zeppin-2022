package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.ActivityInfoRecommendRankingService;
import cn.product.worldmall.service.impl.BaseServiceImpl;


@Service("activityInfoRecommendRankingService")
public class ActivityInfoRecommendRankingServiceImpl extends BaseServiceImpl implements ActivityInfoRecommendRankingService {

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
