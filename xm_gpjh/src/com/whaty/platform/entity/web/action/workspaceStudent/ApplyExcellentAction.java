package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 学生工作室评优申请
 * @author 李冰
 *
 */
public class ApplyExcellentAction extends MyBaseAction {
	private boolean apply; //是否可以申请
	private String message; 
	private String type;//学生申请类型
	private List<SystemApply> applys;//学生已经提交过的申请

	/**
	 * 转向申请页面
	 * @return
	 */
	public String toApplyExcellent(){
		this.setApply(false);
		this.setBean(getLoginStudent());
		if (this.getBean()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		// 检查用户是否第一次申请
//		if(!this.checkFirst()){
//			this.setMessage("您已提交过评优申请了");
//			return "excellent_apply";
//		}
		
		if (!this.getBean().getEnumConstByFlagStudentStatus().getCode().equals("4")) {
			this.setMessage("您未注册在籍，无法提交评优申请！");
			return "excellent_apply";	
		}
		
		if (!this.getBean().getEnumConstByFlagDisobey().getCode().equals("0")) {
			this.setMessage("您有违纪记录，无法提交评优申请！");
			this.setApply(false);		
		} else {
			
			this.setApply(true);
		}
		if(this.semesterNumber()) {
			
		} else {
			if (this.getMessage()!=null){
			this.setMessage(this.getMessage()+"</br> 至少是第四学期才能提交评优申请！");
			} else {
				this.setMessage("至少是第四学期才能提交评优申请！");
			}
			this.setApply(false);
		}
		return "excellent_apply";
	}
	
	/**
	 * 评优条件 至少第四学期
	 * @return
	 */
	private boolean semesterNumber() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (list!= null && !list.isEmpty()) {
			if (list.get(0).getSerialNumber()-this.getBean().getPeGrade().getSerialNumber()>=3) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 提交评优申请
	 * @return
	 */
	public String excellent () {
		this.setTogo("/entity/workspaceStudent/applyExcellent_toApplyExcellent.action");
		try {
			this.setBean((PeStudent)this.getGeneralService().getById(this.getBean().getId()));
			// 检查用户是否第一次提交
			if(!this.checkFirst()) {
				this.setMsg("您已提交过评优申请了");
				return "msg";
			}
			if(this.getType().equals("goodStu")){
				if(!this.checkScore()){
					this.setMsg("您未达到申请学习积极分子的要求！");
					return "msg";
				}
			}
			SystemApply systemApply = new SystemApply();
			systemApply.setPeStudent(this.getBean());
			systemApply.setApplyDate(new Date());
			if(this.getType().equals("goodStu")){
				systemApply.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "1"));
			}else {
				systemApply.setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "2"));
			}
			systemApply.setEnumConstByFlagApplyStatus(
					this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
			this.getGeneralService().save(systemApply);
			this.setMsg("评优申请成功");
		} catch (EntityException e) {
			this.setMsg("评优申请失败");
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
		if(this.getType().equals("goodStu")){
			dc1.add(Restrictions.eq("enumConstByApplyType.code", "1"));
		}else if(this.getType().equals("cadreman")){
			dc1.add(Restrictions.eq("enumConstByApplyType.code", "2"));
		}
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
	
	
	/**
	 * 检查用户的成绩是否达到学习积极分子的标准
	 * @return
	 */
	private boolean checkScore(){
		String sql = "select t.id from pr_tch_stu_elective t , pe_student stu where" +
				" t.score_total!=null and t.score_total<70 and t.fk_stu_id=stu.id and stu.id='" + this.getBean().getId() +"'";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			return false;
		} else {
			return true;
		}
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
		this.servletPath = "/entity/workspaceStudent/applyExcellent.action";
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
	
	public List<SystemApply> getApplys() {
		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc1.add(Restrictions.eq("peStudent", this.getBean()));
		String[] code = new String[]{"1","2"};
		dc1.add(Restrictions.in("enumConstByApplyType.code", code));
		List<SystemApply>  applys = null;
		
		try {
			applys = this.getGeneralService().getList(dc1);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return applys;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setApplys(List<SystemApply> applys) {
		this.applys = applys;
	}

}
