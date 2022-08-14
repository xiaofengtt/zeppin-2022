package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.zeppin.product.score.entity.InfoTeamPlayers;
import cn.zeppin.product.score.util.MyMapper;

public interface InfoTeamPlayersMapper extends MyMapper<InfoTeamPlayers> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoTeamPlayers> getListByParams(Map<String, Object> params);
	
	public void deleteByTeam(@Param("team")String team);
}