package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.mapper.CompanyChannelMapper;
import cn.product.payment.service.CompanyChannelService;

@Service("companyChannelService")
public class CompanyChannelServiceImpl implements CompanyChannelService{
	
	@Autowired
	private CompanyChannelMapper companyChannelMapper;
	
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
}
