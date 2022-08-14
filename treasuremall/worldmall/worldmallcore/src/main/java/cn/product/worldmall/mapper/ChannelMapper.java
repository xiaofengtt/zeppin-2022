package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Channel;
import cn.product.worldmall.util.MyMapper;

public interface ChannelMapper extends MyMapper<Channel> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Channel> getListByParams(Map<String, Object> params);
	
	public void updateIsDefault();
}