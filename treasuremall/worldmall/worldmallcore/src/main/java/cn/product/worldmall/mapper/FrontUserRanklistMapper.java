package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserRanklist;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserRanklistMapper extends MyMapper<FrontUserRanklist> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserRanklist> getListByParams(Map<String, Object> params);
	
	public void truncate();
	
	public void insertInfoList();
}