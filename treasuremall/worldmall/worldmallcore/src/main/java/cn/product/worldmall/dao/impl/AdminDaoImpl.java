package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.entity.Admin.AdminStatus;
import cn.product.worldmall.mapper.AdminMapper;

@Component
public class AdminDaoImpl implements AdminDao{

	@Autowired
    private AdminMapper adminMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return adminMapper.getCountByParams(params);
	}
	
    @Override
    public List<Admin> getListByParams(Map<String, Object> params){
        return adminMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="admin",key="'admin:' + #key")
	public Admin get(String key) {
		return adminMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Admin admin) {
		return adminMapper.insert(admin);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "admin", key = "'admin:' + #key")})
	public int delete(String key) {
		return adminMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "admin", key = "'admin:' + #admin.uuid")})
	public int update(Admin admin) {
		return adminMapper.updateByPrimaryKey(admin);
	}

	@Override
	public Admin getByUsername(String username) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",username);
        params.put("status",AdminStatus.NORMAL);
        List<Admin> adminList =  this.adminMapper.getListByParams(params);
        if(adminList.size()>0){
            return adminList.get(0);
        }
            return null;
	}
}
