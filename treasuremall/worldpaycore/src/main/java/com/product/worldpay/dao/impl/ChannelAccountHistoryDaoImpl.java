package com.product.worldpay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.product.worldpay.dao.ChannelAccountHistoryDao;
import com.product.worldpay.entity.ChannelAccountHistory;
import com.product.worldpay.mapper.ChannelAccountHistoryMapper;

@Component
public class ChannelAccountHistoryDaoImpl implements ChannelAccountHistoryDao{
	
	@Autowired
	private ChannelAccountHistoryMapper channelAccountHistoryMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return channelAccountHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<ChannelAccountHistory> getListByParams(Map<String, Object> params) {
		return channelAccountHistoryMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="channelAccountHistory",key="'channelAccountHistory:' + #key")
	public ChannelAccountHistory get(String key) {
		return channelAccountHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(ChannelAccountHistory channelAccountHistory) {
		return channelAccountHistoryMapper.insert(channelAccountHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channelAccountHistory", key = "'channelAccountHistory:' + #key")})
	public int delete(String key) {
		return channelAccountHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channelAccountHistory", key = "'channelAccountHistory:' + #channelAccountHistory.uuid")})
	public int update(ChannelAccountHistory channelAccountHistory) {
		return channelAccountHistoryMapper.updateByPrimaryKey(channelAccountHistory);
	}
}
