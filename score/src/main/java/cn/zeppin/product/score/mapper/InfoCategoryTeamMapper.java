package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.InfoCategoryTeam;
import cn.zeppin.product.score.util.MyMapper;

public interface InfoCategoryTeamMapper extends MyMapper<InfoCategoryTeam> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoCategoryTeam> getListByParams(Map<String, Object> params);
	
	public void deleteByTeam(String team);
}