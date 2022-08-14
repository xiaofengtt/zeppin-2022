package com.product.worldpay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.product.worldpay.dao.CompanyBankcardDao;
import com.product.worldpay.entity.CompanyBankcard;
import com.product.worldpay.mapper.CompanyBankcardMapper;

@Component
public class CompanyBankcardDaoImpl implements CompanyBankcardDao{
	
	@Autowired
	private CompanyBankcardMapper companyBankcardMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyBankcardMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyBankcard> getListByParams(Map<String, Object> params) {
		return companyBankcardMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyBankcard",key="'companyBankcard:' + #key")
	public CompanyBankcard get(String key) {
		return companyBankcardMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyBankcard companyBankcard) {
		return companyBankcardMapper.insert(companyBankcard);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyBankcard", key = "'companyBankcard:' + #key")})
	public int delete(String key) {
		return companyBankcardMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyBankcard", key = "'companyBankcard:' + #companyBankcard.uuid")})
	public int update(CompanyBankcard companyBankcard) {
		return companyBankcardMapper.updateByPrimaryKey(companyBankcard);
	}
}
