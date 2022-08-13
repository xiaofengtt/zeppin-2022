package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Category;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SubjectRetrieve;

public interface ISubjectRetrieveDAO extends IBaseDAO<SubjectRetrieve, Integer> {
	
	/**
	 * 获取 学科的检索分类
	 * @param map
	 * @return
	 */
	public List<SubjectRetrieve> getSubjectRetrieves(Map<String, Object> map);

	/**
	 * 删除
	 * @param subject
	 * @param srs
	 */
	public void delSubjectRetrieves(int subjectId);

	/**
	 * 获取用户可添加关注的学科列表
	 * @param ssoUser
	 * @param category
	 * @param retrieves
	 * @return
	 */
	public List<Map<String, Object>> getSubjectRetrieves(SsoUser ssoUser,
			Category category, List<Retrieve> retrieves);

}
