package cn.zeppin.entity.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.zeppin.access.Navigation;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.entity.ExamRoomInfo;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.entity.Funcation;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.InvigilationTemplate;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.Task;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class SerializeEntity {
	/**
	 * 功能输出
	 * 
	 * @author Administrator
	 * @param organization
	 * @return
	 */
	public static Map<String, Object> funcation2Map(Funcation func) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", func.getId());
		result.put("name", func.getName());
		result.put("path", func.getPath());
		return result;
	}

	public static Navigation funcation2Navigation(Funcation func) {
		Navigation nv = new Navigation();
		nv.setId(func.getId());
		nv.setName(func.getName());
		nv.setLevel(func.getLevel());
		nv.setPath(func.getPath());
		nv.setIcon(func.getIcon());
		nv.setScode(func.getScode());
		return nv;
	}

	/**
	 * 管理员用户接口输出字段
	 * 
	 * @author Clark
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> sysUser2Map(SysUser sysUser) {
		return sysUser2Map(sysUser, ".");
	}

	/**
	 * 管理员用户接口输出字段
	 * 
	 * @author Clark
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> sysUser2Map(SysUser sysUser, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", sysUser.getId());
		result.put("role" + split + "id", sysUser.getRole().getId());
		result.put("role" + split + "name", sysUser.getRole().getName());
		result.put("organization" + split + "id", sysUser.getOrganization());
		result.put("organization" + split + "name", sysUser.getOrganization());
		result.put("email", sysUser.getEmail());
		result.put("phone", sysUser.getPhone());
		result.put("name", sysUser.getName());
		result.put("sysUser" + split + "id", sysUser.getSysUser().getId());
		result.put("sysUser" + split + "name", sysUser.getSysUser().getName());
		result.put("status", sysUser.getStatus());
		result.put("photo", sysUser.getPhoto().getPath());
		return result;
	}

	public static Map<String, Object> resource2Map(Resource resource) throws FileNotFoundException, IOException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", resource.getId());
		result.put("name", resource.getName());
		result.put("type", resource.getType());
		result.put("status", resource.getStatus());
		result.put("fileSize", resource.getFilesize());
		result.put("suffix", resource.getSuffix());

		String path = ServletActionContext.getRequest().getScheme() + "://"
				+ ServletActionContext.getRequest().getServerName() + ":"
				+ ServletActionContext.getRequest().getServerPort()
				+ ServletActionContext.getRequest().getContextPath();

		result.put("sourcePath", path + "/" + resource.getSourcePath());

		if (resource.getType() == Dictionary.RESOURCE_TYPE_IMAGE) {

			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/") + "/"
					+ resource.getSourcePath();

			File imgPic = new File(serverPath);
			if (imgPic.exists()) {
				BufferedImage sourceImg = ImageIO.read(new FileInputStream(imgPic));
				result.put("wight", sourceImg.getWidth());
				result.put("height", sourceImg.getHeight());
			}
		}

		return result;
	}

	public static Map<String, Object> task2Map(Task task) {
		return task2Map(task, ".");
	}

	public static Map<String, Object> task2Map(Task task, String split) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", task.getId());
		result.put("name", task.getName());
		result.put("type", task.getType());
		result.put("answerType", task.getAnswerType());
		result.put("status", task.getStatus());
		result.put("answerQuantity", task.getAnswerQuantity());

		return result;

	}

	public static Map<String, Object> ExamInformation2Map(ExamInformation examInformation) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", examInformation.getId());
		result.put("name", examInformation.getName());
		result.put("starttime", Utlity.timeSpanToDateString(examInformation.getExamStarttime()));
		result.put("endtime", Utlity.timeSpanToDateString(examInformation.getExamEndtime()));
		result.put("applyendtime", Utlity.timeSpanToString(examInformation.getApplyendtime()));
		result.put("checkendtime", Utlity.timeSpanToString(examInformation.getCheckendtime()));
		result.put("integral", examInformation.getIntegral());
		result.put("information", examInformation.getInformation());
		result.put("invigilationContract", examInformation.getInvigilationContract());
		result.put("status", examInformation.getStatus());
		result.put("applyNotice", examInformation.getApplyNotice() == null ? "" : examInformation.getApplyNotice());
		result.put("invigilationNotice",
				examInformation.getInvigilationNotice() == null ? "" : examInformation.getInvigilationNotice());
		return result;
	}

	public static Map<String, Object> ExamRoom2Map(ExamRoom room) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", room.getId());
		result.put("roomIndex", room.getRoomIndex());
		result.put("roomAddress", room.getRoomAddress());
		// result.put("examnationTime", room.getExamnationTime());
		// result.put("examnationInformation", room.getExamnationInformation());
		// result.put("arrivaltime", room.getArrivaltime());
		result.put("exam", room.getExam().getName());
		result.put("status", room.getStatus());
		result.put("examStarttime", Utlity.timeSpanToDateString(room.getExam().getExamStarttime()));
		result.put("examEndtime", Utlity.timeSpanToDateString(room.getExam().getExamEndtime()));
		if (room.getExamRoomInfo() != null) {
			List<ExamRoomInfo> userList = JSON.parseArray(room.getExamRoomInfo(), ExamRoomInfo.class);
			result.put("examnation", userList);
		} else {
			result.put("examnation", null);
		}
		// try {
		// List<Map<String, Object>> examnationList = new ArrayList<Map<String,
		// Object>>();
		// Map<String, Object> examnation = new LinkedHashMap<String, Object>();
		// String[] examnationInformationArr =
		// room.getExamnationInformation().split(";");
		// String[] examnationTimeArr = room.getExamnationTime().split(";");
		// String[] arrivaltimeArr = room.getArrivaltime().split(";");
		// for (int i = 0; i < examnationTimeArr.length; i++) {
		// examnation = new LinkedHashMap<String, Object>();
		// examnation.put("examnationTime", examnationTimeArr[i]);
		// examnation.put("examnationInformation",
		// Utlity.checkStringNull(examnationInformationArr[i]) ? "无" :
		// examnationInformationArr[i]);
		// examnation.put("arrivaltime",
		// Utlity.checkStringNull(arrivaltimeArr[i]) ? "无" : arrivaltimeArr[i]);
		// examnationList.add(examnation);
		// }
		// result.put("examnation", examnationList);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

		return result;
	}

	/**
	 * 后台封装
	 * 
	 * @param teacher
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map<String, Object> InvigilationTeacher2Map(InvigilationTeacher teacher)
			throws FileNotFoundException, IOException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", teacher.getId());
		result.put("name", teacher.getName());
		result.put("pinyin", teacher.getPinyin());
		result.put("idcard", teacher.getIdcard());
		result.put("mobile", teacher.getMobile());
		result.put("sex", teacher.getSex());
		result.put("ethnic", teacher.getEthnic().getName());
		result.put("ethnicId", teacher.getEthnic().getId());
		result.put("photo", resource2Map(teacher.getPhoto()));
		result.put("major", Utlity.checkStringNull(teacher.getMajor()) ? "无" : teacher.getMajor());
		result.put("type", teacher.getType());
		result.put("organization", teacher.getOrganization());
		result.put("inschooltime", Utlity.timeSpanToString4(Utlity.stringToDate(teacher.getInschooltime())));
		result.put("isChiefExaminer", teacher.getIsChiefExaminer());
		result.put("isMixedExaminer", teacher.getIsMixedExaminer());
		result.put("intgral", teacher.getIntgral());
		result.put("specialty", teacher.getSpecialty());
		result.put("status", teacher.getStatus());
		result.put("reason", teacher.getReason());
		result.put("creator", teacher.getCreator());
		result.put("createtime", Utlity.timeSpanToString(teacher.getCreatetime()));
		result.put("checkStatus", teacher.getCheckStatus());
		result.put("checktime", Utlity.timeSpanToString(teacher.getChecktime()));
		result.put("checker", teacher.getChecker());
		result.put("checkReason", teacher.getCheckReason());
		result.put("invigilateCampus", teacher.getInvigilateCampus());
		String invigilateCampusCN = "";
		if (teacher.getInvigilateCampus() != null) {
			String[] str = teacher.getInvigilateCampus().split(",");
			if (str.length > 0) {
				for (String campus : str) {
					if (campus.equals(Dictionary.INVIGILATION_TEACHER_CAMPUS_KL + "")) {
						invigilateCampusCN += "昆仑校区,";
					} else if (campus.equals(Dictionary.INVIGILATION_TEACHER_CAMPUS_WQ + "")) {
						invigilateCampusCN += "温泉校区,";
					} else if (campus.equals(Dictionary.INVIGILATION_TEACHER_CAMPUS_WG + "")) {
						invigilateCampusCN += "文光校区,";
					}
				}
				if (invigilateCampusCN.length() > 0) {
					invigilateCampusCN = invigilateCampusCN.substring(0, invigilateCampusCN.length() - 1);
				}
			}
		}
		result.put("invigilateCampusCN", invigilateCampusCN);
		result.put("invigilateType", teacher.getInvigilateType());
		String invigilateTypeCN = "";
		if (teacher.getInvigilateType() != null) {
			String[] str = teacher.getInvigilateType().split(",");
			if (str.length > 0) {
				for (String type : str) {
					if (type.equals(Dictionary.INVIGILATE_TEACHER_TYPE1 + "")) {
						invigilateTypeCN += "纸笔,";
					} else if (type.equals(Dictionary.INVIGILATE_TEACHER_TYPE2 + "")) {
						invigilateTypeCN += "无纸化,";
					} else if (type.equals(Dictionary.INVIGILATE_TEACHER_TYPE3 + "")) {
						invigilateTypeCN += "测试,";
					}
				}
				if (invigilateTypeCN.length() > 0) {
					invigilateTypeCN = invigilateTypeCN.substring(0, invigilateTypeCN.length() - 1);
				}
			}
		}
		result.put("invigilateTypeCN", invigilateTypeCN);
		result.put("invigilateCount", teacher.getInvigilateCount());
		result.put("jobDuty", Utlity.checkStringNull(teacher.getJobDuty()) ? "无" : teacher.getJobDuty());
		result.put("studyMajor", Utlity.checkStringNull(teacher.getStudyMajor()) ? "无" : teacher.getStudyMajor());
		result.put("studyGrade", Utlity.checkStringNull(teacher.getStudyGrade()) ? "无" : teacher.getStudyGrade());
		result.put("remark", teacher.getRemark());
		result.put("bankCard", Utlity.checkStringNull(teacher.getBankCard()) ? "无" : teacher.getBankCard());
		result.put("sid", Utlity.checkStringNull(teacher.getSid()) ? "无" : teacher.getSid());

		result.put("releaseTime",
				teacher.getReleaseTime() == null ? "" : Utlity.timeSpanToString(teacher.getReleaseTime()));
		result.put("disableType", teacher.getDisableType());
		result.put("idcardPhoto", resource2Map(teacher.getIdCardPhoto()));
		return result;
	}

	/**
	 * 教师端封装
	 * 
	 * @param teacher
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map<String, Object> InvigilationTeacher2MapTwo(InvigilationTeacher teacher)
			throws FileNotFoundException, IOException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", teacher.getId());
		result.put("name", teacher.getName());
		result.put("pinyin", teacher.getPinyin());
		result.put("idcard", teacher.getIdcard());
		result.put("mobile", teacher.getMobile());
		result.put("sex", teacher.getSex());
		result.put("ethnic", teacher.getStatus());
		result.put("photo", resource2Map(teacher.getPhoto()));
		result.put("major", teacher.getMajor());
		return result;
	}

	/**
	 * 教师申报记录
	 * 
	 * @param etr
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map<String, Object> ExamTeacherRoom2Map(ExamTeacherRoom etr)
			throws FileNotFoundException, IOException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		InvigilationTeacher teacher = etr.getTeacher();
		result.put("exam", etr.getExam().getName());
		result.put("id", etr.getId());
		result.put("tid", teacher.getId());
		result.put("name", teacher.getName());
		result.put("mobile", teacher.getMobile());
		result.put("sex", teacher.getSex());
		result.put("ethnic", teacher.getEthnic().getName());
		result.put("isChiefExaminer", teacher.getIsChiefExaminer());
		result.put("isMixedExaminer", teacher.getIsMixedExaminer());
		result.put("type", teacher.getType());
		result.put("major", teacher.getMajor());
		result.put("intgral", teacher.getIntgral());
		result.put("status", teacher.getStatus());
		if (etr.getRoom() == null) {
			result.put("distribute", 0);
		} else {
			result.put("distribute", 1);
			result.put("room", etr.getRoom().getId());
			result.put("roomIndex", etr.getRoom().getRoomIndex());
			result.put("roomAddress", etr.getRoom().getRoomAddress());
			// result.put("examnationInformation",
			// etr.getRoom().getExamnationInformation());
			// result.put("examnationTime",
			// etr.getRoom().getExam().getExamStarttime() +
			// "-"+etr.getRoom().getExam().getExamEndtime());
			if (etr.getRoom().getExamRoomInfo() != null) {
				List<ExamRoomInfo> userList = JSON.parseArray(etr.getRoom().getExamRoomInfo(), ExamRoomInfo.class);
				result.put("examnation", userList);
			} else {
				result.put("examnation", null);
			}
			// try {
			// List<Map<String, Object>> examnationList = new
			// ArrayList<Map<String, Object>>();
			// Map<String, Object> examnation = new LinkedHashMap<String,
			// Object>();
			// String[] examnationInformationArr =
			// etr.getRoom().getExamnationInformation().split(";");
			// String[] examnationTimeArr =
			// etr.getRoom().getExamnationTime().split(";");
			// String[] arrivaltimeArr =
			// etr.getRoom().getArrivaltime().split(";");
			// for (int i = 0; i < examnationTimeArr.length; i++) {
			// examnation = new LinkedHashMap<String, Object>();
			// examnation.put("examnationTime", examnationTimeArr[i]);
			// examnation.put("examnationInformation",
			// Utlity.checkStringNull(examnationInformationArr[i]) ? "无" :
			// examnationInformationArr[i]);
			// examnation.put("arrivaltime",
			// Utlity.checkStringNull(arrivaltimeArr[i]) ? "无" :
			// arrivaltimeArr[i]);
			// examnationList.add(examnation);
			// }
			// result.put("examnation", examnationList);
			// } catch (Exception e) {
			// // TODO: handle exception
			// }
		}
		result.put("isconfirm", etr.getIsConfirm());
		if (etr.getConfirtime() == null) {
			result.put("confirmTime", "无");
		} else {
			result.put("confirmTime", Utlity.timeSpanToString2(etr.getConfirtime()));
		}
		result.put("applytime", Utlity.timeSpanToString2(etr.getCreatetime()));
		result.put("invigilateType", Utlity.invigilateType(teacher.getInvigilateType()));
		result.put("invigilateCount", teacher.getInvigilateCount() == null ? "0" : teacher.getInvigilateCount());
		result.put("invigilateCampus", Utlity.invigilateCampus(teacher.getInvigilateCampus()));
		result.put("isChief", etr.getIsChief());
		result.put("remark", etr.getRemark());
		result.put("credit", etr.getCredit() == null ? "无" : etr.getCredit());
		return result;
	}

	public static Map<String, Object> InvigilationTemplate2Map(InvigilationTemplate it) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", it.getId());
		result.put("title", it.getTitle());
		result.put("content", it.getContent());
		result.put("createTime", it.getCreatetime());
		result.put("creator", it.getCreator());
		return result;
	}
}
