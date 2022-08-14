package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.util.MyMapper;

public interface FrontDeviceTokenMapper extends MyMapper<FrontDeviceToken> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontDeviceToken> getListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontDeviceToken> getLeftListByParams(Map<String, Object> params);
	
	public List<FrontDeviceToken> getFrontUserGroupDfList();
	
}