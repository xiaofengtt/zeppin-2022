package cn.zeppin.action.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherExamRecords;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.ITeacherExamRecordsService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.Utlity;

public class TeacherExamRecordsAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(TeacherExamRecordsAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private ITeacherExamRecordsService iTeacherExamRecordsService;
	private ITeacherService iTeacherService;
	
	private List<String> searchYear;
	private List<String> searchExam;
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private String pageStatus;// 页面状态
	private String message;// 错误信息
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}
	
	public String initPage(){
		initServlert();
		this.searchYear = this.iTeacherExamRecordsService.getYearList();
		this.searchExam = this.iTeacherExamRecordsService.getExamByParams(new HashMap<String,String>());
		return "init";
	}
	
	public String uploadInit(){
		return "upload";
	}

	public void getList() {

		initServlert();

		// 起始页
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}
		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = Integer.parseInt(ien);

		// 排序
		String sort = request.getParameter("jtSorting");

		// 排序 参数
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sort = sortname + "_" + sorttype;
		} else {
			sort = "id_desc";
		}
		
		String[] sortArr = sort.split("_");
		Map<String, String> sortParams = new HashMap<String, String>();
		sortParams.put(sortArr[0], sortArr[1]);
		
		// 其他搜索条件
		String year = request.getParameter("year");
		year = year == null ? "0" : year;
		
		String exam = request.getParameter("exam");
		exam = exam == null ? "0" : exam;
		
		HashMap<String, String> searchMap = new HashMap<>();
		searchMap.put("year", year);
		searchMap.put("exam", exam);
		
		// 搜索出条数
		int count = this.iTeacherExamRecordsService.getCountByParams(searchMap);

		List<TeacherExamRecords> lidata = this.iTeacherExamRecordsService.getListByParams(searchMap, sortParams, start, limit);

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\",");
			sb.append("\"Message\":\"没有搜索到数据!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");

		sbPack.append("\"Result\":\"OK\"");
		sbPack.append(",");

		sbPack.append("\"TotalRecordCount\":" + count);
		sbPack.append(",");
		sbPack.append("\"Records\":[");

		for (TeacherExamRecords ter : lidata) {

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + ter.getId() + "\",");
			sb.append("\"exam\":\"" + ter.getExam() + "\",");
			sb.append("\"name\":\"" + ter.getTeacher().getName() + "\",");
			sb.append("\"idcard\":\"" + ter.getTeacher().getIdcard() + "\",");
			sb.append("\"score\":\"" + ter.getScore() + "\"");
			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);
	}
	
	public void getExamList(){
		initServlert();
		
		String year = this.request.getParameter("year");
		HashMap<String,String> map = new HashMap<String,String>();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
		List<String> lip = this.iTeacherExamRecordsService.getExamByParams(map);
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"exam\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
		for(String p : lip){
			String sr = "{\"id\":\"" + p + "\",\"name\":\"" + p + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void upload(){
		initServlert();
		String year = this.request.getParameter("year");
		String exam = this.request.getParameter("exam");
		DecimalFormat df = new DecimalFormat("#");
		pageStatus = "OK";
		message = "文件上传成功";
		String rString = "";
		String Path = "";
		hmFiles = new HashMap<>();
		try {
			if(year==null || year.equals("0")){
				pageStatus = "ERROR";
				message = "未选择年份";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if(exam.equals("")){
				pageStatus = "ERROR";
				message = "考试名名不能为空";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			
			List<HashMap<String, Object>> infomationList = new ArrayList<HashMap<String, Object>>();
			initServlert();
			HttpSession session = request.getSession();
			hmFiles = (HashMap<EDocumentType, fileInfo>) session.getAttribute("uploadfile");
			if (hmFiles == null) {
				pageStatus = "ERROR";
				message = "未上传Excel";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if (hmFiles.size() > 0) {
				fileInfo fileInfo = new fileInfo();
				Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter.next();
					fileInfo = (fileInfo) entry.getValue();
//					String rootpath = this.getClass().getClassLoader().getResource("/").getPath()+ "../..";
//					Path = rootpath + fileInfo.getFilePath();
				}
				//根据系统获取实际地址
				String rootpath = Utlity.getRealPath(ServletActionContext.getServletContext());
				Path = rootpath + fileInfo.getFilePath();
				Path = Path.replace("%20", " ");
				
				InputStream is = null;
				try {
					is = new FileInputStream(Path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				HSSFWorkbook wb = new HSSFWorkbook(is);
				HSSFSheet s = wb.getSheet("考试记录表");
				HSSFRow row;
				if(s.getLastRowNum()<1){
					pageStatus = "ERROR";
					message = "表内没有记录";
					rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
					Utlity.ResponseWrite(rString, "json", response);
					return;
				}
				this.session.setAttribute("maxIndex", s.getLastRowNum());
				this.session.setAttribute("percent",0);
				
				for (int i = 1; i <= s.getLastRowNum(); i++) {// 解析Excel过程
					row = s.getRow(i);
					if(row!=null){
						HashMap<String, Object> infomation = new HashMap<String, Object>();
						String idcard = "";
						HSSFCell cell = row.getCell(0);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								idcard = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								idcard = cell.getStringCellValue();
							}
						}
						if(idcard!=null && !idcard.equals("")){
							String name = "";
							cell = row.getCell(1);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									name = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									name = cell.getStringCellValue().trim();
								}
							}
							String score = "";
							cell = row.getCell(2);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									score = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									score = cell.getStringCellValue().trim();
								}
							}
							
							if(!IdCardUtil.IDCardValidate(idcard).equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的教师身份证不合法";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( name==null || name.equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的教师未填写姓名";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							
							if( score!=null && !score.equals("") && !Utlity.isNumeric(score)){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的教师分数错误";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							
							HashMap<String,String> searchMap = new HashMap<String,String>();
							searchMap.put("idcard", idcard);
							List<Teacher> tList = this.iTeacherService.getTeacherListByParams(searchMap);
							if(tList.size()<1){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的教师未在系统中录入";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							Teacher teacher = tList.get(0);
							if(!teacher.getName().trim().equals(name)){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的教师身份证与姓名不相符";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							
							infomation.put("teacher", teacher);
							infomation.put("score", score);
							infomationList.add(infomation);
							int percent = (int) Math.ceil((i * 100) /  (s.getLastRowNum() * 2));
							session.setAttribute("percent", percent);
						}
					}
				}
				this.iTeacherExamRecordsService.addTeacherExamRecords(year,exam, s.getLastRowNum() ,infomationList);
				session.removeAttribute("maxIndex");
				session.removeAttribute("percent");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageStatus = "ERROR";
			message = "上传的文件有误";
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
			Utlity.ResponseWrite(rString, "json", response);
		}
		rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
				+ "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	public void getPercent() {
		initServlert();
		pageStatus = "OK";
		message = "";
		String rString = "";
		if(session.getAttribute("percent")!=null && session.getAttribute("maxIndex")!=null){
			int percent = (int) session.getAttribute("percent");
			int maxIndex = (int) session.getAttribute("maxIndex");
			if (percent >= 0 && maxIndex >= 0) {
				message = "文件处理成功";
			} else {
				pageStatus = "ERROR";
				message = "上传过程中发生异常,上传失败";
			}
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
					+ "\",\"Percent\":\"" + percent + "\",\"MaxIndex\":\""
					+ maxIndex + "\"}";
		}else{
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
					+ "\",\"Percent\":\"" + 0 + "\",\"MaxIndex\":\""
					+ 0 + "\"}";
		}
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	public ITeacherExamRecordsService getiTeacherExamRecordsService() {
		return iTeacherExamRecordsService;
	}

	public void setiTeacherExamRecordsService(ITeacherExamRecordsService iTeacherExamRecordsService) {
		this.iTeacherExamRecordsService = iTeacherExamRecordsService;
	}
	
	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}
	
	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public List<String> getSearchExam() {
		return searchExam;
	}

	public void setSearchExam(List<String> searchExam) {
		this.searchExam = searchExam;
	}
}
