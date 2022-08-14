package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.CapitalAccountHistory;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.StatusCountVO;

public interface CapitalAccountHistoryMapper extends MyMapper<CapitalAccountHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccountHistory> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getTypeList(Map<String, Object> params);
}