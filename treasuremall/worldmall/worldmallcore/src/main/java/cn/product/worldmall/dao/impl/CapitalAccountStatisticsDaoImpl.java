package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.CapitalAccountStatisticsDao;
import cn.product.worldmall.entity.CapitalAccountStatistics;
import cn.product.worldmall.mapper.CapitalAccountStatisticsMapper;

@Component
public class CapitalAccountStatisticsDaoImpl implements CapitalAccountStatisticsDao{
	
	@Autowired
	private CapitalAccountStatisticsMapper capitalAccountStatisticsMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return capitalAccountStatisticsMapper.getCountByParams(params);
	}
	
	@Override
	public List<CapitalAccountStatistics> getListByParams(Map<String, Object> params) {
		return capitalAccountStatisticsMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="capitalAccountStatistics",key="'capitalAccountStatistics:' + #key")
	public CapitalAccountStatistics get(String key) {
		return capitalAccountStatisticsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CapitalAccountStatistics capitalAccountStatistics) {
		return capitalAccountStatisticsMapper.insert(capitalAccountStatistics);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccountStatistics", key = "'capitalAccountStatistics:' + #key")})
	public int delete(String key) {
		return capitalAccountStatisticsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccountStatistics", key = "'capitalAccountStatistics:' + #capitalAccountStatistics.capitalAccount")})
	public int update(CapitalAccountStatistics capitalAccountStatistics) {
		return capitalAccountStatisticsMapper.updateByPrimaryKey(capitalAccountStatistics);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccountStatistics", allEntries = true)})
	public void dailyRefrush() {
		capitalAccountStatisticsMapper.dailyRefrush();
	}
}
