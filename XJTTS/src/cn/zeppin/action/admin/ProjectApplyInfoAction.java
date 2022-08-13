package cn.zeppin.action.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyWorkReport;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectApplyInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectApplyInfoAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectApplyService iProjectApplyService;
	private IProjectTypeService iProjectTypeService;
	private IProjectService iProjectService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IAreaService iAreaService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingAdminService iTrainingAdminService;
	private IProjectExpertService iProjectExpertService;
	private LinkedHashMap<Integer, String[]> projectApplyHash;
	private Map<Integer, List<Map>> studyhourMap;
	private List<String> searchYear;
	private List<ProjectType> searchProjectType;
	private List<Project> searchReportTask;
	private List<teachingSubjectEx> searchSubject;
	private String selectProjectId;
	private String selectProjectTypeId;
	private String selectSubjectId;
	private String selectYear;
	private String selectStatus;
	private int isAdd;

	public ProjectApplyInfoAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	// 项目申报列表
	/**
	 * @author Administrator
	 * @category 项目申报
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String initPage() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.isAdd = 1;
		if (us != null && us.getOrganizationLevel() > 1) {
			this.isAdd = 0;
		}

		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		Map<String, String> sortParams = new HashMap<>();
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");

		String year = request.getParameter("year");
		String rprojectTypeId = request.getParameter("projectType");
		String rprojectId = request.getParameter("projectName");
		String rsubjectId = request.getParameter("subjectName");
		String status = request.getParameter("status");
		this.selectProjectTypeId = rprojectTypeId;
		this.selectProjectId = rprojectId;
		this.selectSubjectId = rsubjectId;
		this.selectYear = year;
		this.selectStatus = status;
		Map<String, Object> paramss = new HashMap<>();
		paramss.put("level", us.getOrganizationLevel());
		this.searchProjectType = this.iProjectTypeService.getProjectTypeList(
				lityps, paramss);
		for (ProjectType pt : this.searchProjectType) {
			if (pt.getLevel() > 1) {
				String space = "";
				for (int i = 0; i < pt.getLevel(); i++) {
					space += "--";
				}
				pt.setName(space + pt.getName());
			}
		}
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String, Object> map = new HashMap();
		if (year != null && !year.equals("0")) {
			map.put("year", year);
		}
		if (rprojectTypeId != null && !rprojectTypeId.equals("0")) {
			ProjectType pt = this.iProjectTypeService.get(Integer
					.valueOf(rprojectTypeId));
			map.put("projectType.scode", pt.getScode());
		}

		// UserSession us = (UserSession) session.getAttribute("usersession");
		map.put("organization", us.getOrganization());
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				sortParams, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		// this.searchReportTask =
		// this.iProjectService.getProjectByParams(map,null,lityps);
		if (rprojectId != null && !rprojectId.equals("0")) {
			map.put("project", rprojectId);
		}
		List<ProjectApply> lipa = this.iProjectApplyService
				.getProjectApplyByParams(map, null, lityps);
		List<teachingSubjectEx> lis = new ArrayList<teachingSubjectEx>();
		for (ProjectApply pa : lipa) {
			if (!lis.contains(pa.getTrainingSubject())) {
				TrainingSubject ts = pa.getTrainingSubject();
				teachingSubjectEx tse = new teachingSubjectEx();
				tse.setId(ts.getId().toString());
				tse.setName(ts.getName());
				tse.setSearchString(ts.getName()
						+ pinyingUtil.getFirstSpell(ts.getName()));
				lis.add(tse);
			}
		}
		this.searchSubject = lis;
		Map<String, Object> params = new HashMap<>();
		if (rprojectId != null && !rprojectId.equals("")
				&& !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		if (year != null && !year.equals("") && !year.equals("0")) {
			params.put("year", year);
		}
		if (status != null && !status.equals("") && !status.equals("-1")) {
			params.put("status", status);
		}
		if (rsubjectId != null && !rsubjectId.equals("")
				&& !rsubjectId.equals("0")) {
			params.put("subjectId", rsubjectId);
		}
		if (rprojectTypeId != null && !rprojectTypeId.equals("0")) {
			ProjectType pt = this.iProjectTypeService.get(Integer
					.valueOf(rprojectTypeId));
			params.put("projectType.scode", pt.getScode());
		}
		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("id", "desc");
		}
		int offset = (start - 1) * DictionyMap.maxPageSize;

		params.put("organization", us.getOrganization());
		// params.put("level", us.getOrganizationLevel());

		List li = this.iProjectApplyService.getProjectApplyByParams(params,
				sortParams, lityps, offset, DictionyMap.maxPageSize);
		this.projectApplyHash = new LinkedHashMap<Integer, String[]>();
		this.studyhourMap = new HashMap<Integer, List<Map>>();

		for (Object ba : li) {
			Object[] obj = (Object[]) ba;
			ProjectApply pa = (ProjectApply) obj[0];
			int id = pa.getId();
			if (!this.projectApplyHash.containsKey(id)) {
				String[] sv = new String[30];
				Project p = pa.getProject();
				sv[0] = p.getName();

				TrainingSubject ts = pa.getTrainingSubject();
				sv[1] = ts.getName();

				TrainingCollege tc = pa.getTrainingCollege();
				sv[2] = tc.getName();

				sv[3] = p.getYear();

				ProjectType pt = p.getProjectType();
				sv[4] = getNaviString(pt);

				sv[5] = getAreaNaviString(pt.getArea());
				if (p.getTraintype() == 1) {
					sv[6] = "集中面授（同步在线）";
				} else if (p.getTraintype() == 2) {
					sv[6] = "远程培训";
				} else {
					sv[6] = "混合培训方式";
				}
				if (pa.getTrainingStarttime() != null) {
					sv[7] = Utlity.timeSpanToDateString(pa
							.getTrainingStarttime());
				} else {
					sv[7] = "待定";
				}
				if (pa.getTrainingEndtime() != null) {
					sv[8] = Utlity
							.timeSpanToDateString(pa.getTrainingEndtime());
				}

				sv[9] = pa.getTrainingClasshour().toString();
				sv[23] = pa.getTrainingOnlineHour().toString();
				sv[24] = p.getTraintype().toString();
				sv[10] = pa.getApproveNumber().toString();
				// ProjectAdmin pacreator =
				// this.iProjectAdminService.get(pa.getCreator());
				// if (pacreator != null) {
				// sv[11] = pacreator.getName();
				// } else {
				// TrainingAdmin ta =
				// this.getiTrainingAdminService().get(pa.getCreator());
				// if (ta != null) {
				// sv[11] = ta.getName();
				// }
				// }
				// TrainingAdmin ta =
				// this.getiTrainingAdminService().get(pa.getCreator());
				Short type = pa.getType();
				if (type == 1) {
					TrainingAdmin ta = this.getiTrainingAdminService().get(
							pa.getCreator());
					if (ta != null) {
						sv[11] = ta.getName();
					} else {
						sv[11] = "";
					}
				} else if (type == 2) {
					ProjectAdmin pacreator = this.iProjectAdminService.get(pa
							.getCreator());
					if (pacreator != null) {
						sv[11] = pacreator.getName();
					} else {
						sv[11] = "";
					}
				} else {
					sv[11] = "";
				}
				// if (ta != null) {
				// sv[11] = ta.getName();
				// }else{
				// ProjectAdmin pacreator =
				// this.iProjectAdminService.get(pa.getCreator());
				// if (pacreator != null) {
				// sv[11] = pacreator.getName();
				// }
				// }

				sv[12] = pa.getContacts();
				sv[13] = pa.getPhone();
				sv[14] = Utlity.timeSpanToString(pa.getCreattime());
				if (p.getPsqByProjectJudgePsq() != null) {
					int projectId = p.getId();
					int subjectId = ts.getId();
					int trainingId = tc.getId();
					int psqId = p.getPsqByProjectJudgePsq().getId();
					int viewNum = iProjectApplyService.getExpertViewCount(
							psqId, projectId, subjectId, trainingId);
					if (viewNum != 0) {
						int totalScore = iProjectApplyService
								.getExpertViewTotalScore(psqId, projectId,
										subjectId, trainingId);
						int maxScore = iProjectApplyService
								.getExpertPsqMaxScore(projectId);
						float score = (float) (Math
								.round(((float) totalScore / (float) viewNum) * 100)) / 100;
						sv[15] = score + "(满分：" + maxScore + ")";
					} else {
						sv[15] = "无";
					}
				} else {
					sv[15] = "无";
				}
				// switch (pa.getStatus()) {
				// case 0:
				// sv[16] = "审核不通过";
				// break;
				// case 1:
				// sv[16] = "未审核";
				// break;
				// case 2:
				// sv[16] = "审核通过";
				// break;
				// case 3:
				// sv[16] = "已中标";
				// break;
				// case 4:
				// sv[16] = "未中标";
				// break;
				// }
				sv[16] = pa.getStatus() + "";

				if (pa.getApprover() != null) {
					ProjectAdmin padmin = this.iProjectAdminService.get(pa
							.getApprover());
					if (padmin != null) {
						sv[17] = padmin.getName();
					}

				}
				sv[18] = Utlity.timeSpanToString(pa.getApprovetime());
				if (pa.getDocumentByProjectApplyDocument() != null) {
					sv[19] = pa.getDocumentByProjectApplyDocument().getTitle();
					sv[20] = pa.getDocumentByProjectApplyDocument()
							.getResourcePath();
				}
				sv[21] = p.getEnrollType().toString();
				if (pa.getEnrollEndTime() != null) {
					sv[22] = Utlity.timeSpanToDateString(pa.getEnrollEndTime());
				}
				String remark = "";
				if(pa.getRemark() != null && !"".equals(pa.getRemark())){
					remark = pa.getRemark();
				}else{
					remark = "无";
				}
				sv[29]= remark;
				
				this.projectApplyHash.put(id, sv);
				this.studyhourMap.put(id, JSONUtils.json2list(pa.getStudyhour(), Map.class));
			}
		}

		return "init";
	}

	@SuppressWarnings({ "unchecked" })
	public void output() throws IOException {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		Map<String, String> sortParams = new HashMap<>();
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		String year = request.getParameter("year");
		String rprojectTypeId = request.getParameter("projectType");
		String rprojectId = request.getParameter("projectName");
		String rsubjectId = request.getParameter("subjectName");
		String status = request.getParameter("status");

		Map<String, Object> params = new HashMap<>();
		if (rprojectId != null && !rprojectId.equals("")
				&& !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		if (year != null && !year.equals("") && !year.equals("0")) {
			params.put("year", year);
		}
		if (status != null && !rsubjectId.equals("") && !status.equals("-1")) {
			params.put("status", status);
		}
		if (rsubjectId != null && !rsubjectId.equals("")
				&& !rsubjectId.equals("0")) {
			params.put("subjectId", rsubjectId);
		}
		if (rprojectTypeId != null && !rprojectTypeId.equals("0")) {
			ProjectType pt = this.iProjectTypeService.get(Integer
					.valueOf(rprojectTypeId));
			params.put("projectType.scode", pt.getScode());
		}
		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("id", "desc");
		}

		params.put("organization", us.getOrganization());
		// params.put("level", us.getOrganizationLevel());

		List li = this.iProjectApplyService.getProjectApplyByParams(params, sortParams, lityps, 0, -1);
		List<String> studyhourTitleList = new ArrayList<String>();
		for (Object ba : li) {
			Object[] obj = (Object[]) ba;
			ProjectApply pa = (ProjectApply) obj[0];
			List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
			if(studyhourList != null) {
				for(Map map : studyhourList){
					String titleName = map.get("nameCN").toString();
					Boolean flagName = true;
					for(String title : studyhourTitleList){
						if(title.equals(titleName)){
							flagName = false;
						}
					}
					if(flagName){
						studyhourTitleList.add(map.get("nameCN").toString());
					}
				}
			} else {
				studyhourList = JSONUtils.json2list(pa.getProjectCycle().getStudyhour(), Map.class);
				for(Map map : studyhourList){
					String titleName = map.get("nameCN").toString();
					Boolean flagName = true;
					for(String title : studyhourTitleList){
						if(title.equals(titleName)){
							flagName = false;
						}
					}
					if(flagName){
						studyhourTitleList.add(map.get("nameCN").toString());
					}
				}
			}
		}
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "项目列表");
		HSSFRow row = s.createRow(0);
		List<String> titleList = new ArrayList<String>();
		String[] titleArrayFront = { "开设年份", "项目类型", "项目名称", "培训学科", "承训院校", "所属地区", "培训形式", "培训开始时间", "培训结束时间"};
		for(String title : titleArrayFront){
			titleList.add(title);
		}
		for(String title : studyhourTitleList){
			titleList.add(title);
		}
		String[] titleArrayBack = {"培训人数", "提交申报人", "专家初审评分", "状态" };
		for(String title : titleArrayBack){
			titleList.add(title);
		}
		for (int j = 0; j < titleList.size(); j++) {
			row.createCell(j).setCellValue(titleList.get(j));
		}
		int t = 0;
		for (Object ba : li) {
			Object[] obj = (Object[]) ba;
			ProjectApply pa = (ProjectApply) obj[0];
			Project p = pa.getProject();
			ProjectType pt = p.getProjectType();
			TrainingSubject ts = pa.getTrainingSubject();
			TrainingCollege tc = pa.getTrainingCollege();

			t++;
			row = s.createRow(t);
			List<String> infoList = new ArrayList<String>();

			infoList.add(p.getYear());
			infoList.add(getNaviString(pt));
			infoList.add(p.getName());
			infoList.add(ts.getName());
			infoList.add(tc.getName());
			infoList.add(getAreaNaviString(pt.getArea()));
			if (p.getTraintype() == 1) {
				infoList.add("集中面授（同步在线）");
			} else if (p.getTraintype() == 2) {
				infoList.add("远程培训");
			} else {
				infoList.add("混合培训方式");
			}
			if (pa.getTrainingStarttime() != null) {
				infoList.add(Utlity.timeSpanToDateString(pa
						.getTrainingStarttime()));
			} else {
				infoList.add("待定");
			}
			if (pa.getTrainingEndtime() != null) {
				infoList.add(Utlity.timeSpanToDateString(pa
						.getTrainingEndtime()));
			} else {
				infoList.add("");
			}
			
			List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
			if(studyhourList != null){
				for(String title : studyhourTitleList){
					Boolean titleFlag = true;
					for(Map map : studyhourList){
						String titleName = map.get("nameCN").toString();
						if(title.equals(titleName)){
							titleFlag = false;
//							infoList.add(map.get("value").toString());
							infoList.add("--");
						}
					}
					if(titleFlag){
						infoList.add("--");
					}
				}
			}else{
				for (int j = 0; j < studyhourTitleList.size(); j++) {
					infoList.add("--");
				}
			}
			
			infoList.add(pa.getApproveNumber().toString());
			Short type = pa.getType();
			if (type == 1) {
				TrainingAdmin ta = this.getiTrainingAdminService().get(
						pa.getCreator());
				if (ta != null) {
					infoList.add(ta.getName());
				} else {
					infoList.add("");
				}
			} else if (type == 2) {
				ProjectAdmin pacreator = this.iProjectAdminService.get(pa
						.getCreator());
				if (pacreator != null) {
					infoList.add(pacreator.getName());
				} else {
					infoList.add("");
				}
			} else {
				infoList.add("");
			}
			if (p.getPsqByProjectJudgePsq() != null) {
				int projectId = p.getId();
				int subjectId = ts.getId();
				int trainingId = tc.getId();
				int psqId = p.getPsqByProjectJudgePsq().getId();
				int viewNum = iProjectApplyService.getExpertViewCount(psqId,
						projectId, subjectId, trainingId);
				if (viewNum != 0) {
					int totalScore = iProjectApplyService
							.getExpertViewTotalScore(psqId, projectId,
									subjectId, trainingId);
					int maxScore = iProjectApplyService
							.getExpertPsqMaxScore(projectId);
					float score = (float) (Math
							.round(((float) totalScore / (float) viewNum) * 100)) / 100;
					infoList.add(score + "(满分：" + maxScore + ")");
				} else {
					infoList.add("无");
				}
			} else {
				infoList.add("无");
			}
			switch (pa.getStatus()) {
			case 0:
				infoList.add("审核不通过");
				break;
			case 1:
				infoList.add("未审核");
				break;
			case 2:
				infoList.add("审核通过");
				break;
			case 3:
				infoList.add("已中标");
				break;
			case 4:
				infoList.add("未中标");
			}

			for (int j = 0; j < infoList.size(); j++) {
				row.createCell(j).setCellValue(infoList.get(j));
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=projectApplyList.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	/**
	 * 根据条件获取项目列表（筛选区）
	 */
	public void getAssignTaskProject() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");
		String projectType = this.request.getParameter("projectType");
		Map<String, Object> map = new HashMap<String, Object>();
		if (year != null && !year.equals("0")) {
			map.put("year", year);
		}
		if (projectType != null && !projectType.endsWith("0")) {
			ProjectType pt = this.iProjectTypeService.get(Integer
					.valueOf(projectType));
			map.put("projectType.scode", pt.getScode());
		}
		map.put("organization", us.getOrganization());
		// map.put("level", us.getOrganizationLevel());
		@SuppressWarnings("rawtypes")
		List lip = this.iProjectService.getProjectListByParams4LevelThree(map, null,
				lityps, -1, -1);
		// List<Project> lip =
		// this.iProjectService.getProjectByParams(map,null,lityps);

		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
		for (int i = 0; i < lip.size(); i++) {
			Object[] obj = (Object[]) lip.get(i);
			Project p = (Project) obj[0];
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName()
					+ "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	public void getExpertViewList() {

		initServlert();
		String paid = request.getParameter("paid");

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"rows\":[");
		@SuppressWarnings("rawtypes")
		List Submitlist = this.iProjectApplyService
				.getProjectApplyExpertInfo(Integer.valueOf(paid));
		for (int i = 0; i < Submitlist.size(); i++) {
			Object[] ob = (Object[]) Submitlist.get(i);
			Submit s = (Submit) ob[2];
			int submit = s.getId();
			String expertName = iProjectExpertService.get(s.getCreater())
					.getName();
			int score = iProjectApplyService.getScoreBySubmit(submit);
			int num = i + 1;
			String st = "{\"id\":" + num + ",\"expert\":\"" + expertName
					+ "\",\"score\":\"" + score + "\"},";

			sb.append(st);
		}

		if (Submitlist.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	@SuppressWarnings("unchecked")
	public void getPageJson() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 搜索参数

		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		String year = request.getParameter("year");
		String status = request.getParameter("status");
		String rprojectType = request.getParameter("projectType");
		String rprojectId = request.getParameter("projectName");
		String rsubjectId = request.getParameter("subjectName");
		Map<String, Object> params = new HashMap<>();
		if (year != null && !year.equals("") && !year.equals("0")) {
			params.put("year", year);
		}
		if (status != null && !status.equals("") && !status.equals("-1")) {
			params.put("status", status);
		}
		if (rprojectId != null && !rprojectId.equals("")
				&& !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		if (rprojectType != null && !rprojectType.equals("")
				&& !rprojectType.equals("0")) {
			Integer projectType = Integer.valueOf(rprojectType);
			ProjectType pt = this.iProjectTypeService.get(projectType);
			params.put("projectType.scode", pt.getScode());
		}
		if (rsubjectId != null && !rsubjectId.equals("")
				&& !rsubjectId.equals("0")) {
			params.put("subjectId", rsubjectId);
		}

		params.put("organization", us.getOrganization());
		// params.put("level", us.getOrganizationLevel());

		int records = this.iProjectApplyService.getProjectApplyCountByParams(
				params, lityps);
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");
		sb.append("\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	@SuppressWarnings("unchecked")
	public void getStatusCount() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		// String ist = (String) request.getParameter("page");
		// if (ist == null || ist.equals("") || ist.equals("NaN")) {
		// ist = "1";
		// }
		//
		// int start = Integer.parseInt(ist);

		// 搜索参数

		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		String year = request.getParameter("year");
		// String status = request.getParameter("status");
		String rprojectType = request.getParameter("projectType");
		String rprojectId = request.getParameter("projectName");
		String rsubjectId = request.getParameter("subjectName");
		Map<String, Object> params = new HashMap<>();
		if (year != null && !year.equals("") && !year.equals("0")) {
			params.put("year", year);
		}
		// if (status != null && !status.equals("") && !status.equals("-1")) {
		// params.put("status", status);
		// }
		if (rprojectId != null && !rprojectId.equals("")
				&& !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		if (rprojectType != null && !rprojectType.equals("")
				&& !rprojectType.equals("0")) {
			Integer projectType = Integer.valueOf(rprojectType);
			ProjectType pt = this.iProjectTypeService.get(projectType);
			params.put("projectType.scode", pt.getScode());
		}
		if (rsubjectId != null && !rsubjectId.equals("")
				&& !rsubjectId.equals("0")) {
			params.put("subjectId", rsubjectId);
		}

		params.put("organization", us.getOrganization());
		// params.put("level", us.getOrganizationLevel());

		int records = this.iProjectApplyService.getProjectApplyCountByParams(
				params, lityps);
		params.put("status", "1");
		int noCheck = this.iProjectApplyService.getProjectApplyCountByParams(
				params, lityps);
		params.put("status", "2");
		int checkPass = this.iProjectApplyService.getProjectApplyCountByParams(
				params, lityps);
		params.put("status", "0");
		int checkNoPass = this.iProjectApplyService
				.getProjectApplyCountByParams(params, lityps);
		// int total = (int) Math.ceil((float) records /
		// DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");// 全部
		sb.append("\"noCheck\":" + (noCheck) + ",");// 未审核
		sb.append("\"checkPass\":" + (checkPass) + ",");// 已通过
		sb.append("\"checkNoPass\":" + (checkNoPass));// 未通过
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	// 项目类型
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + " > " + pt.getName();
		}
	}

	public String getAreaNaviString(Area a) {
		String areaid = a.getName();
		String areaid1 = "";
		String areaid2 = "";
		String areacode = a.getParentcode();
		if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
			// 当前的地区的父地区存在
			Area pae = this.iAreaService.getAreaByCode(areacode);
			areaid1 = pae.getName();
			areacode = pae.getParentcode();
			// 父父地区
			if (areacode != null && !areacode.equals("0")
					&& !areacode.equals("")) {
				pae = this.iAreaService.getAreaByCode(areacode);
				areaid2 = pae.getName();
				return areaid2 + " > " + areaid1 + " > " + areaid;

			} else {
				return areaid1 + " > " + areaid;
			}
		} else {
			return areaid;
		}
	}

	
	//20180315新增文件上传情况一览
	/**
	 * 材料信息管理页面初始化,查看材料上传情况总揽
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String documentInit() {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		String year = request.getParameter("year");
		Map<String,Object> map = new HashMap<String,Object>();
		if (year != null && !year.equals("0")) {
			map.put("year", year);
		}
		map.put("organization", user.getOrganization());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}
		return "document";
	}
	
	/**
	 * 获取文件上传情况详情
	 */
	public void getDocumentList() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		// 显示的条数
		String ien = (String) request.getParameter("pagesize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
			limit = Integer.parseInt(ien);
		}
		int offset = (start - 1) * DictionyMap.maxPageSize;

		// 排序

		try {
			int projectId = Integer.parseInt(request
					.getParameter("projectName"));
			int subjectId = Integer.parseInt(request
					.getParameter("subjectName"));
			int collegeId = Integer.parseInt(request
					.getParameter("trainingUnit"));
			String status = request.getParameter("status") == null ? "-1" : request.getParameter("status");
			if("".equals(status)){
				status = "-1";
			}
			String year = request.getParameter("year");
			
			Map<String, Object> params = new HashMap<String, Object>();
			if(projectId > 0){
				params.put("projectId", projectId);
			}
			if(subjectId > 0){
				params.put("subjectId", subjectId);
			}
			if(collegeId > 0){
				params.put("trainingCollege", collegeId);
			}
			if(!"-1".equals(status)){
				params.put("status", status);
			}
			if (year != null && !year.equals("") && !year.equals("0")) {
				params.put("year", year);
			}
			params.put("lityps", lityps);
			params.put("organization", user.getOrganization());
			

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			Integer totalcount = this.iProjectApplyService.getProjectApplyDCountByParams(params);
			int totalPage = (int) Math.ceil((float) totalcount / DictionyMap.maxPageSize);
			sb.append("\"TotalRecordCount\":" + totalcount);
			sb.append(",");
			sb.append("\"page\":{");
			sb.append("\"currentPage\":" + start + ",");
			sb.append("\"totalPage\":" + totalPage);
			sb.append("}");
			sb.append(",");
			List<ProjectApply> list = this.iProjectApplyService.getProjectApplyDByParams(params, null, offset, limit);
			if (list != null && list.size() > 0) {
				
				sb.append("\"Records\":[");

				for (ProjectApply pa : list) {
					sb.append("{");
					sb.append("\"id\":" + pa.getId());
					sb.append(",");
					// 申报项目学科信息
					String projectName = pa.getProject().getName();
					String trainingSubject = pa.getTrainingSubject().getName();
					String trainingCollege = pa.getTrainingCollege().getName();
					sb.append("\"projectName\":\"" + projectName + "\"");
					sb.append(",");
					sb.append("\"trainingSubject\":\"" + trainingSubject + "\"");
					sb.append(",");
					sb.append("\"trainingCollege\":\"" + trainingCollege + "\"");
					sb.append(",");
					// 材料上传情况
					// 需要上传的材料有：申报书、开班通知、工作简报、绩效报告、课程表、计划书
					//包括上传情况和下载路径+ID
					Document pad = pa.getDocumentByProjectApplyDocument();
					if (pad != null) {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"id\":"+pad.getId());
						sbstr.append(",");
						sbstr.append("\"status\":\"normal\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"已上传\"");
						sbstr.append(",");
						sbstr.append("\"path\":\""+pad.getResourcePath()+"\"");
						sbstr.append(",");
						sbstr.append("\"title\":\""+pad.getTitle()+"\"");
						sbstr.append("}");
						sb.append("\"projectApplyReport\":" + sbstr.toString()
								+ "");
					} else {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"unupload\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"未上传\"");
						sbstr.append("}");
						sb.append("\"projectApplyReport\":" + sbstr.toString()
								+ "");
					}
					sb.append(",");

					Document smd = pa.getDocumentByStartMessage();
					if (smd != null) {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"id\":"+smd.getId());
						sbstr.append(",");
						sbstr.append("\"status\":\"normal\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"已上传\"");
						sbstr.append(",");
						sbstr.append("\"path\":\""+smd.getResourcePath()+"\"");
						sbstr.append(",");
						sbstr.append("\"title\":\""+smd.getTitle()+"\"");
						sbstr.append("}");
						sb.append("\"startMessageReport\":" + sbstr.toString()
								+ "");
					} else {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"unupload\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"未上传\"");
						sbstr.append("}");
						sb.append("\"startMessageReport\":" + sbstr.toString()
								+ "");
					}
					sb.append(",");

					Set<ProjectApplyWorkReport> setpawr = pa.getProjectApplyWorkReport();
					if (setpawr != null && setpawr.size() > 0) {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"normal\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"已上传\"");
						sbstr.append(",");
						sbstr.append("\"reports\":[");
						for(ProjectApplyWorkReport pawr: setpawr){
							Document doc = pawr.getDocument();
							sbstr.append("{");
							sbstr.append("\"id\":"+doc.getId());
							sbstr.append(",");
							sbstr.append("\"path\":\""+doc.getResourcePath()+"\"");
							sbstr.append(",");
							sbstr.append("\"title\":\""+doc.getTitle()+"\"");
							sbstr.append("},");
						}
						sbstr.delete(sbstr.length() - 1, sbstr.length());
						sbstr.append("]");
						sbstr.append("}");
						sb.append("\"workReport\":" + sbstr.toString()
								+ "");
					} else {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"unupload\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"未上传\"");
						sbstr.append("}");
						sb.append("\"workReport\":" + sbstr.toString()
								+ "");
					}
					sb.append(",");

					Document prd = pa.getDocumentByProformanceReport();
					if (prd != null) {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"id\":"+prd.getId());
						sbstr.append(",");
						sbstr.append("\"status\":\"normal\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"已上传\"");
						sbstr.append(",");
						sbstr.append("\"path\":\""+prd.getResourcePath()+"\"");
						sbstr.append(",");
						sbstr.append("\"title\":\""+prd.getTitle()+"\"");
						sbstr.append("}");
						sb.append("\"proformanceReport\":" + sbstr.toString()
								+ "");
					} else {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"unupload\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"未上传\"");
						sbstr.append("}");
						sb.append("\"proformanceReport\":" + sbstr.toString()
								+ "");
					}
					sb.append(",");

					Document cd = pa.getDocumentByCurriculumDocument();
					if (cd != null) {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"id\":"+cd.getId());
						sbstr.append(",");
						sbstr.append("\"status\":\"normal\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"已上传\"");
						sbstr.append(",");
						sbstr.append("\"path\":\""+cd.getResourcePath()+"\"");
						sbstr.append(",");
						sbstr.append("\"title\":\""+cd.getTitle()+"\"");
						sbstr.append("}");
						sb.append("\"curriculumDocument\":" + sbstr.toString()
								+ "");
					} else {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"unupload\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"未上传\"");
						sbstr.append("}");
						sb.append("\"curriculumDocument\":" + sbstr.toString()
								+ "");
					}
					sb.append(",");
					
					Document ppd = pa.getDocumentByProjectPlan();
					if (ppd != null) {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"id\":"+ppd.getId());
						sbstr.append(",");
						sbstr.append("\"status\":\"normal\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"已上传\"");
						sbstr.append(",");
						sbstr.append("\"path\":\""+ppd.getResourcePath()+"\"");
						sbstr.append(",");
						sbstr.append("\"title\":\""+ppd.getTitle()+"\"");
						sbstr.append("}");
						sb.append("\"projectPlan\":" + sbstr.toString()
								+ "");
					} else {
						StringBuilder sbstr = new StringBuilder();
						sbstr.append("{");
						sbstr.append("\"status\":\"unupload\"");
						sbstr.append(",");
						sbstr.append("\"statusCN\":\"未上传\"");
						sbstr.append("}");
						sb.append("\"projectPlan\":" + sbstr.toString()
								+ "");
					}
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[");
			}
			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	public void getDocStatusCount() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		// String ist = (String) request.getParameter("page");
		// if (ist == null || ist.equals("") || ist.equals("NaN")) {
		// ist = "1";
		// }
		//
		// int start = Integer.parseInt(ist);

		// 搜索参数

		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		String year = request.getParameter("year");
		// String status = request.getParameter("status");
		String rprojectType = request.getParameter("projectType");
		String rprojectId = request.getParameter("projectName");
		String rsubjectId = request.getParameter("subjectName");
		Map<String, Object> params = new HashMap<>();
		if (year != null && !year.equals("") && !year.equals("0")) {
			params.put("year", year);
		}
		// if (status != null && !status.equals("") && !status.equals("-1")) {
		// params.put("status", status);
		// }
		if (rprojectId != null && !rprojectId.equals("")
				&& !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		if (rprojectType != null && !rprojectType.equals("")
				&& !rprojectType.equals("0")) {
			Integer projectType = Integer.valueOf(rprojectType);
			ProjectType pt = this.iProjectTypeService.get(projectType);
			params.put("projectType.scode", pt.getScode());
		}
		if (rsubjectId != null && !rsubjectId.equals("")
				&& !rsubjectId.equals("0")) {
			params.put("subjectId", rsubjectId);
		}

		params.put("organization", us.getOrganization());
		// params.put("level", us.getOrganizationLevel());
		params.put("lityps", lityps);
		
		int records = this.iProjectApplyService.getProjectApplyDCountByParams(
				params);
		params.put("status", "1");
		int noCheck = this.iProjectApplyService.getProjectApplyDCountByParams(
				params);
		params.put("status", "2");
		int checkPass = this.iProjectApplyService.getProjectApplyDCountByParams(
				params);
		params.put("status", "0");
		int checkNoPass = this.iProjectApplyService
				.getProjectApplyDCountByParams(params);
		// int total = (int) Math.ceil((float) records /
		// DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");// 全部
		sb.append("\"noCheck\":" + (noCheck) + ",");// 未审核
		sb.append("\"checkPass\":" + (checkPass) + ",");// 已通过
		sb.append("\"checkNoPass\":" + (checkNoPass));// 未通过
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	// 属性

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(
			ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(
			IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public LinkedHashMap<Integer, String[]> getProjectApplyHash() {
		return projectApplyHash;
	}

	public void setProjectApplyHash(
			LinkedHashMap<Integer, String[]> projectApplyHash) {
		this.projectApplyHash = projectApplyHash;
	}

	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	public void setiTrainingAdminService(
			ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
	}

	public List<ProjectType> getSearchProjectType() {
		return searchProjectType;
	}

	public void setSearchProjectType(List<ProjectType> searchProjectType) {
		this.searchProjectType = searchProjectType;
	}

	public List<teachingSubjectEx> getSearchSubject() {
		return searchSubject;
	}

	public void setSearchSubject(List<teachingSubjectEx> searchSubject) {
		this.searchSubject = searchSubject;
	}

	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public String getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(String selectYear) {
		this.selectYear = selectYear;
	}

	public String getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}

	public String getSelectProjectId() {
		return selectProjectId;
	}

	public void setSelectProjectId(String selectProjectId) {
		this.selectProjectId = selectProjectId;
	}

	public String getSelectProjectTypeId() {
		return selectProjectTypeId;
	}

	public void setSelectProjectTypeId(String selectProjectTypeId) {
		this.selectProjectTypeId = selectProjectTypeId;
	}

	public String getSelectSubjectId() {
		return selectSubjectId;
	}

	public void setSelectSubjectId(String selectSubjectId) {
		this.selectSubjectId = selectSubjectId;
	}

	public int getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(int isAdd) {
		this.isAdd = isAdd;
	}


	public Map<Integer, List<Map>> getStudyhourMap() {
		return studyhourMap;
	}
	

	public void setStudyhourMap(Map<Integer, List<Map>> studyhourMap) {
		this.studyhourMap = studyhourMap;
	}

}
