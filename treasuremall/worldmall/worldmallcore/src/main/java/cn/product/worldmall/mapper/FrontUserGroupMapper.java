package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserGroup;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserGroupMapper extends MyMapper<FrontUserGroup> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserGroup> getListByParams(Map<String, Object> params);
}