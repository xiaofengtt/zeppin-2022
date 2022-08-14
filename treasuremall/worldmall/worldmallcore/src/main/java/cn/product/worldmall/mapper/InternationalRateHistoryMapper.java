package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalRateHistory;
import cn.product.worldmall.util.MyMapper;

public interface InternationalRateHistoryMapper extends MyMapper<InternationalRateHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InternationalRateHistory> getListByParams(Map<String, Object> params);
	
	public List<InternationalRateHistory> getLessInfoListByParams(Map<String, Object> params);
	
	public void updateInfo(Map<String, Object> params);
}