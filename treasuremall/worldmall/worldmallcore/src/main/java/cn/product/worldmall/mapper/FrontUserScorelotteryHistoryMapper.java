package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserScorelotteryHistory;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserScorelotteryHistoryMapper extends MyMapper<FrontUserScorelotteryHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserScorelotteryHistory> getListByParams(Map<String, Object> params);
	
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
	public List<FrontUserScorelotteryHistory> getGroupListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserScorelotteryHistory> getLeftListByParams(Map<String, Object> params);
}