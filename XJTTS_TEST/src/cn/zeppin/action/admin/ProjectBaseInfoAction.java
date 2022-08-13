package cn.zeppin.action.admin;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.FunCategory;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectBaseInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectBaseInfoAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService;
	private IProjectTypeService iProjectTypeService;
	private IProjectApplyService iProjectApplyService;

	private IAreaService iAreaService;
	private IProjectAdminService iProjectAdminService;

	private LinkedHashMap<Integer, String[]> projectHash;
	private LinkedHashMap<String, LinkedHashMap<String, Object>> chashMap;
	private ArrayList<String[]> projectApplyList;
	private ArrayList<String[]> subject;
	private ArrayList<String[]> training;
	private ArrayList<String[]> value;
	private List<Project> searchReportTask;
	private List<String> searchYear;


	public ProjectBaseInfoAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @category 列表
	 */
	public String initPage() {

		initServlert();

		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 搜索参数
		Map<String, Object> params = new HashMap<>();
		Map<String, String> sortParams = new HashMap<>();

		// 判断其他搜索字段是否存在

		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "DESC");
		}

		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List<Project> li = this.iProjectService.getProjectByParams(params, sortParams, lityps, offset, DictionyMap.maxPageSize);
		this.projectHash = new LinkedHashMap<Integer, String[]>();
		DecimalFormat df1 = new DecimalFormat("0.00");
		for (Project pro : li) {
			int id = pro.getId();
			if (!this.projectHash.containsKey(id)) {
				String[] sv = new String[20];
				sv[0] = pro.getName();
				switch (pro.getStatus()) {
				case 1:
					sv[1] = "未发布";
					break;
				case 2:
					sv[1] = "发布";
					break;
				case 3:
					sv[1] = "完成";
					break;
				case 4:
					sv[1] = "关闭";
					break;
				}

				sv[2] = pro.getYear();

				ProjectType pt = pro.getProjectType();

				sv[3] = getNaviString(pt);
				sv[4] = pro.getShortname();
				sv[5] = getAreaNaviString(pt.getArea());
				sv[6] = Utlity.timeSpanToDateString(pro.getTimeup());
				sv[7] = df1.format(pro.getFunds());
				sv[8] = pro.getNumber().toString();
				sv[9] = pro.getSubjectMax().toString();

				sv[10] = pro.getRestrictCollege() ? "邀标" : "不限";
				sv[11] = pro.getRestrictSubject() ? "限制" : "不限";
				sv[12] = pro.getTraintype() == 1 ? "集中面授" : "远程培训";
				sv[13] = pt.getProjectLevel().getName();
				sv[14] = pro.getDocument() == null ? "未上传" : "已上传";
				sv[15] = pro.getPsqByProjectJudgePsq() == null ? "未设定" : "已设定";
				ProjectAdmin pa = this.iProjectAdminService.get(pro.getCreator());
				sv[16] = pa.getName();
				sv[17] = Utlity.timeSpanToDateString(pro.getCreattime());
				sv[18] = pro.getEnrollType().toString();

				this.projectHash.put(pro.getId(), sv);
			}
		}

		return "init";
	}

	public void getPageJson() {
		initServlert();

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 判断其他搜索字段是否存在
		Map<String, Object> params = new HashMap<>();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		int records = this.iProjectService.getProjectCountByParams(params, lityps);
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getPageJsonForTrainingCollege() {
		initServlert();

		String collegeid=(String) request.getParameter("id");
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		Date date = new Date();
		List li= this.iProjectService.getProjectForTrainingCollege(null,null,Integer.valueOf(collegeid),date,0,1000000);
		int records =li.size();
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String initProjectResult(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		String year = request.getParameter("year");
		
		this.searchYear = this.iProjectService.getProjectYearList();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		
		return "presult";
	}
	
	public void projectResult() {
		initServlert();
		String projectId = request.getParameter("id");
		String error ="{\"status\":\"ERROR\"}";
		if (projectId != null && !projectId.equals("")) {
			List<ProjectApply> li = this.iProjectApplyService.getList(Integer.parseInt(projectId));
			if(li !=null && !li.isEmpty()){
				this.projectApplyList = new ArrayList<String[]>();
				this.subject=new ArrayList<String[]>(); 
				this.training=new ArrayList<String[]>();
				this.value=new ArrayList<String[]>();
				for (int i=0;i<li.size();i++) {
					String[] sv = new String[5];
					sv[0]=Integer.toString(li.get(i).getTrainingSubject().getId());
					sv[1]=li.get(i).getTrainingSubject().getName();
					sv[2]=Integer.toString(li.get(i).getTrainingCollege().getId());
					sv[3]=li.get(i).getTrainingCollege().getName();
					sv[4]=li.get(i).getApproveNumber()+"";													
					Boolean add_s = true,add_t = true;
					String[] sj={sv[0],sv[1]};
					String[] tc={sv[2],sv[3]};
					String[] vl={sv[0],sv[2],sv[4]};
					for(int j=0;j<subject.size();j++){
						if(sv[0].equals(subject.get(j)[0]))add_s=false;
					}					
					if(add_s)subject.add(sj);
					for(int k=0;k<training.size();k++){
						if(sv[2].equals(training.get(k)[0]))add_t=false;
					}					
					if(add_t)training.add(tc);
					value.add(vl);
				}		
				StringBuilder sb = new StringBuilder();
				sb.append("{\"status\":\"OK\",");
				sb.append("\"training\":[");
				for(int i=0;i<training.size();i++){
					if(i!=0)sb.append(",");
					sb.append("{\"id\":"+(training.get(i)[0])+",\"name\":\""+(training.get(i)[1])+"\"}");
				}
				sb.append("],\"subject\":[");
				for(int i=0;i<subject.size();i++){
					if(i!=0)sb.append(",");
					sb.append("{\"id\":"+(subject.get(i)[0])+",\"name\":\""+(subject.get(i)[1])+"\"}");
				}
				sb.append("],\"value\":{");
				for(int i=0;i<value.size();i++){
					if(i!=0)sb.append(",");
					sb.append("\""+(value.get(i)[0])+"_"+(value.get(i)[1])+"\":\""+(value.get(i)[2])+"\"");
				}
				sb.append("}}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);

			}else{
				Utlity.ResponseWrite(error, "application/json", response);
			}
		}else{
			Utlity.ResponseWrite(error, "application/json", response);
		}
	}

	public void deleteProject() {
		initServlert();
		String id = this.request.getParameter("id");
		if (id != null && !id.equals("")) {
			Project pro = this.iProjectService.get(Integer.valueOf(id));
			if (pro != null) {
				try {

					// TODO 可能根据当前用户与要删除的用户进行 工作单位，也就是组织架构的从属关系，是否能删除，

					if (pro.getProjectApplies().size() > 0) {
						sendResponse("ERROR", "删除失败,存在项目申报信息");
					}else if(pro.getAssignTeacherTasks().size()>0){
						sendResponse("ERROR", "删除失败,存在项目名额分配任务信息");
					}
					else if(pro.getTeacherTrainingRecordses().size()>0){
						sendResponse("ERROR", "删除失败,存在项目学员培训记录信息");
					}
					else {
						this.iProjectService.delete(pro);
						sendResponse("OK", "删除成功");
					}

				} catch (Exception ex) {
					sendResponse("ERROR", "删除失败,存在项目申报信息");
				}
			} else {
				sendResponse("ERROR", "删除失败，不存在项目信息");
			}

		} else {
			sendResponse("ERROR", "删除失败，不存在项目信息");
		}
	}

	public void changeStatus() {
		initServlert();

		String id = this.request.getParameter("id");
		String method = this.request.getParameter("method");
		if (id != null && !id.equals("")) {
			Project pro = this.iProjectService.get(Integer.valueOf(id));
			if (pro != null) {
				try {
					if (method.equals("release")) {
						// 发布
						pro.setStatus((short) 2);
						this.iProjectService.update(pro);
						sendResponse("OK", "发布成功");

					} else if (method.equals("close")) {
						// 关闭
						pro.setStatus((short) 4);
						this.iProjectService.update(pro);
						sendResponse("OK", "关闭成功");
					}

				} catch (Exception ex) {
					sendResponse("ERROR", "删除失败,存在项目申报信息");
				}
			} else {
				sendResponse("ERROR", "删除失败，不存在项目信息");
			}

		} else {
			sendResponse("ERROR", "删除失败，不存在项目信息");
		}
	}

	/**
	 * 根据参数获取项目信息 返回json id和名称
	 */
	public void getProjectsByPramers() {
		initServlert();
		String year = request.getParameter("year");
		String projectType = request.getParameter("projectType");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictionyMap.releaseProject);

		if (year != null && !year.equals("")) {
			params.put("year", year);
		}
		if (projectType != null && !projectType.equals("")) {
			params.put("projectType", Integer.valueOf(projectType));
		}
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		List<Project> li = this.iProjectService.getProjectByParams(params, null, lityps);
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (Project p : li) {
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		if (li.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");

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

	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
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

	public LinkedHashMap<Integer, String[]> getProjectHash() {
		return projectHash;
	}

	public void setProjectHash(LinkedHashMap<Integer, String[]> projectHash) {
		this.projectHash = projectHash;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}
	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
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
}
