package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.InvigilationTeacherOld;
import cn.zeppin.entity.SysUser;

/**
 * ClassName: IInvigilationTeacherService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IInvigilationTeacherService {

	/**
	 * 通过id获取资源
	 * 
	 * @param id
	 * @return
	 */
	InvigilationTeacher getById(int id);

	/**
	 * 添加资源
	 * 
	 * @param InvigilationTeacher
	 * @return
	 */
	InvigilationTeacher add(InvigilationTeacher InvigilationTeacher);

	/**
	 * @param InvigilationTeacher
	 */
	void update(InvigilationTeacher InvigilationTeacher);

	/**
	 * @param id
	 */
	void delById(int id);

	/**
	 * 根据指定条件获取列表
	 * 
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param length
	 * @param role
	 * @return
	 */
	public List<InvigilationTeacher> searchInvigilationTeacher(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length);

	/**
	 * 根据指定条件获取列表
	 * 
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param length
	 * @param role
	 * @return
	 */
	public List<Object[]> searchInvigilationTeacherBySql(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length);
	
	/**
	 * 根据指定条件获取数目
	 * 
	 * @param searchMap
	 * @return
	 */
	public int searchInvigilationTeacherCount(Map<String, Object> searchMap);

	/**
	 * 根据OPENID获取教师信息，openID唯一
	 * 
	 * @param OpenID
	 * @return
	 */
	public InvigilationTeacher getInvigilationTeacherInfoByOpenID(String openID);

	/**
	 * 根据登录名获取教师
	 * 
	 * @param loginname
	 * @return
	 */
	public InvigilationTeacher getTeacher(String loginname);

	/**
	 * 通过身份证号 获取老用户信息
	 * 
	 * @param idcard
	 * @return
	 */
	public InvigilationTeacherOld getTeacherOldByIdcard(String idcard);

	/**
	 * 通过教师id永久删除教师数据
	 * 
	 * @param tid
	 * @return
	 */
	public InvigilationTeacher deleteById(InvigilationTeacher t);
	
	/**
	 *  批量停用
	 * @param id 逗号间隔
	 * @return
	 */
	public void updateDisable(String id, String exam,String reason,int type,int creator);
	
	/**
	 *  启用
	 * @param id
	 */
	public void updateResume(int id,String exam);
	
	
	/**
	 * 根据指定条件停用数据
	 * 
	 * @param searchMap
	 * @return
	 */
	public int updateStopInvigilationTeacher(Map<String, Object> searchMap);

	public int updateChangeStatus(String ids,short status,String reason, SysUser currentUser);
	
	public void  deleteBatch(String id);
}
