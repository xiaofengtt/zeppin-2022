package cn.zeppin.action.teacher;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.zeppin.action.admin.ProjectApplyInfoAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingAssigned;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAssignTeacherCheckService;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingAssginedService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class AutonomousEnrollmentAction extends baseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8139787544096364121L;

	static Logger logger = LogManager.getLogger(ProjectApplyInfoAction.class);

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	private Project project;
	private ProjectApply pa;
	private String date;

	private IProjectService iProjectService;
	private IProjectApplyService iProjectApplyService;
	private IAssignTeacherCheckService iAssignTeacherCheckService;

	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IOrganizationService iOrganizationService; // 组织架构
	private ITeacherService iTeacherService;// 教师操作

	private IHsdtestService iHsdTestService;// 教师学前测评判断

	private IProjectSubjectRangeService iProjectSubjectRangeService;
	private ITeacherTrainingAssginedService teacherTrainingAssignedService;

	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	// private LinkedHashMap<Integer, String[]> orderFormHash;
	//
	// private SimpleDateFormat sdf;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 报名
	 * 
	 * @throws InterruptedException
	 */
//	@SuppressWarnings("rawtypes")
	public void signup() throws InterruptedException {
		/*
		 * 报名流程 1、教师点击报名后向后台传递一下参数：teacherId、subjectId、collegeId 2、判断所报名的项目学科
		 * 承训单位的最大报名人数是否超额 超出额度则提示不能继续报名 3、如果没超出额度 则入库培训记录至teachertrainrecord
		 * 状态为 未审核
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("teachersession");
		Integer teacherId = 0;
		if (us != null) {
			teacherId = us.getId();
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAILED\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"用户未登录，请登录后继续。。。\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
			return;
		}

		try {

			Integer projectApplyId = 0;
			if (request.getParameter("id") != null
					&& !"".equals(request.getParameter("id"))) {
				projectApplyId = Integer.parseInt(request.getParameter("id"));
			}

			String isAdvance = request.getParameter("isAdvance") == null ? "0"
					: request.getParameter("isAdvance");
			if ("".equals(isAdvance)) {
				isAdvance = "0";
			}

			if ("0".equals(isAdvance)) {
				ProjectApply pa = this.iProjectApplyService.get(projectApplyId);

				// //暂不启用该功能
				Project pro = pa.getProject();

				if (pro.getIsTest()) {
					/*
					 * 20171129重新开放使用
					 * 判定是否是需要测评的项目 判断教师是否已经进行过本年的学前测评
					 * 如果有则完成报名流程，如果没有则跳转到学前测评页面进行测评
					 */
					@SuppressWarnings("deprecation")
					int year = new Date().getYear() + 1900;
					// 判断当年已经回答过几次
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("teacher", us.getId());
					map.put("year", year);

					int count = this.getiHsdTestService().getHsdTestCount(map);

					if (count == 0) {
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"REPEAT\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"您还未进行信息技术能力提升工程学前测评，请先完成学前测评后，再来报名吧！\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(),
								"application/json", response);
						return;
					}
				}

				String pid = pa.getProject().getId().toString();
				String sid = Integer.valueOf(pa.getTrainingSubject().getId())
						.toString();
				String tid = pa.getTrainingCollege().getId().toString();
				// 新增约束条件 超过报名截止日期的 不能成功报名
				Timestamp deadLine = pa.getEnrollEndTime();
				Timestamp nowTime = new Timestamp(System.currentTimeMillis());
				int result = (int) ((deadLine.getTime() / (1000 * 60)) - (nowTime
						.getTime() / (1000 * 60)));
				if (result <= 0) {
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"FAIL\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"已超出报名截止日期，无法报名\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(),
							"application/json", response);
					return;
				}
