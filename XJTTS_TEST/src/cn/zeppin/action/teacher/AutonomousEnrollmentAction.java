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
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
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
	
	
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IOrganizationService iOrganizationService; // 组织架构
	private ITeacherService iTeacherService;// 教师操作
	
	private IHsdtestService iHsdTestService;//教师学前测评判断
	
//	private LinkedHashMap<Integer, String[]> orderFormHash;
//	
//	private SimpleDateFormat sdf;
	

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 报名
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("rawtypes")
	public void signup() throws InterruptedException{
		/*
		 * 报名流程
		 * 1、教师点击报名后向后台传递一下参数：teacherId、subjectId、collegeId
		 * 2、判断所报名的项目学科 承训单位的最大报名人数是否超额 超出额度则提示不能继续报名
		 * 3、如果没超出额度 则入库培训记录至teachertrainrecord 状态为 未审核
		 */

		initServlert();
		
		UserSession us = (UserSession) session.getAttribute("teachersession");
		Integer teacherId = 0;
		if(us != null){
			teacherId = us.getId();
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAILED\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"用户未登录，请登录后继续。。。\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		
		try {
			
		
			Integer projectApplyId = 0;
			if(request.getParameter("id") != null && !"".equals(request.getParameter("id"))){
				projectApplyId = Integer.parseInt(request.getParameter("id"));
			}
			
			ProjectApply pa= this.iProjectApplyService.get(projectApplyId);
	
			Project pro = pa.getProject();
			
//			if(pro.getIsTest()){
//				/*
//				 * 判定是否是需要测评的项目
//				 * 判断教师是否已经进行过本年的学前测评
//				 * 如果有则完成报名流程，如果没有则跳转到学前测评页面进行测评
//				 */
//				int year = new Date().getYear() + 1900;
//				// 判断当年已经回答过几次
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("teacher", us.getId());
//				map.put("year", year);
//
//				int count = this.getiHsdTestService().getHsdTestCount(map);
//				
//				if(count == 0){
//					StringBuilder checkSB = new StringBuilder();
//					checkSB.append("{");
//					checkSB.append("\"Result\":\"REPEAT\"");
//					checkSB.append(",");
//					checkSB.append("\"Message\":\"您还未进行信息技术能力提升工程学前测评，请先完成学前测评后，再来报名吧！\"");
//					checkSB.append("}");
//					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//					return;
//				}
//			}
			
			
			String pid = pa.getProject().getId().toString();
			String sid = Integer.valueOf(pa.getTrainingSubject().getId()).toString();
			String tid = pa.getTrainingCollege().getId().toString();
			//新增约束条件 超过报名截止日期的 不能成功报名
			Timestamp deadLine = pa.getEnrollEndTime();
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());
			int result = (int)((deadLine.getTime()/(1000*60))-(nowTime.getTime()/(1000*60)));
			if(result <= 0){
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"FAIL\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"已超出报名截止日期，无法报名\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				return;
			}
			//这一步完成 该教师是否已经报名过此项目
			List<Project> projectList = this.iProjectService.getSameTypeProjectList(pid);
			StringBuilder trainedCount = new StringBuilder();
			trainedCount.append("select count(*) from teacher_training_records t where 1=1");
			trainedCount.append(" and t.teacher=").append(teacherId);
			trainedCount.append(" and t.subject=").append(sid);
			if (projectList != null && projectList.size() > 0) {
				trainedCount.append(" and t.project in (");
				String comba = "";
				for (Project project : projectList) {
					trainedCount.append(comba);
					trainedCount.append(project.getId());
					comba = ",";
				}
				trainedCount.append(")");
			}
			
			List littrs = this.iTeacherTrainingRecordsService.executeSQL(trainedCount.toString(), null);
			int ttrCount = Integer.valueOf(littrs.get(0).toString());
			// 存在过报送/培训记录
			if (ttrCount > 0) {
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"FAIL\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"" + "该教师已经报送过本年度相同项目类型的" + pa.getTrainingSubject().getName() + "学科培训！" + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				return;
			}
			
			
			
			//这一步完成 判断是否超出报名人数的120%
			//查询未终审的所有报名本项目此学科的这个承训单位的人数
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("pid", pid);
			map.put("sid", sid);
			map.put("tid", tid);
			int count = this.iTeacherTrainingRecordsService.getAduTeacherCount(map, null, null);
			
			int totalcount = (int)Math.ceil(pa.getApproveNumber()*1.2);
			if ((totalcount-count) <= 0) {//如果超出最大报名人数,则提示已超出最大报名人数
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"WARNING\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"" + "已超出该承训单位最大报名人数,请重新选择同学科其他承训单位报名..." + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				return;
			}
			
			// 增加一条记录
			TeacherTrainingRecords ttr = new TeacherTrainingRecords();
			Teacher th = this.iTeacherService.get(Integer.valueOf(teacherId));
			String vid = Utlity.getUuidPwd(); // uuid.toString().replaceAll("-", "");
	
			ttr.setProject(pa.getProject());
			ttr.setTrainingSubject(pa.getTrainingSubject());
			ttr.setTrainingCollege(pa.getTrainingCollege());
	
			ttr.setTeacher(th);
			ttr.setCreator(us.getId());
	
			Organization taddOra = this.iOrganizationService.get(us.getOrganization());
			ttr.setOrganization(taddOra);
	
			ttr.setCheckStatus((short) (-1));//教师自主报名默认状态 -1 为未审核
			ttr.setUuid(vid);
			ttr.setIsPrepare(false);
	
			// 其他状态
			ttr.setFinalStatus((short) 1);//未最终审核
			ttr.setTrainingStatus((short) 1);//培训状态为未报到
	
			//设定确认状态
			if(pa.getProject().getTraintype() == 1){
				ttr.setIsConfirm((short)-1); //集中培训 默认未确认状态
			} else {
				ttr.setIsConfirm((short)1);//非集中培训默认 确认通过状态
			}
			
			//重置替换状态
			ttr.setReplaceStatus((short)0);
			ttr.setReplaceTeacher(0);
			//入库
			this.iTeacherTrainingRecordsService.add(ttr);
	
			//页面回传
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"OK\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "提交成功,请等待审核..." + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "报名异常..." + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}
	}
	
	/**
	 * 加载当前报名信息
	 * @return
	 */
	public void initSginUpPage(){
		initServlert();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us != null){
		
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
			
			//报名时间
			sb.append("\"orderDate\":\""+date+"\"");
			sb.append(",");
			
			//加入报名者姓名、编号、身份证号
			String teacherName = us.getName();
			String teacherId = us.getId()+"";
			String teacherIdCard = us.getIdcard();
			
			sb.append("\"teacherName\":\""+teacherName+"\"");
			sb.append(",");
			sb.append("\"teacherId\":\""+teacherId+"\"");
			sb.append(",");
			sb.append("\"teacherIdCard\":\""+teacherIdCard+"\"");
			sb.append(",");
			
			//加入项目信息(名称、学科、承训单位、缴费金额)
			String id = "";
			if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
				id = request.getParameter("id");
				ProjectApply pa = iProjectApplyService.get(Integer.valueOf(id));
				sb.append("\"subjectName\":\""+pa.getTrainingSubject().getName()+"\"");//学科
				sb.append(",");
				sb.append("\"trainingCollege\":\""+pa.getTrainingCollege().getName()+"\"");//承训单位
				sb.append(",");
				sb.append("\"projectName\":\""+pa.getProject().getName()+"\"");//项目
				sb.append(",");
				sb.append("\"funds\":\""+pa.getProject().getFunds()+"\"");//缴费金额
			}
			sb.append("}");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
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
	 * @return
	 */
	public String orderFormList(){
		return "";
	}
	
	/**
	 * 加载报名信息列表
	 * @return
	 */
	public String signUpInfoList(){
		return "";
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

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

//	public LinkedHashMap<Integer, String[]> getOrderFormHash() {
//		return orderFormHash;
//	}
//
//	public void setOrderFormHash(LinkedHashMap<Integer, String[]> orderFormHash) {
//		this.orderFormHash = orderFormHash;
//	}

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

	public void setiOrganizationService(IOrganizationService iOrganizationService) {
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
	
}
