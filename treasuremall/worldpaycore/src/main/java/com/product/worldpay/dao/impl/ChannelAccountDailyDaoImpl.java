package com.product.worldpay.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.product.worldpay.dao.ChannelAccountDailyDao;
import com.product.worldpay.entity.ChannelAccountDaily;
import com.product.worldpay.mapper.ChannelAccountDailyMapper;

@Component
public class ChannelAccountDailyDaoImpl implements ChannelAccountDailyDao{
	
	@Autowired
	private ChannelAccountDailyMapper channelAccountDailyMapper;
    
    @Override
	public ChannelAccountDaily get(String key) {
		return channelAccountDailyMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(ChannelAccountDaily channelAccountDaily) {
		return channelAccountDailyMapper.insert(channelAccountDaily);
	}

	@Override
	public int delete(String key) {
		return channelAccountDailyMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int update(ChannelAccountDaily channelAccountDaily) {
		return channelAccountDailyMapper.updateByPrimaryKey(channelAccountDaily);
	}

	@Override
	public void clearAll() {
		channelAccountDailyMapper.clearAll();
	}
}
