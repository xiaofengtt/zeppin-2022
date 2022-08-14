package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.RoleDao;
import cn.product.score.entity.Role;
import cn.product.score.mapper.RoleMapper;

@Component
public class RoleDaoImpl implements RoleDao{

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
