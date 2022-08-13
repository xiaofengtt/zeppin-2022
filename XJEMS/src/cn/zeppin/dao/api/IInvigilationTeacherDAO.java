package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.InvigilationTeacher;

/**
 * ClassName: IInvigilationTeacherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IInvigilationTeacherDAO extends IBaseDAO<InvigilationTeacher, Integer> {

	/**
	 * 根据指定条件获取列表
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param length
	 * @param role
	 * @return
	 */
	public List<InvigilationTeacher> searchInvigilationTeacher(Map<String, Object> searchMap, Map<String, String> sortParams, int offset, int length);

	/**
	 * 根据指定条件获取数目
	 * @param searchMap
	 * @return
	 */
	public int searchInvigilationTeacherCount(Map<String, Object> searchMap);
	
	/**
	 * 根据OPENID获取教师信息，openID唯一
	 * @param OpenID
	 * @return
	 */
	public InvigilationTeacher getInvigilationTeacherInfoByOpenID(String openID);
	
	/**
	 * 根据登录名获取教师
	 * @param loginname
	 * @return
	 */
	public InvigilationTeacher getTeacher(String loginname);
}
