package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.UserSubject;

public interface IUserSubjectService {
	
	/**
	 * 添加用户学科
	 * @param us
	 */
	public UserSubject addUserSubject(UserSubject us);
	
	/**
	 * 更新
	 * @param us
	 */
	public UserSubject updateUserSubject(UserSubject us);
	
	/**
	 * 删除
	 * @param us
	 */
	public UserSubject deleteUserSubject(UserSubject us);
	

	/**
	 * 获取用户绑定学科个数
	 * 
	 * @param map
	 * @return
	 */
	public int getUserSubjectCount(Map<String, Object> map);

	/**
	 * 获取用户绑定学科
	 * 
	 * @param map
	 * @return
	 */
	public List<UserSubject> getUserSubjectsByMap(Map<String, Object> map, String sort, int offset, int length);

	/**
	 * 获取用户关注学科
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> getUserSubjects(Integer uid);

	/**
	 * 保存用户关注学科
	 * @param ssoUser
	 * @param subjectIDs
	 */
	public void saveUserSubjects(SsoUser ssoUser, Integer[] subjectIDs);

	/**
	 * 通过唯一索引获取用户关注学科
	 * @param uid
	 * @param subjectId
	 * @return
	 */
	public UserSubject getByKey(int uid, int subjectId);

	/**
	 * 获取用户在某个学科下载备考进度排名
	 * @param id
	 * @param id2
	 * @return
	 */
	public int getUserSubjectTestProgressRanking(Integer uid, Integer subjectId);

	/**
	 * 获取用户该学科相关题型信息
	 * @param ssoUser
	 * @param subject
	 * @param isStandard
	 * @return
	 */
	public List<Map<String, Object>> getUserSubjectItemType(SsoUser ssoUser, Subject subject, Integer isStandard);

}
