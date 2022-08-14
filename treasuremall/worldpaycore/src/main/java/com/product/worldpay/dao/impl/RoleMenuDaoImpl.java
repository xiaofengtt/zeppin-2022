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

import com.product.worldpay.dao.RoleMenuDao;
import com.product.worldpay.entity.Role;
import com.product.worldpay.entity.RoleMenu;
import com.product.worldpay.mapper.RoleMenuMapper;
import com.product.worldpay.vo.system.MenuVO;
import com.product.worldpay.vo.system.RoleMenuVO;

@Component
public class RoleMenuDaoImpl implements RoleMenuDao {
	
	@Autowired
    private RoleMenuMapper roleMenuMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return roleMenuMapper.getCountByParams(params);
	}

	@Override
	public List<RoleMenu> getListByParams(Map<String, Object> params) {
		return roleMenuMapper.getListByParams(params);
	}

	@Override
	public List<RoleMenuVO> getListForPage(Map<String, Object> params) {
		return roleMenuMapper.getListForPage(params);
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "roleMenu", allEntries = true)})
	public void updateAll(Role role, List<MenuVO> menuList) {
		//删除现有权限
		roleMenuMapper.deleteByRole(role.getUuid());
		//循环一级权限
		for(int i=0;i<menuList.size();i++){
			MenuVO menu = menuList.get(i);
			//添加一级权限
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setUuid(UUID.randomUUID().toString());
			roleMenu.setMenu(menu.getUuid());
			roleMenu.setRole(role.getUuid());
			roleMenuMapper.insert(roleMenu);
		}
	}

	@Override
	@Cacheable(cacheNames="roleMenu",key="'roleMenu:' + #key")
	public RoleMenu get(String key) {
		return roleMenuMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(RoleMenu roleMenu) {
		return roleMenuMapper.insert(roleMenu);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "roleMenu", key = "'roleMenu:' + #key")})
	public int delete(String key) {
		return roleMenuMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "roleMenu", key = "'roleMenu:' + #roleMenu.uuid")})
	public int update(RoleMenu roleMenu) {
		return roleMenuMapper.updateByPrimaryKey(roleMenu);
	}
}
