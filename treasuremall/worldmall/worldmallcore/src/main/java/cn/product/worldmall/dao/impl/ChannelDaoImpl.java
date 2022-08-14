package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.ChannelDao;
import cn.product.worldmall.entity.Channel;
import cn.product.worldmall.mapper.ChannelMapper;

@Component
public class ChannelDaoImpl implements ChannelDao{

	@Autowired
    private ChannelMapper channelMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return channelMapper.getCountByParams(params);
	}
	
    @Override
    public List<Channel> getListByParams(Map<String, Object> params){
        return channelMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="channel",key="'channel:' + #key")
	public Channel get(String key) {
		return channelMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channel", key = "'channel:' + #channel.channelId")})
	public int insert(Channel channel) {
		return channelMapper.insert(channel);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channel", key = "'channel:' + #key")})
	public int delete(String key) {
		return channelMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channel", key = "'channel:' + #channel.uuid")
	,@CacheEvict(value = "channel", key = "'channel:' + #channel.channelId")})
	public int update(Channel channel) {
		return channelMapper.updateByPrimaryKey(channel);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "channel", allEntries = true)})
	public void updateIsDefault(Channel channel) {
		this.channelMapper.updateIsDefault();
		this.channelMapper.updateByPrimaryKey(channel);
	}

	@Override
	@Cacheable(cacheNames="channel",key="'channel:' + #channelId")
	public Channel getById(String channelId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelId", channelId);
		List<Channel> list = this.channelMapper.getListByParams(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Cacheable(cacheNames="channel",key="'channel:' + #defaultId")
	public Channel getDefault(String defaultId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDefault", 1);
		List<Channel> list = this.channelMapper.getListByParams(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}		
	}

}
