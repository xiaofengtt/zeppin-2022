package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Team;
import cn.product.score.util.MyMapper;

public interface TeamMapper extends MyMapper<Team> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Team> getListByParams(Map<String, Object> params);
}