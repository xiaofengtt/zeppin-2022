package cn.zeppin.service.imp;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.dao.api.IExamRoomDAO;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.service.api.IExamRoomService;

/**
 * ClassName: ExamRoomService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamRoomService implements IExamRoomService {

	IExamRoomDAO iExamRoomDAO;

	/**
	 * @return the iExamRoomDAO
	 */
	public IExamRoomDAO getiExamRoomDAO() {
		return iExamRoomDAO;
	}

	/**
	 * @param iExamRoomDAO
	 *            the iExamRoomDAO to set
	 */
	public void setiExamRoomDAO(IExamRoomDAO iExamRoomDAO) {
		this.iExamRoomDAO = iExamRoomDAO;
	}

	@Override
	public ExamRoom getById(int id) {

		return this.getiExamRoomDAO().get(id);
	}

	@Override
	public ExamRoom add(ExamRoom ExamRoom) {

		return this.getiExamRoomDAO().save(ExamRoom);
	}

	@Override
	public void update(ExamRoom ExamRoom) {

		this.getiExamRoomDAO().update(ExamRoom);
	}

	@Override
	public void delById(int id) {

		ExamRoom ExamRoom = this.getiExamRoomDAO().get(id);
		ExamRoom.setStatus((short) 0);
		this.getiExamRoomDAO().update(ExamRoom);
	}

	@Override
	public List<ExamRoom> searchExamRoom(Map<String, Object> searchMap, Map<String, String> sortParams, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.getiExamRoomDAO().searchExamRoom(searchMap, sortParams, offset, length);
	}

	@Override
	public int searchExamRoomCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getiExamRoomDAO().searchExamRoomCount(searchMap);
	}

	@Override
	public void addExamRooms(List<Map<String, String>> excelList, Map<String, Object> params) {
		// TODO Auto-generated method stub
		HttpSession session = ServletActionContext.getRequest().getSession();
		Integer rowCount = (Integer) params.get("rowCount");
		ExamInformation information = (ExamInformation) params.get("exam");
		Integer creator = (Integer) params.get("creator");

		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("exam", information.getId());
		List<ExamRoom> list = this.getiExamRoomDAO().searchExamRoom(searchMap, null, -1, -1);
		boolean isUpdate = false;// 是否更新

		for (Map<String, String> excel : excelList) {
			ExamRoom room = new ExamRoom();
			String roomIndex = excel.get("roomIndex");
			String roomAddress = excel.get("roomAddress");
//			String examnationTime = excel.get("examnationTime");
//			String examnationInformation = excel.get("examnationInformation");
//			String arrivaltime = excel.get("arrivaltime");
			String examRoomInfo = excel.get("examRoomInfo");

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					isUpdate = false;
					ExamRoom currenExamRoom = list.get(i);
					if (roomAddress.equals(currenExamRoom.getRoomAddress())) {// 已存在考场地点
						currenExamRoom.setRoomIndex(roomIndex);
//						currenExamRoom.setExamnationTime(examnationTime);
//						currenExamRoom.setExamnationInformation(examnationInformation);
//						currenExamRoom.setArrivaltime(arrivaltime);
					    currenExamRoom.setExamRoomInfo(examRoomInfo);
						this.getiExamRoomDAO().update(currenExamRoom);// 更新
						isUpdate = true;
						break;
					}
				}
			}
			if (!isUpdate) {
				room.setRoomIndex(roomIndex);
				room.setRoomAddress(roomAddress);
				room.setExam(information);
//				room.setExamnationTime(examnationTime);
//				room.setExamnationInformation(examnationInformation);
//				room.setArrivaltime(arrivaltime);
				room.setStatus((short) 1);
				room.setCreator(creator);
				room.setCreatetime(new Timestamp(System.currentTimeMillis()));
				room.setExamRoomInfo(examRoomInfo);
				this.getiExamRoomDAO().save(room);
			}

			int percent = (int) Math.ceil(((rowCount + excelList.indexOf(excel)) * 100) / (rowCount * 2));
			session.setAttribute("percent", percent);
		}
	}

}
