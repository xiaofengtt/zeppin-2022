package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyExpert;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyExpertService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectApplyInfoOptAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectApplyInfoOptAction.class);

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
	private IAssignTeacherTaskService iAssignTeacherTaskService;
	private IOrganizationService iOragnizationService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IProjectExpertService iProjectExpertService;
	private IProjectApplyExpertService iProjectApplyExpertService;

	private String id;
	private String document;
	private String project; // 项目
	private String trainingSubject; // 学科
	private String trainingCollege; // 承训单位
	private String type; // 申报类型
	private String status; // 状态
	private String trainingStarttime; // 培训开始时间
	private String trainingEndtime; // 培训结束时间
	private String trainingClasshour; // 学时
	private String trainingAddress; // 培训地址
	private String contacts; // 联系人
	private String phone; // 电话
	private String creator; // 创建者
	private String creattime; // 创建时间
	private String approver; // 批准人
	private String approvetime; // 批准时间
	private String approveNumber;// 培训人数
	private String[] restrictExpertId;
	private List<String[]> restrictExpertList;

	private ProjectApply projectApply;

	//
	// 年份
	private String[] yearArray;
	private String year;
	private List<Project> lsProject;

	private String pageStatus;
	private String message;

	public ProjectApplyInfoOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 打开页面
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public String initPage() {

		initServlert();

		String id = request.getParameter("id");
		this.id = id;

		this.pageStatus = "";
		this.message = "";
		this.year = (new Date().getYear() + 1900) + "";

		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		lsProject = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);

		if (yearArray == null) {
			int year = new Date().getYear() + 1900;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}

		// 获取所有项目

		if (this.id == null || this.id.equals("0") || this.equals("")) {
			// 添加
			projectApply = new ProjectApply();

		} else {
			// 编辑
			projectApply = iProjectApplyService.get(Integer.parseInt(id));
		}

		return "init";
	}

	/**
	 * 打开审核页面
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String initPassPage() {

		initServlert();

		String id = request.getParameter("id");
		this.id = id;

		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		lsProject = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);

		if (yearArray == null) {
			int year = new Date().getYear() + 1900;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}

		projectApply = iProjectApplyService.get(Integer.parseInt(id));
		this.year = projectApply.getProject().getYear();

		return "initPass";
	}

	/**
	 * 添加评审专家
	 */
	public String initExpertPage() {
		initServlert();
		String id = request.getParameter("id");
		this.id = id;
		projectApply = iProjectApplyService.get(Integer.parseInt(id));
		this.year = projectApply.getProject().getYear();
		this.project = projectApply.getProject().getName();
		this.trainingSubject = projectApply.getTrainingSubject().getName();
		this.trainingCollege = projectApply.getTrainingCollege().getName();
		Set<ProjectApplyExpert> setExpertRanges = projectApply.getProjectApplyExpert();
		this.restrictExpertId = new String[setExpertRanges.size()];
		this.restrictExpertList = new ArrayList<String[]>();
		int index = 0;
		for (ProjectApplyExpert pae : setExpertRanges) {
			ProjectExpert pe1 = pae.getProjectExpert();
			this.restrictExpertId[index] = pe1.getId().toString();
			String pe2 = pe1.getName();
			if (pe2.length() > 0) {
				String[] spts = pe2.split("&");
				String[] rerights = new String[spts.length + 1];
				for (int i = 0; i < spts.length; i++) {
					rerights[i] = spts[i];
				}
				rerights[spts.length] = pe1.getId().toString();
				this.restrictExpertList.add(rerights);
			}
			index++;
		}

		return "initExpert";
	}

	@SuppressWarnings("rawtypes")
	public void getProjectApplyExpert() {
		initServlert();
		String paid = request.getParameter("paid");
		String expertName = request.getParameter("expertName");
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;
		List liT = this.iProjectExpertService.getProjectExpert(expertName, "name", null, null, offset, DictionyMap.maxPageSize);
		if (liT == null || liT.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		int records = this.iProjectExpertService.getProjectExpertCount(expertName, "name", null, null);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"page\":{");

		sb.append("\"currentPage\":" + startPage + ",");
		sb.append("\"totalPage\":" + totalPage);
		sb.append("}");

		sb.append(",");
		sb.append("\"records\":[");
		for (Object obj : liT) {
			ProjectExpert e = (ProjectExpert) obj;

			StringBuilder tb = new StringBuilder();
			tb.append("{");
			tb.append("\"id\":\"" + e.getId() + "\",");
			tb.append("\"name\":\"" + e.getName() + "\",");
			tb.append("\"phone\":\"" + e.getMobile() + "\",");
			tb.append("\"organization\":\"" + e.getOrganization() + "\",");
			tb.append("\"jobtitle\":\"" + e.getJobTitle() + "\",");
			tb.append("\"teach\":\"" + e.getTeach() + "\",");
			tb.append("\"research\":\"" + e.getResearch() + "\",");
			int paid1 = Integer.valueOf(paid);
			int eid = e.getId();
			int ttr = this.iProjectApplyExpertService.checkProjectApplyExpert(paid1, eid);
			tb.append("\"record_status\":" + (ttr == 0));
			tb.append("}");
			sb.append(tb.toString() + ",");
		}
		if (liT.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	public void addProjectApplyExpert() {
		initServlert();
		String paid = request.getParameter("paid");
		String expertid = request.getParameter("id");
		ProjectApply pa = this.iProjectApplyService.get(Integer.valueOf(paid));
		ProjectExpert pe = this.iProjectExpertService.get(Integer.valueOf(expertid));
		ProjectApplyExpert pae = new ProjectApplyExpert();
		pae.setProjectApply(pa);
		pae.setProjectExpert(pe);
		this.iProjectApplyExpertService.add(pae);
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"OK\"");
		checkSB.append(",");
		checkSB.append("\"paid\":\"" + paid + "\"");
		checkSB.append(",");
		checkSB.append("\"expertid\":\"" + expertid + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public void deleteProjectApplyExpert() {
		initServlert();
		String paid = request.getParameter("paid");
		String expertid = request.getParameter("id");
		ProjectApplyExpert pae = this.iProjectApplyExpertService.getProjectApplyExpertByAll(Integer.valueOf(paid), Integer.valueOf(expertid));
		this.iProjectApplyExpertService.delete(pae);
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"OK\"");
		checkSB.append(",");
		checkSB.append("\"paid\":\"" + paid + "\"");
		checkSB.append(",");
		checkSB.append("\"expertid\":\"" + expertid + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public void getExpertListForPage() {
		initServlert();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		List<ProjectExpert> list = this.iProjectExpertService.getProjectExpertList();
		for (ProjectExpert pe : list) {
			int peid = pe.getId();
			String sr = "{\"id\":" + peid + ",\"name\":\"" + pe.getName() + "\"},";
			sb.append(sr);
		}
		if (list.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * 项目补录
	 * 
	 * @category 项目申报补录
	 */
	@SuppressWarnings({ "unused" })
	public void addProjectApply() {
		// 补录的话只有添加
		initServlert();

		try {
			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";
			String document = parMap.containsKey("document") ? parMap.get("document")[0] : "";
			String project = parMap.containsKey("project") ? parMap.get("project")[0] : ""; // 项目

			String trainingSubject = parMap.get("trainingSubject")[0]; // 学科
			String trainingCollege = parMap.get("trainingCollege")[0]; // 承训单位

			String type = parMap.containsKey("type") ? parMap.get("type")[0] : ""; // 申报类型
			String status = parMap.containsKey("status") ? parMap.get("status")[0] : ""; // 状态

			String enrollEndTime ="";
			if(parMap.get("enrollEndTime") != null){
				enrollEndTime = parMap.get("enrollEndTime")[0];
			}
//			String 
			String trainingStarttime = parMap.get("trainingStarttime")[0]; // 培训开始时间
			String trainingEndtime = parMap.get("trainingEndtime")[0]; // 培训结束时间
			String trainingClasshour = parMap.get("trainingClasshour")[0]; // 学时

			String trainingAddress = parMap.containsKey("trainingAddress") ? parMap.get("trainingAddress")[0] : ""; // 培训地址
			String contacts = parMap.get("contacts")[0]; // 联系人
			String phone = parMap.get("phone")[0]; // 电话

			String creator = parMap.containsKey("creator") ? parMap.get("creator")[0] : ""; // 创建者
			String creattime = parMap.containsKey("creattime") ? parMap.get("creattime")[0] : ""; // 创建时间

			String approver = parMap.containsKey("approver") ? parMap.get("approver")[0] : ""; // 批准人
			String approvetime = parMap.containsKey("approvetime") ? parMap.get("approvetime")[0] : ""; // 批准时间
			String approveNumber = parMap.get("approveNumber")[0];// 培训人数

			// 验证用户输入
			if (project == null || project.equals("0") || project.equals("")) {
				// 项目没有选择
				Utlity.sendResponse("ERROR", "没有选择项目", response);
				return;
			}

			if (trainingStarttime == null || trainingStarttime.equals("") || trainingStarttime.equals("0")) {
				Utlity.sendResponse("ERROR", "没有设置培训开始时间", response);
				return;
			}

			if (trainingEndtime == null || trainingEndtime.equals("") || trainingEndtime.equals("0")) {
				Utlity.sendResponse("ERROR", "没有设置培训结束时间", response);
				return;
			}

			if (trainingClasshour == null || trainingClasshour.equals("")) {
				Utlity.sendResponse("ERROR", "没有设置培训课时", response);
				return;
			}

			if (approveNumber == null || approveNumber.equals("")) {
				Utlity.sendResponse("ERROR", "没有设置培训人数", response);
				return;
			}

			ProjectApply pa = new ProjectApply();
			if (!id.equals("") && id != null) {
				pa = iProjectApplyService.get(Integer.parseInt(id));
			}
			// 項目
			Project p = iProjectService.get(Integer.valueOf(project));
			pa.setProject(p);

			// 承训单位
			TrainingCollege tc = iTrainingCollegeService.get(Integer.valueOf(trainingCollege));
			pa.setTrainingCollege(tc);

			// 培训学科
			TrainingSubject ts = iTrainingSubjectService.get(Short.valueOf(trainingSubject));
			pa.setTrainingSubject(ts);

			// 设置培训开始时间 和 结束时间
			pa.setTrainingStarttime(Timestamp.valueOf(trainingStarttime + " 00:00:00"));
			pa.setTrainingEndtime(Timestamp.valueOf(trainingEndtime + " 00:00:00"));
			
			if (enrollEndTime != null && !enrollEndTime.equals("") && !enrollEndTime.equals("0")) {
				pa.setEnrollEndTime(Timestamp.valueOf(enrollEndTime+" 00:00:00"));
			}
			
			// if(pa.getTrainingStarttime().after(pa.getCreattime())) {
			// Utlity.sendResponse("ERROR", "培训开始 ", response);
			// return;
			// }

			// 培训课时
			pa.setTrainingClasshour(Integer.valueOf(trainingClasshour));

			// 培训人数
			pa.setApproveNumber(Integer.valueOf(approveNumber));

			pa.setContacts(contacts);
			pa.setPhone(phone);

			// 创建者
			UserSession us = (UserSession) session.getAttribute("usersession");
			Organization organization = iOragnizationService.get(us.getOrganization());
			// 审核人
			pa.setApprover(us.getId());
			if (!id.equals("") && id != null) {
				pa.setId(Integer.parseInt(id));
				iProjectApplyService.update(pa);

				if(p.getEnrollType() == 1){
					
					// 修改教师分配表
					AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService.get(p.getId().toString(), ts.getId().toString(), tc.getId().toString(), (short) 1);
					boolean isupdate = true;
					if (assignTeacherTask == null) {
						assignTeacherTask = new AssignTeacherTask();
						isupdate = false;
					}
					assignTeacherTask.setProject(p);
					assignTeacherTask.setTrainingSubject(ts);
					assignTeacherTask.setTrainingCollege(tc);
					assignTeacherTask.setOrganizationByGOrganization(organization);
					assignTeacherTask.setOrganizationByPOrganization(organization);
					assignTeacherTask.setLevel((short) 1);
	
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, 30);
					String v = Utlity.timeSpanToDateString(c.getTime()) + " 23:59:59";
					Timestamp ts1 = Timestamp.valueOf(v);
					assignTeacherTask.setTimeup(ts1);
					assignTeacherTask.setStatus((short) 1);
					assignTeacherTask.setTeacherNumber(Integer.valueOf(approveNumber));
					assignTeacherTask.setReceiveFlag((short) 0);
					assignTeacherTask.setCreator(us.getId());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = df.format(new Date());
					Timestamp ts2 = Timestamp.valueOf(time);
					assignTeacherTask.setCreattime(ts2);
					if (isupdate) {
						iAssignTeacherTaskService.update(assignTeacherTask);
					} else {
						iAssignTeacherTaskService.add(assignTeacherTask);
					}
				}
				Utlity.sendResponse("OK", "编辑成功", response);
			} else {

				pa.setCreator(us.getId());
				pa.setStatus((short) 2);
				pa.setType((short) 2);

				iProjectApplyService.add(pa);
				
				if(p.getEnrollType() == 1){
					
					// 同时插入教师分配表
					AssignTeacherTask assignTeacherTask = new AssignTeacherTask();
					assignTeacherTask.setProject(p);
					assignTeacherTask.setTrainingSubject(ts);
					assignTeacherTask.setTrainingCollege(tc);
					assignTeacherTask.setOrganizationByGOrganization(organization);
					assignTeacherTask.setOrganizationByPOrganization(organization);
					assignTeacherTask.setLevel((short) 1);
	
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, 30);
					String v = Utlity.timeSpanToDateString(c.getTime()) + " 23:59:59";
					Timestamp ts1 = Timestamp.valueOf(v);
					assignTeacherTask.setTimeup(ts1);
					assignTeacherTask.setStatus((short) 1);
					assignTeacherTask.setTeacherNumber(Integer.valueOf(approveNumber));
					assignTeacherTask.setReceiveFlag((short) 0);
					assignTeacherTask.setCreator(us.getId());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = df.format(new Date());
					Timestamp ts2 = Timestamp.valueOf(time);
					assignTeacherTask.setCreattime(ts2);
					iAssignTeacherTaskService.add(assignTeacherTask);
				}
				Utlity.sendResponse("OK", "添加成功", response);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			Utlity.sendResponse("ERROR", "相同项目下已经存在当前分配的学科和承训单位", response);
			return;

		}
	}

	public void delete() {
		// 补录的话只有添加
		initServlert();
		Map<String, String[]> parMap = request.getParameterMap();
		String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization organization = iOragnizationService.get(us.getOrganization());

		if (id.equals(null) || id.equals("")) {
			Utlity.sendResponse("ERROR", "ID为空，没有选择项目中标记录！", response);
			return;
		}

		if (us.getRole() != ROLEENUM.ADMIN.getValue() || organization.getOrganizationLevel().getId() != 1) {
			Utlity.sendResponse("ERROR", "当前用户无权删除项目中标信息！", response);
			return;
		}

		projectApply = iProjectApplyService.get(Integer.parseInt(id));
		Integer projectId = projectApply.getProject().getId();
		Integer trainingSubjectId = Integer.valueOf(projectApply.getTrainingSubject().getId());
		Integer trainingUnitId = Integer.valueOf(projectApply.getTrainingCollege().getId());

		AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService.get(projectId.toString(), trainingSubjectId.toString(), trainingUnitId.toString(), (short) 1);

		if (assignTeacherTask != null && assignTeacherTask.getAssignTeacherTasks().size() > 0) {
			Utlity.sendResponse("ERROR", "已经向下级部门分配了学员报送任务，不能删除项目中标信息！", response);
			return;
		}

		int ttrCount = iTeacherTrainingRecordsService.getAduTeacherCount(projectId, trainingSubjectId, trainingUnitId, -1,0);

		if (ttrCount > 0) {
			Utlity.sendResponse("ERROR", "已经存在学员报送/培训记录，无法删除项目中标信息！", response);
			return;
		}
		// assignTeacherTask==null的时候为垃圾数据
		if (assignTeacherTask != null)
			iAssignTeacherTaskService.delete(assignTeacherTask);
		// 删除项目中标信息
		iProjectApplyService.delete(projectApply);
		// 删除提交的文档等资料（暂不删除，没有逻辑删除的状态位，又无法物理删除）
		Utlity.sendResponse("OK", "删除成功", response);

	}

	/**
	 * 通过审核
	 */
	public void passProjectApply() {
		initServlert();
		try {

			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";
			String project = parMap.containsKey("project") ? parMap.get("project")[0] : ""; // 项目

			String trainingSubject = parMap.get("trainingSubject")[0]; // 学科
			String trainingCollege = parMap.get("trainingCollege")[0]; // 承训单位

			String trainingStarttime = parMap.get("trainingStarttime")[0]; // 培训开始时间
			String trainingEndtime = parMap.get("trainingEndtime")[0]; // 培训结束时间
			String trainingClasshour = parMap.get("trainingClasshour")[0]; // 学时

			String contacts = parMap.get("contacts")[0]; // 联系人
			String phone = parMap.get("phone")[0]; // 电话

			String approveNumber = parMap.get("approveNumber")[0];// 培训人数

			// 验证用户输入
			if (project == null || project.equals("0") || project.equals("")) {
				// 项目没有选择
				Utlity.sendResponse("ERROR", "没有选择项目", response);
				return;
			}

			if (trainingStarttime == null || trainingStarttime.equals("") || trainingStarttime.equals("0")) {
				Utlity.sendResponse("ERROR", "没有设置培训开始时间", response);
				return;
			}

			if (trainingEndtime == null || trainingEndtime.equals("") || trainingEndtime.equals("0")) {
				Utlity.sendResponse("ERROR", "没有设置培训结束时间", response);
				return;
			}

			if (trainingClasshour == null || trainingClasshour.equals("")) {
				Utlity.sendResponse("ERROR", "没有设置培训课时", response);
				return;
			}

			if (approveNumber == null || approveNumber.equals("")) {
				Utlity.sendResponse("ERROR", "没有设置培训人数", response);
				return;
			}

			ProjectApply pa = this.iProjectApplyService.get(Integer.parseInt(id));

			// 項目
			Project p = iProjectService.get(Integer.valueOf(project));
			pa.setProject(p);

			// 承训单位
			TrainingCollege tc = iTrainingCollegeService.get(Integer.valueOf(trainingCollege));
			pa.setTrainingCollege(tc);

			// 培训学科
			TrainingSubject ts = iTrainingSubjectService.get(Short.valueOf(trainingSubject));
			pa.setTrainingSubject(ts);

			// 设置培训开始时间 和 结束时间
			pa.setTrainingStarttime(Timestamp.valueOf(trainingStarttime + " 00:00:00"));
			pa.setTrainingEndtime(Timestamp.valueOf(trainingEndtime + " 00:00:00"));

			pa.setStatus((short) 2);

			// 培训课时
			pa.setTrainingClasshour(Integer.valueOf(trainingClasshour));

			// 培训人数
			pa.setApproveNumber(Integer.valueOf(approveNumber));

			pa.setContacts(contacts);
			pa.setPhone(phone);

			UserSession us = (UserSession) session.getAttribute("usersession");
			// 审核人
			pa.setApprover(us.getId());

			Organization organization = iOragnizationService.get(us.getOrganization());

			if(pa.getProject().getEnrollType() == 2){//自主报名项目不存在任务分配功能
				iProjectApplyService.update(pa);

				Utlity.sendResponse("OK", "审核通过", response);
				return;
			}
			// 修改教师分配表
			AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService.get(p.getId().toString(), ts.getId().toString(), tc.getId().toString(), (short) 1);
			boolean isupdate = true;
			if (assignTeacherTask == null) {
				assignTeacherTask = new AssignTeacherTask();
				isupdate = false;
			}
			assignTeacherTask.setProject(p);
			assignTeacherTask.setTrainingSubject(ts);
			assignTeacherTask.setTrainingCollege(tc);
			assignTeacherTask.setOrganizationByGOrganization(organization);
			assignTeacherTask.setOrganizationByPOrganization(organization);
			assignTeacherTask.setLevel((short) 1);

			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 30);
			String v = Utlity.timeSpanToDateString(c.getTime()) + " 23:59:59";
			Timestamp ts1 = Timestamp.valueOf(v);

			assignTeacherTask.setTimeup(ts1);
			assignTeacherTask.setStatus((short) 1);
			assignTeacherTask.setTeacherNumber(Integer.valueOf(approveNumber));
			assignTeacherTask.setReceiveFlag((short) 0);
			assignTeacherTask.setCreator(us.getId());

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(new Date());
			Timestamp ts2 = Timestamp.valueOf(time);

			assignTeacherTask.setCreattime(ts2);

			if (isupdate) {
				iAssignTeacherTaskService.update(assignTeacherTask);
			} else {
				iAssignTeacherTaskService.add(assignTeacherTask);
			}

			iProjectApplyService.update(pa);

			Utlity.sendResponse("OK", "审核通过", response);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			Utlity.sendResponse("ERROR", "审核失败", response);
			return;

		}
	}

	/**
	 * 不通过审核
	 */
	@SuppressWarnings("unused")
	public void passNoProjectApply() {
		initServlert();
		try {
			/**
			 * 要判断是否存在分配名额子任务，如果没有那就得需要删除名额分配任务
			 */
			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";

			ProjectApply pa = this.iProjectApplyService.get(Integer.parseInt(id));
			UserSession us = (UserSession) session.getAttribute("usersession");
			Organization organization = iOragnizationService.get(us.getOrganization());

			if(pa.getProject().getEnrollType() == 2){
				pa.setStatus((short) 0);
				pa.setApprover(us.getId());
				iProjectApplyService.update(pa);

				Utlity.sendResponse("OK", "审核不通过", response);
				return;
			}
			// 修改教师分配表
			AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService.get(pa.getProject().getId().toString(), pa.getTrainingSubject().getId().toString(), pa.getTrainingCollege().getId().toString(), (short) 1);

			if (assignTeacherTask != null && assignTeacherTask.getAssignTeacherTasks().size() > 0) {
				// 存在名额分配子任务
				Utlity.sendResponse("ERROR", "操作失败，存在学员名额分配任务!", response);
			} else {

				// 删除名额分配
				if (assignTeacherTask != null) {
					iAssignTeacherTaskService.delete(assignTeacherTask);
				}

				pa.setStatus((short) 0);
				pa.setApprover(us.getId());
				iProjectApplyService.update(pa);

				Utlity.sendResponse("OK", "审核不通过", response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			Utlity.sendResponse("ERROR", "审核失败", response);
			return;

		}
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTrainingSubject() {
		return trainingSubject;
	}

	public void setTrainingSubject(String trainingSubject) {
		this.trainingSubject = trainingSubject;
	}

	public String getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(String trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrainingStarttime() {
		return trainingStarttime;
	}

	public void setTrainingStarttime(String trainingStarttime) {
		this.trainingStarttime = trainingStarttime;
	}

	public String getTrainingEndtime() {
		return trainingEndtime;
	}

	public void setTrainingEndtime(String trainingEndtime) {
		this.trainingEndtime = trainingEndtime;
	}

	public String getTrainingClasshour() {
		return trainingClasshour;
	}

	public void setTrainingClasshour(String trainingClasshour) {
		this.trainingClasshour = trainingClasshour;
	}

	public String getTrainingAddress() {
		return trainingAddress;
	}

	public void setTrainingAddress(String trainingAddress) {
		this.trainingAddress = trainingAddress;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(String approvetime) {
		this.approvetime = approvetime;
	}

	public String getApproveNumber() {
		return approveNumber;
	}

	public void setApproveNumber(String approveNumber) {
		this.approveNumber = approveNumber;
	}

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the iAssignTeacherTaskService
	 */
	public IAssignTeacherTaskService getiAssignTeacherTaskService() {
		return iAssignTeacherTaskService;
	}

	/**
	 * @param iAssignTeacherTaskService
	 *            the iAssignTeacherTaskService to set
	 */
	public void setiAssignTeacherTaskService(IAssignTeacherTaskService iAssignTeacherTaskService) {
		this.iAssignTeacherTaskService = iAssignTeacherTaskService;
	}

	/**
	 * @return the iOragnizationService
	 */
	public IOrganizationService getiOragnizationService() {
		return iOragnizationService;
	}

	/**
	 * @param iOragnizationService
	 *            the iOragnizationService to set
	 */
	public void setiOragnizationService(IOrganizationService iOragnizationService) {
		this.iOragnizationService = iOragnizationService;
	}

	/**
	 * @return the iTeacherTrainingRecordsService
	 */
	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	/**
	 * @param iTeacherTrainingRecordsService
	 *            the iTeacherTrainingRecordsService to set
	 */
	public void setiTeacherTrainingRecordsService(ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public IProjectApplyExpertService getiProjectApplyExpertService() {
		return iProjectApplyExpertService;
	}

	public void setiProjectApplyExpertService(IProjectApplyExpertService iProjectApplyExpertService) {
		this.iProjectApplyExpertService = iProjectApplyExpertService;
	}

	public List<Project> getLsProject() {
		return lsProject;
	}

	public void setLsProject(List<Project> lsProject) {
		this.lsProject = lsProject;
	}

	/**
	 * @return the projectApply
	 */
	public ProjectApply getProjectApply() {
		return projectApply;
	}

	/**
	 * @param projectApply
	 *            the projectApply to set
	 */
	public void setProjectApply(ProjectApply projectApply) {
		this.projectApply = projectApply;
	}

	public String[] getRestrictExpertId() {
		return restrictExpertId;
	}

	public void setRestrictExpertId(String[] restrictExpertId) {
		this.restrictExpertId = restrictExpertId;
	}

	public List<String[]> getRestrictExpertList() {
		return restrictExpertList;
	}

	public void setRestrictExpertList(List<String[]> restrictExpertList) {
		this.restrictExpertList = restrictExpertList;
	}

}
