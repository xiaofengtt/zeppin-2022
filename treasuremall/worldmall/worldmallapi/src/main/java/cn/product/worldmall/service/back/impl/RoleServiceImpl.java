package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.RoleService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{

	@Override
	public void all(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
