package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 任务下达业务逻辑
 * 
 * @author Administrator
 * 
 */
public class AssignTeacherTaskAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(AssignTeacherTaskAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IOrganizationService iOrganization; // 组织架构
	private ITrainingCollegeService iTrainingCollegeService; // 承训单位
	private ITrainingSubjectService iTrainingSubjectService; // 培训科目

	private IProjectApplyService iProjectApplyService; // 项目中标信息
	private IProjectService iProjectService; // 项目信息
	private IProjectAdminService iProjectAdminService;

	private IAssignTeacherTaskService iAssignTeacherTaskService;
	private ArrayList<String[]> organization;
	private ArrayList<String[]> subject;
	private ArrayList<String[]> training;
	private ArrayList<String[]> value;

	private LinkedHashMap<String, Object[]> projectApplyHash;

	private List<String> searchYear;
	private List<Project> searchReportTask;
	private String selectYear;
	private String selectProjectId;
	
	private String organizationLevel;

	public AssignTeacherTaskAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 列表页面，获取任务列表，根据当前用户登陆的组织架构来判断当前用的级别 不同级别的数据不同
	 * 
	 * 1. 不同级别的操作也不同 2. 不同级别获取的人数方式也不同 3. 流程 1.首先查看本级别添加的任务 2.查看由上级分发的任务
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String initPage() {
		
		initServlert();
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		Hashtable<String, Object> outHt = new Hashtable<>();
		
		UserSession us = (UserSession) session.getAttribute("usersession");
		String rprojectId = request.getParameter("projectName");
		String rsubjectId = request.getParameter("subjectName");
		String year = request.getParameter("year");
		
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
			
		this.selectYear = year ;
		this.selectProjectId = rprojectId;
		this.organizationLevel = us.getOrganizationLevel() + "";

		this.iAssignTeacherTaskService.getAssignTeacherTaskByOrganization(request, us, year , rprojectId, rsubjectId , outHt);
		//隋总算页数后面用的
		session.setAttribute("assigntasksql", outHt.get("assigntasksql").toString());

		// 查询数据
		List li = (List) outHt.get("data");

		projectApplyHash = new LinkedHashMap<String, Object[]>();
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			if (Integer.valueOf(obj[10].toString()) > 0) {
				int assignId = (int) obj[0];
				int projectid = (int) obj[1];
				String projectName = obj[2].toString();

				short subjectid = (short) obj[3];
				String subjectName = obj[4].toString();

				byte status = (byte) obj[5];
				String timeup = Utlity.timeSpanToDateString(obj[6]);

				int creator = (int) obj[7];
				String creattime = Utlity.timeSpanToString(obj[8]);

				byte level = (byte) obj[9];
				int countNum = Integer.valueOf(obj[10].toString());
//				String startTime=Utlity.timeSpanToString(obj[11]);

				ProjectAdmin pa = this.iProjectAdminService.get(creator);
				String taskCreator = pa.getOrganization().getName() + "-" + pa.getName();

				String key = projectid + "-" + subjectid;

				if (!projectApplyHash.containsKey(key)) {
					Object[] values = new Object[15];

					values[0] = projectName;
					values[1] = subjectName;
					values[2] = countNum;

					// 获取
					Object[] params = new Object[] { projectid, subjectid };
					Hashtable<String, Object> teachNum = new Hashtable<>();
					this.iAssignTeacherTaskService.getTrainingRecordsAllTeacherNumber(params, us, teachNum);

					values[3] = teachNum.get("total").toString();
					values[4] = teachNum.get("passNum").toString();
					values[5] = teachNum.get("noPass").toString();
					values[6] = status == 1 ? "可报送" : "停止报送";

					values[7] = timeup;
					values[8] = taskCreator;
					values[9] = creattime;

					if (us.isSchool()) {
						values[10] = 1;
					} else {
						if (us.getOrganizationLevel() == 1) {
							// 管理员创建的
							values[10] = 2;
						} else {
							if (level == 1 && us.getOrganization() == pa.getOrganization().getId()) {
								// 管理员创建的
								values[10] = 2;
							} else {
								values[10] = 3;
							}
						}
					}
					int organizationid=us.getOrganization();
					int assignStatus = this.iAssignTeacherTaskService.getAssignStatus(projectid,subjectid,organizationid);
					if (assignStatus == 0) {
						values[11] = "未分配";
					} else {
						values[11] = "已分配";
					}
//					values[12]=startTime.substring(0,startTime.length()-8);

					this.projectApplyHash.put(key, values);

				}

			}

		}
		return "init";
	}

	@SuppressWarnings("unchecked")
	public String initAssignResult() {
		initServlert();

		// 查询出所有已经发布的项目
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);

		return "presult";
	}

	public void assignResult() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		//short roleid = us.getRole();
		int Organization = us.getOrganization();
		String projectId = request.getParameter("id");
		String error = "{\"status\":\"ERROR\"}";
		if (projectId != null && !projectId.equals("")) {

			List<AssignTeacherTask> li = this.iAssignTeacherTaskService.getAssignTeacherTaskByProject(Integer.valueOf(projectId), Organization);
			if (li != null && !li.isEmpty()) {
				this.organization = new ArrayList<String[]>();
				this.subject = new ArrayList<String[]>();
				this.training = new ArrayList<String[]>();
				this.value = new ArrayList<String[]>();
				for (int i = 0; i < li.size(); i++) {
					String[] sv = new String[8];
					sv[0] = Integer.toString(li.get(i).getTrainingCollege().getId());
					sv[1] = li.get(i).getTrainingCollege().getName();
					sv[2] = Integer.toString(li.get(i).getTrainingSubject().getId());
					sv[3] = li.get(i).getTrainingSubject().getName();
					sv[4] = Integer.toString(li.get(i).getOrganizationByGOrganization().getId());
					sv[5] = li.get(i).getOrganizationByGOrganization().getName();
					sv[6] = Integer.toString(li.get(i).getOrganizationByPOrganization().getId());
					sv[7] = Integer.toString(li.get(i).getTeacherNumber());
					String[] sj = { sv[2], sv[3] };
					String[] tc = { sv[0], sv[1] };
					String[] og = { sv[4], sv[5] };
					String[] vl = { sv[0], sv[2], sv[4], sv[7] };
					boolean add_t = true;
					boolean add_o = true;
					if (sv[4].equals(sv[6])) {
						add_o = false;
					} else {
						for (int j = 0; j < organization.size(); j++) {
							if (sv[4].equals(organization.get(j)[0])) {
								add_o = false;
							}
						}
					}
					if (add_o)
						organization.add(og);
					for (int k = 0; k < training.size(); k++) {
						if (sv[0].equals(training.get(k)[0]) && sv[2].equals(subject.get(k)[0]))
							add_t = false;
					}
					if (add_t) {
						subject.add(sj);
						training.add(tc);
					}
					if (!sv[4].equals(sv[6]))
						value.add(vl);
				}
				StringBuilder sb = new StringBuilder();
				sb.append("{\"status\":\"OK\",");
				sb.append("\"organization\":[");
				for (int i = 0; i < organization.size(); i++) {
					if (i != 0)
						sb.append(",");
					sb.append("{\"id\":" + (organization.get(i)[0]) + ",\"name\":\"" + (organization.get(i)[1]) + "\"}");
				}
				sb.append("],\"trainingsubject\":[");
				for (int i = 0; i < training.size(); i++) {
					if (i != 0)
						sb.append(",");
					sb.append("{\"trainingid\":" + (training.get(i)[0]) + ",\"trainingname\":\"" + (training.get(i)[1]) + "\",\"subjectid\":" + (subject.get(i)[0]) + ",\"subjectname\":\"" + (subject.get(i)[1]) + "\"}");
				}
				sb.append("],\"value\":{");
				for (int i = 0; i < value.size(); i++) {
					if (i != 0)
						sb.append(",");
					sb.append("\"" + (value.get(i)[0]) + "_" + (value.get(i)[1]) + "_" + (value.get(i)[2]) + "\":\"" + (value.get(i)[3]) + "\"");
				}
				sb.append("}}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			} else {
				Utlity.ResponseWrite(error, "application/json", response);
			}
		} else {
			Utlity.ResponseWrite(error, "application/json", response);
		}
	}

	public void delteTeacherTask() {

		try {

			initServlert();

			String psid = request.getParameter("id");

			if (psid == null) {
				sendResponse("ERROR", "删除失败，不存在任务分配信息");
			}

			String[] t = psid.split("-");

			String projectId = t[0];
			String subjectId = t[1];

			UserSession user = (UserSession) session.getAttribute("usersession");

			String hql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId + " and t.organizationByGOrganization=" + user.getOrganization();

			List<AssignTeacherTask> liapply = this.iAssignTeacherTaskService.getListByHSQL(hql);

			boolean flag = false;

			for (AssignTeacherTask att : liapply) {
				if (att.getAssignTeacherTasks().size() > 0) {
					flag = true;
					break;
				}
			}

			if (flag) {
				// 不可以删除
				sendResponse("ERROR", "删除失败，存在下级任务分配信息");
			} else {
				// 可以删除
				for (AssignTeacherTask att : liapply) {
					this.iAssignTeacherTaskService.delete(att);
				}
				sendResponse("OK", "删除成功");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			sendResponse("ERROR", "删除失败");
		}

	}

	@SuppressWarnings("rawtypes")
	public void getPageJson() {
		initServlert();

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		String sql = "select count(*) from (" + session.getAttribute("assigntasksql").toString() + ") t";

		List licount = this.iAssignTeacherTaskService.executeSQL(sql, null);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		if (licount != null && licount.size() > 0) {
			int records = Integer.parseInt(licount.get(0).toString());
			int total = records / DictionyMap.maxPageSize;
			if ((records % DictionyMap.maxPageSize) > 0) {
				total += 1;
			}
			sb.append("\"totalPage\":" + total);
		} else {
			sb.append("\"totalPage\":" + 0);
		}
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
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

	// 属性区

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
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

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IAssignTeacherTaskService getiAssignTeacherTaskService() {
		return iAssignTeacherTaskService;
	}

	public void setiAssignTeacherTaskService(IAssignTeacherTaskService iAssignTeacherTaskService) {
		this.iAssignTeacherTaskService = iAssignTeacherTaskService;
	}

	public LinkedHashMap<String, Object[]> getProjectApplyHash() {
		return projectApplyHash;
	}

	public void setProjectApplyHash(LinkedHashMap<String, Object[]> projectApplyHash) {
		this.projectApplyHash = projectApplyHash;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public String getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
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
