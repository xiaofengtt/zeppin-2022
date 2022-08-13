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
	 * 培训记录页面初始化
	 * 回传的信息有teacher_train_records中的相关信息
	 * totalCount--总培训记录数
	 * createTime--发布时间
	 * trainingStatus--培训状态
	 * subjectName--培训学科
	 * projectName--培训项目
	 * trainingCollege--承训单位
	 * trainingAdd--培训地址
	 * trainingScore--培训成绩
	 * trainHour--获得学时
	 * certificate--证书编号
	 * trainingOnlineHour--获得网络研修学时
	 * trainingClassHour--培训总学时
	 * trainingStartTime--培训开始时间
	 * trainingEndTime--培训结束时间
	 * 
	 * trainType --培训方式
	 * 
	 * 未报道的、培训未结束的都没有培训成绩相关内容
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
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		
		// 排序（格式creatime-desc or creattime-asc）
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
		//其他筛选参数
		//项目ID
		int projectId = 0;
		if(request.getParameter("projectId")!=null){
			projectId = Integer.parseInt(request.getParameter("projectId"));
			pagram.put("projectId", projectId);
		}
		//学科ID
		int subjectId = 0;
		if(request.getParameter("subjectId")!=null){
			projectId = Integer.parseInt(request.getParameter("subjectId"));
			pagram.put("subjectId", subjectId);
		}
		//承训单位ID
		int trainCollegeId = 0;
		if(request.getParameter("trainCollegeId")!=null){
			projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
			pagram.put("trainCollegeId", trainCollegeId);
		}
		//培训方式ID
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
			sb.append("\"Message\":\"查询完毕\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for(int i = 0; i < li.size(); i++){
				Object[] obj = (Object[])li.get(i);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				Project p = (Project)obj[1];
				ProjectApply pa = (ProjectApply)obj[2];
				
				//培训结束的记录
				if(ttr.getTrainingStatus()>2){
					endCount++;
				}
				
//				* totalCount--总培训记录数
//				 * createTime--发布时间
//				 * trainingStatus--培训状态
//				 * subjectName--培训学科
//				 * projectName--培训项目
//				 * trainingCollege--承训单位
//				 * trainingAddr--培训地址
//				 * trainingScore--培训成绩
//				 * trainHour--获得学时
//				 * certificate--证书编号
//				 * trainingOnlineHour--获得网络研修学时
//				 * trainingClassHour--培训总学时
//				 * trainingStartTime--培训开始时间
//				 * trainingEndTime--培训结束时间
//				 * trainingReason--培训不合格原因
//				 * trainType --培训方式
//				 * ttrId --培训记录ID
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
					sb.append("\"certificate\":\"未获得\"");
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
					sb.append("\"trainingReason\":\"无\"");
				}else{
					sb.append("\"trainingReason\":\""+ttr.getTrainingReason()+"\"");
				}
				sb.append(",");
				if(pa.getTrainingAddress() == null){
					sb.append("\"trainingAddr\":\"无\"");
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
				String trainUrl = "";//教师一键登录连接地址
				if(p.getTraintype() == 1){
					sb.append("\"trainType\":\"集中面授\"");
				}else if(p.getTraintype() == 2){
					sb.append("\"trainType\":\"远程培训\"");
					trainUrl = pa.getTrainingCollege().getTrainURL();
					if(trainUrl != null && !"".equals(trainUrl)){
						if(!trainUrl.startsWith("http://")){
							trainUrl = "http://"+trainUrl;
						}
					}else{
						trainUrl="";
					}
				}else{
					sb.append("\"trainType\":\"其他\"");
				}
				sb.append(",");
				sb.append("\"trainTypeN\":\""+p.getTraintype()+"\"");//培训方式--用以区分是否显示‘去报名’按钮
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
			sb.append("\"Message\":\"暂无数据\"");
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
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String ttrId = request.getParameter("ttrId") == null? "":request.getParameter("ttrId");
		
		if(!"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
			
			
			// 判断当年已经回答过几次
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
					sb.append("\"Message\":\"您还未进行信息技术能力提升工程学前测评，请先完成学前测评后，再去培训吧！\"");
					sb.append("}");
					
				}else{
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"继续跳转。。。\"");
					sb.append("}");
				}
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}else{
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"继续跳转。。。\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"异常\"");
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
