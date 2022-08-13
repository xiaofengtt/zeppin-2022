package cn.zeppin.action.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
import cn.zeppin.entity.OtherTrainingRecords;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.IOtherTrainingRecordsService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.Utlity;

public class OtherTrainingRecordsAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(OtherTrainingRecordsAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private IOtherTrainingRecordsService iOtherTrainingRecordsService;
	private IProjectTypeService iProjectTypeService;
	private ITrainingSubjectService iTrainingSubjectService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITeacherService iTeacherService;
	
	private List<String> searchYear;
	private List<String> searchProject;
	private List<ProjectType> searchProjectType;
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
		this.searchYear = this.iOtherTrainingRecordsService.getProjectYearList();
		this.searchProject = this.iOtherTrainingRecordsService.getProjectNameByParams(new HashMap<String,String>());
		return "init";
	}
	
	public String uploadInit(){
		this.searchProjectType = this.iProjectTypeService.getProjectTypeList(null);
		for(ProjectType pt : this.searchProjectType){
			if(pt.getLevel() >1){
				String space = "";
				for(int i=0;i<pt.getLevel();i++){
					space += "--";
				}
				pt.setName(space + pt.getName());
			}
		}
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
		
		String projectName = request.getParameter("projectName");
		projectName = projectName == null ? "0" : projectName;

		String trainingSubjectId = request.getParameter("subjectName");
		trainingSubjectId = trainingSubjectId == null ? "0" : trainingSubjectId;

		String trainingCollegeId = request.getParameter("trainingUnit");
		trainingCollegeId = trainingCollegeId == null ? "0" : trainingCollegeId;
		
		HashMap<String, String> searchMap = new HashMap<>();
		searchMap.put("year", year);
		searchMap.put("projectName", projectName);
		searchMap.put("trainingSubject", trainingSubjectId);
		searchMap.put("trainingCollege", trainingCollegeId);
		
		// 搜索出条数
		int count = this.iOtherTrainingRecordsService.getCountByParams(searchMap);

		List<OtherTrainingRecords> lidata = this.iOtherTrainingRecordsService.getListByParams(searchMap, sortParams, start, limit);

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

		for (OtherTrainingRecords otr : lidata) {

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + otr.getId() + "\",");

			sb.append("\"projectName\":\"" + otr.getProjectName() + "\",");
			sb.append("\"name\":\"" + otr.getTeacher().getName() + "\",");
			sb.append("\"idcard\":\"" + otr.getTeacher().getIdcard() + "\",");
			sb.append("\"trainingCollege\":\"" + otr.getTrainingCollege().getName() + "\",");

			sb.append("\"trainingSubject\":\"" + otr.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingHour\":\"" + otr.getTrainingHour() + "\",");

			sb.append("\"trainingOnlineHour\":\"" +otr.getTrainingOnlineHour() + "\",");
			sb.append("\"startTime\":\"" + otr.getStartTime() + "\",");
			sb.append("\"endTime\":\"" + otr.getEndTime() + "\""); 
			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);
	}
	
	public void getProjectList(){
		initServlert();
		
		String year = this.request.getParameter("year");
		HashMap<String,String> map = new HashMap<String,String>();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
		List<String> lip = this.iOtherTrainingRecordsService.getProjectNameByParams(map);
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
		for(String p : lip){
			String sr = "{\"id\":\"" + p + "\",\"name\":\"" + p + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	public void getSearshList() {
		initServlert();
		String projectName = this.request.getParameter("projectName");
		HashMap<String,String> searchMap = new HashMap<String,String>();
		searchMap.put("projectName", projectName);
		List<OtherTrainingRecords> liatts = this.iOtherTrainingRecordsService.getListByParams(searchMap, null, null, null);
		
		Hashtable<Short, String> htSubject = new Hashtable<>();
		Hashtable<Integer, String> htTrainingUnit = new Hashtable<>();

		for (OtherTrainingRecords otr : liatts) {
			if (!htSubject.containsKey(otr.getTrainingSubject().getId())) {
				htSubject.put(otr.getTrainingSubject().getId(), otr.getTrainingSubject().getName());
			}
			if (!htTrainingUnit.containsKey(otr.getTrainingCollege().getId())) {
				htTrainingUnit.put(otr.getTrainingCollege().getId(), otr.getTrainingCollege().getName());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"subjects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");

		for (Iterator<Short> itr = htSubject.keySet().iterator(); itr.hasNext();) {
			short key = (short) itr.next();
			String name = (String) htSubject.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append("]");

		sb.append(",\"trainingUnits\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");

		for (Iterator<Integer> itr = htTrainingUnit.keySet().iterator(); itr.hasNext();) {
			int key = (int) itr.next();
			String name = (String) htTrainingUnit.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());

		sb.append("]}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void upload(){
		initServlert();
		String year = this.request.getParameter("year");
		String projectTypeId = this.request.getParameter("projectType");
		String projectName = this.request.getParameter("projectName");
		String shortName = this.request.getParameter("shortName");
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
			ProjectType projectType = this.iProjectTypeService.get(Integer.valueOf(projectTypeId));
			if(projectType==null){
				pageStatus = "ERROR";
				message = "未选择项目类型";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if(projectName.equals("")){
				pageStatus = "ERROR";
				message = "项目名不能为空";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if(shortName.equals("")){
				pageStatus = "ERROR";
				message = "项目简称不能为空";
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
				Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles
						.entrySet().iterator();
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
				HSSFSheet s = wb.getSheet("培训记录表");
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
				HashMap<String,TrainingSubject> tsMap = new HashMap<String,TrainingSubject>();
				List<TrainingSubject> tsList = this.iTrainingSubjectService.findAll();
				for(TrainingSubject ts : tsList){
					tsMap.put(ts.getName().trim(), ts);
				}
				
				HashMap<String,TrainingCollege> tcMap = new HashMap<String,TrainingCollege>();
				List<TrainingCollege> tcList = this.iTrainingCollegeService.findAll();
				for(TrainingCollege tc : tcList){
					tcMap.put(tc.getName().trim(), tc);
				}
				
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
							String college = "";
							cell = row.getCell(2);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									college = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									college = cell.getStringCellValue().trim();
								}
							}
							String subject = "";
							cell = row.getCell(3);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									subject = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									subject = cell.getStringCellValue().trim();
								}
							}
							String trainingHour = "";
							cell = row.getCell(4);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									trainingHour = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									trainingHour = cell.getStringCellValue().trim();
								}
							}
							String trainingOnlineHour = "";
							cell = row.getCell(5);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									trainingOnlineHour = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									trainingOnlineHour = cell.getStringCellValue().trim();
								}
							}
							String startTime = "";
							cell = row.getCell(6);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									startTime = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									startTime = cell.getStringCellValue().trim();
								}
							}
							String endTime = "";
							cell = row.getCell(7);
							if (cell != null) {
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									endTime = df.format(cell.getNumericCellValue()).trim();
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									endTime = cell.getStringCellValue().trim();
								}
							}
							if(!IdCardUtil.IDCardValidate(idcard).equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员身份证不合法";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( name==null || name.equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写姓名";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( college==null || college.equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写承训院校";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( subject==null || subject.equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写培训学科";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( (trainingHour==null || trainingHour.equals("")) && (trainingOnlineHour==null || trainingOnlineHour.equals(""))){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写获得学时";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( trainingHour!=null && !trainingHour.equals("") && !Utlity.isNumeric(trainingHour)){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员集中培训学时不合法";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( trainingOnlineHour!=null && !trainingOnlineHour.equals("") && !Utlity.isNumeric(trainingOnlineHour)){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员远程培训学时不合法";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( startTime==null || startTime.equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写培训开始时间";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if( endTime==null || endTime.equals("")){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写培训结束时间";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							HashMap<String,String> searchMap = new HashMap<String,String>();
							searchMap.put("idcard", idcard);
							List<Teacher> tList = this.iTeacherService.getTeacherListByParams(searchMap);
							if(tList.size()<1){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未在系统中录入";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							Teacher teacher = tList.get(0);
							if(!teacher.getName().trim().equals(name)){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员身份证与姓名不相符";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if(tsMap.get(subject)==null){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员培训学科未在系统中录入";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							if(tcMap.get(college)==null){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员承训院校未在系统中录入";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							infomation.put("teacher", teacher);
							infomation.put("trainingSubject", tsMap.get(subject));
							infomation.put("trainingCollege", tcMap.get(college));
							infomation.put("trainingHour", trainingHour);
							infomation.put("trainingOnlineHour", trainingOnlineHour);
							infomation.put("startTime", startTime);
							infomation.put("endTime", endTime);
							infomationList.add(infomation);
							int percent = (int) Math.ceil((i * 100) /  (s.getLastRowNum() * 2));
							session.setAttribute("percent", percent);
						}
					}
				}
				this.iOtherTrainingRecordsService.addOtherTrainingRecords(year,projectType,projectName,shortName , s.getLastRowNum() ,infomationList);
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
	
	public IOtherTrainingRecordsService getiOtherTrainingRecordsService() {
		return iOtherTrainingRecordsService;
	}

	public void setiOtherTrainingRecordsService(IOtherTrainingRecordsService iOtherTrainingRecordsService) {
		this.iOtherTrainingRecordsService = iOtherTrainingRecordsService;
	}
	
	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}
	
	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}
	
	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
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
	
	public List<String> getSearchProject() {
		return searchProject;
	}

	public void setSearchProject(List<String> searchProject) {
		this.searchProject = searchProject;
	}
	
	public List<ProjectType> getSearchProjectType() {
		return searchProjectType;
	}

	public void setSearchProjectType(List<ProjectType> searchProjectType) {
		this.searchProjectType = searchProjectType;
	}
}
