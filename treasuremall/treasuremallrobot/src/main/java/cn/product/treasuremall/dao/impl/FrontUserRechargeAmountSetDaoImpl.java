package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.FrontUserRechargeAmountSetDao;
import cn.product.treasuremall.entity.FrontUserRechargeAmountSet;
import cn.product.treasuremall.mapper.FrontUserRechargeAmountSetMapper;

@Component
public class FrontUserRechargeAmountSetDaoImpl implements FrontUserRechargeAmountSetDao{
	
	@Autowired
	private FrontUserRechargeAmountSetMapper frontUserRechargeAmountSetMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserRechargeAmountSetMapper.getCountByParams(params);
	}
	
	@Override
	@Cacheable(value = "listPageAmountSet")
	public List<FrontUserRechargeAmountSet> getListByParams(Map<String, Object> params) {
		return frontUserRechargeAmountSetMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserRechargeAmountSet",key="'frontUserRechargeAmountSet:' + #key")
	public FrontUserRechargeAmountSet get(String key) {
		return frontUserRechargeAmountSetMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listPageAmountSet", allEntries = true)})
	public int insert(FrontUserRechargeAmountSet frontUserRechargeAmountSet) {
		return frontUserRechargeAmountSetMapper.insert(frontUserRechargeAmountSet);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserRechargeAmountSet", key = "'frontUserRechargeAmountSet:' + #key")
		, @CacheEvict(value = "listPageAmountSet", allEntries = true)})
	public int delete(String key) {
		return frontUserRechargeAmountSetMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserRechargeAmountSet", key = "'frontUserRechargeAmountSet:' + #frontUserRechargeAmountSet.uuid")
	, @CacheEvict(value = "listPageAmountSet", allEntries = true)})
	public int update(FrontUserRechargeAmountSet frontUserRechargeAmountSet) {
		return frontUserRechargeAmountSetMapper.updateByPrimaryKey(frontUserRechargeAmountSet);
	}
}
