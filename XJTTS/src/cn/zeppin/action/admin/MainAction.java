package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IMailConnectionService;
import cn.zeppin.service.IMailInformationService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.IServiceApplyService;
import cn.zeppin.service.ITeacherEduAdvanceService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeacherTrainingReplaceService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("rawtypes")
public class MainAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectTypeService iProjectTypeService;
	private IProjectApplyService iProjectApplyService;
	private IProjectService iProjectService;
	private ITrainingAdminService iTrainingAdminService;
	private IAssignTeacherTaskService iAssignTeacherTaskService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;
	private IMailInformationService mailInformationService;
	private IServiceApplyService iServiceApplyService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingAdminService iTrainingAdmin;
	private IMailConnectionService mailConnectionService;
	private IOrganizationService iOrganization; // organization
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private ITeacherEduAdvanceService iTeacherEduAdvanceService;
	private ITeacherTrainingReplaceService iTeacherTrainingReplaceService;

	private Short role;
	private int organizationLevel;

	private void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void init() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		role = us.getRole();
		organizationLevel = us.getOrganizationLevel();

		if (role == ROLEENUM.TRAINING.getValue()) {
			// 如果是“承训单位管理员”，则只加载“可以申报的项目”总数
			StringBuilder training = new StringBuilder();
			training.append("\"canApplyForTrainingProject\":\""
					+ getCanApplyForTrainingProject(us) + "\"");
			training.append(initNotice());
			responseJsonToHtml(training);
		} else if (role == ROLEENUM.ADMIN.getValue()) {
			// 项目管理员
			StringBuilder admin = new StringBuilder();
			admin.append("\"notAuditForTrainingTeacher\":\""
					+ getNotAuditForTrainingTeacher() + "\",");// 待审核的培训学员
			admin.append("\"notAuditRecords\":\"" + getNotAuditRecords(us)// 需要审核的申报记录
					+ "\",");
			admin.append("\"notReleaseForTrainingProject\":\""// 统计已开展的培训项目
					+ getNotReleaseForTrainingProject() + "\",");
			admin.append("\"teacherToSubmitTask\":\""
					+ getTeacherToSubmitTask(us) + "\",");
			admin.append("\"teacherEduAdvances\":\""//未审核的学员培训记录
					+ getCountForTeacherEduAdvance(us) + "\",");
			admin.append("\"teacherTrainingReplace\":\""//未审核的学员培训记录
					+ getCountForTeacherTrainingReplace() + "\",");
//			admin.append("\"countForServiceApply\":\""
//					+ getCountForServiceApply() + "\",");
			admin.append("\"countToUnReadMessae\":\""
					+ getCountToUnReadMessae(us) + "\"");
			admin.append(initNotice());// 公告
			admin.append(getGraphs());// 每日增加培训人数“曲线图”
			responseJsonToHtml(admin);
		} else {
			// 其他
			StringBuilder otherBuilder = new StringBuilder();
			otherBuilder.append(initNotice());// 公告
			responseJsonToHtml(otherBuilder);
		}
	}

	/**
	 * 初始化公告(管理员、承训单位、评审专家获取收件箱列表)
	 */
	private StringBuilder initNotice() {
		StringBuilder sb = new StringBuilder();
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		// 显示条数（默认5条）
		int limit = 5;
		// 起始页
		int start = 0;
		// 排序
		String sort = "id desc";
		// 查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		Integer projectAdminId = us.getId();
		Short role = us.getRole();
		params.put("paId", projectAdminId);
		params.put("role", role);// 权限
		params.put("type", 2);// 公告
		try {
			// 查询
			List list = this.mailConnectionService.getListByParams(params,
					start, limit, sort);
			if (list != null && list.size() > 0) {
				sb.append(",\"notice\":[");
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
//					MailConnection mc = (MailConnection) obj[0];
					MailInformation mi = (MailInformation) obj[1];
					String creator;
					// 公告发布人
					if (mi.getCreatorRole() == 1) {
						ProjectAdmin pa = this.iProjectAdminService.get(mi
								.getCreator());
						creator = pa.getOrganization().getName() + "管理员--"
								+ pa.getName();
					} else {
						TrainingAdmin ta = this.iTrainingAdmin.get(mi
								.getCreator());
						creator = ta.getTrainingCollege().getName() + "管理员--"
								+ ta.getName();
					}
					sb.append("{");
					sb.append("\"title\":\"" + mi.getTitle() + "\",");
					sb.append("\"content\":\"" + mi.getText() + "\",");
					sb.append("\"creator\":\"" + creator + "\",");
					sb.append("\"createtime\":\""
							+ dataTimeConvertUtility.DateToString(
									mi.getSendtime(), "yyyy年MM月dd日") + "\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb;
	}

	/**
	 * 提交json到html中
	 * 
	 * @param builder
	 *            不同情况 拼接的json
	 */
	private void responseJsonToHtml(StringBuilder builder) {
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("{");
		countBuilder.append("\"status\":\"OK\"");
		countBuilder.append(",");
		countBuilder.append("\"role\":\"" + role + "\",");
		countBuilder.append("\"organizationLevel\":\"" + organizationLevel + "\",");
		countBuilder.append("\"records\":{");
		countBuilder.append(builder);
		countBuilder.append("}");
		countBuilder.append("}");

		Utlity.ResponseWrite(countBuilder.toString(), "application/json",
				response);
	}

	/**
	 * 可以申报的项目 注： 只针对“承训单位管理员”
	 */
	private int getCanApplyForTrainingProject(UserSession user) {
		TrainingAdmin trainingAdmin = iTrainingAdminService.get(user.getId());
		Date date = new Date();
		List li = this.iProjectService.getProjectForTrainingCollege(null, null,
				trainingAdmin.getTrainingCollege().getId(), date, 0, -1);
		if (li == null) {
			return 0;
		}
		// System.out.println("可以申报的项目： " + li.size());
		return li.size();
	}

	/**
	 * 统计需要审核的申报记录 注： 只针对“管理员”
	 */
	private int getNotAuditRecords(UserSession us) {
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		Map<String, Object> params = new HashMap<>();
		// “未审核” 状态
		params.put("status", 1);
		params.put("organization", us.getOrganization());
		int count = this.iProjectApplyService.getProjectApplyCountByParams(
				params, lityps);
		// System.out.println("统计需要审核的申报记录结果：  " + count);
		return count;
	}

	/**
	 * 已开展的培训项目 区分各级项目管理员开设的项目信息
	 */
	private int getNotReleaseForTrainingProject() {
		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		Map<String, Object> params = new HashMap<>();
		// “未审核” 状态
		params.put("status", 2);
		params.put("level", us.getOrganizationLevel());
		params.put("organization", us.getOrganization());
		int count = this.iProjectService.getProjectListCountByParams(params,
				lityps);
		// System.out.println("待开展的培训项目结果：  " + count);
		return count;
	}

	/**
	 * 统计学员报送任务
	 */
	private int getTeacherToSubmitTask(UserSession us) {
		int count = 0;
//		List<Map<String, Object>> results = this.getiAssignTeacherTaskService()
//				.getAssignTeacherTaskResults(null, -1, -1, us, 0, -1);
//
//		if (results != null || results.size() != 0) {
//			for (Map<String, Object> att : results) {
//				// 未过截止日期
//				if ((new Date()).getTime() <= ((Timestamp) att.get("timeup"))
//						.getTime()) {
//					int teacherNum = (int) att.get("teacher_number");
//					int passNum = (int) att.get("passnum");
//					// 审核已通过人数 <任务计划报送人数
//					if (passNum < teacherNum) {
//						count++;
//					}
//				}
//			}
//		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("us", us);
		params.put("timeup", new Timestamp(System.currentTimeMillis()));
//		long start = System.currentTimeMillis();
		count = this.iAssignTeacherTaskService.getAssignTeacherTaskCount(params);
		// System.out.println("统计学员报送任务： " + count);
//		long end = System.currentTimeMillis();
//		System.out.println("待审核的培训学员："+(end-start));
		return count;
	}

	/**
	 * 待审核的培训学员
	 * 20161009第二次优化数据获取接口
	 */
	private int getNotAuditForTrainingTeacher() {
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization org = this.iOrganization.get(us.getOrganization());
//		@SuppressWarnings("unchecked")
//		List<ProjectType> lityps = (List<ProjectType>) session
//				.getAttribute("lstProjectType");

//		List list = this.iTeacherTrainingRecords.getAduTeacher(null, -1, -1,
//				-1, 1, 1, org, lityps, 0, -1, null);
//		if (list == null) {
//			return 0;
//		}
		int isadmin = 0;
		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
			isadmin = 1;
		}
		long start = System.currentTimeMillis();
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(isadmin, 1, org, us.getOrganizationLevel());
		// System.out.println("待审核的培训学员： " + list.size());
		long end = System.currentTimeMillis();
		System.out.println("待审核的培训学员："+(end-start));
		return records;
	}

	/**
	 * 统计下级管理员申请待回复的记录
	 * 
	 * @return
	 */
//	private int getCountForServiceApply() {
//		Map<String, Object> params = new HashMap<>();
//		// “未回复” 状态
//		params.put("status", 0);
//		return iServiceApplyService.getCountByParams(params);
//	}
	
	/**
	 * 统计未处理的学历提升记录
	 * @return
	 */
	private int getCountForTeacherEduAdvance(UserSession us) {
		Map<String, Object> params = new HashMap<>();
		
		
		// “未回复” 状态
		params.put("status", -1);
		params.put("finalStatus", -1);
		return this.iTeacherEduAdvanceService.getListCountByParams(params);//未审核;
	}
	
	/**
	 * 获取未审核的教师培训替换记录
	 * @return
	 */
	private int getCountForTeacherTrainingReplace(){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", "1");
		return this.iTeacherTrainingReplaceService.getRecordsListByParams(param, null);
	}

	/**
	 * 未读站内信
	 */
	private int getCountToUnReadMessae(UserSession us) {
		Integer projectAdminId = us.getId();
		Short role = us.getRole();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("paId", projectAdminId);
		params.put("role", role);
		params.put("status", 1);// 未读
		int count = this.mailConnectionService.getListCountByParams(params);
		// System.out.println("未读站内信： " + count);
		return count;
	}

	// /////////////////////////////////////////////////////////
	/**
	 * 曲线图
	 */
//	private StringBuilder getGraph() {
//		Timestamp beginDate = null;// 开始日期
//		Timestamp endDate = null;// 结束日期
//		int count;
//		StringBuilder sb = new StringBuilder();
//
//		Calendar theCa = Calendar.getInstance();
//		try {
//			sb.append(",\"graph\":[");
//			for (int i = -30; i < 0; i++) {
//				theCa.setTime(new Date());
//				theCa.set(Calendar.HOUR_OF_DAY, 0);
//				theCa.set(Calendar.SECOND, 0);
//				theCa.set(Calendar.MINUTE, 0);
//				theCa.add(theCa.DATE, i);
//
//				endDate = new Timestamp(theCa.getTimeInMillis());
//
//				if (beginDate != null) {
//					count = iTeacherTrainingRecordsService
//							.getAduTeacherCountByTimePeriod(beginDate, endDate);
//					sb.append("{");
//					sb.append("\"date\":\""
//							+ dataTimeConvertUtility.DateToString(endDate,
//									"MM/dd") + "\",");
//					sb.append("\"count\":\"" + count + "\"");
//					sb.append("},");
//					beginDate = endDate;
//				} else {
//					beginDate = new Timestamp(theCa.getTimeInMillis());
//					theCa.add(theCa.DATE, i);
//				}
//			}
//			sb.delete(sb.length() - 1, sb.length());
//			sb.append("]");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return sb;
//	}
	
	private StringBuilder getGraphs(){
		StringBuilder sb = new StringBuilder();
		Timestamp beginDate = null;// 开始日期
		Timestamp endDate = null;// 结束日期
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(new Date());
		theCa.set(Calendar.HOUR_OF_DAY, 0);
		theCa.set(Calendar.SECOND, 0);
		theCa.set(Calendar.MINUTE, 0);
		theCa.add(Calendar.DATE, -30);
		beginDate = new Timestamp(theCa.getTimeInMillis());//统计第一天
		theCa.setTime(new Date());
		theCa.set(Calendar.HOUR_OF_DAY, 0);
		theCa.set(Calendar.SECOND, 0);
		theCa.set(Calendar.MINUTE, 0);
		theCa.add(Calendar.DATE, -1);
		endDate = new Timestamp(theCa.getTimeInMillis());//统计最后一天
		List listRecordsTimes = this.iTeacherTrainingRecords.getAduTeacherTimeListByTimePeriod(beginDate, endDate);
		Map<String, Object> dayCounts = new LinkedHashMap<String, Object>();
		String beginDay = Utlity.timeSpanToDateString(beginDate);
		String endDay = Utlity.timeSpanToDateString(endDate);
		dayCounts.put(beginDay, 0);
		for (int i = -29; i < -1; i++) {
			theCa.setTime(new Date());
			theCa.set(Calendar.HOUR_OF_DAY, 0);
			theCa.set(Calendar.SECOND, 0);
			theCa.set(Calendar.MINUTE, 0);
			theCa.add(Calendar.DATE, i);

			endDate = new Timestamp(theCa.getTimeInMillis());
			String day = Utlity.timeSpanToDateString(endDate);
			dayCounts.put(day, 0);
		}
		dayCounts.put(endDay, 0);
		
		if(listRecordsTimes != null && listRecordsTimes.size() > 0){
			for(int i = 0; i < listRecordsTimes.size(); i++){
				Object[] obj = (Object[])listRecordsTimes.get(i);
				String day = obj[0].toString();
				String count = obj[1].toString();
				dayCounts.put(day, count);
			}

		}
		try {
			sb.append(",\"graph\":[");
			
			for (Map.Entry<String, Object> entry : dayCounts.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue().toString();
				sb.append("{");
				sb.append("\"date\":\""+ dataTimeConvertUtility.DateToString(Utlity.stringToDate(key),"MM/dd") + "\",");
				sb.append("\"count\":\"" + value + "\"");
				sb.append("},");
			}
			
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return sb;
		
	}

	public String openServiceInfo() {
		ServiceApplyInfoAction serviceApplyInfoAction = new ServiceApplyInfoAction();
		serviceApplyInfoAction.init();
		return "open_serviceInfo";
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	public void setiTrainingAdminService(
			ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}

	public IAssignTeacherTaskService getiAssignTeacherTaskService() {
		return iAssignTeacherTaskService;
	}

	public void setiAssignTeacherTaskService(
			IAssignTeacherTaskService iAssignTeacherTaskService) {
		this.iAssignTeacherTaskService = iAssignTeacherTaskService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}

	public void setiTeacherTrainingRecords(
			ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}

	public IMailInformationService getMailInformationService() {
		return mailInformationService;
	}

	public void setMailInformationService(
			IMailInformationService mailInformationService) {
		this.mailInformationService = mailInformationService;
	}

	public IServiceApplyService getiServiceApplyService() {
		return iServiceApplyService;
	}

	public void setiServiceApplyService(
			IServiceApplyService iServiceApplyService) {
		this.iServiceApplyService = iServiceApplyService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public ITrainingAdminService getiTrainingAdmin() {
		return iTrainingAdmin;
	}

	public void setiTrainingAdmin(ITrainingAdminService iTrainingAdmin) {
		this.iTrainingAdmin = iTrainingAdmin;
	}

	public IMailConnectionService getMailConnectionService() {
		return mailConnectionService;
	}

	public void setMailConnectionService(
			IMailConnectionService mailConnectionService) {
		this.mailConnectionService = mailConnectionService;
	}

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public ITeacherEduAdvanceService getiTeacherEduAdvanceService() {
		return iTeacherEduAdvanceService;
	}

	public void setiTeacherEduAdvanceService(
			ITeacherEduAdvanceService iTeacherEduAdvanceService) {
		this.iTeacherEduAdvanceService = iTeacherEduAdvanceService;
	}

	
	public ITeacherTrainingReplaceService getiTeacherTrainingReplaceService() {
		return iTeacherTrainingReplaceService;
	}
	

	public void setiTeacherTrainingReplaceService(
			ITeacherTrainingReplaceService iTeacherTrainingReplaceService) {
		this.iTeacherTrainingReplaceService = iTeacherTrainingReplaceService;
	}

}
