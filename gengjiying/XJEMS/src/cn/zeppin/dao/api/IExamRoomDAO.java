package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ExamRoom;

/**
 * ClassName: IExamRoomDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IExamRoomDAO extends IBaseDAO<ExamRoom, Integer> {

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
	public List<ExamRoom> searchExamRoom(Map<String, Object> searchMap, Map<String, String> sortParams, int offset, int length);

	/**
	 * 根据指定条件获取数目
	 * @param searchMap
	 * @return
	 */
	public int searchExamRoomCount(Map<String, Object> searchMap);
	
	public List<Object[]> searchByGroup(Map<String, Object> searchMap);
	
	/**
	 *  根据考场的id更新每个考场对应的监考注意事项
	 * @param invigilationNotice
	 */
	public void updateInvigilationNotice(Map<String, Object> searchMap, String invigilationNotice);
}
