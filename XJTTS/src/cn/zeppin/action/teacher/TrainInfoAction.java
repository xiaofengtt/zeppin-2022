package cn.zeppin.action.teacher;
 
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
//import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingRecords;
//import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherTrainingAssginedService;
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
	private ITeacherTrainingAssginedService teacherTrainingAssignedService;
	private IOrganizationService iOrganizationService; // ????????????
	
	
	public TrainInfoAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 20160726
	 * ????????????????????????????????????
	 * 
	 */
//	@SuppressWarnings("rawtypes")
	public void initProjectAPI(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		List<ProjectType> lityps = iProjectTypeService.findAll();
		//20180612????????????????????????????????? ???????????????????????????????????????????????????????????????
		String organization = "";
		Organization o = this.iOrganizationService.get(us.getOrganization());
		if(o != null){
			organization  = getOrg(o);
		}
		List<Project> lstProject = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM,organization);
		int records = 0;
		if(lstProject != null && !lstProject.isEmpty()){
			records = lstProject.size();
			
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for(Project project : lstProject){
				sb.append("{");
				sb.append("\"projectId\":\""+project.getId()+"\"");
				sb.append(",");
				sb.append("\"projectName\":\""+project.getName()+"????????????\"");
				sb.append(",");
				sb.append("\"projectYear\":\""+project.getYear()+"\""); //??????????????????
				sb.append(",");
				String trainType = "";
				if(project.getTraintype() == 1){
					trainType = "??????????????????????????????";
				}else if(project.getTraintype() == 2){
					trainType = "????????????";
				}else{
					trainType = "??????????????????";
				}
				sb.append("\"trainType\":\""+trainType+"\"");
				sb.append("},");
			}
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
			sb.append("\"Message\":\"????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("enrollType", DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
//		params.put("status", 2);
//		params.put("group", "t.project");
//		
//		List lstProject = this.iProjectApplyService.getProjectByParams(params, lityps);
//		int records = 0;
//		if( lstProject != null && !lstProject.isEmpty()){
//			records = lstProject.size();
//			
//			sb.append("{");
//			sb.append("\"Result\":\"OK\"");
//			sb.append(",");
//			sb.append("\"Message\":\"????????????\"");
//			sb.append(",");
//			sb.append("\"Records\":[");
//			for(Object obj: lstProject){
//				Object[] o = (Object[])obj;
////				ProjectApply pa = (ProjectApply)o[0];
//				Project project = (Project)o[1];
//				sb.append("{");
//				sb.append("\"projectId\":\""+project.getId()+"\"");
//				sb.append(",");
//				sb.append("\"projectName\":\""+project.getName()+"????????????\"");
//				sb.append(",");
//				sb.append("\"projectYear\":\""+project.getYear()+"\""); //??????????????????
//				sb.append(",");
//				String trainType = "";
//				if(project.getTraintype() == 1){
//					trainType = "??????????????????????????????";
//				}else if(project.getTraintype() == 2){
//					trainType = "????????????";
//				}else{
//					trainType = "??????????????????";
//				}
//				sb.append("\"trainType\":\""+trainType+"\"");
//				sb.append("},");
//				
//			}
//			sb.delete(sb.length() - 1, sb.length());
//			sb.append("]");
//			sb.append(",");
//			sb.append("\"TotalRecordCount\":" + records);
//			sb.append("}");
//			
//			Utlity.ResponseWrite(sb.toString(), "application/json", response);
//			
//		}else{
//			sb.append("{");
//			sb.append("\"Result\":\"ERROR\"");
//			sb.append(",");
//			sb.append("\"Message\":\"????????????????????????\"");
//			sb.append("}");
//			Utlity.ResponseWrite(sb.toString(), "application/json", response);
//		}
		
		
	}
	public String getOrg(Organization org){
		String orgStr = org.getId()+"";
		while (org.getOrganizationLevel().getLevel() > 1) {
			orgStr += ","+org.getOrganization().getId();
			org = org.getOrganization();
		}
		return orgStr;
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
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		// ?????????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

//		int start = Integer.parseInt(ist);

		Map<String, String> sortParams = new HashMap<>();
		List<ProjectType> lityps = iProjectTypeService.findAll();
		
		Map<String, Object> params = new HashMap<>();
		String projectId = "";
		if(request.getParameter("id")!=null){
			projectId = request.getParameter("id");
			params.put("projectId", projectId);
			
		}
		params.put("enrollType", DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
		params.put("status", "2");//???????????????????????????
		
		// ??????
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			
			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "desc");
		}
//		int offset = (start - 1) * DictionyMap.maxPageSize;

//		List li = this.iProjectApplyService.getProjectApplyByParams(params, sortParams, lityps, offset, DictionyMap.maxPageSize);
		
		Project project = this.iProjectService.get(Integer.parseInt(projectId));
		int records = 0;//???
		int isEnrollCount = 0;//?????????
		int unEnrollCount = 0;//?????????
		if(project.getIsAdvance() == 1){
			/*
			 * ????????????????????????????????????????????????????????????
			 * ?????????????????? ???????????????????????????????????????????????????
			 */
			Set<ProjectSubjectRange> setSubjectRanges = project.getProjectSubjectRanges();
			if(setSubjectRanges == null){
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"??????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			records = setSubjectRanges.size();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for (ProjectSubjectRange tsr : setSubjectRanges) {
				String id = tsr.getId()+"";
				Project p = tsr.getProject();
				TrainingSubject ts = tsr.getTrainingSubject();
				
				long nowTime = System.currentTimeMillis();
				double deadLine = 0;//???????????????  ??????????????????????????????
				if(p.getEndtime() != null){//????????????????????????
					Timestamp enrollEndTime = p.getEndtime();
					deadLine = Math.ceil(((enrollEndTime.getTime()-nowTime)/(60*60*24*1000)));
				}
				HashMap<String, String> searchMap = new HashMap<>();
				searchMap.put("project", p.getId()+"");
				searchMap.put("subject", ts.getId()+"");
				
				//???????????????
				int countT = this.teacherTrainingAssignedService.getCountByParams(searchMap);
				String isSignupCount = countT+"";		
				
				// ???????????????
				searchMap.put("teacher", us.getId()+"");
				int count = this.teacherTrainingAssignedService.getCountByParams(searchMap);
				String isSignup = "";
				if(count > 0){
					isSignup = "1";
					isEnrollCount++;
				}
				if(deadLine < 0 && !"1".equals(isSignup)) {
					records--;
					continue;
				}
				//??????????????????
				Map<String, Object> pagram = new HashMap<String, Object>();
				pagram.put("projectId", p.getId());
				pagram.put("subjectId", Integer.parseInt(ts.getId().toString()));
				List ttrs = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(us.getId(), 0, DictionyMap.maxPageSize, null, pagram);
				
				if(ttrs != null && !ttrs.isEmpty()){
					Object objTtr = (Object)ttrs.get(0);
					Object[] ttrss = (Object[])objTtr;
					TeacherTrainingRecords ttr = (TeacherTrainingRecords)ttrss[0];
					if(ttr != null){
						isSignup = ttr.getFinalStatus().toString();
					}
				}
				
				sb.append("{");
				sb.append("\"projectApplyId\":\"" +id+ "\"");
				sb.append(",");
				sb.append("\"trainSubject\":\"" +ts.getName()+ "\"");
				sb.append(",");
				sb.append("\"trainCollege\":\"??????????????????\"");
				sb.append(",");
				sb.append("\"trainAdd\":\"??????????????????\"");
				sb.append(",");
				sb.append("\"startTime\":\"??????\"");
				sb.append(",");
				sb.append("\"endTime\":\"??????\"");
				sb.append(",");
				sb.append("\"deadline\":\"" +(int)deadLine+ "\"");
				sb.append(",");
				sb.append("\"trainClassHour\":\"??????\"");
				sb.append(",");
				sb.append("\"approveNumber\":0");
				sb.append(",");
				sb.append("\"funds\":\"" +p.getFunds().toString()+ "\"");
				sb.append(",");
				sb.append("\"isEnrollType\":\"" +p.getEnrollType().toString()+ "\"");
				sb.append(",");
				sb.append("\"isAdvance\":" + project.getIsAdvance());
				sb.append(",");
				sb.append("\"isSignup\":\"" +isSignup+ "\"");
				sb.append(",");
				sb.append("\"isSignupCount\":\"" +isSignupCount+ "\"");
				sb.append("},");
				
			}
			if(records <= 0) {
				StringBuilder sbtr = new StringBuilder();
				sbtr.append("{");
				sbtr.append("\"Result\":\"ERROR\"");
				sbtr.append(",");
				sbtr.append("\"Message\":\"??????????????????\"");
				sbtr.append("}");
				Utlity.ResponseWrite(sbtr.toString(), "application/json", response);
				return;
			}
			unEnrollCount = records - isEnrollCount;
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
			sb.append(",");
			sb.append("\"ProjectName\":\"" + project.getName()+"\"");//????????????
			sb.append(",");
			String trainType = "";
			if(project.getTraintype() == 1){
				trainType = "??????????????????????????????";
			}else if(project.getTraintype() == 2){
				trainType = "????????????";
			}else{
				trainType = "??????????????????";
			}
			sb.append("\"TrainType\":\"" + trainType + "\"");
			sb.append(",");
			sb.append("\"TotalRecordCount\":" + records);
			sb.append(",");
			sb.append("\"IsEnrollType\":" + isEnrollCount);
			sb.append(",");
			sb.append("\"UnEnrollType\":" + unEnrollCount);
			sb.append("}");
			
		}else{
			List li = this.iProjectApplyService.getProjectByParams(params, lityps);
			
			if(li != null && !li.isEmpty()){
				records = li.size();
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append(",");
				sb.append("\"Records\":[");
				for (Object ba : li) {
					Object[] obj = (Object[]) ba;
					ProjectApply pa = (ProjectApply) obj[0];
					int id = pa.getId();
					Project p = pa.getProject();
//					String projectName = p.getName();//??????
					
					TrainingSubject ts = pa.getTrainingSubject();
					String subjectName = ts.getName();//??????
					
					TrainingCollege tc = pa.getTrainingCollege();
					String collegeName = tc.getName();//????????????
					
//					String trainType = p.getTraintype() == 1 ? "??????????????????????????????":"????????????";//????????????
					String trainAdd = pa.getTrainingAddress() == null? "??????????????????":pa.getTrainingAddress();//????????????
					if("".equals(trainAdd)){
						trainAdd = "??????????????????";
					}
					
					String startTime = "??????";
					if(pa.getTrainingStarttime() != null){
						startTime = Utlity.timeSpanToDateString(pa.getTrainingStarttime());//????????????
					}
					String endTime = Utlity.timeSpanToDateString(pa.getTrainingEndtime());//????????????
					
					long nowTime = System.currentTimeMillis();
//					String deadline = Utlity.timeSpanToDateString(new Timestamp((new Date()).getTime()));//??????????????????
					double deadLine = 0;//???????????????  ??????????????????????????????
					if(pa.getEnrollEndTime() != null){
//						deadline = Utlity.timeSpanToDateString(pa.getEnrollEndTime());//??????????????????
						Timestamp enrollEndTime = pa.getEnrollEndTime();
						deadLine = Math.ceil(((enrollEndTime.getTime()-nowTime)/(60*60*24*1000)));
					}
					
					String trainClassHour = "";//??????
					
					if(p.getTraintype() == 1){
						trainClassHour = pa.getTrainingClasshour().toString();//??????
					}else if(p.getTraintype() == 2){
						trainClassHour = pa.getTrainingOnlineHour().toString();//????????????
					}else{
						trainClassHour = (pa.getTrainingClasshour()+pa.getTrainingOnlineHour())+"";
					}
					
					String approveNumber = pa.getApproveNumber().toString();//??????
					
					String funds = p.getFunds().toString();//??????
					String isEnrollType = p.getEnrollType().toString();//??????????????????
					
					//???????????????
//					String teacherIdCard = us.getIdcard();
//					String teacherName = us.getName();
//					TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.getTeacherTrainingRecordByIdCard(teacherName, teacherIdCard, p.getName(), tc.getName(), ts.getName());
//					if(ttr != null){
//						isSignup = ttr.getFinalStatus().toString();
//						isEnrollCount++;
//					}
					
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

					if(deadLine < 0 && ("-1".equals(isSignup) || "".equals(isSignup))) {
						records--;
						continue;
					}
					//???????????????
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("pid", pa.getProject().getId().toString());
					map.put("sid", pa.getTrainingSubject().getId().toString());
					map.put("tid", pa.getTrainingCollege().getId().toString());
					int count = this.iTeacherTrainingRecordsService.getAduTeacherCount(map, null, null);
					String isSignupCount = count+"";
					
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
					sb.append("\"isAdvance\":" + project.getIsAdvance());
					sb.append(",");
					sb.append("\"isSignup\":\"" +isSignup+ "\"");
					sb.append(",");
					sb.append("\"isSignupCount\":\"" +isSignupCount+ "\"");
					sb.append("},");
				}
				
				if(records <= 0) {
					StringBuilder sbtr = new StringBuilder();
					sbtr.append("{");
					sbtr.append("\"Result\":\"ERROR\"");
					sbtr.append(",");
					sbtr.append("\"Message\":\"??????????????????\"");
					sbtr.append("}");
					Utlity.ResponseWrite(sbtr.toString(), "application/json", response);
					return;
				}
				
				unEnrollCount = records - isEnrollCount;
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]");
				sb.append(",");
				sb.append("\"ProjectName\":\"" + project.getName()+"\"");//????????????
				sb.append(",");
				String trainType = "";
				if(project.getTraintype() == 1){
					trainType = "??????????????????????????????";
				}else if(project.getTraintype() == 2){
					trainType = "????????????";
				}else{
					trainType = "??????????????????";
				}
				sb.append("\"TrainType\":\"" + trainType + "\"");
				sb.append(",");
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"IsEnrollType\":" + isEnrollCount);
				sb.append(",");
				sb.append("\"UnEnrollType\":" + unEnrollCount);
				sb.append("}");
				
//				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"??????????????????\"");
				sb.append("}");
				
			}
		}
			
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	// ????????????
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + " > " + pt.getName();
		}
	}

	/**
	 * ??????????????????
	 * @param a
	 * @return
	 */
	public String getAreaNaviString(Area a) {
		String areaid = a.getName();
		String areaid1 = "";
		String areaid2 = "";
		String areacode = a.getParentcode();
		if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
			// ?????????????????????????????????
			Area pae = this.iAreaService.getAreaByCode(areacode);
			areaid1 = pae.getName();
			areacode = pae.getParentcode();
			// ????????????
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

		public ITeacherTrainingAssginedService getTeacherTrainingAssignedService() {
			return teacherTrainingAssignedService;
		}

		public void setTeacherTrainingAssignedService(
				ITeacherTrainingAssginedService teacherTrainingAssignedService) {
			this.teacherTrainingAssignedService = teacherTrainingAssignedService;
		}


		public IOrganizationService getiOrganizationService() {
			return iOrganizationService;
		}
		

		public void setiOrganizationService(IOrganizationService iOrganizationService) {
			this.iOrganizationService = iOrganizationService;
		}

		
}
