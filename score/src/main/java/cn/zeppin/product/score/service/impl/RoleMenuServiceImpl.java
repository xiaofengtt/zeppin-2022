package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.Role;
import cn.zeppin.product.score.entity.RoleMenu;
import cn.zeppin.product.score.mapper.RoleMenuMapper;
import cn.zeppin.product.score.service.RoleMenuService;
import cn.zeppin.product.score.vo.back.MenuVO;
import cn.zeppin.product.score.vo.back.RoleMenuVO;

@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
	
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
