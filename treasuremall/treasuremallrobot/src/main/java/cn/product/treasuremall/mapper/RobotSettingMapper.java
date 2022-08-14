package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.RobotSetting;
import cn.product.treasuremall.util.MyMapper;

public interface RobotSettingMapper extends MyMapper<RobotSetting> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<RobotSetting> getListByParams(Map<String, Object> params);
	
	public void updateStatus(Map<String, Object> params);
}