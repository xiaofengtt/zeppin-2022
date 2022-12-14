package cn.zeppin.action.teacher;
 
import java.util.Calendar;
//import java.util.Date;
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
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DESUtil;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class TrainRecordsAction extends baseAction{
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
	private IHsdtestService iHsdTestService;
	
	
	public TrainRecordsAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	
	/**
	 * ???????????????????????????
	 * ??????????????????teacher_train_records??????????????????
	 * totalCount--??????????????????
	 * createTime--????????????
	 * trainingStatus--????????????
	 * subjectName--????????????
	 * projectName--????????????
	 * trainingCollege--????????????
	 * trainingAdd--????????????
	 * trainingScore--????????????
	 * trainHour--????????????
	 * certificate--????????????
	 * trainingOnlineHour--????????????????????????
	 * trainingClassHour--???????????????
	 * trainingStartTime--??????????????????
	 * trainingEndTime--??????????????????
	 * 
	 * trainType --????????????
	 * 
	 * ??????????????????????????????????????????????????????????????????
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void initPage(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		Integer teacherId = 0;
		
		if(us!=null){
			teacherId = us.getId();
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		// ??????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		
		// ???????????????creatime-desc or creattime-asc???
		Map<String, String> sortParams = new HashMap<>();
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			
			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "desc");
		}
		
		
		Map<String, Object> pagram = new HashMap<String, Object>();
		//??????????????????
		//??????ID
		int projectId = 0;
		if(request.getParameter("projectId")!=null){
			projectId = Integer.parseInt(request.getParameter("projectId"));
			pagram.put("projectId", projectId);
		}
		//??????ID
		int subjectId = 0;
		if(request.getParameter("subjectId")!=null){
			projectId = Integer.parseInt(request.getParameter("subjectId"));
			pagram.put("subjectId", subjectId);
		}
		//????????????ID
		int trainCollegeId = 0;
		if(request.getParameter("trainCollegeId")!=null){
			projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
			pagram.put("trainCollegeId", trainCollegeId);
		}
		//????????????ID
		int trainType = 0;
		if(request.getParameter("trainType")!=null){
			projectId = Integer.parseInt(request.getParameter("trainType"));
			pagram.put("trainType", trainType);
		}
		
		pagram.put("finalStatus", 2);
		
		
		int count = this.iTeacherTrainingRecordsService.getTeacherRecordsCountByTid(teacherId, pagram);
		List li = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(teacherId, offset, count, sortParams, pagram);
		int endCount = 0;
		
		if(li!=null && !li.isEmpty()){
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for(int i = 0; i < li.size(); i++){
				Object[] obj = (Object[])li.get(i);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				Project p = (Project)obj[1];
				ProjectApply pa = (ProjectApply)obj[2];
				
				//?????????????????????
				if(ttr.getTrainingStatus()>2){
					endCount++;
				}
				
//				* totalCount--??????????????????
//				 * createTime--????????????
//				 * trainingStatus--????????????
//				 * subjectName--????????????
//				 * projectName--????????????
//				 * trainingCollege--????????????
//				 * trainingAddr--????????????
//				 * trainingScore--????????????
//				 * trainHour--????????????
//				 * certificate--????????????
//				 * trainingOnlineHour--????????????????????????
//				 * trainingClassHour--???????????????
//				 * trainingStartTime--??????????????????
//				 * trainingEndTime--??????????????????
//				 * trainingReason--?????????????????????
//				 * trainType --????????????
//				 * ttrId --????????????ID
				sb.append("{");
				sb.append("\"createTime\":\""+ttr.getCreattime()+"\"");
				sb.append(",");
				sb.append("\"trainingStatus\":\""+ttr.getTrainingStatus()+"\"");
				sb.append(",");
				sb.append("\"subjectName\":\""+ttr.getTrainingSubject().getName()+"\"");
				sb.append(",");
				sb.append("\"projectName\":\""+ttr.getProject().getName()+"\"");
				sb.append(",");
				sb.append("\"trainingCollege\":\""+ttr.getTrainingCollege().getName()+"\"");
				sb.append(",");
				
				if(ttr.getTrainingScore() == null){
					sb.append("\"trainingScore\":\"0\"");
				}else{
					sb.append("\"trainingScore\":\""+ttr.getTrainingScore()+"\"");
				}
				sb.append(",");
				
				if(ttr.getTrainingClasshour() == null){
					sb.append("\"trainHour\":\"0\"");
				}else{
					sb.append("\"trainHour\":\""+ttr.getTrainingClasshour()+"\"");
				}
				sb.append(",");
				
				if(ttr.getCertificate() == null){
					sb.append("\"certificate\":\"?????????\"");
				}else{
					sb.append("\"certificate\":\""+ttr.getCertificate()+"\"");
				}
				sb.append(",");
				
				if(ttr.getTrainingOnlineHour() == null){
					sb.append("\"trainingOnlineHour\":\"0\"");
				}else{
					sb.append("\"trainingOnlineHour\":\""+ttr.getTrainingOnlineHour()+"\"");
				}
				sb.append(",");
				
				if(ttr.getTrainingReason() == null){
					sb.append("\"trainingReason\":\"???\"");
				}else{
					sb.append("\"trainingReason\":\""+ttr.getTrainingReason()+"\"");
				}
				sb.append(",");
				if(pa.getTrainingAddress() == null){
					sb.append("\"trainingAddr\":\"???\"");
				}else{
					sb.append("\"trainingAddr\":\""+pa.getTrainingAddress()+"\"");
				}
				sb.append(",");
				sb.append("\"trainingClassHour\":\""+pa.getTrainingClasshour()+"\"");
				sb.append(",");
				sb.append("\"trainingStartTime\":\""+pa.getTrainingStarttime()+"\"");
				sb.append(",");
				sb.append("\"trainingEndTime\":\""+pa.getTrainingEndtime()+"\"");
				sb.append(",");
				String trainUrl = "";//??????????????????????????????
				if(p.getTraintype() == 1){
					sb.append("\"trainType\":\"????????????\"");
				}else if(p.getTraintype() == 2){
					sb.append("\"trainType\":\"????????????\"");
					trainUrl = pa.getTrainingCollege().getTrainURL();
					if(trainUrl != null && !"".equals(trainUrl)){
						if(!trainUrl.startsWith("http://")){
							trainUrl = "http://"+trainUrl;
						}
					}else{
						trainUrl="";
					}
				}else{
					sb.append("\"trainType\":\"??????\"");
				}
				sb.append(",");
				sb.append("\"trainTypeN\":\""+p.getTraintype()+"\"");//????????????--?????????????????????????????????????????????
				sb.append(",");
				sb.append("\"trainURL\":\""+trainUrl+"\"");
				
				int trainingSubjectId = pa.getTrainingSubject().getId();
				String teacherUuid = "";
				String str = us.getId()+"_"+us.getName();
				try {
					DESUtil des = new DESUtil();
					teacherUuid = des.encrypt(str);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				sb.append(",");
				sb.append("\"projectId\":\"" +p.getId()+ "\"");
				sb.append(",");
				sb.append("\"trainingSubjectId\":\"" +trainingSubjectId+ "\"");
				sb.append(",");
				sb.append("\"teacherUuid\":\"" +teacherUuid+ "\"");
				sb.append(",");
				sb.append("\"ttrId\":\"" +ttr.getId()+ "\"");
				
				sb.append("},");
				
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("],");
			sb.append("\"TotalRecordCount\":\""+count+"\"");
			sb.append(",");
			sb.append("\"EndCount\":\""+endCount+"\"");
			sb.append(",");
			int onTrainCount = count-endCount;
			sb.append("\"OnTrainCount\":\""+onTrainCount+"\"");
			sb.append("}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			
			sb.append("{");
			sb.append("\"Result\":\"EMPTY\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}
		
		
		
	}
	
	public void checkIsTest(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us==null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String ttrId = request.getParameter("ttrId") == null? "":request.getParameter("ttrId");
		
		if(!"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
			
			
			// ?????????????????????????????????
//			int year = new Date().getYear() + 1900;
			Calendar a=Calendar.getInstance();
			int year = a.get(Calendar.YEAR);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("teacher", us.getId());
			map.put("year", year);

			int count = this.getiHsdTestService().getHsdTestCount(map);
			
			if(ttr.getProject().getIsTest()){
				if(count == 0){
					sb.append("{");
					sb.append("\"Result\":\"REDIRECT\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????????????????????????????????????????????????????????????????????????????????????????????????????\"");
					sb.append("}");
					
				}else{
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"?????????????????????\"");
					sb.append("}");
				}
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}else{
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"?????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"??????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
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

		public IHsdtestService getiHsdTestService() {
			return iHsdTestService;
		}

		public void setiHsdTestService(IHsdtestService iHsdTestService) {
			this.iHsdTestService = iHsdTestService;
		}

		
}
