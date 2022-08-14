package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.FrontUserConcernDao;
import cn.product.score.entity.FrontUserConcern;
import cn.product.score.mapper.FrontUserConcernMapper;

@Component
public class FrontUserConcernDaoImpl implements FrontUserConcernDao{
	
	@Autowired
	private FrontUserConcernMapper frontUserConcernMapper;
	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserConcernMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserConcern> getListByParams(Map<String, Object> params) {
		return frontUserConcernMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="frontUserConcern",key="'frontUserConcern:' + #key")
	public FrontUserConcern get(String key) {
		return frontUserConcernMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserConcern frontUserConcern) {
		return frontUserConcernMapper.insert(frontUserConcern);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserConcern", key = "'frontUserConcern:' + #key")})
	public int delete(String key) {
		return frontUserConcernMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserConcern", key = "'frontUserConcern:' + #frontUserConcern.uuid")})
	public int update(FrontUserConcern frontUserConcern) {
		return frontUserConcernMapper.updateByPrimaryKey(frontUserConcern);
	}
}
