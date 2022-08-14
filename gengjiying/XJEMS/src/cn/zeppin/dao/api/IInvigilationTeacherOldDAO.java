package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.InvigilationTeacherOld;

/**
 * ClassName: IInvigilationTeacherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IInvigilationTeacherOldDAO extends IBaseDAO<InvigilationTeacherOld, Integer> {

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
	public InvigilationTeacherOld getTeacherOldByIdcard(String idcard);

}
