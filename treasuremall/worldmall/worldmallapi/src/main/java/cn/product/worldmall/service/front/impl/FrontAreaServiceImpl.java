package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontAreaService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontAreaService")
public class FrontAreaServiceImpl extends BaseServiceImpl implements FrontAreaService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void country(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}

	@Override
	public void currency(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
