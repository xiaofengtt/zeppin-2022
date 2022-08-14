package cn.product.score.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.back.CapitalAccountHistoryService;
import cn.product.score.service.impl.BaseServiceImpl;

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
