package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.FrontUserDailyDao;
import cn.product.treasuremall.entity.FrontUserDaily;
import cn.product.treasuremall.mapper.FrontUserDailyMapper;

/**
 */
@Component
public class FrontUserDailyDaoImpl implements FrontUserDailyDao{
	
	private final static Logger log = LoggerFactory.getLogger(FrontUserDailyDaoImpl.class);
	
	@Autowired
	private FrontUserDailyMapper frontUserDailyMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserDailyMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserDaily> getListByParams(Map<String, Object> params) {
		return frontUserDailyMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserDaily",key="'frontUserDaily:' + #key")
	public FrontUserDaily get(String key) {
		return frontUserDailyMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserDaily", key = "'frontUserDaily:' + #frontUserDaily.uuid")})
	public int insert(FrontUserDaily frontUserDaily) {
		return frontUserDailyMapper.insert(frontUserDaily);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserDaily", key = "'frontUserDaily:' + #key")})
	public int delete(String key) {
		return frontUserDailyMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserDaily", key = "'frontUserDaily:' + #frontUserDaily.uuid")})
	public int update(FrontUserDaily frontUserDaily) {
		return frontUserDailyMapper.updateByPrimaryKey(frontUserDaily);
	}

	@Override
	public List<Map<String, Object>> getActiveListByParams(Map<String, Object> params) {
		return frontUserDailyMapper.getActiveListByParams(params);
	}
}
