/**
x * This class is used for 考场信息操作
 * 
 */
package cn.zeppin.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.entity.ExamRoomInfo;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IExamInformationService;
import cn.zeppin.service.api.IExamRoomService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JsonValidator;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: ExamRoomAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamRoomAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;

	private IExamRoomService examRoomService;

	private IExamInformationService examInformationService;

	public IExamRoomService getExamRoomService() {
		return examRoomService;
	}

	public void setExamRoomService(IExamRoomService examRoomService) {
		this.examRoomService = examRoomService;
	}

	public IExamInformationService getExamInformationService() {
		return examInformationService;
	}

	public void setExamInformationService(IExamInformationService examInformationService) {
		this.examInformationService = examInformationService;
	}

	// /**
	// * 添加 需要确定导入模板 考试信息查询 上传并解析Excel文件 需要拿到文件存储路径 转换文件流使用POI进行文件解析
	// * 支持上传的文件类型有xls和xlsx
	// */
	// @SuppressWarnings("resource")
	// @AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	// @ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false,
	// emptyable = false)
	// // @ActionParam(key = "resource", type = ValueType.NUMBER, nullable =
	// false,
	// // emptyable = false)
	// @ActionParam(key = "resourcePath", type = ValueType.STRING, nullable =
	// false, emptyable = false)
	// public void Add() {
	//
	// SysUser currentUser = (SysUser) session.getAttribute("usersession");
	// ActionResult actionResult = new ActionResult();
	// // 接受页面参数
	// Integer exam = this.getIntValue(request.getParameter("exam"));
	// ExamInformation information = this.examInformationService.getById(exam);
	//
	// if (information == null) {
	// actionResult.init(FAIL_STATUS, "考试信息不存在", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	//
	// if (information.getStatus() == 0) {
	// actionResult.init(FAIL_STATUS, "本次考试已结束", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	//
	// // Integer resource =
	// //
	// this.getIntValue(request.getParameter("resource"));//resourceID用来获取存储路径
	// String resourcePath = request.getParameter("resourcePath");//
	// resource存储路径
	// try {
	// String serverPath =
	// ServletActionContext.getServletContext().getRealPath("/").replace("\\",
	// "/");
	// resourcePath = resourcePath.split("upload")[1];
	// File file = new File(serverPath + "/upload" + resourcePath);
	// InputStream is = null;
	// boolean isE2007 = false; // 判断是否是excel2007格式
	// if (resourcePath.endsWith("xlsx")) {
	// isE2007 = true;
	// }
	// if (file.exists()) {
	// try {
	// is = new FileInputStream(file);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// Workbook wb = null;
	// if (is == null) {
	// actionResult.init(FAIL_STATUS, "文件不存在，请重新上传", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	// if (isE2007) {
	// wb = new XSSFWorkbook(is);
	// } else {
	// wb = new HSSFWorkbook(is);
	// }
	// Sheet s = wb.getSheetAt(0);
	// Row row;
	// int t = s.getLastRowNum();
	// session.setAttribute("maxIndex", t);
	// session.setAttribute("percent", 0);
	// List<Map<String, String>> excelList = new ArrayList<Map<String,
	// String>>();
	// for (int i = 1; i <= t; i++) {// 解析Excel过程
	// row = s.getRow(i);
	//// Thread.sleep(1000);// 手动延迟1s
	// String roomIndex = "";
	// String roomAddress = "";
	// String examnationTime = "";
	// String examnationInformation = "";
	// String arrivaltime = "";
	//
	// if (row.getCell(0) != null) {
	// row.getCell(0).setCellType(CellType.STRING);
	// roomIndex = row.getCell(0).getStringCellValue();
	// }
	// if ("".equals(roomIndex)) {
	// actionResult.init(FAIL_STATUS, "第" + i + "行考场号不能为空", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	// if (row.getCell(1) != null) {
	// row.getCell(1).setCellType(CellType.STRING);
	// roomAddress = row.getCell(1).getStringCellValue();
	// }
	// if ("".equals(roomAddress)) {
	// actionResult.init(FAIL_STATUS, "第" + i + "行考场地点不能为空", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	// if (row.getCell(2) != null) {
	// row.getCell(2).setCellType(CellType.STRING);
	// examnationTime = row.getCell(2).getStringCellValue();
	// }
	// if ("".equals(examnationTime)) {
	// actionResult.init(FAIL_STATUS, "第" + i + "行考试时间不能为空", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	// if (row.getCell(3) != null) {
	// row.getCell(3).setCellType(CellType.STRING);
	// examnationInformation = row.getCell(3).getStringCellValue();
	// }
	// if ("".equals(examnationInformation)) {
	// actionResult.init(FAIL_STATUS, "第" + i + "行考试内容不能为空", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	// if (row.getCell(4) != null) {
	// row.getCell(4).setCellType(CellType.STRING);
	// arrivaltime = row.getCell(4).getStringCellValue();
	// }
	// if ("".equals(arrivaltime)) {
	// actionResult.init(FAIL_STATUS, "第" + i + "行到岗时间不能为空", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	//
	// String[] examnationInformationArr = examnationInformation.split(";");
	// String[] examnationTimeArr =examnationTime.split(";");
	// String[] arrivaltimeArr = arrivaltime.split(";");
	// System.out.println(examnationInformationArr.length+ "-----"+
	// examnationTimeArr.length+ "----"+arrivaltimeArr.length);
	// if(examnationInformationArr.length != examnationTimeArr.length ||
	// examnationInformationArr.length != arrivaltimeArr.length){
	// actionResult.init(FAIL_STATUS, "第" + i + "行考试科目、考试时间、到岗时间不是一一对应关系",
	// null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	//
	// Map<String, String> excel = new HashMap<String, String>();
	// excel.put("roomIndex", roomIndex);
	// excel.put("roomAddress", roomAddress);
	// excel.put("examnationTime", examnationTime);
	// excel.put("examnationInformation", examnationInformation);
	// excel.put("arrivaltime", arrivaltime);
	// excelList.add(excel);
	//
	// int percent = (int) Math.ceil((i * 100) / (t * 2));
	// session.setAttribute("percent", percent);
	// }
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("rowCount", t);
	// params.put("creator", currentUser.getId());
	// params.put("exam", information);
	// this.examRoomService.addExamRooms(excelList, params);
	//
	// session.removeAttribute("maxIndex");
	// session.removeAttribute("percent");
	// actionResult.init(SUCCESS, "导入数据成功", null);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// actionResult.init(FAIL_STATUS, "上传解析异常", null);
	// }
	//
	// Utlity.ResponseWrite(actionResult, dataType, response);
	//
	// }

	/**
	 * 添加 需要确定导入模板 考试信息查询 上传并解析Excel文件 需要拿到文件存储路径 转换文件流使用POI进行文件解析
	 * 支持上传的文件类型有xls和xlsx
	 */
	@SuppressWarnings("resource")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	// @ActionParam(key = "resource", type = ValueType.NUMBER, nullable = false,
	// emptyable = false)
	@ActionParam(key = "resourcePath", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Add() {

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult actionResult = new ActionResult();
		// 接受页面参数
		Integer exam = this.getIntValue(request.getParameter("exam"));
		ExamInformation information = this.examInformationService.getById(exam);

		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "本次考试已结束", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		// Integer resource =
		// this.getIntValue(request.getParameter("resource"));//resourceID用来获取存储路径
		String resourcePath = request.getParameter("resourcePath");// resource存储路径
		try {
			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
			resourcePath = resourcePath.split("upload")[1];
			File file = new File(serverPath + "/upload" + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // 判断是否是excel2007格式
			if (resourcePath.endsWith("xlsx")) {
				isE2007 = true;
			}
			if (file.exists()) {
				try {
					is = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			Workbook wb = null;
			if (is == null) {
				actionResult.init(FAIL_STATUS, "文件不存在，请重新上传", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (isE2007) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			Sheet s = wb.getSheetAt(0);
			Row row;
			int t = s.getLastRowNum();
			int coloumNum = s.getRow(4).getPhysicalNumberOfCells();
			System.out.println("列数： " + coloumNum);
			session.setAttribute("maxIndex", t);
			session.setAttribute("percent", 0);
			List<Map<String, String>> excelList = new ArrayList<Map<String, String>>();
			List<ExamRoomInfo> examRoomInfoList = new ArrayList<ExamRoomInfo>();
			for (int i = 4; i <= t; i++) {// 解析Excel过程
				row = s.getRow(i);
				// Thread.sleep(1000);// 手动延迟1s
				String roomIndex = "";
				String roomAddress = "";
				String roomType = "";
				String examnationTime = "";
				String examnationInformation = "";
				String arrivaltime = "";

				if (row.getCell(0) != null) {
					row.getCell(0).setCellType(CellType.STRING);
					roomIndex = row.getCell(0).getStringCellValue();
				}
				if ("".equals(roomIndex)) {
					actionResult.init(FAIL_STATUS, "第" + i + "行考场号不能为空", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(CellType.STRING);
					roomAddress = row.getCell(1).getStringCellValue();
				}
				if ("".equals(roomAddress)) {
					actionResult.init(FAIL_STATUS, "第" + i + "行考场地点不能为空", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if (row.getCell(2) != null) {
					row.getCell(2).setCellType(CellType.STRING);
					roomType = row.getCell(2).getStringCellValue().replace(" ", "");
				}else {
					roomType = "";
				}
				// if ("".equals(roomAddress)) {
				// actionResult.init(FAIL_STATUS, "第" + i + "行考场地点不能为空", null);
				// Utlity.ResponseWrite(actionResult, dataType, response);
				// return;
				// }
				examRoomInfoList = new ArrayList<ExamRoomInfo>();
				for (int j = 3; j < coloumNum; j++) {
					if (row.getCell(j) != null) {
						row.getCell(j).setCellType(CellType.STRING);
						examnationInformation = row.getCell(j).getStringCellValue();
						examnationTime = row.getCell(j + 1).getStringCellValue();
						arrivaltime = row.getCell(j + 2).getStringCellValue();
					}else {
						examnationInformation = null;
						examnationTime = null;
						arrivaltime = null;
					}
					if (!Utlity.checkStringNull(examnationInformation)) {
						ExamRoomInfo examRoomInfo = new ExamRoomInfo();
						examRoomInfo.setExamnationInformation(
								Utlity.checkStringNull(examnationInformation) ? "无" : examnationInformation);
						examRoomInfo
								.setExamnationTime(Utlity.checkStringNull(examnationTime) ? "无" : examnationTime);
						examRoomInfo.setArrivaltime(Utlity.checkStringNull(arrivaltime) ? "无" : arrivaltime);
						examRoomInfoList.add(examRoomInfo);
					}
					j += 2;
				}
				String examRoomInfo = JSON.toJSONString(examRoomInfoList, true);

				Map<String, String> excel = new HashMap<String, String>();
				excel.put("roomIndex", roomIndex);
				excel.put("roomAddress", roomAddress);
				excel.put("roomType", roomType);
				excel.put("examnationTime", examnationTime);
				excel.put("examnationInformation", examnationInformation);
				excel.put("arrivaltime", arrivaltime);
				excel.put("examRoomInfo", examRoomInfo);
				excelList.add(excel);

				int percent = (int) Math.ceil((i * 100) / (t * 2));
				session.setAttribute("percent", percent);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("rowCount", t);
			params.put("creator", currentUser.getId());
			params.put("exam", information);
			this.examRoomService.addExamRooms(excelList, params);

			session.removeAttribute("maxIndex");
			session.removeAttribute("percent");
			actionResult.init(SUCCESS, "导入数据成功", null);
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "上传解析异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 获取上传解析进度
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
				actionResult.init(FAIL_STATUS, "上传过程中发生异常,上传失败", null);
				actionResult.put("Percent", 0 + "");
				actionResult.put("MaxIndex", 0 + "");
			}
		} else {
			actionResult.init(FAIL_STATUS, "上传失败", null);
			actionResult.put("Percent", 0 + "");
			actionResult.put("MaxIndex", 0 + "");
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 修改
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "roomIndex", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "roomAddress", type = ValueType.STRING, nullable = false, emptyable = false)
	// @ActionParam(key = "examnationTime", type = ValueType.STRING, nullable =
	// false, emptyable = false)
	// @ActionParam(key = "examnationInformation", type = ValueType.STRING,
	// nullable = false, emptyable = false)
	// @ActionParam(key = "arrivaltime", type = ValueType.STRING, nullable =
	// false, emptyable = false)
	@ActionParam(key = "examRoomInfo", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "roomType", type = ValueType.STRING)
	public void Update() {

		ActionResult actionResult = new ActionResult();
		// check paras
		// 接受页面参数
		Integer id = this.getIntValue(request.getParameter("id"));
		String roomIndex = request.getParameter("roomIndex");
		String roomAddress = request.getParameter("roomAddress");
		// String examnationTime = request.getParameter("examnationTime");
		// String examnationInformation =
		// request.getParameter("examnationInformation");
		// String arrivaltime = request.getParameter("arrivaltime");
		String examRoomInfo = request.getParameter("examRoomInfo");
		String roomType = request.getParameter("roomType");
		examRoomInfo = examRoomInfo.replaceAll("；", ";").replaceAll("，", ",").replaceAll("【", "[").replaceAll("】", "]");
		// 是否是json格式
		boolean ret = new JsonValidator().validate(examRoomInfo);
		if (!ret) {
			actionResult.init(FAIL_STATUS, "考场信息json格式有误", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		// 校验页面参数格式

		try {
			ExamRoom room = this.examRoomService.getById(id);
			if (room != null) {
				room.setRoomIndex(roomIndex);
				room.setRoomAddress(roomAddress);
				room.setRoomType(roomType);
				// room.setExamnationTime(examnationTime);
				// room.setExamnationInformation(examnationInformation);
				// room.setArrivaltime(arrivaltime);
				room.setExamRoomInfo(examRoomInfo);
			} else {
				actionResult.init(FAIL_STATUS, "考场信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			this.examRoomService.update(room);
			actionResult.init(SUCCESS, "考场信息更新成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "考试信息更新异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 删除 批量 格式：“1，2，3”
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		String id = request.getParameter("id");
		String ids[] = id.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				ExamRoom room = examRoomService.getById(Integer.parseInt(ids[i]));
				if (room != null) {
					room.setStatus((short) 0);
					this.examRoomService.update(room);
				}
			}
			actionResult.init(SUCCESS, "删除成功", null);
		} catch (Exception e) {
			actionResult.init(FAIL_STATUS, "删除失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 根绝ID获取考场信息
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Get() {

		int id = -1;
		ActionResult actionResult = new ActionResult();
		id = Integer.parseInt(request.getParameter("id"));
		ExamRoom room = examRoomService.getById(id);
		if (room != null) {
			if (room.getStatus() == 0) {// 已删除
				actionResult.init(FAIL_STATUS, "考场已被删除", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			Map<String, Object> data = null;
			try {
				data = SerializeEntity.ExamRoom2Map(room);
			} catch (Exception e) {
				e.printStackTrace();
				actionResult.init(FAIL_STATUS, "信息异常", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			actionResult.init(SUCCESS_STATUS, "获取信息成功", data);

		} else {
			actionResult.init(FAIL_STATUS, "信息不存在", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取考试信息列表
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "examnationInformation", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {

		ActionResult actionResult = new ActionResult();

		Integer exam = this.getIntValue(request.getParameter("exam"));
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		String examnationInformation = request.getParameter("examnationInformation");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("examnationInformation", examnationInformation);
		searchMap.put("exam", exam);

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}
		try {
			int recordCount = this.examRoomService.searchExamRoomCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List<ExamRoom> list = this.examRoomService.searchExamRoom(searchMap, sortParams, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (ExamRoom room : list) {
					Map<String, Object> data = null;
					try {
						data = SerializeEntity.ExamRoom2Map(room);

						dataList.add(data);
					} catch (Exception e) {
						e.printStackTrace();
						actionResult.init(FAIL_STATUS, "信息转换异常", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
				}

			}
			actionResult.init(SUCCESS_STATUS, "搜索完成！", dataList);
			actionResult.setPageCount(pageCount);
			actionResult.setPageNum(pagenum);
			actionResult.setPageSize(pagesize);
			actionResult.setTotalCount(recordCount);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息获取异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取考场的类型分组
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void GetGroupByRoomType() {
		ActionResult actionResult = new ActionResult();
		Integer exam = this.getIntValue(request.getParameter("exam"));
		try {
			ExamInformation information = this.examInformationService.getById(exam);
			if (information == null) {
				actionResult.init(FAIL_STATUS, "考试信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			if (information.getStatus() == 0) {
				actionResult.init(FAIL_STATUS, "考试已结束", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("exam", exam);
			searchMap.put("groupByRoomType", true);
			List<Object[]> list = this.examRoomService.searchByGroup(searchMap);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (Object[] object : list) {
					Map<String, Object> data = new HashMap<>();
					data.put("count", object[0]);
					if (Utlity.checkStringNull((String) object[1])) {
						data.put("roomType", "无");
					}else {
						data.put("roomType", object[1]);
					}
					dataList.add(data);
				}
			}
			actionResult.init(SUCCESS_STATUS, "获取成功！", dataList);
		} catch (Exception e) {
			actionResult.init(FAIL_STATUS, "获取异常", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

}
