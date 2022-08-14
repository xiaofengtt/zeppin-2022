package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Version;

public interface VersionDao extends IDao<Version>{
	
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
	public List<Version> getListByParams(Map<String, Object> params);
	
	/**
	 * 条件获取数据
	 * @param channel
	 * @param version
	 * @return
	 */
	public Version getByChannelVersion(String channel, String version);
}
