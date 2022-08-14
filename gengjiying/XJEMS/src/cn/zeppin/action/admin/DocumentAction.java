package cn.zeppin.action.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.fastjson.JSON;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.entity.ExamRoomInfo;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.service.api.IExamInformationService;
import cn.zeppin.service.api.IExamRoomService;
import cn.zeppin.service.api.IExamTeacherRoomService;
import cn.zeppin.utility.Utlity;

/**
 * <p>
 * Title:DocumentAction
 * </p>
 * <p>
 * Description:导出
 * </p>
 * 
 * @author geng
 * @date 2017年7月27日 上午11:14:49
 */
public class DocumentAction extends BaseAction {
	private String examName = "考试";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IExamTeacherRoomService examTeacherRoomService;
	private IExamInformationService examInformationService;
	private IExamRoomService examRoomService;

	public IExamInformationService getExamInformationService() {
		return examInformationService;
	}

	public void setExamInformationService(IExamInformationService examInformationService) {
		this.examInformationService = examInformationService;
	}

	public IExamTeacherRoomService getExamTeacherRoomService() {
		return examTeacherRoomService;
	}

	public void setExamTeacherRoomService(IExamTeacherRoomService examTeacherRoomService) {
		this.examTeacherRoomService = examTeacherRoomService;
	}

	public IExamRoomService getExamRoomService() {
		return examRoomService;
	}

	public void setExamRoomService(IExamRoomService examRoomService) {
		this.examRoomService = examRoomService;
	}

