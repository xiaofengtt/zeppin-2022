package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.CompanyAccountHistory;
import cn.product.payment.mapper.CompanyAccountHistoryMapper;
import cn.product.payment.service.CompanyAccountHistoryService;

@Service("companyAccountHistoryService")
public class CompanyAccountHistoryServiceImpl implements CompanyAccountHistoryService{
	
	@Autowired
	private CompanyAccountHistoryMapper companyAccountHistoryMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyAccountHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyAccountHistory> getListByParams(Map<String, Object> params) {
		return companyAccountHistoryMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyAccountHistory",key="'companyAccountHistory:' + #key")
	public CompanyAccountHistory get(String key) {
		return companyAccountHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyAccountHistory companyAccountHistory) {
		return companyAccountHistoryMapper.insert(companyAccountHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyAccountHistory", key = "'companyAccountHistory:' + #key")})
	public int delete(String key) {
		return companyAccountHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyAccountHistory", key = "'companyAccountHistory:' + #companyAccountHistory.uuid")})
	public int update(CompanyAccountHistory companyAccountHistory) {
		return companyAccountHistoryMapper.updateByPrimaryKey(companyAccountHistory);
	}
}
