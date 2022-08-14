package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.RoleMethodService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("roleMethodService")
public class RoleMethodServiceImpl extends BaseServiceImpl implements RoleMethodService {

	@Override
	public void all(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void getMethodListByRole(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);	
	}
	
}
