package cn.product.treasuremall.service.back.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.dao.RoleDao;
import cn.product.treasuremall.entity.Role;
import cn.product.treasuremall.service.back.RoleService;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

	@Override
	public void all(InputParams params, DataResult<Object> result) {
    	
    	List<Role> role = roleDao.getListByParams(new HashMap<String, Object>());
    	result.setData(role);
		result.setStatus(ResultStatusType.SUCCESS);
	}
    
}
