package cn.product.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.ChannelDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.mapper.ChannelMapper;

@Component
public class ChannelDaoImpl implements ChannelDao{
	
	@Autowired
	private ChannelMapper channelMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return channelMapper.getCountByParams(params);
	}
	
	@Override
	public List<Channel> getListByParams(Map<String, Object> params) {
		return channelMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="channel",key="'channel:' + #key")
	public Channel get(String key) {
		return channelMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Channel channel) {
		return channelMapper.insert(channel);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channel", key = "'channel:' + #key")})
	public int delete(String key) {
		return channelMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channel", key = "'channel:' + #channel.uuid")})
	public int update(Channel channel) {
		return channelMapper.updateByPrimaryKey(channel);
	}
}
