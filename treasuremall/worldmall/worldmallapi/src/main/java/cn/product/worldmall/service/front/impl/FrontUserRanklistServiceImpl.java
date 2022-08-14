package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontUserRanklistService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontUserRanklistService")
public class FrontUserRanklistServiceImpl extends BaseServiceImpl implements FrontUserRanklistService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
