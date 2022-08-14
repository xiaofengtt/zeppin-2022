package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserHistory;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.StatusCountVO;

public interface FrontUserHistoryMapper extends MyMapper<FrontUserHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserHistory> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
}