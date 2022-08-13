package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ExamScoreChangeApplyAction extends MyBaseAction {
	private boolean apply; //是否可以申请
	private String message; 
	private List<SystemApply> normalapplys;//学生已经提交过的申请
	private List<SystemApply> workapplys;//学生已经提交过的申请
	private List<SystemApply> examapplys;//学生已经提交过的申请
	private List<PrExamBooking>  courseList;
	private String courseId;
	private String type;
	private String reason;


	/**
	 * 转向申请页面
	 * @return
	 */
	public String toExamChangeApply(){
		this.setApply(false);
		this.setBean(getLoginStudent());
		if (this.getBean()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (!this.getBean().getEnumConstByFlagStudentStatus().getCode().equals("4")) {
			this.setMsg("您未注册在籍，无法提交申请！");
			return "msg";	
		}
		this.ifCanApply();
		this.stuCourse();
		this.oldApply();
		return "toApply";
	}
	
	/**
	 * 设置学生当前学期的考试课程
	 */
	private void stuCourse(){
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		DetachedCriteria dcPrTchOpencourse = dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse");
		DetachedCriteria dcPeTchCourse = dcPrTchOpencourse.createCriteria("peTchCourse", "peTchCourse");
		DetachedCriteria dcPeSemester = dc.createCriteria("peSemester", "peSemester");
		dcPeSemester.add(Restrictions.eq("flagActive", "1"));
		dcPrTchStuElective.add(Restrictions.eq("peStudent", this.getBean()));
		try {
			this.setCourseList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取得学生已经提交的申请
	 */
	private void oldApply(){
		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc1.add(Restrictions.eq("peStudent", this.getBean()));
		dc1.add(Restrictions.eq("enumConstByApplyType.code", "16"));
		List list = null;
		try {
			 list = this.getGeneralService().getList(dc1);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setNormalapplys(list);
		}
		DetachedCriteria dc2 = DetachedCriteria.forClass(SystemApply.class);
		dc2.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc2.add(Restrictions.eq("peStudent", this.getBean()));
		dc2.add(Restrictions.eq("enumConstByApplyType.code", "17"));
		list = null;
		try {
			 list = this.getGeneralService().getList(dc2);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setWorkapplys(list);
		}
		DetachedCriteria dc3 = DetachedCriteria.forClass(SystemApply.class);
		dc3.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc3.add(Restrictions.eq("peStudent", this.getBean()));
		dc3.add(Restrictions.eq("enumConstByApplyType.code", "18"));
		list = null;
		try {
			 list = this.getGeneralService().getList(dc3);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setExamapplys(list);
		}
	}
	
	private void setOldApply(String code){

	}
	/**
	 * 设置期末考试成绩复查时间是否已经到
	 */
	private void ifCanApply(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		try {
			List<PeSemester> list = this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				PeSemester peSemester=list.get(0);
				Date time = peSemester.getFinalExamScoreDate();
				Date now = new Date();
				long num = now.getTime()-time.getTime();
				if(num>0&&num<(60*60*24*7*1000)){
					this.setApply(true);
				}
				else {
					this.setApply(false);
				}
			}else{
				this.setApply(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setApply(false);
		}
	}
	/**
	 * 提交申请
	 * @return
	 */
	public String examScoreChangeApply() {
		this.setTogo("/entity/workspaceStudent/examScoreChangeApply_toExamChangeApply.action");
		try {
			this.setBean(getLoginStudent());
			// 检查用户是否第一次提交
			if(!this.checkFirst()) {
				this.setMsg("您已提交过相同内容的申请了");
				this.setTogo("back");
			} else {
			PrExamBooking booking = (PrExamBooking)this.getMyListService().getById(PrExamBooking.class, this.getCourseId())	;
//			PeTchCourse peTchCourse = (PeTchCourse)this.getMyListService().getById(PeTchCourse.class, this.getCourseId());
			SystemApply systemApply = new SystemApply();
			systemApply.setPeStudent(this.getBean());
			systemApply.setApplyDate(new Date());
			systemApply.setApplyNote(this.getCourseId()+"|"+booking.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse().getName()+"|"+this.getReason());
			String code = "";
			if(this.getType().equals("normal")){
				code = "16";
			}
			if (this.getType().equals("work")){
				code = "17";
			}
			if (this.getType().equals("exam")){
				code = "18";
			}
			systemApply.setEnumConstByApplyType(
					this.getMyListService().getEnumConstByNamespaceCode("ApplyType", code));
			systemApply.setEnumConstByFlagApplyStatus(
					this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
			this.getGeneralService().save(systemApply);
			this.setMsg("期末考试成绩复查申请成功");
			}
		} catch (EntityException e) {
			this.setMsg("期末考试成绩复查申请失败");
			this.setTogo("back");
			e.printStackTrace();
		}
		return "msg";
	}
	
	//取得当前登陆学生peStudent对象
	private PeStudent getLoginStudent(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(student != null && !student.isEmpty()) {
			return (PeStudent)student.get(0);
		}
		return null;
	}
	
	/**
	 * 检查用户是否第一次提交
	 * @return
	 */
	private boolean checkFirst() {
		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc1.add(Restrictions.eq("peStudent", this.getBean()));
		String code = "";
		if(this.getType().equals("normal")){
			code = "16";
		}
		if (this.getType().equals("work")){
			code = "17";
		}
		if (this.getType().equals("exam")){
			code = "18";
		}
		dc1.add(Restrictions.eq("enumConstByApplyType.code", code));
		dc1.add(Restrictions.ilike("applyNote", this.getCourseId(), MatchMode.START));
		List list = null;
		
		try {
			list = this.getGeneralService().getList(dc1);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(list != null && !list.isEmpty()){
			return false;
		}
		return true;
	}
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/examScoreChangeApply.action";
	}
	
	public void setBean(PeStudent bean) {
		super.superSetBean(bean);
	}
	
	public PeStudent getBean() {
		return (PeStudent)super.superGetBean();
	}

	public boolean isApply() {
		return apply;
	}

	public void setApply(boolean apply) {
		this.apply = apply;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	public List<PrExamBooking> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<PrExamBooking> courseList) {
		this.courseList = courseList;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<SystemApply> getNormalapplys() {
		return normalapplys;
	}

	public void setNormalapplys(List<SystemApply> normalapplys) {
		this.normalapplys = normalapplys;
	}

	public List<SystemApply> getWorkapplys() {
		return workapplys;
	}

	public void setWorkapplys(List<SystemApply> workapplys) {
		this.workapplys = workapplys;
	}

	public List<SystemApply> getExamapplys() {
		return examapplys;
	}

	public void setExamapplys(List<SystemApply> examapplys) {
		this.examapplys = examapplys;
	}

}
