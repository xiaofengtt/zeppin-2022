package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontChannelService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontChannelService")
public class FrontChannelServiceImpl extends BaseServiceImpl implements FrontChannelService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