//				// 这一步完成 该教师是否已经报名过此项目
//				List<Project> projectList = this.iProjectService
//						.getSameTypeProjectList(pid);
//				StringBuilder trainedCount = new StringBuilder();
//				trainedCount
//						.append("select count(*) from teacher_training_records t where 1=1");
//				trainedCount.append(" and t.teacher=").append(teacherId);
//				trainedCount.append(" and t.subject=").append(sid);
//				if (projectList != null && projectList.size() > 0) {
//					trainedCount.append(" and t.project in (");
//					String comba = "";
//					for (Project project : projectList) {
//						trainedCount.append(comba);
//						trainedCount.append(project.getId());
//						comba = ",";
//					}
//					trainedCount.append(")");
//				}
//
//				List littrs = this.iTeacherTrainingRecordsService.executeSQL(
//						trainedCount.toString(), null);
//				int ttrCount = Integer.valueOf(littrs.get(0).toString());
//				// 存在过报送/培训记录
//				if (ttrCount > 0) {
//					StringBuilder checkSB = new StringBuilder();
//					checkSB.append("{");
//					checkSB.append("\"Result\":\"FAIL\"");
//					checkSB.append(",");
//					checkSB.append("\"Message\":\"" + "该教师已经报送过本年度相同项目类型的"
//							+ pa.getTrainingSubject().getName() + "学科培训！"
//							+ "\"");
//					checkSB.append("}");
//					Utlity.ResponseWrite(checkSB.toString(),
//							"application/json", response);
//					return;
//				}

				// 这一步完成 判断是否超出报名人数的120% -- 20190704修改为100%
				// 查询未终审的所有报名本项目此学科的这个承训单位的人数
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("pid", pid);
				map.put("sid", sid);
				map.put("tid", tid);
				int count = this.iTeacherTrainingRecordsService
						.getAduTeacherCount(map, null, null);

				int totalcount = (int) Math.ceil(pa.getApproveNumber() * 1);
				if ((totalcount - count) <= 0) {// 如果超出最大报名人数,则提示已超出最大报名人数
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"WARNING\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\""
							+ "已超出该承训单位最大报名人数,请重新选择同学科其他承训单位报名..." + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(),
							"application/json", response);
					return;
				}

				// 增加一条记录
				TeacherTrainingRecords ttr = new TeacherTrainingRecords();
				
				StringBuilder trainedCount = new StringBuilder();
				trainedCount.append(" from TeacherTrainingRecords t where 1=1");
				trainedCount.append(" and t.teacher=").append(teacherId);
				trainedCount.append(" and t.trainingSubject=").append(sid);
				trainedCount.append(" and t.project=").append(pid);
				trainedCount.append(" and t.trainingCollege=").append(tid);
				trainedCount.append(" and t.finalStatus = -1");

				List<TeacherTrainingRecords> littrs = this.iTeacherTrainingRecordsService.getListByHSQL(trainedCount.toString());
				boolean flag = true;
				if(littrs != null && littrs.size() > 0){
					flag = false;
					ttr = littrs.get(0);
					
				}
				
				Teacher th = this.iTeacherService.get(Integer
						.valueOf(teacherId));
				String vid = Utlity.getUuidPwd(); // uuid.toString().replaceAll("-",
													// "");

				ttr.setProject(pa.getProject());
				ttr.setTrainingSubject(pa.getTrainingSubject());
				ttr.setTrainingCollege(pa.getTrainingCollege());

				ttr.setTeacher(th);
				ttr.setCreator(us.getId());

				Organization taddOra = this.iOrganizationService.get(us
						.getOrganization());
				ttr.setOrganization(taddOra);

				ttr.setCheckStatus((short) (-1));// 教师自主报名默认状态 -1 为未审核
				ttr.setUuid(vid);
				ttr.setIsPrepare(false);

				// 其他状态
				ttr.setFinalStatus((short) 1);// 未最终审核
				ttr.setTrainingStatus((short) 1);// 培训状态为未报到

				// 设定确认状态
				if (pa.getProject().getTraintype() == 1 || pa.getProject().getTraintype() == 3) {
					ttr.setIsConfirm((short) -1); // 集中培训 默认未确认状态
				} else {
					ttr.setIsConfirm((short) 1);// 非集中培训默认 确认通过状态
				}

				// 重置替换状态
				ttr.setReplaceStatus((short) 0);
				ttr.setReplaceTeacher(0);
				
				// 9月12号 培训记录表冗余教师信息
				newAddTeacherInfo(ttr);
				ttr.setProjectCycle(pa.getProject().getProjectCycle());
				
				if(flag){
					// 入库
					ttr = this.iTeacherTrainingRecordsService.add(ttr);
				} else {
					ttr = this.iTeacherTrainingRecordsService.update(ttr);
				}

				AssignTeacherCheck atc = new AssignTeacherCheck();
				atc.setTeacherTrainingRecords(ttr);
				atc.setChecker(us.getId());
				atc.setChecktime(new Timestamp(System.currentTimeMillis()));
				atc.setType((short) 0);
				this.iAssignTeacherCheckService.add(atc);

				// 页面回传
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"OK\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"" + "提交成功,请等待审核..." + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json",
						response);
			} else {// 先报后分
				ProjectSubjectRange psr = this.iProjectSubjectRangeService
						.get(Integer.valueOf(projectApplyId));
				if (psr != null) {
					TrainingSubject ts = psr.getTrainingSubject();
					Project pro = psr.getProject();

					if (pro.getIsTest()) {
						/*
						 * 20171129重新开放使用
						 * 判定是否是需要测评的项目 判断教师是否已经进行过本年的学前测评
						 * 如果有则完成报名流程，如果没有则跳转到学前测评页面进行测评
						 */
						@SuppressWarnings("deprecation")
						int year = new Date().getYear() + 1900;
						// 判断当年已经回答过几次
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("teacher", us.getId());
						map.put("year", year);

						int count = this.getiHsdTestService().getHsdTestCount(
								map);

						if (count == 0) {
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"REPEAT\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"您还未进行信息技术能力提升工程学前测评，请先完成学前测评后，再来报名吧！\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(),
									"application/json", response);
							return;
						}
					}

					// 新增约束条件 超过报名截止日期的 不能成功报名
					Timestamp deadLine = pro.getEndtime();
					Timestamp nowTime = new Timestamp(
							System.currentTimeMillis());
					int result = (int) ((deadLine.getTime() / (1000 * 60)) - (nowTime
							.getTime() / (1000 * 60)));
					if (result <= 0) {
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"FAIL\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"已超出报名截止日期，无法报名\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(),
								"application/json", response);
						return;
					}

					// 判断是否报过名（双重判断）
					HashMap<String, String> searchMap = new HashMap<>();
					searchMap.put("project", pro.getId() + "");
					searchMap.put("subject", ts.getId() + "");
					searchMap.put("teacher", us.getId() + "");
					int count = this.teacherTrainingAssignedService
							.getCountByParams(searchMap);

					// 存在过报送/培训记录
					if (count > 0) {
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"FAIL\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "该教师已经报送过本年度相同项目类型的"
								+ ts.getName() + "学科培训！" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(),
								"application/json", response);
						return;
					}

					// 这一步完成 判断是否超出报名人数的120%
					// 查询未终审的所有报名本项目此学科的这个承训单位的人数
					HashMap<String, String> map = new HashMap<String, String>();
					searchMap.put("project", pro.getId() + "");
					searchMap.put("subject", ts.getId() + "");
					int countT = this.teacherTrainingAssignedService
							.getCountByParams(map);

					int totalcount = (int) Math.ceil(pro.getNumber() * 1.2);
					if ((totalcount - countT) <= 0) {// 如果超出最大报名人数,则提示已超出最大报名人数
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"WARNING\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\""
								+ "已超出该项目最大报名人数,请重新选择其他项目学科进行报名..." + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(),
								"application/json", response);
						return;
					}

					TeacherTrainingAssigned tta = new TeacherTrainingAssigned();

					tta.setCreatetime(new Timestamp(System.currentTimeMillis()));
					tta.setProject(pro);
					tta.setSubject(ts);
					Teacher teacher = this.iTeacherService.get(us.getId());
					tta.setTeacher(teacher);
					tta.setStatus((short) 1);
					this.teacherTrainingAssignedService.add(tta);

					// 页面回传
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"OK\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "提交成功,请等待审核..." + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(),
							"application/json", response);
				} else {
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"FAIL\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "报名信息错误" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(),
							"application/json", response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "报名异常..." + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
		}
	}
	
	
	/**
	 * 已报名未审核学员 自主撤回功能
	 */
	public void reset(){
		initServlert();

		UserSession us = (UserSession) session.getAttribute("teachersession");
		Integer teacherId = 0;
		if (us != null) {
			teacherId = us.getId();
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAILED\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"用户未登录，请登录后继续。。。\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
			return;
		}
		
		try {

			Integer projectApplyId = 0;
			if (request.getParameter("id") != null
					&& !"".equals(request.getParameter("id"))) {
				projectApplyId = Integer.parseInt(request.getParameter("id"));
			}

			String isAdvance = request.getParameter("isAdvance") == null ? "0"
					: request.getParameter("isAdvance");
			if ("".equals(isAdvance)) {
				isAdvance = "0";
			}

			if ("0".equals(isAdvance)) {
				ProjectApply pa = this.iProjectApplyService.get(projectApplyId);


				String pid = pa.getProject().getId().toString();
				String sid = Integer.valueOf(pa.getTrainingSubject().getId())
						.toString();
				String tid = pa.getTrainingCollege().getId().toString();
				
				
				StringBuilder trainedCount = new StringBuilder();
				trainedCount.append(" from TeacherTrainingRecords t where 1=1");
				trainedCount.append(" and t.teacher=").append(teacherId);
				trainedCount.append(" and t.trainingSubject=").append(sid);
				trainedCount.append(" and t.project=").append(pid);
				trainedCount.append(" and t.trainingCollege=").append(tid);
				trainedCount.append(" and t.finalStatus = 1");

				List<TeacherTrainingRecords> littrs = this.iTeacherTrainingRecordsService.getListByHSQL(trainedCount.toString());
				
				if(littrs != null && littrs.size() > 0){
					
					TeacherTrainingRecords ttr = littrs.get(0);
					if(ttr != null){
						if(ttr.getFinalStatus() != 1 && ttr.getFinalStatus() != -1){//不是未审核
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"FAIL\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"已审核 不能撤回。。。\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json",
									response);
							return;
						}
						//撤回
						ttr.setFinalStatus((short)DictionyMap.TEACHER_TRAINING_RECORDS_REVOKE);
						this.iTeacherTrainingRecordsService.update(ttr);
						
						AssignTeacherCheck atc = new AssignTeacherCheck();
						String reason = "学员自主撤回";
						if(reason!=null&&!reason.equals("")){
							atc.setReason(reason);//撤回原因
						}
						atc.setTeacherTrainingRecords(ttr);
						atc.setType((short) 7);//撤回记录
						atc.setChecker(us.getId());
						this.iAssignTeacherCheckService.add(atc);
						
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"OK\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"操作成功\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json",
								response);
						return;
					} else {
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"FAIL\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"数据错误。。。\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json",
								response);
						return;
					}
				} else {
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"FAIL\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"未查询到报名信息。。。\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json",
							response);
					return;
				}

			} else {// 先报后分暂不处理
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"FAIL\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"暂不支持该类培训的撤回操作。。。\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json",
						response);
				return;
