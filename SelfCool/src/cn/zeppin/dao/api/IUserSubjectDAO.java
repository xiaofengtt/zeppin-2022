package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserSubject;

public interface IUserSubjectDAO extends IBaseDAO<UserSubject, Integer> {
	
	/**
	 * 获取用户绑定学科个数
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
	 * 取客户端学科列表，除了基本字段还包括备考进度、刷题量、正确率、剩余备考天数、击败人数百分比
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> getUserSubjects(Integer uid);

	/**
	 * 通过唯一索引主键来获取一个用户关注学科
	 * @param id
	 * @param subjectID
	 * @return
	 */
	public UserSubject getByKey(Integer uid, Integer subjectid);

	/**
	 * 获取用户在某个学科下载备考进度排名
	 * @param id
	 * @param id2
	 * @return
	 */
	public int getUserSubjectTestProgressRanking(Integer uid, Integer subjectId);

}
