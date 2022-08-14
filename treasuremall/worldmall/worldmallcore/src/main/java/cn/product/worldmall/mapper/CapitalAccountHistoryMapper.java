package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CapitalAccountHistory;
import cn.product.worldmall.util.MyMapper;
import cn.product.worldmall.vo.back.StatusCountVO;

public interface CapitalAccountHistoryMapper extends MyMapper<CapitalAccountHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccountHistory> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getTypeList(Map<String, Object> params);
}