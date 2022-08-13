package cn.zeppin.action.teacher;
 
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.action.admin.ProjectBaseInfoAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Project;
//import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingRecords;
//import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class TrainInfoAction extends baseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8102948942441565476L;
	
	static Logger logger = LogManager.getLogger(ProjectBaseInfoAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private IProjectApplyService iProjectApplyService;
	private IProjectService iProjectService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingAdminService iTrainingAdminService;
	private IAreaService iAreaService;
	private IProjectTypeService iProjectTypeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	
	
	public TrainInfoAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	@SuppressWarnings("rawtypes")
	public void initProjectAPI(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		List<ProjectType> lityps = iProjectTypeService.findAll();
//		List<Project> lstProject = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enrollType", DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
		params.put("status", 2);
		params.put("group", "t.project");
		
		List lstProject = this.iProjectApplyService.getProjectByParams(params, lityps);
		int records = 0;
		if( lstProject != null && !lstProject.isEmpty()){
			records = lstProject.size();
			
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"查询完毕\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for(Object obj: lstProject){
				Object[] o = (Object[])obj;
//				ProjectApply pa = (ProjectApply)o[0];
				Project project = (Project)o[1];
				sb.append("{");
				sb.append("\"projectId\":\""+project.getId()+"\"");
				sb.append(",");
				sb.append("\"projectName\":\""+project.getName()+"报名入口\"");
				sb.append(",");
				sb.append("\"projectYear\":\""+project.getYear()+"\""); //项目开设年份
				sb.append(",");
				String trainType = project.getTraintype() == 1 ? "集中面授":"远程培训";//培训形式
				sb.append("\"trainType\":\""+trainType+"\"");
				sb.append("},");
				
			}
//			for(Project project : lstProject){
//				sb.append("{");
//				sb.append("\"projectId\":\""+project.getId()+"\"");
//				sb.append(",");
//				sb.append("\"projectName\":\""+project.getName()+"报名入口\"");
//				sb.append(",");
//				sb.append("\"projectYear\":\""+project.getYear()+"\""); //项目开设年份
//				sb.append(",");
//				String trainType = project.getTraintype() == 1 ? "集中面授":"远程培训";//培训形式
//				sb.append("\"trainType\":\""+trainType+"\"");
//				sb.append("},");
//			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
			sb.append(",");
			sb.append("\"TotalRecordCount\":" + records);
			sb.append("}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"暂无相关报名入口\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void initPage() {

		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		Map<String, String> sortParams = new HashMap<>();
		List<ProjectType> lityps = iProjectTypeService.findAll();
		
		Map<String, Object> params = new HashMap<>();
		String projectId = "";
		if(request.getParameter("id")!=null){
			projectId = request.getParameter("id");
			params.put("projectId", projectId);
			
		}
		params.put("enrollType", DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
		params.put("status", "2");//通过审核的申报记录
		
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

//		List li = this.iProjectApplyService.getProjectApplyByParams(params, sortParams, lityps, offset, DictionyMap.maxPageSize);
		List li = this.iProjectApplyService.getProjectByParams(params, lityps);
		Project project = this.iProjectService.get(Integer.parseInt(projectId));
		int records = 0;//总
		int isEnrollCount = 0;//已报名
		int unEnrollCount = 0;//未报名
		if(li != null && !li.isEmpty()){
			records = li.size();
			
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"查询完毕\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for (Object ba : li) {
				Object[] obj = (Object[]) ba;
				ProjectApply pa = (ProjectApply) obj[0];
				int id = pa.getId();
				Project p = pa.getProject();
//				String projectName = p.getName();//项目
				
				TrainingSubject ts = pa.getTrainingSubject();
				String subjectName = ts.getName();//学科
				
				TrainingCollege tc = pa.getTrainingCollege();
				String collegeName = tc.getName();//承训单位
				
//				String trainType = p.getTraintype() == 1 ? "集中面授":"远程培训";//培训形式
				String trainAdd = pa.getTrainingAddress() == null? "无":pa.getTrainingAddress();//培训地址
				
				String startTime = Utlity.timeSpanToDateString(pa.getTrainingStarttime());//开始时间
				String endTime = Utlity.timeSpanToDateString(pa.getTrainingEndtime());//结束时间
				
				long nowTime = System.currentTimeMillis();
//				String deadline = Utlity.timeSpanToDateString(new Timestamp((new Date()).getTime()));//报名截止时间
				double deadLine = 0;//报名倒计时  截止时间减去当前时间
				if(pa.getEnrollEndTime() != null){
//					deadline = Utlity.timeSpanToDateString(pa.getEnrollEndTime());//报名截止时间
					Timestamp enrollEndTime = pa.getEnrollEndTime();
					deadLine = Math.ceil(((enrollEndTime.getTime()-nowTime)/(60*60*24*1000)));
				}
				
				String trainClassHour = pa.getTrainingClasshour().toString();//学时
				String approveNumber = pa.getApproveNumber().toString();//人数
				
				String funds = p.getFunds().toString();//金额
				String isEnrollType = p.getEnrollType().toString();//是否自主报名
				
				//是否已报名
//				String teacherIdCard = us.getIdcard();
//				String teacherName = us.getName();
//				TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.getTeacherTrainingRecordByIdCard(teacherName, teacherIdCard, p.getName(), tc.getName(), ts.getName());
//				if(ttr != null){
//					isSignup = ttr.getFinalStatus().toString();
//					isEnrollCount++;
//				}
				
				Map<String, Object> pagram = new HashMap<String, Object>();
				pagram.put("projectId", p.getId());
				pagram.put("subjectId", Integer.parseInt(ts.getId().toString()));
				pagram.put("trainCollegeId", tc.getId());
				List ttrs = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(us.getId(), 0, DictionyMap.maxPageSize, null, pagram);
				String isSignup = "";
				
				if(ttrs != null && !ttrs.isEmpty()){
					Object objTtr = (Object)ttrs.get(0);
					Object[] ttrss = (Object[])objTtr;
					TeacherTrainingRecords ttr = (TeacherTrainingRecords)ttrss[0];
					if(ttr != null){
						isSignup = ttr.getFinalStatus().toString();
						isEnrollCount++;
					}
				}
				
				//已报名人数
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("pid", pa.getProject().getId().toString());
				map.put("sid", pa.getTrainingSubject().getId().toString());
				map.put("tid", pa.getTrainingCollege().getId().toString());
				int count = this.iTeacherTrainingRecordsService.getAduTeacherCount(map, null, null);
				String isSignupCount = count+"";
				
//				//远程培训url
//				String trainURL = tc.getTrainURL();
//				if(trainURL != null && !"".equals(trainURL)){
//					if(!trainURL.startsWith("http://")){
//						trainURL = "http://"+trainURL;
//					}
//				}else{
//					trainURL = "";
//				}
				
				
//				int trainingSubjectId = ts.getId();
////				String teacherUuid = ttr.getTeacher().getUUID;
//				String teacherUuid = "";
//				String str = us.getId()+"_"+us.getName();
////				teacherUuid = MD5Util.string2MD5(str);
////				teacherUuid = MD5Util.convertMD5(str);
//				try {
//					DESUtil des = new DESUtil();
//					teacherUuid = des.encrypt(str);
////					System.out.println(str);
////					System.out.println(teacherUuid);
////					System.out.println(des.decrypt(teacherUuid));
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				
				sb.append("{");
				sb.append("\"projectApplyId\":\"" +id+ "\"");
				sb.append(",");
				sb.append("\"trainSubject\":\"" +subjectName+ "\"");
				sb.append(",");
				sb.append("\"trainCollege\":\"" +collegeName+ "\"");
				sb.append(",");
				sb.append("\"trainAdd\":\"" +trainAdd+ "\"");
				sb.append(",");
				sb.append("\"startTime\":\"" +startTime+ "\"");
				sb.append(",");
				sb.append("\"endTime\":\"" +endTime+ "\"");
				sb.append(",");
				sb.append("\"deadline\":\"" +(int)deadLine+ "\"");
				sb.append(",");
				sb.append("\"trainClassHour\":\"" +trainClassHour+ "\"");
				sb.append(",");
				sb.append("\"approveNumber\":\"" +approveNumber+ "\"");
				sb.append(",");
				sb.append("\"funds\":\"" +funds+ "\"");
				sb.append(",");
				sb.append("\"isEnrollType\":\"" +isEnrollType+ "\"");
				sb.append(",");
				sb.append("\"isSignup\":\"" +isSignup+ "\"");
				sb.append(",");
				sb.append("\"isSignupCount\":\"" +isSignupCount+ "\"");
//				sb.append(",");
//				sb.append("\"trainURL\":\"" +trainURL+ "\"");
//				sb.append(",");
//				sb.append("\"projectId\":\"" +projectId+ "\"");
//				sb.append(",");
//				sb.append("\"trainingSubjectId\":\"" +trainingSubjectId+ "\"");
//				sb.append(",");
//				sb.append("\"teacherUuid\":\"" +teacherUuid+ "\"");
				sb.append("},");
			}
			unEnrollCount = records - isEnrollCount;
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
			sb.append(",");
			sb.append("\"ProjectName\":\"" + project.getName()+"\"");//项目名称
			sb.append(",");
			String trainType = project.getTraintype() == 1 ? "集中面授":"远程培训";//培训形式
			sb.append("\"TrainType\":\"" + trainType + "\"");
			sb.append(",");
			sb.append("\"TotalRecordCount\":" + records);
			sb.append(",");
			sb.append("\"IsEnrollType\":" + isEnrollCount);
			sb.append(",");
			sb.append("\"UnEnrollType\":" + unEnrollCount);
			sb.append("}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"暂无相关报名记录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	
	// 项目类型
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + " > " + pt.getName();
		}
	}

	/**
	 * 获取区域信息
	 * @param a
	 * @return
	 */
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

		public IProjectAdminService getiProjectAdminService() {
			return iProjectAdminService;
		}

		public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
			this.iProjectAdminService = iProjectAdminService;
		}

		public ITrainingAdminService getiTrainingAdminService() {
			return iTrainingAdminService;
		}

		public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
			this.iTrainingAdminService = iTrainingAdminService;
		}

		public IAreaService getiAreaService() {
			return iAreaService;
		}

		public void setiAreaService(IAreaService iAreaService) {
			this.iAreaService = iAreaService;
		}

		public IProjectTypeService getiProjectTypeService() {
			return iProjectTypeService;
		}

		public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
			this.iProjectTypeService = iProjectTypeService;
		}


		public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
			return iTeacherTrainingRecordsService;
		}

		public void setiTeacherTrainingRecordsService(
				ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
			this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
		}

		
}
