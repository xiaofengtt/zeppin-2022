package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.CompanyTrade;
import cn.product.payment.mapper.CompanyTradeMapper;
import cn.product.payment.service.CompanyTradeService;

@Service("companyTradeService")
public class CompanyTradeServiceImpl implements CompanyTradeService{
	
	@Autowired
	private CompanyTradeMapper companyTradeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyTradeMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyTrade> getListByParams(Map<String, Object> params) {
		return companyTradeMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyTrade",key="'companyTrade:' + #key")
	public CompanyTrade get(String key) {
		return companyTradeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyTrade companyTrade) {
		return companyTradeMapper.insert(companyTrade);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyTrade", key = "'companyTrade:' + #key")})
	public int delete(String key) {
		return companyTradeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyTrade", key = "'companyTrade:' + #companyTrade.uuid")})
	public int update(CompanyTrade companyTrade) {
		return companyTradeMapper.updateByPrimaryKey(companyTrade);
	}
}
