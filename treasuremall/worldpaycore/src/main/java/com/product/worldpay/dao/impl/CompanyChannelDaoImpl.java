package com.product.worldpay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.product.worldpay.dao.CompanyChannelAccountDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyChannelAccount;
import com.product.worldpay.mapper.CompanyChannelMapper;

@Component
public class CompanyChannelDaoImpl implements CompanyChannelDao{
	
	@Autowired
	private CompanyChannelMapper companyChannelMapper;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyChannelMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyChannel> getListByParams(Map<String, Object> params) {
		return companyChannelMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyChannel",key="'companyChannel:' + #key")
	public CompanyChannel get(String key) {
		return companyChannelMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyChannel companyChannel) {
		return companyChannelMapper.insert(companyChannel);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannel", key = "'companyChannel:' + #key")})
	public int delete(String key) {
		return companyChannelMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannel", key = "'companyChannel:' + #companyChannel.uuid")})
	public int update(CompanyChannel companyChannel) {
		return companyChannelMapper.updateByPrimaryKey(companyChannel);
	}

	@Override
	@Transactional
	public void insertCompanyChannel(CompanyChannel companyChannel, List<CompanyChannelAccount> companyChannelAccountList) {
		companyChannelMapper.insert(companyChannel);
		for(CompanyChannelAccount cca : companyChannelAccountList){
			this.companyChannelAccountDao.insert(cca);
		}
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannel", key = "'companyChannel:' + #companyChannel.uuid")})
	@Transactional
	public void updateCompanyChannel(CompanyChannel companyChannel, List<CompanyChannelAccount> companyChannelAccountList) {
		companyChannelMapper.updateByPrimaryKey(companyChannel);
		this.companyChannelAccountDao.deleteByCompanyChannel(companyChannel.getUuid());
		for(CompanyChannelAccount cca : companyChannelAccountList){
			this.companyChannelAccountDao.insert(cca);
		}
	}
}
