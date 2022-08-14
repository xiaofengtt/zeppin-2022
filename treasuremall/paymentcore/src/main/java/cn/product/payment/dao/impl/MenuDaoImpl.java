package cn.product.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.MenuDao;
import cn.product.payment.entity.Menu;
import cn.product.payment.mapper.MenuMapper;

@Component
public class MenuDaoImpl implements MenuDao {
	
	@Autowired
    private MenuMapper menuMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return menuMapper.getCountByParams(params);
	}
	
    @Override
    public List<Menu> getListByParams(Map<String, Object> params){
        return menuMapper.getListByParams(params);
    }

    @Override
    public List<Menu> getListByRole(Map<String,Object> params) {
        return menuMapper.getListByRole(params);
    }

	@Override
	@Cacheable(cacheNames="menu",key="'menu:' + #key")
	public Menu get(String key) {
		return menuMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Menu menu) {
		return menuMapper.insert(menu);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "menu", key = "'menu:' + #key")})
	public int delete(String key) {
		return menuMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "menu", key = "'menu:' + #menu.uuid")})
	public int update(Menu menu) {
		return menuMapper.updateByPrimaryKey(menu);
	}
	
}
