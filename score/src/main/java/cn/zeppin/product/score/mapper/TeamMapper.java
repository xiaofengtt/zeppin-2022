package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.util.MyMapper;

public interface TeamMapper extends MyMapper<Team> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Team> getListByParams(Map<String, Object> params);
}