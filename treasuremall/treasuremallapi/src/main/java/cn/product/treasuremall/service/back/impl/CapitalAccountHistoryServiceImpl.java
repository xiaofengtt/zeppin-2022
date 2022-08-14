package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.CapitalAccountHistoryService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("capitalAccountHistoryService")
public class CapitalAccountHistoryServiceImpl extends BaseServiceImpl implements CapitalAccountHistoryService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void typeList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
