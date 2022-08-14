package cn.product.payment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Admin.AdminStatus;
import cn.product.payment.mapper.AdminMapper;
import cn.product.payment.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return adminMapper.getCountByParams(params);
	}
	
	@Override
	public List<Admin> getListByParams(Map<String, Object> params) {
		return adminMapper.getListByParams(params);
	}
	
    @Override
    public Admin getByUsername(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",username);
        params.put("status",AdminStatus.NORMAL);
        List<Admin> adminList =  this.getListByParams(params);
        if(adminList.size()>0){
            return adminList.get(0);
        }else{
            return null;
        }
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
}
