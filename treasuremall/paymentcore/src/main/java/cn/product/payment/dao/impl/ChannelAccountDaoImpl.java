package cn.product.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.mapper.ChannelAccountMapper;

@Component
public class ChannelAccountDaoImpl implements ChannelAccountDao{
	
	@Autowired
	private ChannelAccountMapper channelAccountMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return channelAccountMapper.getCountByParams(params);
	}
	
	@Override
	public List<ChannelAccount> getListByParams(Map<String, Object> params) {
		return channelAccountMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="channelAccount",key="'channelAccount:' + #key")
	public ChannelAccount get(String key) {
		return channelAccountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(ChannelAccount channelAccount) {
		return channelAccountMapper.insert(channelAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channelAccount", key = "'channelAccount:' + #key")})
	public int delete(String key) {
		return channelAccountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channelAccount", key = "'channelAccount:' + #channelAccount.uuid")})
	public int update(ChannelAccount channelAccount) {
		return channelAccountMapper.updateByPrimaryKey(channelAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "channelAccount", allEntries = true)})
	public void rebootAllSuspend() {
		this.channelAccountMapper.rebootAllSuspend();
	}
}
