package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectCollegeRangeService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectBaseInfoOptAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(ProjectBaseInfoOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService;
	private IProjectTypeService iProjectTypeService;
	private IDocumentService iDocumentService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IProjectSubjectRangeService iProjectSubjectRangeService;
	private ITrainingCollegeService iTrainingCollegeService;
	private IProjectCollegeRangeService iProjectCollegeRangeService;
	private IOrganizationService iOrganizationService;

	// from 表单
	private String id;
	private String projectType;
	private String name;
	private String shortname;
	private String year;
	private String enrollType;
	private String traintype;
	private String subjectMax;
	private String timeup;
	private String funds;
	private String number;
	private String status;
	private String remark;
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private Document document;
	private String doPath;
	private String doTitle;
	private String restrictSubject;
	private String restrictCollege;
	private String isTest;//是否需要进行信息技术能力提升测评==1是 ==0否

	// 年份
	private String[] yearArray;
	// 一级项目类型
	private List<ProjectType> first;
	private String[] restrictRightId;
	private String[] restrictRightList;
	private String[] restrictSubjectId;
	private List<String[]> restrictSubjectList;
	private String[] restrictCollegeId;
	private List<String[]> restrictCollegeList;

	private String pageStatus;
	private String message;

	public ProjectBaseInfoOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	// 初始化項目信息編輯頁面
	public String initPage() {

		initServlert();
		String id = request.getParameter("id");
		this.id = id;

		this.pageStatus = "";
		this.message = "";

		if (yearArray == null) {
			int year = new Date().getYear() + 1900;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}

		if (this.first == null) {
			this.first = this.iProjectTypeService.getListByPid(0);
		}

		if (this.id == null || this.id.equals("0") || this.equals("")) {
			// 添加
			this.id = "";
			this.projectType = "";
			this.name = "";
			this.shortname = "";
			this.year = (new Date().getYear() + 1900) + "";
			this.enrollType = "";
			this.traintype = "";
			this.subjectMax = "";
			this.timeup = "";
			this.funds = "";
			this.number = "";
			this.remark = "";
			this.restrictRightId = null;
			this.restrictRightList = null;
			this.document=null;
			this.doPath=null;
			this.doTitle=null;
			this.restrictSubject="0";
			this.restrictSubjectId = null;
			this.restrictSubjectList = null;
			this.restrictCollege="0";
			this.restrictCollegeId = null;
			this.restrictCollegeList = null;
		} else {
			// 编辑
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df1 = new DecimalFormat("0.00");
			Project pro = this.iProjectService.get(Integer.parseInt(id));
			if (pro != null) {
				this.name = pro.getName();
				this.shortname = pro.getShortname();
				this.year = pro.getYear();
				this.enrollType = pro.getEnrollType().toString();
				this.traintype = pro.getTraintype().toString();
				this.subjectMax = pro.getSubjectMax().toString();
				this.timeup = df.format(pro.getTimeup());
				this.funds = df1.format(pro.getFunds());
				this.number = pro.getNumber().toString();
				this.remark = pro.getRemark();
				this.document=pro.getDocument();
				if(document!=null){
				this.doPath=document.getResourcePath();
				this.doTitle=document.getTitle();
				}
				this.restrictSubject = pro.getRestrictSubject() ? "1" : "0";
				this.restrictCollege = pro.getRestrictCollege() ? "1" : "0";
				this.isTest = pro.getIsTest() ? "1" : "0";//是否需要进行信息技术能力提升工程评测
				
				ProjectType pt = pro.getProjectType();
				this.restrictRightId = new String[1];
				this.restrictRightId[0] = pt.getId().toString();
				String ts = getNaviString(pt);
				if (ts.length() > 0) {
					String[] spts = ts.split("&");
					this.restrictRightList = new String[spts.length + 1];
					for (int i = 0; i < spts.length; i++) {
						this.restrictRightList[i] = spts[i];
					}
					this.restrictRightList[spts.length] = pt.getId().toString();
				}				
				Set<ProjectSubjectRange> setSubjectRanges = pro.getProjectSubjectRanges();
				this.restrictSubjectId = new String[setSubjectRanges.size()];
				this.restrictSubjectList = new ArrayList<String[]>();
				int indexs = 0;
				for (ProjectSubjectRange tsr : setSubjectRanges) {
					TrainingSubject ts1 = tsr.getTrainingSubject();
					this.restrictSubjectId[indexs] = ts1.getId().toString();
					String ts2 = ts1.getName();
					if (ts2.length() > 0) {
						String[] spts = ts2.split("&");
						String[] rerights = new String[spts.length + 1];
						for (int i = 0; i < spts.length; i++) {
							rerights[i] = spts[i];
						}
						rerights[spts.length] = ts1.getId().toString();
						this.restrictSubjectList.add(rerights);
					}
					indexs++;
				}
				Set<ProjectCollegeRange> setCollegeRanges = pro.getProjectCollegeRanges();
				this.restrictCollegeId = new String[setCollegeRanges.size()];
				this.restrictCollegeList = new ArrayList<String[]>();
				int indexc = 0;
				for (ProjectCollegeRange pcr : setCollegeRanges) {
					TrainingCollege tc1 = pcr.getTrainingCollege();
					this.restrictCollegeId[indexc] = tc1.getId().toString();
					String tc2 = tc1.getName();
					if (ts.length() > 0) {
						String[] spts = tc2.split("&");
						String[] rerights = new String[spts.length + 1];
						for (int i = 0; i < spts.length; i++) {
							rerights[i] = spts[i];
						}
						rerights[spts.length] = tc1.getId().toString();
						this.restrictCollegeList.add(rerights);
					}	
					indexc++;
				}

			}
		}

		return "init";
	}

	public String authorityPage(){
		initServlert();
		this.id = request.getParameter("id");
		return "authority";
	}
	
	public void getAdminList(){
		initServlert();
		
		try {
			UserSession user = (UserSession) session.getAttribute("usersession");
			String id = request.getParameter("id");
			if(!Utlity.isNumeric(id)){
				id = (id.split("-"))[0];
			}
			ProjectType pt = this.iProjectService.get(Integer.valueOf(id)).getProjectType();
			List<Integer> ptids = new ArrayList<Integer>();
			ptids = addParentProjectType(ptids,pt);
			
			Map<String,List<Object[]>> map = new HashMap<>();
			List<Organization> olist = this.iOrganizationService.getOrganizationByPid(user.getOrganization());
			for (Organization o : olist){
				map.put(o.getName(), new ArrayList<Object[]>());
			}
			
			List<Object[]> li = this.iProjectService.getProjectAdminList(user.getOrganizationScode() ,user.getOrganizationLevel() , ptids);
			for (Object[] obj : li){
				map.get(obj[5]).add(obj);
			}
			Object[] ao = {0,"","","","","","","",""};
			for(String key : map.keySet()){
				if(map.get(key).size() == 0){
					map.get(key).add(ao);
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			sb.append("\"Records\":[");
			for (String key : map.keySet()){
				sb.append("{\"organization\":\"").append(key).append("\",");
				sb.append("\"admin\":[");
				for(Object[] obj : map.get(key)){
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + obj[0]);
					sbstr.append(",");
					sbstr.append("\"name\":\"" + obj[1] + "\"");
					sbstr.append(",");
					sbstr.append("\"mobile\":\"" + obj[2] + "\"");
					sbstr.append(",");
					if(obj[3].toString().equals("1")){
						obj[3] = "男";
					}else if(obj[3].toString().equals("2")){
						obj[3] = "女";
					}else{
						obj[3] = "";
					}
					sbstr.append("\"sex\":\"" + obj[3] + "\"");
					sbstr.append(",");
					sbstr.append("\"ethnic\":\"" + obj[4] + "\"");
					sbstr.append(",");
					sbstr.append("\"organization\":\"" + obj[5] + "\"");
					sbstr.append(",");
					sbstr.append("\"department\":\"" + obj[6] + "\"");
					sbstr.append(",");
					sbstr.append("\"phone\":\"" + obj[7] + "\"");
					sbstr.append(",");
					sbstr.append("\"jobDuty\":\"" + obj[8] + "\"");
					sbstr.append("},");
					sb.append(sbstr.toString());
				}
				if(map.get(key).size()>0){
					sb.delete(sb.length() - 1, sb.length());
				}
				sb.append("]},");
			}
			if(map.keySet().size()>0){
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	List<Integer> addParentProjectType(List<Integer> ptList , ProjectType pt){
		ptList.add(pt.getId());
		if(pt.getProjectType() != null){
			ptList = addParentProjectType(ptList,pt.getProjectType());
		}
		return ptList;
	}
	
	public void opProjectBaseInfoOpt() {

		initServlert();

		try {

			UserSession us = (UserSession) session.getAttribute("usersession");
			
			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.get("id")[0];
			String projectType = parMap.containsKey("projectType") ? parMap.get("projectType")[0] : "";
			String name = parMap.get("name")[0];
			String shortname = parMap.get("shortname")[0];
			String year = parMap.get("year")[0];
			String enrollType = parMap.get("enrollType")[0];
			String traintype = parMap.get("traintype")[0];
			String subjectMax = parMap.get("subjectMax")[0];
			String timeup = parMap.get("timeup")[0];
			String funds = parMap.get("funds")[0];
			String number = parMap.get("number")[0];
			String status = parMap.containsKey("status") ? parMap.get("status")[0] : "";
			String remark = parMap.containsKey("remark") ? parMap.get("remark")[0] : "";

			String[] restrictRightId = parMap.containsKey("restrictRightId") ? parMap.get("restrictRightId") : null;
			String restrictSubject = parMap.containsKey("restrictSubject") ? parMap.get("restrictSubject")[0] : ""; // 是否开启学科权限
			String[] restrictSubjectId = parMap.get("restrictSubjectId");			
			String restrictCollege = parMap.containsKey("restrictCollege") ? parMap.get("restrictCollege")[0] : ""; // 是否开启学科权限
			String[] restrictCollegeId = parMap.get("restrictCollegeId");
			
			String isTest = parMap.containsKey("isTest") ? parMap.get("isTest")[0] : null;//是否需要进行信息技术能力提升评测
			// TODO 验证用户输入
			boolean rightFlag = false;
			if (restrictRightId != null) {
				for (String s : restrictRightId) {
					if (s != null && !s.equals("0") && !s.equals("")) {
						rightFlag = true;
					}
				}
			}

			if (!rightFlag) {
				sendResponse("ERROR", "项目 类型 不能为空");
				return;
			}

			if (name == null || name.equals("0") || name.equals("")) {
				// 项目没有选择
				sendResponse("ERROR", "项目 名称 没有填写");
				return;
			}

			if (timeup == null || timeup.equals("0") || timeup.equals("")) {
				// 项目没有选择
				sendResponse("ERROR", "项目 申报截止时间 没有填写");
				return;
			}

			if (funds == null || funds.equals("")) {
				// 项目没有选择
				sendResponse("ERROR", "项目 人均经费标准 没有填写");
				return;
			}

			if (number == null || number.equals("")) {
				// 项目没有选择
				sendResponse("ERROR", "项目 计划培训人数 没有填写");
				return;
			}

			if (subjectMax == null || subjectMax.equals("")) {
				// 项目没有选择
				sendResponse("ERROR", "项目 最大申报学科 没有填写");
				return;
			}

			if (id == null || id.equals("0") || id.equals("")) {
				// 添加
				HttpSession session = request.getSession();
				hmFiles = (HashMap<EDocumentType, fileInfo>) session.getAttribute("uploadfile");
				if(hmFiles!=null){
					fileInfo fileInfo = new fileInfo();
					Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter.next();
						fileInfo = (fileInfo) entry.getValue();
						document = new Document();
						document.setCreater(us.getId());
						document.setCreatetime(dataTimeConvertUtility.getCurrentTime(""));
						document.setName(fileInfo.getFileGuid());
						document.setSize(fileInfo.getFileSize());
						document.setResourcePath(fileInfo.getFilePath());
						if (fileInfo.getFileName().contains(".")) {
							document.setTitle(fileInfo.getFileName());
						} else {
							document.setTitle(fileInfo.getFileName() + fileInfo.getFileExt());
						}
						document.setType((short) 1);
						document.setResourceType((short) 1);
						document = iDocumentService.add(document);
					}
				}
				Project pro = new Project();
				pro.setName(name);
				pro.setShortname(shortname);
				pro.setYear(year);
				pro.setTimeup(Timestamp.valueOf(timeup + " 00:00:00"));
				pro.setEnrollType(Short.parseShort(enrollType));
				pro.setTraintype(Short.parseShort(traintype));
				pro.setFunds(Double.valueOf(funds));
				pro.setNumber(Integer.valueOf(number));
				pro.setSubjectMax(Short.valueOf(subjectMax));
				pro.setDocument(document);
				
				if(isTest != null){
					Boolean it = Integer.parseInt(isTest) == 1? true : false;
					pro.setIsTest(it);// pro.getIsTest() ? "1" : "0";
				}else{
					pro.setIsTest(false);
				}

				// 设置默认学科与诚寻单位
				if (restrictSubject != null && restrictSubject.equals("on")) {
					pro.setRestrictSubject(true);
				} else {
					pro.setRestrictSubject(false);
				}
				
				if (restrictCollege != null && restrictCollege.equals("on")) {
					pro.setRestrictCollege(true);
				} else {
					pro.setRestrictCollege(false);
				}

				if (restrictRightId != null) {
					for (String s : restrictRightId) {

						if (s != null && !s.equals("0") && !s.equals("")) {

							ProjectType pt = iProjectTypeService.get(Integer.valueOf(s));
							pro.setProjectType(pt);

							break;
						}

					}
				}

				pro.setStatus((short) 1); // 为发布

				// 创建者
				pro.setCreator(us.getId());

				iProjectService.add(pro);
				if (restrictSubjectId != null) {
					for (String s : restrictSubjectId) {
						if (s != null && !s.equals("0") && !s.equals("")) {

							TrainingSubject ts = this.iTrainingSubjectService.get(Short.valueOf(s));
							ProjectSubjectRange psr = new ProjectSubjectRange();
							psr.setTrainingSubject(ts);
							psr.setProject(pro);
							psr.setCreator(us.getId());
							this.iProjectSubjectRangeService.add(psr);

						}
					}
				}
				if (restrictCollegeId != null) {
					for (String s : restrictCollegeId) {
						if (s != null && !s.equals("0") && !s.equals("")) {

							TrainingCollege tc = this.iTrainingCollegeService.get(Integer.valueOf(s));
							ProjectCollegeRange psr = new ProjectCollegeRange();
							psr.setTrainingCollege(tc);
							psr.setProject(pro);
							psr.setCreator(us.getId());
							this.iProjectCollegeRangeService.add(psr);

						}
					}
				}
				sendResponse("OK", "添加成功");
				return;

			} else {

				Project pro = iProjectService.get(Integer.parseInt(id));
				if (pro != null) {
					
					document = pro.getDocument();
					if (document != null) {
						pro.setDocument(null);
						iProjectService.update(pro);
						iDocumentService.delete(document);
					}
					
					HttpSession session = request.getSession();
					hmFiles = (HashMap<EDocumentType, fileInfo>) session.getAttribute("uploadfile");
					if(hmFiles!=null){
						fileInfo fileInfo = new fileInfo();
						Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles.entrySet().iterator();
						while (iter.hasNext()) {
							Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter.next();
							fileInfo = (fileInfo) entry.getValue();
							document = new Document();
							document.setCreater(us.getId());
							document.setCreatetime(dataTimeConvertUtility.getCurrentTime(""));
							document.setName(fileInfo.getFileGuid());
							document.setSize(fileInfo.getFileSize());
							document.setResourcePath(fileInfo.getFilePath());
							if (fileInfo.getFileName().contains(".")) {
								document.setTitle(fileInfo.getFileName());
							} else {
								document.setTitle(fileInfo.getFileName() + fileInfo.getFileExt());
							}
							document.setType((short) 1);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
						}
					}
					pro.setName(name);
					pro.setShortname(shortname);
					pro.setYear(year);
					pro.setTimeup(Timestamp.valueOf(timeup + " 00:00:00"));
					pro.setEnrollType(Short.parseShort(enrollType));
					pro.setTraintype(Short.parseShort(traintype));
					pro.setFunds(Double.valueOf(funds));
					pro.setNumber(Integer.valueOf(number));
					pro.setSubjectMax(Short.valueOf(subjectMax));
					pro.setDocument(document);
					
					if(isTest != null){
						Boolean it = Integer.parseInt(isTest) == 1? true : false;
						pro.setIsTest(it);// pro.getIsTest() ? "1" : "0";
					}else{
						pro.setIsTest(false);
					}
					
					if (restrictSubject != null && restrictSubject.equals("on")) {
						pro.setRestrictSubject(true);
					} else {
						pro.setRestrictSubject(false);
					}
					
					if (restrictCollege != null && restrictCollege.equals("on")) {
						pro.setRestrictCollege(true);
					} else {
						pro.setRestrictCollege(false);
					}

					if (restrictRightId != null) {
						for (String s : restrictRightId) {

							if (s != null && !s.equals("0") && !s.equals("")) {

								ProjectType pt = iProjectTypeService.get(Integer.valueOf(s));
								pro.setProjectType(pt);

								break;
							}

						}
					}

					iProjectService.update(pro);					
					this.iProjectSubjectRangeService.deleteByProject(pro.getId());
					if (restrictSubjectId != null) {
						for (String s : restrictSubjectId) {
							if (s != null && !s.equals("0") && !s.equals("")) {
								TrainingSubject ts = this.iTrainingSubjectService.get(Short.valueOf(s));
								ProjectSubjectRange psr = new ProjectSubjectRange();
								psr.setTrainingSubject(ts);
								psr.setProject(pro);
								psr.setCreator(us.getId());
								this.iProjectSubjectRangeService.add(psr);
							}
						}
					}
					
					this.iProjectCollegeRangeService.deleteByProject(pro.getId());
					if (restrictCollegeId != null) {
						for (String s : restrictCollegeId) {
							if (s != null && !s.equals("0") && !s.equals("")) {
								TrainingCollege ts = this.iTrainingCollegeService.get(Integer.valueOf(s));
								ProjectCollegeRange psr = new ProjectCollegeRange();
								psr.setTrainingCollege(ts);
								psr.setProject(pro);
								psr.setCreator(us.getId());
								this.iProjectCollegeRangeService.add(psr);
							}
						}
					}
				}

				sendResponse("OK", "编辑成功");
				return;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			sendResponse("ERROR", "操作失败,请检查输入选项");
			return;
		}

	}

	// 发送信息
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	// 属性
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + "&" + pt.getName();
		}
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}
	
	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}

	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}
	
	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}
	
	public IProjectSubjectRangeService getiProjectSubjectRangeService() {
		return iProjectSubjectRangeService;
	}

	public void setiProjectSubjectRangeService(IProjectSubjectRangeService iProjectSubjectRangeService) {
		this.iProjectSubjectRangeService = iProjectSubjectRangeService;
	}
	
	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}
	
	public IProjectCollegeRangeService getiProjectCollegeRangeService() {
		return iProjectCollegeRangeService;
	}

	public void setiProjectCollegeRangeService(IProjectCollegeRangeService iProjectCollegeRangeService) {
		this.iProjectCollegeRangeService = iProjectCollegeRangeService;
	}
	
	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	public void setiOrganizationService(IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public HashMap<EDocumentType, fileInfo> getHmFiles() {
		return hmFiles;
	}

	public void setHmFiles(HashMap<EDocumentType, fileInfo> hmFiles) {
		this.hmFiles = hmFiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEnrollType() {
		return enrollType;
	}

	public void setEnrollType(String enrollType) {
		this.enrollType = enrollType;
	}

	public String getTraintype() {
		return traintype;
	}

	public void setTraintype(String traintype) {
		this.traintype = traintype;
	}

	public String getSubjectMax() {
		return subjectMax;
	}

	public void setSubjectMax(String subjectMax) {
		this.subjectMax = subjectMax;
	}

	public String getTimeup() {
		return timeup;
	}

	public void setTimeup(String timeup) {
		this.timeup = timeup;
	}

	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDoPath() {
		return doPath;
	}

	public void setDoPath(String doPath) {
		this.doPath = doPath;
	}
	
	public String getDoTitle() {
		return doTitle;
	}

	public void setDoTitle(String doTitle) {
		this.doTitle = doTitle;
	}

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

	public List<ProjectType> getFirst() {
		return first;
	}

	public void setFirst(List<ProjectType> first) {
		this.first = first;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getRestrictRightId() {
		return restrictRightId;
	}

	public void setRestrictRightId(String[] restrictRightId) {
		this.restrictRightId = restrictRightId;
	}

	public String[] getRestrictRightList() {
		return restrictRightList;
	}

	public void setRestrictRightList(String[] restrictRightList) {
		this.restrictRightList = restrictRightList;
	}

	public String getRestrictSubject() {
		return restrictSubject;
	}

	public void setRestrictSubject(String restrictSubject) {
		this.restrictSubject = restrictSubject;
	}
	
	public String[] getRestrictSubjectId() {
		return restrictSubjectId;
	}

	public void setRestrictSubjectId(String[] restrictSubjectId) {
		this.restrictSubjectId = restrictSubjectId;
	}

	public List<String[]> getRestrictSubjectList() {
		return restrictSubjectList;
	}

	public void setRestrictSubjectList(List<String[]> restrictSubjectList) {
		this.restrictSubjectList = restrictSubjectList;
	}
	
	public String getRestrictCollege() {
		return restrictCollege;
	}

	public void setRestrictCollege(String restrictCollege) {
		this.restrictCollege = restrictCollege;
	}
	
	public String[] getRestrictCollegeId() {
		return restrictCollegeId;
	}

	public void setRestrictCollegeId(String[] restrictCollegeId) {
		this.restrictCollegeId = restrictCollegeId;
	}

	public List<String[]> getRestrictCollegeList() {
		return restrictCollegeList;
	}

	public void setRestrictCollegeList(List<String[]> restrictCollegeList) {
		this.restrictCollegeList = restrictCollegeList;
	}

	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
	
}
