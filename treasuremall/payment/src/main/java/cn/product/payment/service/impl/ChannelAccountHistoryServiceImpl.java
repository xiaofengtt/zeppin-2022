package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.ChannelAccountHistory;
import cn.product.payment.mapper.ChannelAccountHistoryMapper;
import cn.product.payment.service.ChannelAccountHistoryService;

@Service("channelAccountHistoryService")
public class ChannelAccountHistoryServiceImpl implements ChannelAccountHistoryService{
	
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
