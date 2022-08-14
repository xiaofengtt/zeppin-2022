package com.product.worldpay.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Company;
import com.product.worldpay.mapper.CompanyMapper;
import com.product.worldpay.util.PaymentUtil;
import com.product.worldpay.util.Utlity;

@Component
public class CompanyDaoImpl implements CompanyDao{
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyMapper.getCountByParams(params);
	}
	
	@Override
	public List<Company> getListByParams(Map<String, Object> params) {
		return companyMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="company",key="'company:' + #key")
	public Company get(String key) {
		return companyMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Company company) {
		return companyMapper.insert(company);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "company", key = "'company:' + #key"),@CacheEvict(value = "companyCode", allEntries = true)})
	public int delete(String key) {
		return companyMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "company", key = "'company:' + #company.uuid"), @CacheEvict(value = "companyCode", key = "'companyCode:' + #company.code")})
	public int update(Company company) {
		return companyMapper.updateByPrimaryKey(company);
	}

	@Override
	@Transactional
	public void insertCompany(Company company) {
		Integer count = this.getCountByParams(new HashMap<String, Object>());
		company.setCode(Utlity.timestampFormat(company.getCreatetime(), "yyyyMMdd") + Utlity.numberFormat(count, "0000"));
		this.insert(company);
	}

	@Override
	public Map<String, String> getCompanyKeys(Company company) {
		return PaymentUtil.createKeys(company.getUuid());
	}

	@Override
	@Cacheable(cacheNames="companyCode",key="'companyCode:' + #code")
	public Company getByCode(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("code",code);
        List<Company> list =  this.getListByParams(params);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
	}
	
}
