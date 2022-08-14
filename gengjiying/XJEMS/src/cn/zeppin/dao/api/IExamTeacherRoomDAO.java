package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ExamTeacherRoom;

/**
 * ClassName: IExamTeacherRoomDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IExamTeacherRoomDAO extends IBaseDAO<ExamTeacherRoom, Integer> {

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
	@SuppressWarnings("rawtypes")
	public List searchExamTeacherRoom(Map<String, Object> searchMap, Map<String, String> sortParams, int offset, int length);

	/**
	 * 根据指定条件获取数目
	 * @param searchMap
	 * @return
	 */
	public int searchExamTeacherRoomCount(Map<String, Object> searchMap);
	
	/**
	 * 根据指定条件  获取教师信息
	 * @param searchMap
	 * @param sortParams
	 * @param offset
	 * @param length
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    public List searchUndistributionTeacher(Map<String, Object> searchMap, Map<String, String> sortParams, int offset, int length);
	
	public List<ExamTeacherRoom> getByParams(Map<String, Object> searchMap);
	
	public int deleteByTeacherId(int tid);
}
