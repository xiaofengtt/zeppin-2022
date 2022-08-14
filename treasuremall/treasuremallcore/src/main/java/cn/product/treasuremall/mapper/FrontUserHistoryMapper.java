package cn.product.treasuremall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserHistoryMapper extends MyMapper<FrontUserHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserHistory> getListByParams(Map<String, Object> params);
	
	public BigDecimal getAmountByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserHistory> getLeftListByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getMonthListByParams(Map<String, Object> params);
}