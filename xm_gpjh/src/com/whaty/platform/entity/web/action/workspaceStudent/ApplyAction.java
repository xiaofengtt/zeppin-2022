package com.whaty.platform.entity.web.action.workspaceStudent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeSite;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.entity.service.studentStatas.PeChangeSiteService;
import com.whaty.platform.entity.service.studentStatas.StudentJudgmentService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ApplyAction extends MyBaseAction {
	
	private StudentJudgmentService studentJudgmentService;
	private SystemApplyService systemApplyService;
	
	private List applys;//已经提交过的申请
	
	private String edutype;// 判断学生的层次
	
	private String message;
	
	private boolean degree;
	
	private boolean graduate;

	public boolean isGraduate() {
		return graduate;
	}

	public void setGraduate(boolean graduate) {
		this.graduate = graduate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
	}
	
	public void setBean(PeStudent bean) {
		super.superSetBean(bean);
	}
	
	public PeStudent getBean() {
		return (PeStudent)super.superGetBean();
	}
	
	public String toApplyMenu() {
		PeStudent student = this.getLoginStudent();
		if (student!=null) {
			if(student.getPeEdutype().getName().indexOf("本")>=0) {
				this.setEdutype("本");
			}
		}
		return "applyMenu";
	}
	
	//取得当前登陆学生peStudent对象
	private PeStudent getLoginStudent(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
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
	
	public String turnToGraduateApply() {
		this.setBean(getLoginStudent());
		message = "";
		graduate = false;
		degree = false;

		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc1.add(Restrictions.eq("peStudent", this.getBean()));
		dc1.add(Restrictions.eq("enumConstByApplyType.code", "8"));
		List apply = null;
		
		try {
			apply = this.getGeneralService().getList(dc1);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(apply != null && !apply.isEmpty()){
			this.setApplys(apply);
			graduate = false;
			message = "您已提交过毕业申请了";
			return "graduate_apply";
		}
		
		//TODO 最小学年的判断
		
		DetachedCriteria dc0 = DetachedCriteria.forClass(PeSemester.class);
		dc0.add(Restrictions.eq("flagActive", "1"));
		boolean back = true;
		try {
			apply = this.getGeneralService().getList(dc0);
			if(apply==null||apply.size()<=0){
				message = "当前学期还不能申请毕业...<br>";
			}else{
				PeSemester semester = (PeSemester)apply.remove(0);
				if(semester.getGraduateStartDate()==null||semester.getGraduateEndDate()==null){
					message += "当前学期还不能申请毕业...<br>";
				}else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					message += "当前学期毕业申请开始时间："+sdf.format(semester.getGraduateStartDate())+"；毕业申请结束时间：" + sdf.format(semester.getGraduateEndDate())+"。<br>";
					if(semester.getGraduateStartDate().after(new Date())){
						message += "还不到毕业申请时间...<br>";
					} else if(semester.getGraduateEndDate().before(new Date())){
						message += "毕业申请时间已过...<br>";
					}else
						back = false;
				}
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
			message = "当前学期还不能申请毕业。<br>";	
		}
		if(back)
			return "graduate_apply";
		
		
		
		try{
			if(this.getBean().getPeEdutype().getName().indexOf("本")>=0){
				graduate = this.getStudentJudgmentService().matchGraduation(this.getBean().getId());//毕业验证
				if(graduate)
					degree = this.getStudentJudgmentService().matchDegree(this.getBean().getId());//学位验证	
			}else{
				degree = true;
				graduate = this.getStudentJudgmentService().matchGraduation(this.getBean().getId());//毕业验证
			}
		}catch(EntityException e){
			message += e.getMessage();
		}
		
		return "graduate_apply";
	}
	
	public String applyGraduate() {
		SystemApply systemApply = new SystemApply();
		systemApply.setPeStudent(this.getBean());
		systemApply.setApplyDate(new Date());
		systemApply.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "8"));
		systemApply.setEnumConstByFlagApplyStatus(this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
		try {
			this.getGeneralService().save(systemApply);
			this.setMsg("毕业申请成功");
		} catch (EntityException e) {
			this.setMsg("毕业申请失败");
			e.printStackTrace();
		}
		return "message";
	}

	public String applyGraduateAndDegree() {
		try {
			if(this.getSystemApplyService().saveForApplyGraduationAndDegree(this.getBean()))
				this.setMsg("毕业学位申请成功!");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMessage(e.getMessage());
		}
		return "message";
	}
	
	public StudentJudgmentService getStudentJudgmentService() {
		return studentJudgmentService;
	}

	public void setStudentJudgmentService(
			StudentJudgmentService studentJudgmentService) {
		this.studentJudgmentService = studentJudgmentService;
	}

	public boolean getDegree() {
		return degree;
	}

	public void setDegree(boolean degree) {
		this.degree = degree;
	}

	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}
	
	public String toPaperReapply() {
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		DetachedCriteria dcSystemApply = DetachedCriteria.forClass(SystemApply.class);
		DetachedCriteria  dcApplyType = dcSystemApply.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dcSystemApply.add(Restrictions.eq("peStudent", this.getLoginStudent()));
		dcApplyType.add(Restrictions.eq("code", "10"));
		try {
			List list = this.getGeneralService().getList(dcSystemApply);
			if (list!=null &&list.size()>0 ) {
				this.setApplys(list);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		DetachedCriteria peSemesterDC = DetachedCriteria.forClass(PeSemester.class);
		peSemesterDC.add(Restrictions.eq("flagActive", "1"));
		PeSemester peSemester = new PeSemester();
		try {
			peSemester = (PeSemester) this.getGeneralService().getList(peSemesterDC).get(0);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		Date today = new Date();
		if (peSemester.getPaperStartDate()==null||peSemester.getPaperRejoinEndDate()==null) {
			this.setMessage("现在还不是申请时间。");
			return "toPaperReapply";
		}
		if (today.after(peSemester.getPaperStartDate()) && today.before(peSemester.getPaperRejoinEndDate())) {
			this.setMessage("现在还不是申请时间。");
			return "toPaperReapply";
		}
		
		PeStudent student = this.getLoginStudent();
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent", "peStudent");
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createCriteria("peTchCourse", "peTchCourse");
		dc.add(Restrictions.eq("peStudent", student));
		dc.add(Restrictions.eq("peTchCourse.name", "毕业论文"));
		PrTchStuElective paperElective ;
		try {
			List electiveList =  this.getGeneralService().getList(dc);
			if(electiveList==null||electiveList.isEmpty()){
				this.setMessage("您还没有做过毕业论文，不能重修。");
				return "toPaperReapply";
			}
			paperElective = (PrTchStuElective) electiveList.get(0);
			if (paperElective == null) {
				this.setMessage("您还没有做过毕业论文，不能重修。");
				return "toPaperReapply";
			}
			if (paperElective.getScoreTotal()==null ||paperElective.getScoreTotal() < 60) {
				this.setMessage("您的毕业论文成绩不足60分。不能重修。");
				return "toPaperReapply";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//判断该学生有没有申请过重修毕业论文,不管有没有审核重修通过。
		DetachedCriteria applyDC = DetachedCriteria.forClass(SystemApply.class);
		applyDC.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		applyDC.add(Restrictions.eq("peStudent", student));
		applyDC.add(Restrictions.eq("enumConstByApplyType.code", "10"));
		try {
			List applyList = this.getGeneralService().getList(applyDC);
			if (applyList.size() > 0) {
				this.setMessage("毕业论文只能申请一次。");
				return "toPaperReapply";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toPaperReapply";
	}
	
	public String applyPaperReapply() {
		
		PeStudent student = this.getLoginStudent();
		SystemApply sa = new SystemApply();
		sa.setPeStudent(student);
		sa.setApplyDate(new Date());
		sa.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "10"));
		sa.setEnumConstByFlagApplyStatus(this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
		try {
			this.getGeneralService().save(sa);
			this.setMsg("毕业论文重修申请成功。");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "message";
	}
	
	public String toDegreeEnglish() {
		
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		DetachedCriteria  dcApplyType = dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getLoginStudent()));
		dcApplyType.add(Restrictions.eq("code", "11"));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list!=null &&list.size()>0 ) {
				this.setApplys(list);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toDegreeEnglish";
	}
	
	public String applyDegreeEnglish() {
		
		PeStudent student = this.getLoginStudent();
		
//		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
//		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
//		dc.add(Restrictions.eq("peStudent", student));
//		dc.add(Restrictions.eq("enumConstByApplyType.code", "11"));
//		
//		try {
//			List applyList = this.getGeneralService().getList(dc);
//		} catch (EntityException e1) {
//			e1.printStackTrace();
//		}
		
		
		SystemApply sa = new SystemApply();
		sa.setPeStudent(student);
		sa.setApplyDate(new Date());
		sa.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "11"));
		sa.setEnumConstByFlagApplyStatus(this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
		try {
			this.getGeneralService().save(sa);
			this.setMsg("学位英语报名申请成功。");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "message"; 
	}
	
	private String changeType;
	private String changeValue;
	private String reason;
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}

	public String changeStudent() {
		
		PeStudent student = this.getLoginStudent();
		SystemApply sa = new SystemApply();
		//存储note字段，使用 changeValue:reason 的形式存储。 如：转到专业“计算机”，原因是“123” 则存储为：计算机:123
		if (this.getChangeType().equals("a")) {
			//转学习中心
			if (student.getPeSite().getName().equals(this.getChangeValue())) {
				this.setMsg("您申请的学习中心与原学习中心一致。");
				return "message";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
			dc.createCriteria("enumConstByApplyType", "enumConstByApplyType").add(Restrictions.eq("code", "12"));
			dc.createCriteria("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus").add(Restrictions.eq("code", "0"));
			dc.add(Restrictions.eq("peStudent", student));
			List list = null;
			try {
				list = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				this.setMsg("您有未审核的学习中心变动申请，无法再次提交申请。");
				return "message";
			}
			sa.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "12"));
			sa.setApplyNote(this.getChangeValue() + ":" + this.getReason());
			
		} else if (this.getChangeType().equals("b")) {
			//转层次
			if (student.getPeEdutype().getName().equals(this.getChangeValue())) {
				this.setMsg("您申请的层次与原层次一致。");
				return "message";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
			dc.createCriteria("enumConstByApplyType", "enumConstByApplyType").add(Restrictions.eq("code", "13"));
			dc.createCriteria("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus").add(Restrictions.eq("code", "0"));
			dc.add(Restrictions.eq("peStudent", student));
			List list = null;
			try {
				list = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				this.setMsg("您有未审核的层次变动申请，无法再次提交申请。");
				return "message";
			}
			sa.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "13"));
			sa.setApplyNote(this.getChangeValue() + ":" + this.getReason());
		} else {
			//转专业
			if (student.getPeMajor().getName().equals(this.getChangeValue())) {
				this.setMsg("您申请的专业与原专业一致。");
				return "message";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
			dc.createCriteria("enumConstByApplyType", "enumConstByApplyType").add(Restrictions.eq("code", "14"));
			dc.createCriteria("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus").add(Restrictions.eq("code", "0"));
			dc.add(Restrictions.eq("peStudent", student));
			List list = null;
			try {
				list = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				this.setMsg("您有未审核的专业变动申请，无法再次提交申请。");
				return "message";
			}
			sa.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "14"));
			sa.setApplyNote(this.getChangeValue() + ":" + this.getReason());
		}
		sa.setEnumConstByFlagApplyStatus(this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
		sa.setApplyInfo(this.getReason());
		sa.setPeStudent(student);
		sa.setApplyDate(new Date());
		
		try {
			this.getGeneralService().save(sa);
			this.setMsg("申请成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMessage("申请失败。");
		}
		
		return "message";
	}

	public String getEdutype() {
		return edutype;
	}

	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}

	public List getApplys() {
		return applys;
	}

	public void setApplys(List applys) {
		this.applys = applys;
	}

}
