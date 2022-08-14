package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoTeamCoaches;

/**
 * Created by lyt.
 */
public interface InfoTeamCoachesDao extends IDao<InfoTeamCoaches>{
	
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
	public List<InfoTeamCoaches> getListByParams(Map<String, Object> params);
}
