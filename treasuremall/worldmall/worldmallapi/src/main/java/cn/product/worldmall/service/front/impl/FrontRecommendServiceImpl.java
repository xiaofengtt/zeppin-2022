package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontRecommendService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontRecommendService")
public class FrontRecommendServiceImpl extends BaseServiceImpl implements FrontRecommendService{

	@Override
	public void rankingList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
	@Override
	public void agentList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void awardList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}
}