	/**
	 * 导出教师考场安排
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DownloadTeacherInfo() throws IOException {
		response.reset();
		ActionResult actionResult = new ActionResult();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("教师考场安排");
		HSSFRow row = sheet.createRow(0);
		Cell cell;
		// title
		String title[] = { "学工号", "姓名", "手机号", "身份证号", "民族", "性别", "身份", "银行账号", "所在学院或部门", "学分", "考场号", "考场地点", "角色",
				"二次确认", "确认时间", "监考类型", "监考校区" };
		// for (int i = 0; i < title.length; i++) {
		// cell = row.createCell(i);
		// cell.setCellValue(title[i]);
		// // cell.setCellStyle(Utlity.setStyleForTitle(wb));
		// // sheet.autoSizeColumn(i); // 调整宽度
		// }
		// 内容
		Integer exam = this.getIntValue(request.getParameter("exam"));
		try {
			ExamInformation infomation = examInformationService.getById(exam);
			examName = infomation.getName();
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", 1);// 状态：正常
			searchMap.put("exam", exam);// 当前考试
			searchMap.put("distribute", "1");//
			Map<String, String> sortParams = new HashMap<String, String>();
			sortParams.put("room0", "desc");// 按教师考场信息“EXAM_ROOM”字段排序
			List list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, sortParams, 0, -1);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < title.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(title[i]);
					// cell.setCellStyle(Utlity.setStyleForTitle(wb));
					// sheet.autoSizeColumn(i); // 调整宽度
				}
				int currentCell = title.length;
				int coloumNum = 0;

				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					ExamRoom room = etr.getRoom();
					List<ExamRoomInfo> userList = JSON.parseArray(room.getExamRoomInfo(), ExamRoomInfo.class);
					if (userList.size() > coloumNum) {
						coloumNum = userList.size();
					}
				}
				for (int i = 0; i < coloumNum; i++) {
					cell = row.createCell(currentCell++);
					cell.setCellValue("考试科目");
					cell = row.createCell(currentCell++);
					cell.setCellValue("考试时间");
					cell = row.createCell(currentCell++);
					cell.setCellValue("到岗时间");
				}

				session.setAttribute("maxIndex", list.size());
				session.setAttribute("percent", 0);

				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow(i + 1);
					Object[] obj = (Object[]) list.get(i);
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					InvigilationTeacher teacher = (InvigilationTeacher) obj[1];
					String sid = teacher.getSid();
					String bankCard = teacher.getBankCard();
					String name = teacher.getName();
					String mobile = teacher.getMobile();
					String idcard = teacher.getIdcard();
					String ethnic = teacher.getEthnic().getName();
					String sex = teacher.getSex() == 1 ? "男" : "女";
					String type = Utlity.getTeacherType(teacher.getType());
					// 所在学院
					String organization = teacher.getOrganization();
					String intgral = teacher.getIntgral() + "";
					// 安排考场（格式：069（监理）-文史楼701室）
					String roomIndex = etr.getRoom().getRoomIndex();
					String roomAddress = etr.getRoom().getRoomAddress();
					String role = teacher.getIsChiefExaminer() == 1 ? "主考" : "副考";
					String reConfirm = etr.getIsConfirm() == 1 ? "是" : "否";
					String confirmTime = "";
					if (etr.getConfirtime() != null) {
						confirmTime = Utlity.timeSpanToString2(etr.getConfirtime());
					}

					// String examinationInformation =
					// etr.getRoom().getExamnationInformation();
					// String arrivaltime = "";
					// if (etr.getRoom().getArrivaltime() != null) {
					// arrivaltime = etr.getRoom().getArrivaltime();
					// }
					String invigilateType = Utlity.invigilateType(teacher.getInvigilateType());
					String invigilateCampus = Utlity.invigilateCampus(teacher.getInvigilateCampus());

					String info[] = { sid, name, mobile, idcard, ethnic, sex, type, bankCard, organization, intgral,
							roomIndex, roomAddress, role, reConfirm, confirmTime, invigilateType, invigilateCampus };
					for (int j = 0; j < info.length; j++) {
						cell = row.createCell(j);
						cell.setCellValue(info[j]);
						sheet.autoSizeColumn(j);
						// cell.setCellStyle(Utlity.setStyleForContent(wb));
						// sheet.autoSizeColumn(i); // 调整宽度
					}
					currentCell = info.length;
					List<ExamRoomInfo> userList = JSON.parseArray(etr.getRoom().getExamRoomInfo(), ExamRoomInfo.class);
					for (ExamRoomInfo examRoomInfo : userList) {
						cell = row.createCell(currentCell++);
						cell.setCellValue(examRoomInfo.getExamnationInformation());
						sheet.autoSizeColumn(currentCell);
						cell = row.createCell(currentCell++);
						cell.setCellValue(examRoomInfo.getExamnationTime());
						sheet.autoSizeColumn(currentCell);
						cell = row.createCell(currentCell++);
						cell.setCellValue(examRoomInfo.getArrivaltime());
						sheet.autoSizeColumn(currentCell);
					}

					int percent = (int) Math.ceil(((i + 1) * 100) / list.size());
					session.setAttribute("percent", percent);
				}
			}
			session.removeAttribute("maxIndex");
			session.removeAttribute("percent");
			actionResult.init(SUCCESS, "下载成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "下载失败", null);
		}
		response.setContentType("application/vnd.ms-excel");
		String filename = examName + "-教师考场安排";
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String(filename.getBytes(), "iso-8859-1") + ".xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		wb.close();
	}

	/**
	 * 导出未录用人员名单
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DownloadUndTeacher() throws IOException {
		ActionResult actionResult = new ActionResult();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("未录用人员名单");
		HSSFRow row = sheet.createRow(0);
		Cell cell;
		// title
		String title[] = { "学工号", "姓名", "手机号", "民族", "性别", "身份", "银行账号", "二次确认", "报名时间", "监考类型", "监考校区" };
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 内容
		Integer exam = this.getIntValue(request.getParameter("exam"));
		try {
			ExamInformation infomation = examInformationService.getById(exam);
			examName = infomation.getName();
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("distribute", "2");// “EXAM_ROOM”字段 为空
			searchMap.put("exam", exam);// 当前考试
			searchMap.put("status", 1);//
			List list = this.examTeacherRoomService.searchInvigilationTeacher(searchMap, null, 0, -1);
			if (list != null && list.size() > 0) {
				session.setAttribute("maxIndex", list.size());
				session.setAttribute("percent", 0);
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow(i + 1);
					Object[] obj = (Object[]) list.get(i);
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					InvigilationTeacher teacher = (InvigilationTeacher) obj[1];
					String sid = teacher.getSid();
					String bankCard = teacher.getBankCard();
					String name = teacher.getName();
					String mobile = teacher.getMobile();
					String ethnic = teacher.getEthnic().getName();
					String sex = teacher.getSex() == 1 ? "男" : "女";
					String type = Utlity.getTeacherType(teacher.getType());
					String reConfirm = etr.getIsConfirm() == 1 ? "是" : "否";
					// String applyTime = "";
					// if (etr.getConfirtime() != null) {
					// applyTime = Utlity.timeSpanToString2(etr.getApplytime());
					// }
					String createtime = Utlity.timeSpanToString2(etr.getCreatetime());
					String invigilateType = Utlity.invigilateType(teacher.getInvigilateType());
					String invigilateCampus = Utlity.invigilateCampus(teacher.getInvigilateCampus());
					String info[] = { sid, name, mobile, ethnic, sex, type, bankCard, reConfirm, createtime,
							invigilateType, invigilateCampus };
					for (int j = 0; j < info.length; j++) {
						cell = row.createCell(j);
						cell.setCellValue(info[j]);
						sheet.autoSizeColumn(j);
					}
					int percent = (int) Math.ceil(((i + 1) * 100) / list.size());
					session.setAttribute("percent", percent);
				}
			}
			session.removeAttribute("maxIndex");
			session.removeAttribute("percent");
			actionResult.init(SUCCESS, "下载成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "下载失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String filename = examName + "-未录用人员名单";
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String(filename.getBytes(), "iso-8859-1") + ".xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		wb.close();
	}

	/**
	 * 考场安排：考场维度导出
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DownloadExamRoom() throws IOException {
		response.reset();
		ActionResult actionResult = new ActionResult();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("考场");
		HSSFRow row = sheet.createRow(0);
		Cell cell;
		// title

		Integer exam = this.getIntValue(request.getParameter("exam"));
		try {
			ExamInformation infomation = examInformationService.getById(exam);
			examName = infomation.getName();

			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("exam", exam);
			searchMap.put("status", 1);// 正常的

			List<ExamRoom> list = this.examRoomService.searchExamRoom(searchMap, null, -1, -1);
			if (list != null && list.size() > 0) {
				String title[] = { "考场号", "考场地点"};
				for (int i = 0; i < title.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(title[i]);
				}
				int currentCell = title.length;
				int erInfoCountMax = 0;
				// 每个考场安排最多的教师总数
//				int teacherCountMax = 0;
				//每个考场里主考最多的人数
			    int maxChiefCount1 = 0;
			    int maxChiefCount2 = 0;

				for (int i = 0; i < list.size(); i++) {
					ExamRoom room = list.get(i);
					List<ExamRoomInfo> userList = JSON.parseArray(room.getExamRoomInfo(), ExamRoomInfo.class);
					if (userList.size() > erInfoCountMax) {
						erInfoCountMax = userList.size();
					}
					// 查询每个考场已分配教师的信息
					Map<String, Object> searchMap2 = new HashMap<String, Object>();
					searchMap2.put("exam", exam);
					searchMap2.put("room", room.getId());
					searchMap2.put("status", 1);// 正常的
					List listTeacher = this.examTeacherRoomService.searchExamTeacherRoom(searchMap2, null, -1, -1);
					
					if (listTeacher != null && listTeacher.size() > 0) {
//						if (listTeacher.size() > teacherCountMax) {
//							teacherCountMax = listTeacher.size();
//						}
						int currentChiefCount1 = 0;
						int currentChiefCount2 = 0;
						//对比获取考场里主考最多的人数
						for (int j = 0; j < listTeacher.size(); j++) {
				    		Object o = listTeacher.get(j);
							Object[] obj = (Object[]) o;
							ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
							if (etr.getIsChief() == 1) {// 主考
								currentChiefCount1++;
							} else {
								currentChiefCount2++;
							}
				    	}
						if(currentChiefCount1 > maxChiefCount1){
							maxChiefCount1 = currentChiefCount1;
						}
						if(currentChiefCount2 > maxChiefCount2){
							maxChiefCount2 = currentChiefCount2;
						}
					}
				}
				for (int i = 0; i < erInfoCountMax; i++) {
					cell = row.createCell(currentCell++);
					cell.setCellValue("考试科目");
					cell = row.createCell(currentCell++);
					cell.setCellValue("考试时间");
					cell = row.createCell(currentCell++);
					cell.setCellValue("到岗时间");
				}
				for (int i = 0; i < maxChiefCount1; i++) {
					cell = row.createCell(currentCell++);
					cell.setCellValue("主考");
					cell = row.createCell(currentCell++);
					cell.setCellValue("监考教师");
					cell = row.createCell(currentCell++);
					cell.setCellValue("手机号");
					cell = row.createCell(currentCell++);
					cell.setCellValue("身份证号");
					cell = row.createCell(currentCell++);
					cell.setCellValue("银行卡号");
				}
				for (int i = 0; i < maxChiefCount2; i++) {
					cell = row.createCell(currentCell++);
					cell.setCellValue("副考");
					cell = row.createCell(currentCell++);
					cell.setCellValue("监考教师");
					cell = row.createCell(currentCell++);
					cell.setCellValue("手机号");
					cell = row.createCell(currentCell++);
					cell.setCellValue("身份证号");
					cell = row.createCell(currentCell++);
					cell.setCellValue("银行卡号");
				}

				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow(i + 1);
					ExamRoom room = list.get(i);
					currentCell = 0;
					cell = row.createCell(currentCell++);
					cell.setCellValue(room.getRoomIndex());
					sheet.autoSizeColumn(currentCell);
					cell = row.createCell(currentCell++);
					cell.setCellValue(room.getRoomAddress());
					sheet.autoSizeColumn(currentCell);					
					//考场信息
					List<ExamRoomInfo> userList = JSON.parseArray(room.getExamRoomInfo(), ExamRoomInfo.class);
					for (ExamRoomInfo examRoomInfo : userList) {
						cell = row.createCell(currentCell++);
						cell.setCellValue(examRoomInfo.getExamnationInformation());
						sheet.autoSizeColumn(currentCell);
						cell = row.createCell(currentCell++);
						cell.setCellValue(examRoomInfo.getExamnationTime());
						sheet.autoSizeColumn(currentCell);
						cell = row.createCell(currentCell++);
						cell.setCellValue(examRoomInfo.getArrivaltime());
						sheet.autoSizeColumn(currentCell);
					}
					
					// 查询已分配教师的信息
					Map<String, Object> searchMap2 = new HashMap<String, Object>();
					searchMap2.put("exam", exam);
					searchMap2.put("room", room.getId());
					searchMap2.put("status", 1);// 正常的
					Map<String, String> sortParams = new HashMap<String,String>();
					sortParams.put("isChief0", "desc");
					
					//导出的主考在同一列，副考在同一列
					List listTeacher = this.examTeacherRoomService.searchExamTeacherRoom(searchMap2, sortParams, -1, -1);
					currentCell = title.length + erInfoCountMax * 3;
				    int index = currentCell + maxChiefCount1 * 5;
					if (listTeacher != null && listTeacher.size() > 0) {
						for (int j = 0; j < listTeacher.size(); j++) {
							Object o = listTeacher.get(j);
							Object[] obj = (Object[]) o;
							ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
							if (etr.getIsChief() == 1) {// 主考
								cell = row.createCell(currentCell++);
								cell.setCellValue("主考");
								cell = row.createCell(currentCell++);
								cell.setCellValue(etr.getTeacher().getName());
								sheet.autoSizeColumn(currentCell);
								currentCell = teacherOtherInfo(sheet, row, currentCell, etr);
							} else {// 副考
								cell = row.createCell(index++);
								cell.setCellValue("副考");
								cell = row.createCell(index++);
								cell.setCellValue(etr.getTeacher().getName());
								sheet.autoSizeColumn(index);
								index = teacherOtherInfo(sheet, row, index, etr);
							}
						}
					}

					int percent = (int) Math.ceil(((i + 1) * 100) / list.size());
					session.setAttribute("percent", percent);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "下载失败", null);
		}
		session.removeAttribute("maxIndex");
		session.removeAttribute("percent");
		actionResult.init(SUCCESS, "下载成功", null);
		response.setContentType("application/vnd.ms-excel");
		String filename = examName + "-考场监考教师";
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String(filename.getBytes(), "iso-8859-1") + ".xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		wb.close();
	}

	/**
	 * 教师其他信息  
	 * @param sheet
	 * @param row
	 * @param currentCell
	 * @param etr
	 * @return
	 */
	private int teacherOtherInfo(HSSFSheet sheet, HSSFRow row, int currentCell, ExamTeacherRoom etr) {
		Cell cell;
		String tBankcard="";
		cell = row.createCell(currentCell++);
		cell.setCellValue(etr.getTeacher().getMobile());//手机号
		sheet.autoSizeColumn(currentCell);
		cell = row.createCell(currentCell++);
		cell.setCellValue(etr.getTeacher().getIdcard());//身份证号
		sheet.autoSizeColumn(currentCell);
		if(!Utlity.checkStringNull(etr.getTeacher().getBankCard())){
		    tBankcard = etr.getTeacher().getBankCard();//银行卡号
		}else {
			tBankcard = "无";
		}
		cell = row.createCell(currentCell++);
		cell.setCellValue(tBankcard);
		sheet.autoSizeColumn(currentCell);
		return currentCell;
	}
	
	/**
	 * 获取数据解析进度
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	public void Percent() {
		ActionResult actionResult = new ActionResult();
		if (session.getAttribute("percent") != null && session.getAttribute("maxIndex") != null) {
			int percent = (int) session.getAttribute("percent");
			int maxIndex = (int) session.getAttribute("maxIndex");
			if (percent >= 0 && maxIndex >= 0) {
				actionResult.init(SUCCESS, "文件处理成功", null);
				actionResult.put("Percent", percent);
				actionResult.put("MaxIndex", maxIndex);
			} else {
				actionResult.init(FAIL_STATUS, "下载成功", null);
				actionResult.put("Percent", 0 + "");
				actionResult.put("MaxIndex", 0 + "");
			}
		} else {
			actionResult.init(FAIL_STATUS, "下载失败", null);
			actionResult.put("Percent", 0 + "");
			actionResult.put("MaxIndex", 0 + "");
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}
}
