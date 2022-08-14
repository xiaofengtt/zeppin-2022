package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Category;
import cn.product.score.entity.Team;

public interface TeamDao extends IDao<Team>{
	
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
	public List<Team> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param team
	 * @return
	 */
	public void insertTeam(Team team);
	
	/**
	 * 根据参数获取列表
	 * @param team
	 * @return
	 */
	public void updateTeam(Team team);
	
	/**
	 * 根据参数获取列表
	 * @param team
	 * @return
	 */
	public void deleteTeam(Team team);
	
	/**
	 * 批处理接口返回数据
	 * @param data
	 * @param cat
	 */
	public void batchProcess(Map<String, Object> data, Category cat);
}
