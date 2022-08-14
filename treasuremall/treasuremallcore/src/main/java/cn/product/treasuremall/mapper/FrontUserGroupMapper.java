package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserGroup;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserGroupMapper extends MyMapper<FrontUserGroup> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserGroup> getListByParams(Map<String, Object> params);
}