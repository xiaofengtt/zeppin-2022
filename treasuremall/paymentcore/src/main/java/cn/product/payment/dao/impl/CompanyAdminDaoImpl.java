package cn.product.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.CompanyAdminDao;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.CompanyAdmin.CompanyAdminStatus;
import cn.product.payment.mapper.CompanyAdminMapper;

@Component
public class CompanyAdminDaoImpl implements CompanyAdminDao{
	
	@Autowired
	private CompanyAdminMapper companyAdminMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyAdminMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyAdmin> getListByParams(Map<String, Object> params) {
		return companyAdminMapper.getListByParams(params);
	}
	
	/**
	 * 根据手机号取商户管理员
	 */
    @Override
    public CompanyAdmin getByMobile(String mobile) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",mobile);
        params.put("status",CompanyAdminStatus.NORMAL);
        List<CompanyAdmin> companyAdminList =  this.getListByParams(params);
        if(companyAdminList.size()>0){
            return companyAdminList.get(0);
        }
            return null;
    }
    
    @Override
	@Cacheable(cacheNames="companyAdmin",key="'companyAdmin:' + #key")
	public CompanyAdmin get(String key) {
		return companyAdminMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyAdmin companyAdmin) {
		return companyAdminMapper.insert(companyAdmin);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyAdmin", key = "'companyAdmin:' + #key")})
	public int delete(String key) {
		return companyAdminMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyAdmin", key = "'companyAdmin:' + #companyAdmin.uuid")})
	public int update(CompanyAdmin companyAdmin) {
		return companyAdminMapper.updateByPrimaryKey(companyAdmin);
	}
}
