package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.FrontUserConcern;
import cn.zeppin.product.score.mapper.FrontUserConcernMapper;
import cn.zeppin.product.score.service.FrontUserConcernService;

@Service("frontUserConcernService")
public class FrontUserConcernServiceImpl implements FrontUserConcernService{
	
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
