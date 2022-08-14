package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoCoaches;
import cn.product.score.util.MyMapper;

public interface InfoCoachesMapper extends MyMapper<InfoCoaches> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoCoaches> getListByParams(Map<String, Object> params);
}