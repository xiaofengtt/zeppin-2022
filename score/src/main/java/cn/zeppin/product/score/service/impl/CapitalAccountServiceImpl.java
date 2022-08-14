package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.mapper.CapitalAccountMapper;
import cn.zeppin.product.score.service.CapitalAccountService;

@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService{
	
	@Autowired
	private CapitalAccountMapper capitalAccountMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return capitalAccountMapper.getCountByParams(params);
	}
	
	@Override
	public List<CapitalAccount> getListByParams(Map<String, Object> params) {
		return capitalAccountMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="capitalAccount",key="'capitalAccount:' + #key")
	public CapitalAccount get(String key) {
		return capitalAccountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CapitalAccount capitalAccount) {
		return capitalAccountMapper.insert(capitalAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccount", key = "'capitalAccount:' + #key")})
	public int delete(String key) {
		return capitalAccountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccount", key = "'capitalAccount:' + #capitalAccount.uuid")})
	public int update(CapitalAccount capitalAccount) {
		return capitalAccountMapper.updateByPrimaryKey(capitalAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccount", allEntries = true)})
	public void dailyRefrush() {
		capitalAccountMapper.dailyRefrush();
	}
}
