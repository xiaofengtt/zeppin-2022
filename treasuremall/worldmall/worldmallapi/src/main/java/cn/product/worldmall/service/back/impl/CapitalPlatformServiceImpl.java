package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.CapitalPlatformService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("capitalPlatformService")
public class CapitalPlatformServiceImpl extends BaseServiceImpl implements CapitalPlatformService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
