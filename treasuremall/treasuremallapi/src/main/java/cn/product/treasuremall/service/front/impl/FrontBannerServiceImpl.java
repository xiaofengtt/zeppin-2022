package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontBannerService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontBannerService")
public class FrontBannerServiceImpl extends BaseServiceImpl implements FrontBannerService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
