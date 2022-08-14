package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoMapper extends MyMapper<ActivityInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<ActivityInfo> getListByParams(Map<String, Object> params);
}