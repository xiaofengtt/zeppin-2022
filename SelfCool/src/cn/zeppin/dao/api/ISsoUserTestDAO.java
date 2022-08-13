package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTest;
import cn.zeppin.entity.Subject;

public interface ISsoUserTestDAO extends IBaseDAO<SsoUserTest, Long> {
	
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
	 * 获取用户错题本的答题SsoUserTest
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public SsoUserTest getWrongbookUserTest(SsoUser currentUser, Subject subject);
	
}
