package cn.zeppin.action.admin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.Area;
//import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
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
import cn.zeppin.utility.Utlity;

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
	@SuppressWarnings("unused")
	private Map<String, Object> params;
	private LinkedHashMap<Integer, String[]> projectApplyHash;
	private List<String> searchYear;
	private List<Project> searchReportTask;
	private String selectProjectId;
	private String selectYear;

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

		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		Map<String, String> sortParams = new HashMap<>();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String year = request.getParameter("year");
		String rprojectId = request.getParameter("projectName");
		this.selectProjectId = rprojectId;
		this.selectYear = year;
		
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		
		Map<String, Object> params = new HashMap<>();
		if (rprojectId != null && !rprojectId.equals("") && !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		if(year != null  && !year.equals("") && !year.endsWith("0")){
			params.put("year", year);
		}
		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "desc");
		}
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List li = this.iProjectApplyService.getProjectApplyByParams(params, sortParams, lityps, offset, DictionyMap.maxPageSize);
		this.projectApplyHash = new LinkedHashMap<Integer, String[]>();

		for (Object ba : li) {
			Object[] obj = (Object[]) ba;
			ProjectApply pa = (ProjectApply) obj[0];
			int id = pa.getId();
			if (!this.projectApplyHash.containsKey(id)) {
				String[] sv = new String[23];
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

				sv[6] = p.getTraintype() == 1 ? "集中面授" : "远程培训";
				if (pa.getTrainingStarttime() != null) {
					sv[7] = Utlity.timeSpanToDateString(pa.getTrainingStarttime());
				}
				if (pa.getTrainingEndtime() != null) {
					sv[8] = Utlity.timeSpanToDateString(pa.getTrainingEndtime());
				}

				sv[9] = pa.getTrainingClasshour().toString();
				sv[10] = pa.getApproveNumber().toString();
//				ProjectAdmin pacreator = this.iProjectAdminService.get(pa.getCreator());
//				if (pacreator != null) {
//					sv[11] = pacreator.getName();
//				} else {
//					TrainingAdmin ta = this.getiTrainingAdminService().get(pa.getCreator());
//					if (ta != null) {
//						sv[11] = ta.getName();
//					}
//				}
//				TrainingAdmin ta = this.getiTrainingAdminService().get(pa.getCreator());
				Short type = pa.getType();
				if(type == 1){
					TrainingAdmin ta = this.getiTrainingAdminService().get(pa.getCreator());
					if(ta != null){
						sv[11] = ta.getName();
					}else{
						sv[11] = "";
					}
				}else if(type == 2){
					ProjectAdmin pacreator = this.iProjectAdminService.get(pa.getCreator());
					if (pacreator != null) {
						sv[11] = pacreator.getName();
					} else{
						sv[11] = "";
					}
				}else{
					sv[11] = "";
				}
//				if (ta != null) {
//					sv[11] = ta.getName();
//				}else{
//					ProjectAdmin pacreator = this.iProjectAdminService.get(pa.getCreator());
//					if (pacreator != null) {
//						sv[11] = pacreator.getName();
//					} 
//				}

				sv[12] = pa.getContacts();
				sv[13] = pa.getPhone();
				sv[14] = Utlity.timeSpanToString(pa.getCreattime());
				if(p.getPsqByProjectJudgePsq()!=null){
					int projectId=p.getId();
					int subjectId=ts.getId();
					int trainingId=tc.getId();
					int psqId=p.getPsqByProjectJudgePsq().getId();
					int viewNum=iProjectApplyService.getExpertViewCount(psqId,projectId,subjectId,trainingId);
					if(viewNum!=0){
						int totalScore=iProjectApplyService.getExpertViewTotalScore(psqId,projectId,subjectId,trainingId);
						int maxScore=iProjectApplyService.getExpertPsqMaxScore(projectId);
						float score =(float)(Math.round(((float)totalScore / (float)viewNum)*100))/100;
						sv[15] = score +"(满分："+maxScore+")";
					}else{
						sv[15]="无";
					}				
				}else{
					sv[15]="无";
				}
				switch (pa.getStatus()) {
				case 0:
					sv[16] = "审核不通过";
					break;
				case 1:
					sv[16] = "未审核";
					break;
				case 2:
					sv[16] = "审核通过";
					break;
				case 3:
					sv[16] = "已中标";
					break;
				case 4:
					sv[16] = "未中标";
					break;
				}

				if (pa.getApprover() != null) {
					ProjectAdmin padmin = this.iProjectAdminService.get(pa.getApprover());
					if (padmin != null) {
						sv[17] = padmin.getName();
					}

				}
				sv[18] = Utlity.timeSpanToString(pa.getApprovetime());
				if(pa.getDocumentByProjectApplyDocument()!=null){
					sv[19]=pa.getDocumentByProjectApplyDocument().getTitle();
					sv[20]=pa.getDocumentByProjectApplyDocument().getResourcePath();
				}
				sv[21] = p.getEnrollType().toString();
				if(pa.getEnrollEndTime()!=null){
					sv[22] =  Utlity.timeSpanToDateString(pa.getEnrollEndTime());
				}
				this.projectApplyHash.put(id, sv);
			}
		}

		return "init";
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
		List Submitlist=this.iProjectApplyService.getProjectApplyExpertInfo(Integer.valueOf(paid));
		for (int i=0;i<Submitlist.size();i++) {
			Object[] ob=(Object[]) Submitlist.get(i);
			Submit s=(Submit)ob[2];
			int submit=s.getId();
			String expertName=iProjectExpertService.get(s.getCreater()).getName();
			int score=iProjectApplyService.getScoreBySubmit(submit);
			int num=i+1;
			String st = "{\"id\":" + num + ",\"expert\":\"" + expertName + "\",\"score\":\"" + score + "\"},";

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

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 搜索参数

		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String rprojectId = request.getParameter("projectName");
		Map<String, Object> params = new HashMap<>();
		if (rprojectId != null && !rprojectId.equals("") && !rprojectId.equals("0")) {
			params.put("projectId", rprojectId);
		}
		int records = this.iProjectApplyService.getProjectApplyCountByParams(params, lityps);
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
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
			if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
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

	// 属性

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
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

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(ITrainingSubjectService iTrainingSubjectService) {
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

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}
	
	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public LinkedHashMap<Integer, String[]> getProjectApplyHash() {
		return projectApplyHash;
	}

	public void setProjectApplyHash(LinkedHashMap<Integer, String[]> projectApplyHash) {
		this.projectApplyHash = projectApplyHash;
	}

	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
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
	
	public String getSelectProjectId() {
		return selectProjectId;
	}

	public void setSelectProjectId(String selectProjectId) {
		this.selectProjectId = selectProjectId;
	}

}
