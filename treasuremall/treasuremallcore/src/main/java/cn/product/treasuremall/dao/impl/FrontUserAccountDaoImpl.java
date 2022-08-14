package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.mapper.FrontUserAccountMapper;

@Component
public class FrontUserAccountDaoImpl implements FrontUserAccountDao{
	
	@Autowired
	private FrontUserAccountMapper frontUserAccountMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserAccountMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserAccount> getListByParams(Map<String, Object> params) {
		return frontUserAccountMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserAccount",key="'frontUserAccount:' + #key")
	public FrontUserAccount get(String key) {
		return frontUserAccountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserAccount frontUserAccount) {
		return frontUserAccountMapper.insert(frontUserAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAccount", key = "'frontUserAccount:' + #key"),
			@CacheEvict(value = "frontUserAccountByFrontUser", allEntries=true)})
	public int delete(String key) {
		return frontUserAccountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAccount", key = "'frontUserAccount:' + #frontUserAccount.frontUser", beforeInvocation = true),
			@CacheEvict(value = "frontUserAccountByFrontUser", key = "'frontUserAccountByFrontUser:' + #frontUserAccount.frontUser", beforeInvocation = true)})
	public int update(FrontUserAccount frontUserAccount) {
		return frontUserAccountMapper.updateByPrimaryKey(frontUserAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAccount", allEntries=true)})
	@Transactional
	public void updateStatus(Map<String, Object> params) {
		frontUserAccountMapper.updateStatus(params);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAccount", key = "'frontUserAccount:' + #frontUserAccount.frontUser", beforeInvocation = true)})
	@Transactional
	public void updateInfo(FrontUserAccount frontUserAccount, Map<String, Object> params) {
		this.frontUserAccountMapper.updateInfo(params);
	}
	
}
