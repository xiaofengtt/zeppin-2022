package com.product.worldpay.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.product.worldpay.dao.RoleMethodDao;
import com.product.worldpay.entity.Method;
import com.product.worldpay.entity.Role;
import com.product.worldpay.entity.RoleMethod;
import com.product.worldpay.mapper.RoleMethodMapper;

@Component
public class RoleMethodDaoImpl implements RoleMethodDao {
	
	@Autowired
    private RoleMethodMapper roleMethodMapper;
    
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "roleMethod", allEntries = true)})
	public void updateAll(Role role, List<Method> methodList) {
		//删除现有权限
		roleMethodMapper.deleteByRole(role.getUuid());
		//循环添加权限
		for(Method method : methodList){
			RoleMethod roleMethod = new RoleMethod();
			roleMethod.setUuid(UUID.randomUUID().toString());
			roleMethod.setMethod(method.getUuid());
			roleMethod.setRole(role.getUuid());
			roleMethodMapper.insert(roleMethod);
		}
	}

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return roleMethodMapper.getCountByParams(params);
	}

	@Override
	public List<RoleMethod> getListByParams(Map<String, Object> params) {
		return roleMethodMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="roleMethod",key="'roleMethod:' + #key")
	public RoleMethod get(String key) {
		return roleMethodMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(RoleMethod roleMethod) {
		return roleMethodMapper.insert(roleMethod);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "roleMethod", key = "'roleMethod:' + #key")})
	public int delete(String key) {
		return roleMethodMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "roleMethod", key = "'roleMethod:' + #roleMethod.uuid")})
	public int update(RoleMethod roleMethod) {
		return roleMethodMapper.updateByPrimaryKey(roleMethod);
	}
	
	@Override
	public List<Method> getMethodListByRole(String role) {
		return roleMethodMapper.getMethodListByRole(role);
	}
}
