package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 统考免考申请
 * @author 李冰
 *
 */
public class UniteExamAvoidAction extends MyBaseAction {
	private String note;//保存申请说明
	private String type;//保存申请类型：统考英语A：A， 统考英语B：B， 统考计算机：C
	
	private List applys;//已经提交过的申请
	private List<String> uniteCourse;
	public List getApplys() {
		return applys;
	}

	public void setApplys(List applys) {
		this.applys = applys;
	}
	
	public String toMenu() {
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		
		this.theApplys();
		this.theUniteCourse();
		return "menu";
		
	}

	/**
	 * 学生以前提交过的申请
	 */
	private void theApplys() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		DetachedCriteria  dcApplyType = dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		dcApplyType.add(Restrictions.eq("code", "4"));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list!=null &&list.size()>0 ) {
				this.setApplys(list);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得教学计划中的统考课程
	 */
	private void theUniteCourse(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchProgram.class);
		dc.add(Restrictions.eq("peMajor", this.getBean().getPeMajor()));
		dc.add(Restrictions.eq("peGrade", this.getBean().getPeGrade()));
		dc.add(Restrictions.eq("peEdutype", this.getBean().getPeEdutype()));
		dc.add(Restrictions.eq("enumConstByFlagMajorType", this.getBean().getEnumConstByFlagMajorType()));
		try {
			List<PeTchProgram> list = this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				List<String> program = new ArrayList<String>();
				if(list.get(0).getEnumConstByFlagUniteA() != null){
					program.add(list.get(0).getEnumConstByFlagUniteA().getName());
				}
				if(list.get(0).getEnumConstByFlagUniteB() != null){
					program.add(list.get(0).getEnumConstByFlagUniteB().getName());
				}
				this.setUniteCourse(program);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 转向统考英语A免考申请页面
	 * @return
	 */
	public String toAvoidEnglishA() {
		this.setTogo("back");
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		
		if (!this.checkStatus()) {
			this.setMsg("您的学籍状态不是注册在籍，无法进行操作！");
			return "msg";
		}
		
		if(!this.checkApplyEnglish()){
			this.setMsg("本科学历以下的英语专业学生不可以申请统考英语免考！");
			return "msg";
		}
		
		this.setType("A");
		
		if (this.checkStudent()) {
			this.setMsg("您已经有统考英语成绩，无需申请");
			return "msg";
		}
		
		if (this.checkApply()) {
			this.setMsg("您已经提交过统考英语免试申请，无法重复申请");
			return "msg";
		}
		
		return "avoidEnglishA";
	}
	
	/**
	 * 检查是否可以申请统考英语免考
	 * @return
	 */
	private boolean checkApplyEnglish(){
		if(this.getBean().getPeMajor().getName().contains("英语")&&!this.checkXueLi()){
			return false;
		}
		return true;
	}
	/**
	 * 检查是否可以申请统考计算机免考
	 * @return
	 */
	private boolean checkApplyComputer(){
		if(this.getBean().getPeMajor().getName().contains("计算机")&&!this.checkXueLi()){
			return false;
		}
		return true;
	}
	
	/**
	 * 检查学历是否是本科及以上
	 * @return
	 */
	private boolean checkXueLi(){
		String xueli = this.getBean().getPrStudentInfo().getXueli();
		if(xueli!=null){
			if(xueli.equals("本科")||xueli.equals("硕士")||xueli.equals("博士")){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断学生是否是在籍状态
	 * @return
	 */
	private boolean checkStatus() {
		if (this.getBean().getEnumConstByFlagStudentStatus().getCode().equals("4")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断学生是否已经有成绩 
	 * @return true 有 false 无
	 */
	private boolean checkStudent() {
		boolean check = false;
		if (this.getType().equals("大学英语（A）")) {
			if (this.getBean().getEnumConstByScoreUniteEnglishA()!=null) {
				check = true;
			} 
		} else if (this.getType().equals("大学英语（B）")) {
			if (this.getBean().getEnumConstByScoreUniteEnglishB()!=null) {
				check = true;
			}
		} else if (this.getType().equals("计算机应用基础")) {
			if (this.getBean().getEnumConstByScoreUniteComputer()!=null) {
				check = true;
			}
		} else if (this.getType().equals("大学英语（C）")) {
			if (this.getBean().getEnumConstByScoreUniteEnglishC()!=null) {
				check = true;
			}
		} else if (this.getType().equals("大学语文（B）")) {
			if (this.getBean().getEnumConstByScoreUniteYuwen()!=null) {
				check = true;
			}
		} else if (this.getType().equals("高等数学（B）")) {
			if (this.getBean().getEnumConstByScoreUniteShuxue()!=null) {
				check = true;
			}
		} 
			return check;

	}
	
	/**
	 * 判断是否存在未审核状态的申请
	 * @return true 存在 false不存在
	 */
	private boolean checkApply() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		DetachedCriteria  dcApplyType = dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		DetachedCriteria dcFlagApplyStatus = dc.createCriteria("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus");
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		dcApplyType.add(Restrictions.eq("code", "4"));
		dc.add(Restrictions.eq("applyNote", this.getType()));
		dcFlagApplyStatus.add(Restrictions.eq("code", "0"));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list!=null &&list.size()>0 ) {
				return true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 提交申请
	 * @return
	 */
	public String apply () {
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}

		this.setTogo("/entity/workspaceStudent/uniteExamAvoid_toMenu.action");
		
		if (!this.checkStatus()) {
			this.setMsg("您的学籍状态不是注册在籍，无法进行操作！");
			return "msg";
		}
		
		if(this.getType().contains("英语")){
			if(!this.checkApplyEnglish()){
				this.setMsg("本科学历以下的英语专业学生不可以申请统考英语免考！");
				return "msg";
			}
		}
		
		if(this.getType().contains("计算机")){
			if(!this.checkApplyComputer()){
				this.setMsg("本科以下学历的计算机专业学生不可以申请统考计算机免考！");
				return "msg";
			}
		}
		
		/**
		 * 再次检查是否可以提交申请
		 */
		if (this.checkStudent()) {
			this.setMsg("您申请的科目成绩状态不为空，无法申请免考");
			return "msg";
		}
		
		if (this.checkApply()) {
			this.setMsg("您申请的科目有未审核的申请，无法重复申请");
			return "msg";
		}
		
		try {
			SystemApply systemApply = new SystemApply();
			systemApply.setPeStudent(this.getBean());
			systemApply.setApplyDate(new Date());
			systemApply.setApplyNote(this.getType());
			systemApply.setEnumConstByApplyType(
					this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "4"));
			systemApply.setEnumConstByFlagApplyStatus(
					this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
			systemApply.setApplyInfo(this.getNote());
			this.getGeneralService().save(systemApply);
			this.setMsg("申请成功");
		} catch (EntityException e) {
			this.setMsg("申请失败");
			e.printStackTrace();
		}
		return "msg";
	}
	
	/**
	 * 转向统考英语B免考申请页面
	 * @return
	 */
	public String toAvoidEnglishB() {
		this.setTogo("back");
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (!this.checkStatus()) {
			this.setMsg("您的学籍状态不是注册在籍，无法进行操作！");
			return "msg";
		}
		
		if(!this.checkApplyEnglish()){
			this.setMsg("本科学历以下的英语专业学生不可以申请统考英语免考！");
			return "msg";
		}
		
		
		this.setType("B");
		
		if (this.checkStudent()) {
			this.setMsg("您已经有统考英语成绩，无需申请");
			return "msg";
		}
		
		if (this.checkApply()) {
			this.setMsg("您已经提交过统考英语免试申请，无法重复申请");
			return "msg";
		}
		
		return "avoidEnglishB";
	}

	/**
	 * 转向统考计算机免考申请页面
	 * @return
	 */
	public String toAvoidComputer() {
		this.setTogo("back");
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (!this.checkStatus()) {
			this.setMsg("您的学籍状态不是注册在籍，无法进行操作！");
			return "msg";
		}
		if(this.getBean().getPeMajor().getName().contains("计算机")&&!this.checkApplyComputer()){
			this.setMsg("本科以下学历的计算机专业学生不可以申请统考计算机免考！");
			return "msg";
		}
		
		this.setType("C");
		
		if (this.checkStudent()) {
			this.setMsg("您已经有统考计算机成绩，无需申请");
			return "msg";
		}
		
		if (this.checkApply()) {
			this.setMsg("您已经提交过统考计算机免试申请，无法重复申请");
			return "msg";
		}
		
		return "avoidComputer";
	}
	
	//取得当前登陆学生peStudent对象
	private PeStudent getLoginStudent(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List<PeStudent> student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(student != null && !student.isEmpty()) {
			this.setBean(student.get(0));
			return (PeStudent)student.get(0);
		}
		return null;
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
		this.servletPath = "/entity/workspaceStudent/uniteExamAvoid.action";
	}
	
	public void setBean(PeStudent bean) {
		super.superSetBean(bean);
	}
	
	public PeStudent getBean() {
		return (PeStudent)super.superGetBean();
	}
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getUniteCourse() {
		return uniteCourse;
	}

	public void setUniteCourse(List<String> uniteCourse) {
		this.uniteCourse = uniteCourse;
	}

}
