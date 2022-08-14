package cn.product.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.StatisticsChannelDao;
import cn.product.payment.entity.StatisticsChannel;
import cn.product.payment.mapper.StatisticsChannelMapper;

@Component
public class StatisticsChannelDaoImpl implements StatisticsChannelDao{
	
	@Autowired
	private StatisticsChannelMapper statisticsChannelMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return statisticsChannelMapper.getCountByParams(params);
	}
	
	@Override
	public List<StatisticsChannel> getListByParams(Map<String, Object> params) {
		return statisticsChannelMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="statisticsChannel",key="'statisticsChannel:' + #key")
	public StatisticsChannel get(String key) {
		return statisticsChannelMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(StatisticsChannel statisticsChannel) {
		return statisticsChannelMapper.insert(statisticsChannel);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "statisticsChannel", key = "'statisticsChannel:' + #key")})
	public int delete(String key) {
		return statisticsChannelMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "statisticsChannel", key = "'statisticsChannel:' + #statisticsChannel.uuid")})
	public int update(StatisticsChannel statisticsChannel) {
		return statisticsChannelMapper.updateByPrimaryKey(statisticsChannel);
	}
	
	@Override
	public void statisticsDaily(String dailyDate) {
		statisticsChannelMapper.statisticsDaily(dailyDate);
	}
}
