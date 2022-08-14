package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.UserRecommendService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("userRecommendService")
public class UserRecommendServiceImpl extends BaseServiceImpl implements UserRecommendService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
