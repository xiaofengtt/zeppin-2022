package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.FrontUserRanklistDao;
import cn.product.treasuremall.entity.FrontUserRanklist;
import cn.product.treasuremall.mapper.FrontUserRanklistMapper;

@Component
public class FrontUserRanklistDaoImpl implements FrontUserRanklistDao{
	
	@Autowired
	private FrontUserRanklistMapper frontUserRanklistMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserRanklistMapper.getCountByParams(params);
	}
	
	@Override
	@Cacheable(value = "listPageRanklist")
	public List<FrontUserRanklist> getListByParams(Map<String, Object> params) {
		return frontUserRanklistMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserRanklist",key="'frontUserRanklist:' + #key")
	public FrontUserRanklist get(String key) {
		return frontUserRanklistMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserRanklist frontUserRanklist) {
		return frontUserRanklistMapper.insert(frontUserRanklist);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserRanklist", key = "'frontUserRanklist:' + #key")})
	public int delete(String key) {
		return frontUserRanklistMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserRanklist", key = "'frontUserRanklist:' + #frontUserRanklist.frontUser")})
	public int update(FrontUserRanklist frontUserRanklist) {
		return frontUserRanklistMapper.updateByPrimaryKey(frontUserRanklist);
	}

	@Override
	public void truncate() {
		this.frontUserRanklistMapper.truncate();
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listPageRanklist", allEntries=true),@CacheEvict(value = "frontUserRanklist", allEntries=true)})
	public void insertInfoList() {
		this.frontUserRanklistMapper.insertInfoList();		
	}

	
}
