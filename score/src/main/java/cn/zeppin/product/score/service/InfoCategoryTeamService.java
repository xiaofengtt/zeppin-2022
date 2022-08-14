package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.InfoCategoryTeam;

public interface InfoCategoryTeamService extends IService<InfoCategoryTeam>{
	
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
	public List<InfoCategoryTeam> getListByParams(Map<String, Object> params);
	
	/**
	 * 删除球队赛事关联信息
	 * @param team
	 * @return
	 */
	public void deleteByTeam(String team);
}
