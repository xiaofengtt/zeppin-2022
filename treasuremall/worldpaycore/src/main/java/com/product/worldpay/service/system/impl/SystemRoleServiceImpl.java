package com.product.worldpay.service.system.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.RoleDao;
import com.product.worldpay.entity.Role;
import com.product.worldpay.service.system.SystemRoleService;

@Service("systemRoleService")
public class SystemRoleServiceImpl implements SystemRoleService{
	
	@Autowired
    private RoleDao roleDao;

	@Override
	public void all(InputParams params, DataResult<Object> result) {
    	List<Role> role = roleDao.getListByParams(new HashMap<String, Object>());
    	result.setData(role);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
