package cn.product.payment.service.system.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.RoleDao;
import cn.product.payment.entity.Role;
import cn.product.payment.service.system.SystemRoleService;

/**
 * 管理员角色
 */
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
