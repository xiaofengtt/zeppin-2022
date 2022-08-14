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

import cn.product.treasuremall.dao.FrontUserGroupDao;
import cn.product.treasuremall.entity.FrontUserGroup;
import cn.product.treasuremall.mapper.FrontUserGroupMapper;

/**
 */
@Component
public class FrontUserGroupDaoImpl implements FrontUserGroupDao{
	
	private final static Logger log = LoggerFactory.getLogger(FrontUserGroupDaoImpl.class);
	
	@Autowired
	private FrontUserGroupMapper frontUserGroupMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserGroupMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserGroup> getListByParams(Map<String, Object> params) {
		return frontUserGroupMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserGroup",key="'frontUserGroup:' + #key")
	public FrontUserGroup get(String key) {
		return frontUserGroupMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserGroup frontUserGroup) {
		return frontUserGroupMapper.insert(frontUserGroup);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserGroup", key = "'frontUserGroup:' + #key")})
	public int delete(String key) {
		return frontUserGroupMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserGroup", key = "'frontUserGroup:' + #frontUserGroup.name")})
	public int update(FrontUserGroup frontUserGroup) {
		return frontUserGroupMapper.updateByPrimaryKey(frontUserGroup);
	}
}
