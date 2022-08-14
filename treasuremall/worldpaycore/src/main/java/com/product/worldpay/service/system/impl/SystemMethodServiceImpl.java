package com.product.worldpay.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.MethodDao;
import com.product.worldpay.service.system.SystemMethodService;

@Service("systemMethodService")
public class SystemMethodServiceImpl implements SystemMethodService{

    @Autowired
    private MethodDao methodDao;

	@Override
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result) {
		result.setData(this.methodDao.loadFilterChainDefinitions());
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
