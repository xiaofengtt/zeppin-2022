package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IExamTeacherRoomDAO;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.service.api.IExamTeacherRoomService;

/**
 * ClassName: ExamTeacherRoomService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamTeacherRoomService implements IExamTeacherRoomService {

	IExamTeacherRoomDAO iExamTeacherRoomDAO;

	/**
	 * @return the iExamTeacherRoomDAO
	 */
	public IExamTeacherRoomDAO getiExamTeacherRoomDAO() {
		return iExamTeacherRoomDAO;
	}

	/**
	 * @param iExamTeacherRoomDAO
	 *            the iExamTeacherRoomDAO to set
	 */
	public void setiExamTeacherRoomDAO(IExamTeacherRoomDAO iExamTeacherRoomDAO) {
		this.iExamTeacherRoomDAO = iExamTeacherRoomDAO;
	}

	@Override
	public ExamTeacherRoom getById(int id) {

		return this.getiExamTeacherRoomDAO().get(id);
	}

	@Override
	public ExamTeacherRoom add(ExamTeacherRoom ExamTeacherRoom) {

		return this.getiExamTeacherRoomDAO().save(ExamTeacherRoom);
	}

	@Override
	public void update(ExamTeacherRoom ExamTeacherRoom) {

		this.getiExamTeacherRoomDAO().update(ExamTeacherRoom);
	}

	@Override
	public void delById(int id) {

		ExamTeacherRoom ExamTeacherRoom = this.getiExamTeacherRoomDAO().get(id);
		ExamTeacherRoom.setStatus((short) 0);
		this.getiExamTeacherRoomDAO().update(ExamTeacherRoom);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List searchExamTeacherRoom(Map<String, Object> searchMap, Map<String, String> sortParams, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.getiExamTeacherRoomDAO().searchExamTeacherRoom(searchMap, sortParams, offset, length);
	}

	@Override
	public int searchExamTeacherRoomCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getiExamTeacherRoomDAO().searchExamTeacherRoomCount(searchMap);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List searchInvigilationTeacher(Map<String, Object> searchMap, Map<String, String> sortParams, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.getiExamTeacherRoomDAO().searchUndistributionTeacher(searchMap, sortParams, offset, length);
	}

	@Override
	public List<ExamTeacherRoom> getByParams(Map<String, Object> searchMap) {
		return this.getiExamTeacherRoomDAO().getByParams(searchMap);
	}
}
