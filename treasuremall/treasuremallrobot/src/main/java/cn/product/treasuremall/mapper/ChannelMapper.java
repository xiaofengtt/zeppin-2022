package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Channel;
import cn.product.treasuremall.util.MyMapper;

public interface ChannelMapper extends MyMapper<Channel> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Channel> getListByParams(Map<String, Object> params);
	
	public void updateIsDefault();
}