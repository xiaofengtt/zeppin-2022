package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.mapper.FrontUserAccountMapper;

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
	
	@Cacheable(cacheNames="frontUserAccountByFrontUser",key="'frontUserAccountByFrontUser:' + #frontUser")
	public FrontUserAccount getByFrontUser(String frontUser) {
		return frontUserAccountMapper.getByFrontUser(frontUser);
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
	@Caching(evict={@CacheEvict(value = "frontUserAccount", key = "'frontUserAccount:' + #frontUserAccount.uuid"),
			@CacheEvict(value = "frontUserAccountByFrontUser", key = "'frontUserAccountByFrontUser:' + #frontUserAccount.frontUser")})
	public int update(FrontUserAccount frontUserAccount) {
		return frontUserAccountMapper.updateByPrimaryKey(frontUserAccount);
	}
}
