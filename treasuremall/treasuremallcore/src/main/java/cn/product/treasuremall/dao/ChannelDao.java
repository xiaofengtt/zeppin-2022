package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Channel;

public interface ChannelDao extends IDao<Channel> {
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<Channel> getListByParams(Map<String, Object> params);
	
	/**
	 * 设置默认
	 * @param channel
	 */
	public void updateIsDefault(Channel channel);
	
	/**
	 * 根据ID获取渠道
	 * @param channelId
	 */
	public Channel getById(String channelId);
	
	/**
	 * 获取默认渠道信息
	 * @param defaultId
	 */
	public Channel getDefault(String defaultId);
}
