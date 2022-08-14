package cn.product.treasuremall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.util.MyMapper;

public interface WinningInfoMapper extends MyMapper<WinningInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<WinningInfo> getListByParams(Map<String, Object> params);
	
	public Integer getCountByRobotParams(Map<String, Object> params);
	
	public List<WinningInfo> getListByRobotParams(Map<String, Object> params);
	
	public BigDecimal getAmountByParams(Map<String, Object> params);
}

