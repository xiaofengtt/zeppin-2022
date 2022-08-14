package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserBuyfreeOrder;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserBuyfreeOrderMapper extends MyMapper<FrontUserBuyfreeOrder> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserBuyfreeOrder> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getGroupCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserBuyfreeOrder> getGroupListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserBuyfreeOrder> getLeftListByParams(Map<String, Object> params);
}