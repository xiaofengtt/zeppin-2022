package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserRanklist;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserRanklistMapper extends MyMapper<FrontUserRanklist> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserRanklist> getListByParams(Map<String, Object> params);
	
	public void truncate();
	
	public void insertInfoList();
}