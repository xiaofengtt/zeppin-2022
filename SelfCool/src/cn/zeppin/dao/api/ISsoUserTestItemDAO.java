package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;

public interface ISsoUserTestItemDAO extends IBaseDAO<SsoUserTestItem, Long> {

	
	/**
	 * 获取个数
	 * 
	 * @param map
	 * @return
	 */
	public int getUserTestItemCountByMap(Map<String, Object> searchMap);

	/**
	 * 获取列表
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoUserTestItem> getUserTestItemsByMap(Map<String, Object> searchMap, int offset, int length);

	/**
	 * 该学科用户正确题总数（每个试题只计算最后一次做题是否正确）
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public Integer calculateUserLastTotalRightItemCount(SsoUser currentUser, Subject subject, Short isStandard);
	
	/**
	 * 该学科用户正确题总数按题型分组（每个试题只计算最后一次做题是否正确）
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public List<Map<String,Object>> calculateUserLastTotalRightItemCountGroupByItemType(SsoUser currentUser, Subject subject);

	/**
	 * 通过本次做题记录取用户上一次做题记录（用于与最后一次成绩做对比）
	 * @param ssoUserTestItem
	 * @return
	 */
	public SsoUserTestItem getPreviousSsoUserTestItem(SsoUserTestItem ssoUserTestItem);
	
	
}
