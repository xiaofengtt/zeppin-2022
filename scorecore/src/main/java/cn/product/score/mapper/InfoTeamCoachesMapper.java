package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoTeamCoaches;
import cn.product.score.util.MyMapper;

public interface InfoTeamCoachesMapper extends MyMapper<InfoTeamCoaches> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoTeamCoaches> getListByParams(Map<String, Object> params);
}