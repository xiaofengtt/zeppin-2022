package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.util.MyMapper;

public interface InfoPlayersMapper extends MyMapper<InfoPlayers> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoPlayers> getListByParams(Map<String, Object> params);
}