package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CapitalAccountHistory;
import cn.product.treasuremall.util.MyMapper;
import cn.product.treasuremall.vo.back.StatusCountVO;

public interface CapitalAccountHistoryMapper extends MyMapper<CapitalAccountHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccountHistory> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getTypeList(Map<String, Object> params);
}