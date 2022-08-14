package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.RobotSetting;
import cn.product.worldmall.util.MyMapper;

public interface RobotSettingMapper extends MyMapper<RobotSetting> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<RobotSetting> getListByParams(Map<String, Object> params);
	
	public void updateStatus(Map<String, Object> params);
}