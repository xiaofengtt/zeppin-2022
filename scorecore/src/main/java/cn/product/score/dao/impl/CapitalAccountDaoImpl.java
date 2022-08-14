package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.mapper.CapitalAccountMapper;

@Component
public class CapitalAccountDaoImpl implements CapitalAccountDao{
	
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
