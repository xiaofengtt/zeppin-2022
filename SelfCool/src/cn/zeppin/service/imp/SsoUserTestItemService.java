package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoUserTestItemDAO;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.api.ISsoUserTestItemService;
import cn.zeppin.utility.Dictionary;

public class SsoUserTestItemService implements ISsoUserTestItemService {

	private ISsoUserTestItemDAO ssoUserTestItemDAO;

	public ISsoUserTestItemDAO getSsoUserTestItemDAO() {
		return ssoUserTestItemDAO;
	}

	public void setSsoUserTestItemDAO(ISsoUserTestItemDAO ssoUserTestItemDAO) {
		this.ssoUserTestItemDAO = ssoUserTestItemDAO;
	}

	/**
	 * 添加用户答题记录
	 */
	@Override
	public void addUserTestItem(SsoUserTestItem uti) {

		this.getSsoUserTestItemDAO().save(uti);

	}
//	
	/**
	 * 获取个数
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int getUserTestItemCountByMap(Map<String, Object> map){
		return this.getSsoUserTestItemDAO().getUserTestItemCountByMap(map);
	}
//
//	/**
//	 * 获取列表
//	 * @param map
//	 * @param offset
//	 * @param length
//	 * @return
//	 */
//	@Override
//	public List<SsoUserTestItem> getUserTestItemsByMap(Map<String, Object> map, int offset, int length){
//		return this.getSsoUserTestItemDAO().getUserTestItemsByMap(map, offset, length);
//	}

	
	/**
	 * 该学科用户刷题次总数（量）
	 * 刷题次数并不关心试题的状态是否发布或停用，只要用户能做、做过，就计算入内
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public Integer calculateUserTotalTestItemCount(SsoUser currentUser,
			Subject subject, Short isStandard) {
		// TODO Auto-generated method stub
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("user.id", currentUser.getId());
		searchMap.put("subject.id", subject.getId());
		searchMap.put("itemType.isStandard", isStandard);
		searchMap.put("ssoUserTest.status",Dictionary.USER_TEST_STATUS_COMPLETE);
		return this.getSsoUserTestItemDAO().getUserTestItemCountByMap(searchMap);
	}

	/**
	 * 该学科用户做正确次总数
	 * 刷题次数并不关心试题的状态是否发布或停用，只要用户能做、做过，就计算入内
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public Integer calculateUserTotalRightItemCount(SsoUser currentUser,
			Subject subject, Short isStandard) {
		// TODO Auto-generated method stub
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("user.id", currentUser.getId());
		searchMap.put("subject.id", subject.getId());
		searchMap.put("itemType.isStandard", isStandard);
		searchMap.put("completeType",Dictionary.SSO_USER_TEST_ITEM_CORRECT);
		searchMap.put("ssoUserTest.status",Dictionary.USER_TEST_STATUS_COMPLETE);
		return this.getSsoUserTestItemDAO().getUserTestItemCountByMap(searchMap);
	}

	/**
	 * 该学科用户正确题总数（每个试题只计算最后一次做题是否正确）
	 * 正确题总数关心试题的状态是否发布或停用，计算进度时不能超过总（已发布）的题数
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public Integer calculateUserLastTotalRightItemCount(SsoUser currentUser,
			Subject subject, Short isStandard) {
		// TODO Auto-generated method stub
		return this.getSsoUserTestItemDAO().calculateUserLastTotalRightItemCount(currentUser, subject, isStandard);
	}

	/**
	 * 该学科用户正确题总数按题型分组（每个试题只计算最后一次做题是否正确）
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public List<Map<String,Object>> calculateUserLastTotalRightItemCountGroupByItemType(SsoUser currentUser, Subject subject){
		return this.getSsoUserTestItemDAO().calculateUserLastTotalRightItemCountGroupByItemType(currentUser, subject);
	}
}
