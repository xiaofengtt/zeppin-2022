package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserCheckinHistory;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserCheckinHistoryMapper extends MyMapper<FrontUserCheckinHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserCheckinHistory> getListByParams(Map<String, Object> params);
	
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
	public List<FrontUserCheckinHistory> getGroupListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserCheckinHistory> getLeftListByParams(Map<String, Object> params);
}