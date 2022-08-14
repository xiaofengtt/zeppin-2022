package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.RobotWinningRate;
import cn.product.treasuremall.util.MyMapper;

public interface RobotWinningRateMapper extends MyMapper<RobotWinningRate> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<RobotWinningRate> getListByParams(Map<String, Object> params);
	
	public void updateStatus(Map<String, Object> params);
}