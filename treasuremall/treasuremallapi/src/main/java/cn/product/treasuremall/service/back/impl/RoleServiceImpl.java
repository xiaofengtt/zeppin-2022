package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.RoleService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{

	@Override
	public void all(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
