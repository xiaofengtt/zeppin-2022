package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontUserBlacklistDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBlacklist;
import cn.product.worldmall.mapper.FrontUserBlacklistMapper;

/**
 */
@Component
public class FrontUserBlacklistDaoImpl implements FrontUserBlacklistDao{
	
	private final static Logger log = LoggerFactory.getLogger(FrontUserBlacklistDaoImpl.class);
	
	@Autowired
	private FrontUserBlacklistMapper frontUserBlacklistMapper;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserBlacklistMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserBlacklist> getListByParams(Map<String, Object> params) {
		return frontUserBlacklistMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserBlacklist",key="'frontUserBlacklist:' + #key")
	public FrontUserBlacklist get(String key) {
		return frontUserBlacklistMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserBlacklist frontUserBlacklist) {
		return frontUserBlacklistMapper.insert(frontUserBlacklist);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBlacklist", key = "'frontUserBlacklist:' + #key")})
	public int delete(String key) {
		return frontUserBlacklistMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBlacklist", key = "'frontUserBlacklist:' + #frontUserBlacklist.uuid")})
	public int update(FrontUserBlacklist frontUserBlacklist) {
		return frontUserBlacklistMapper.updateByPrimaryKey(frontUserBlacklist);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBlacklist", key = "'frontUserBlacklist:' + #frontUserBlacklist.uuid")})
	public void delete(FrontUserBlacklist frontUserBlacklist, FrontUser fu) {
		this.frontUserDao.update(fu);
		this.frontUserBlacklistMapper.delete(frontUserBlacklist);
	}
}
