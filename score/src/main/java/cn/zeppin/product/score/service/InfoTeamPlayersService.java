package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.InfoTeamPlayers;

public interface InfoTeamPlayersService extends IService<InfoTeamPlayers>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<InfoTeamPlayers> getListByParams(Map<String, Object> params);
	
	/**
	 * 删除球队球员关联信息
	 * @param team
	 * @return
	 */
	public void deleteByTeam(String team);
}
