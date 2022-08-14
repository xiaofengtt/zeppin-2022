package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontUserAddressService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontUserAddressService")
public class FrontUserAddressServiceImpl extends BaseServiceImpl implements FrontUserAddressService{

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
	public void delete(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
