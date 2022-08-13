package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ExamRoom;

/**
 * ClassName: IExamRoomService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IExamRoomService {

	/**
	 * 通过id获取资源
	 * 
	 * @param id
	 * @return
	 */
	ExamRoom getById(int id);

	/**
	 * 添加资源
	 * 
	 * @param ExamRoom
	 * @return
	 */
	ExamRoom add(ExamRoom ExamRoom);

	/**
	 * @param ExamRoom
	 */
	void update(ExamRoom ExamRoom);

	/**
	 * @param id
	 */
	void delById(int id);
	
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
	
	/**
	 * 批量插入考场信息
	 * @param excelList
	 * @param params
	 */
	public void addExamRooms(List<Map<String, String>> excelList,Map<String, Object> params);

}
