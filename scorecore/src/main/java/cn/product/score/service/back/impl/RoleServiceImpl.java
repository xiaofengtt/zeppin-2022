package cn.product.score.service.back.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.RoleDao;
import cn.product.score.entity.Role;
import cn.product.score.service.back.RoleService;

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
