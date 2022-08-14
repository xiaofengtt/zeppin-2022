package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoPlayers;
import cn.product.score.util.MyMapper;

public interface InfoPlayersMapper extends MyMapper<InfoPlayers> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoPlayers> getListByParams(Map<String, Object> params);
}