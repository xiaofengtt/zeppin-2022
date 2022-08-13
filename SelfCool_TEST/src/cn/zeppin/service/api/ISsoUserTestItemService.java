package cn.zeppin.service.api;

import java.util.Map;
import java.util.List;

import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;

public interface ISsoUserTestItemService {

	/**
	 * 添加一个用户答题记录
	 * 
	 * @param uti
	 */
	public void addUserTestItem(SsoUserTestItem uti);

	/**
	 * 获取个数
	 * 
	 * @param map
	 * @return
	 */
	public int getUserTestItemCountByMap(Map<String, Object> map);

//	/**
//	 * 获取列表
//	 * @param map
//	 * @param offset
//	 * @param length
//	 * @return
//	 */
//	public List<SsoUserTestItem> getUserTestItemsByMap(Map<String, Object> map, int offset, int length);

	/**
	 * 该学科用户刷题次总数（量）
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public Integer calculateUserTotalTestItemCount(SsoUser currentUser, Subject subject, Short isStandard);

	/**
	 * 该学科用户做正确次总数
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public Integer calculateUserTotalRightItemCount(SsoUser currentUser, Subject subject, Short isStandard);

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

}
