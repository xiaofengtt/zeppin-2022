package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.product.payment.entity.Method;
import cn.product.payment.entity.Role;
import cn.product.payment.entity.RoleMethod;
import cn.product.payment.mapper.RoleMethodMapper;
import cn.product.payment.service.RoleMethodService;
import cn.product.payment.shiro.ShiroSystemRealm;

@Service("roleMethodService")
public class RoleMethodServiceImpl implements RoleMethodService {
	
	@Autowired
    private RoleMethodMapper roleMethodMapper;
	
    @Autowired
    private ShiroSystemRealm myShiroRealm;
    
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
}
