package com.product.worldpay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.product.worldpay.dao.CompanyChannelAccountDao;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.CompanyChannelAccount;
import com.product.worldpay.mapper.CompanyChannelAccountMapper;

@Component
public class CompanyChannelAccountDaoImpl implements CompanyChannelAccountDao{
	
	@Autowired
	private CompanyChannelAccountMapper companyChannelAccountMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyChannelAccountMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyChannelAccount> getListByParams(Map<String, Object> params) {
		return companyChannelAccountMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyChannelAccount",key="'companyChannelAccount:' + #key")
	public CompanyChannelAccount get(String key) {
		return companyChannelAccountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyChannelAccount companyChannelAccount) {
		return companyChannelAccountMapper.insert(companyChannelAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannelAccount", key = "'companyChannelAccount:' + #key")})
	public int delete(String key) {
		return companyChannelAccountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannelAccount", key = "'companyChannelAccount:' + #companyChannelAccount.uuid")})
	public int update(CompanyChannelAccount companyChannelAccount) {
		return companyChannelAccountMapper.updateByPrimaryKey(companyChannelAccount);
	}

	@Override
	public void deleteByCompanyChannel(String companyChannel) {
		companyChannelAccountMapper.deleteByCompanyChannel(companyChannel);
	}

	@Override
	public List<ChannelAccount> getChannelAccountListByParams(Map<String, Object> params) {
		return companyChannelAccountMapper.getChannelAccountListByParams(params);
	}
}
