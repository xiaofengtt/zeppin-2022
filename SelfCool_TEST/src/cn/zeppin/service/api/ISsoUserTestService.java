package cn.zeppin.service.api;

import java.util.Map;
import java.util.List;

import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTest;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;

public interface ISsoUserTestService {
	
	
	/**
	 * 添加一个用户考试记录（答题）
	 * @param sut
	 * @return
	 */
	public void addUserTest(SsoUserTest sut);
	
	/**
	 * 更新
	 * @param sut
	 */
	public void updateUserTest(SsoUserTest sut);
	
	/**
	 * 获取 考试记录
	 * @param id
	 * @return
	 */
	public SsoUserTest getUserTestById(Long id);
	
	/**
	 * 获取 用户答题记录数
	 * @param map
	 * @return
	 */
	public int getUserTestCount(Map<String,Object> map);
	
	/**
	 * 获取用户答题记录
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoUserTest> getUserTestByMap(Map<String,Object> map,String sorts,int offset,int length);

	/**
	 * 根据试题列表生成一次智能出题的考试
	 * @param subject
	 * @param knowledge
	 * @param currentUser
	 * @param selectItems
	 * @param split 
	 * @return autoPaperMap
	 */
	public Map<String, Object> createAutoTest(Subject subject, SsoUser currentUser, List<Map<String, Object>> selectItems, String split);

	
	/**
	 * 保存自动化试卷
	 * @param ssoUserTest
	 * @param answers
	 */
	@SuppressWarnings("rawtypes")
	public List<SsoUserTestItem> saveSsoUserAutoTest(SsoUserTest ssoUserTest, List<Map> answers);

	
	/**
	 * 本次做题一级知识点掌握情况变化,每个试题只计算最后一次做题是否正确（暂以正确题数增减为主）
	 * @param subject 
	 */
	// knowledge.id(一级)
	// knowledge.totalItemCount
	// knowledge.userTestItemCount
	// knowledge.userTestRightItemCount
	// knowledge.userTestRightItemIncreaseCount
	public List<Map<String, Object>> calculateKnowledgeTestChange(SsoUser ssoUser, 
			List<SsoUserTestItem> ssoUserTestItemList, Subject subject, String split);
	
}
