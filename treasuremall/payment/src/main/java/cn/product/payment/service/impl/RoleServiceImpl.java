package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.Role;
import cn.product.payment.mapper.RoleMapper;
import cn.product.payment.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;
    
    @Override
	public Integer getCountByParams(Map<String, Object> params) {
		return roleMapper.getCountByParams(params);
	}
	
	@Override
	public List<Role> getListByParams(Map<String, Object> params) {
		return roleMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="role",key="'role:' + #key")
	public Role get(String key) {
		return roleMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Role role) {
		return roleMapper.insert(role);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "role", key = "'role:' + #key")})
	public int delete(String key) {
		return roleMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "role", key = "'role:' + #role.uuid")})
	public int update(Role role) {
		return roleMapper.updateByPrimaryKey(role);
	}
}