//				ProjectSubjectRange psr = this.iProjectSubjectRangeService
//						.get(Integer.valueOf(projectApplyId));
//				if (psr != null) {
//					TrainingSubject ts = psr.getTrainingSubject();
//					Project pro = psr.getProject();
//
//					// 新增约束条件 超过报名截止日期的 不能成功报名
//					Timestamp deadLine = pro.getEndtime();
//					Timestamp nowTime = new Timestamp(
//							System.currentTimeMillis());
//					int result = (int) ((deadLine.getTime() / (1000 * 60)) - (nowTime
//							.getTime() / (1000 * 60)));
//					if (result <= 0) {
//						StringBuilder checkSB = new StringBuilder();
//						checkSB.append("{");
//						checkSB.append("\"Result\":\"FAIL\"");
//						checkSB.append(",");
//						checkSB.append("\"Message\":\"已超出报名截止日期，无法报名\"");
//						checkSB.append("}");
//						Utlity.ResponseWrite(checkSB.toString(),
//								"application/json", response);
//						return;
//					}
//
//					// 判断是否报过名（双重判断）
//					HashMap<String, String> searchMap = new HashMap<>();
//					searchMap.put("project", pro.getId() + "");
//					searchMap.put("subject", ts.getId() + "");
//					searchMap.put("teacher", us.getId() + "");
//					int count = this.teacherTrainingAssignedService
//							.getCountByParams(searchMap);
//
//					// 存在过报送/培训记录
//					if (count > 0) {
//						StringBuilder checkSB = new StringBuilder();
//						checkSB.append("{");
//						checkSB.append("\"Result\":\"FAIL\"");
//						checkSB.append(",");
//						checkSB.append("\"Message\":\"" + "该教师已经报送过本年度相同项目类型的"
//								+ ts.getName() + "学科培训！" + "\"");
//						checkSB.append("}");
//						Utlity.ResponseWrite(checkSB.toString(),
//								"application/json", response);
//						return;
//					}
//
//					// 这一步完成 判断是否超出报名人数的120%
//					// 查询未终审的所有报名本项目此学科的这个承训单位的人数
//					HashMap<String, String> map = new HashMap<String, String>();
//					searchMap.put("project", pro.getId() + "");
//					searchMap.put("subject", ts.getId() + "");
//					int countT = this.teacherTrainingAssignedService
//							.getCountByParams(map);
//
//					int totalcount = (int) Math.ceil(pro.getNumber() * 1.2);
//					if ((totalcount - countT) <= 0) {// 如果超出最大报名人数,则提示已超出最大报名人数
//						StringBuilder checkSB = new StringBuilder();
//						checkSB.append("{");
//						checkSB.append("\"Result\":\"WARNING\"");
//						checkSB.append(",");
//						checkSB.append("\"Message\":\""
//								+ "已超出该项目最大报名人数,请重新选择其他项目学科进行报名..." + "\"");
//						checkSB.append("}");
//						Utlity.ResponseWrite(checkSB.toString(),
//								"application/json", response);
//						return;
//					}
//
//					TeacherTrainingAssigned tta = new TeacherTrainingAssigned();
//
//					tta.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					tta.setProject(pro);
//					tta.setSubject(ts);
//					Teacher teacher = this.iTeacherService.get(us.getId());
//					tta.setTeacher(teacher);
//					tta.setStatus((short) 1);
//					this.teacherTrainingAssignedService.add(tta);
//
//					// 页面回传
//					StringBuilder checkSB = new StringBuilder();
//					checkSB.append("{");
//					checkSB.append("\"Result\":\"OK\"");
//					checkSB.append(",");
//					checkSB.append("\"Message\":\"" + "提交成功,请等待审核..." + "\"");
//					checkSB.append("}");
//					Utlity.ResponseWrite(checkSB.toString(),
//							"application/json", response);
//				} else {
//					StringBuilder checkSB = new StringBuilder();
//					checkSB.append("{");
//					checkSB.append("\"Result\":\"FAIL\"");
//					checkSB.append(",");
//					checkSB.append("\"Message\":\"" + "报名信息错误" + "\"");
//					checkSB.append("}");
//					Utlity.ResponseWrite(checkSB.toString(),
//							"application/json", response);
//				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAIL\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "操作异常..." + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
		}
	}

	/**
	 * 加载当前报名信息
	 * 
	 * @return
	 */
	public void initSginUpPage() {
		initServlert();

		UserSession us = (UserSession) session.getAttribute("teachersession");

		if (us != null) {

			String isAdvance = request.getParameter("isAdvance") == null ? "0"
					: request.getParameter("isAdvance");
			if ("".equals(isAdvance)) {
				isAdvance = "0";
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"" + "查询成功" + "\"");
			sb.append(",");
			sb.append("\"Records\":");
			sb.append("{");

			// 报名时间
			sb.append("\"orderDate\":\"" + date + "\"");
			sb.append(",");

			// 加入报名者姓名、编号、身份证号
			String teacherName = us.getName();
			String teacherId = us.getId() + "";
			String teacherIdCard = us.getIdcard();

			sb.append("\"teacherName\":\"" + teacherName + "\"");
			sb.append(",");
			sb.append("\"teacherId\":\"" + teacherId + "\"");
			sb.append(",");
			sb.append("\"teacherIdCard\":\"" + teacherIdCard + "\"");
			sb.append(",");

			// 加入项目信息(名称、学科、承训单位、缴费金额)
			String id = "";
			if (request.getParameter("id") != null
					&& !"".equals(request.getParameter("id"))) {
				id = request.getParameter("id");
				if ("0".equals(isAdvance)) {
					ProjectApply pa = iProjectApplyService.get(Integer
							.valueOf(id));
					if (pa != null) {
						sb.append("\"subjectName\":\""
								+ pa.getTrainingSubject().getName() + "\"");// 学科
						sb.append(",");
						sb.append("\"trainingCollege\":\""
								+ pa.getTrainingCollege().getName() + "\"");// 承训单位
						sb.append(",");
						sb.append("\"projectName\":\""
								+ pa.getProject().getName() + "\"");// 项目
						sb.append(",");
						sb.append("\"funds\":\"" + pa.getProject().getFunds()
								+ "\"");// 缴费金额
					} else {
						sb.append("\"subjectName\":\"无\"");// 学科
						sb.append(",");
						sb.append("\"trainingCollege\":\"无\"");// 承训单位
						sb.append(",");
						sb.append("\"projectName\":\"无\"");// 项目
						sb.append(",");
						sb.append("\"funds\":\"无\"");// 缴费金额
					}
				} else {
					ProjectSubjectRange psr = this.iProjectSubjectRangeService
							.get(Integer.valueOf(id));
					if (psr != null) {
						TrainingSubject ts = psr.getTrainingSubject();
						Project p = psr.getProject();
						sb.append("\"subjectName\":\"" + ts.getName() + "\"");// 学科
						sb.append(",");
						sb.append("\"trainingCollege\":\"待定\"");// 承训单位
						sb.append(",");
						sb.append("\"projectName\":\"" + p.getName() + "\"");// 项目
						sb.append(",");
						sb.append("\"funds\":\"" + p.getFunds() + "\"");// 缴费金额
					} else {
						sb.append("\"subjectName\":\"无\"");// 学科
						sb.append(",");
						sb.append("\"trainingCollege\":\"无\"");// 承训单位
						sb.append(",");
						sb.append("\"projectName\":\"无\"");// 项目
						sb.append(",");
						sb.append("\"funds\":\"无\"");// 缴费金额
					}
				}
			}
			sb.append("}");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"" + "用户未登录，请先登录..." + "\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * 订单入库,报名信息入库,加载订单列表
	 * 
	 * @return
	 */
	public String orderFormList() {
		return "";
	}

	/**
	 * 加载报名信息列表
	 * 
	 * @return
	 */
	public String signUpInfoList() {
		return "";
	}

	/**
	 * 培训记录表冗余教师信息
	 */
	private void newAddTeacherInfo(TeacherTrainingRecords teacherTrainingRecords) {
		// 通过id，从教师表中获取
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", teacherTrainingRecords.getTeacher().getId() + "");
//		List<Teacher> teacherList = iTeacherService.getTeacherListByParams(map);
		Teacher teacher = teacherTrainingRecords.getTeacher();
		teacherTrainingRecords.setTeacherOrganization(teacher.getOrganization()
				.getId());// 学校
		teacherTrainingRecords.setJobTitle(teacher.getJobTitle());// 职称
		teacherTrainingRecords.setEductionBackground(teacher
				.getEductionBackground());// 学历
		teacherTrainingRecords.setPolitics(teacher.getPolitics());// 政治面貌
//		// 通过教师id，从TeachingSubject表中获取
//		Map<String, Object> tsMap = new HashMap<String, Object>();
//		tsMap.put("id", teacher.getId() + "");
//		List<TeachingSubject> teachingSubjectList = iTeachingSubjectService
//				.getListByParams(tsMap);
//		for (TeachingSubject teachingSubject : teachingSubjectList) {
//			if (teachingSubject.getIsprime().equals("1")) {
//				teacherTrainingRecords.setSubject(teachingSubject.getSubject());// 主要学科
//			}
//		}
//		// 通过教师id，从TeachingGrade表中获取
//		List<TeachingGrade> teachingGradeList = iTeachingGradeService
//				.getListByParams(tsMap);
//		for (TeachingGrade teachingGrade : teachingGradeList) {
//			if (teachingGrade.getIsprime().equals("1")) {
//				teacherTrainingRecords.setGrade(teachingGrade.getGrade());// 主要教学学段
//			}
//		}
//		List<TeachingLanguage> teachingLanguageList = iTeachingLanguageService
//				.getListByParams(tsMap);
//		for (TeachingLanguage teachingLanguage : teachingLanguageList) {
//			if (teachingLanguage.getIsprime().equals("1")) {
//				teacherTrainingRecords.setLanguage(teachingLanguage.getLanguage());// 主要教学语言
//			}
//		}
		// 获取主要教学语言
		String hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

		List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
		if (lstTeachingLanguages.size() > 0) {
			TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
			teacherTrainingRecords.setLanguage(teachingLanguage.getLanguage());// 主要教学语言
		}

		// 获取主要教学学段
		hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
		List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
		if (lstTeachingGrades.size() > 0) {
			TeachingGrade teachingGrade = lstTeachingGrades.get(0);
			teacherTrainingRecords.setGrade(teachingGrade.getGrade());// 主要教学学段
		}

		// 获取主要教学学科
		hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
		List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
		if (lsttTeachingSubjects.size() > 0) {
			TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
			teacherTrainingRecords.setSubject(teachingSubject.getSubject());// 主要学科
		}
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectApply getPa() {
		return pa;
	}

	public void setPa(ProjectApply pa) {
		this.pa = pa;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	// public LinkedHashMap<Integer, String[]> getOrderFormHash() {
	// return orderFormHash;
	// }
	//
	// public void setOrderFormHash(LinkedHashMap<Integer, String[]>
	// orderFormHash) {
	// this.orderFormHash = orderFormHash;
	// }

	public IAssignTeacherCheckService getiAssignTeacherCheckService() {
		return iAssignTeacherCheckService;
	}

	public void setiAssignTeacherCheckService(
			IAssignTeacherCheckService iAssignTeacherCheckService) {
		this.iAssignTeacherCheckService = iAssignTeacherCheckService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	public void setiOrganizationService(
			IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public IHsdtestService getiHsdTestService() {
		return iHsdTestService;
	}

	public void setiHsdTestService(IHsdtestService iHsdTestService) {
		this.iHsdTestService = iHsdTestService;
	}

	public IProjectSubjectRangeService getiProjectSubjectRangeService() {
		return iProjectSubjectRangeService;
	}

	public void setiProjectSubjectRangeService(
			IProjectSubjectRangeService iProjectSubjectRangeService) {
		this.iProjectSubjectRangeService = iProjectSubjectRangeService;
	}

	public ITeacherTrainingAssginedService getTeacherTrainingAssignedService() {
		return teacherTrainingAssignedService;
	}

	public void setTeacherTrainingAssignedService(
			ITeacherTrainingAssginedService teacherTrainingAssignedService) {
		this.teacherTrainingAssignedService = teacherTrainingAssignedService;
	}

	public ITeachingLanguageService getiTeachingLanguageService() {
		return iTeachingLanguageService;
	}

	public void setiTeachingLanguageService(
			ITeachingLanguageService iTeachingLanguageService) {
		this.iTeachingLanguageService = iTeachingLanguageService;
	}

	public ITeachingGradeService getiTeachingGradeService() {
		return iTeachingGradeService;
	}

	public void setiTeachingGradeService(ITeachingGradeService iTeachingGradeService) {
		this.iTeachingGradeService = iTeachingGradeService;
	}

	public ITeachingSubjectService getiTeachingSubjectService() {
		return iTeachingSubjectService;
	}

	public void setiTeachingSubjectService(
			ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
	}

}
