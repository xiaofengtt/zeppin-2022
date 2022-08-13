package com.whaty.platform.sso.web.action.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.PeStudentService;
import com.whaty.platform.entity.service.teaching.PeTeacherService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PePriRole;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.PePriRoleService;
import com.whaty.platform.sso.service.SsoUserService;

public class TeacherStuPriorityManageAction extends MyBaseAction {

	private PeTeacherService peTeacherService;
	private PePriRoleService pePriRoleService;
	private SsoUserService ssoUserService;
	private PeStudentService peStudentService;
	
	public PeStudentService getPeStudentService() {
		return peStudentService;
	}

	public void setPeStudentService(PeStudentService peStudentService) {
		this.peStudentService = peStudentService;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public PePriRoleService getPePriRoleService() {
		return pePriRoleService;
	}

	public void setPePriRoleService(PePriRoleService pePriRoleService) {
		this.pePriRoleService = pePriRoleService;
	}

	public PeTeacherService getPeTeacherService() {
		return peTeacherService;
	}

	public void setPeTeacherService(PeTeacherService peTeacherService) {
		this.peTeacherService = peTeacherService;
	}

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String loginId;
	private List<PeTeacher> list; 
	private String url;
	private String managerId;
	private String title;
	private String type;

	/**
	 * 显示教师搜索页面
	 */
	@SuppressWarnings("unchecked")
	public String showTeacherList()throws EntityException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		if(getLoginId()!= null){
			DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
			dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole",CriteriaSpecification.LEFT_JOIN);
			dc.add(Restrictions.eq("ssoUser.loginId", getLoginId()));
			setList(getPeTeacherService().getList(dc));
		}
		setUrl("/sso/admin/teacherStudentManagerOper_showTeacherList.action");
		setTitle(this.getText("test.teacher"));
		setType(null);
		
		return "showright";
	}
	
	/**
	 * 显示学生搜索页面
	 */
	@SuppressWarnings("unchecked")
	public String showStudentList()throws EntityException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		if(getLoginId()!= null){
			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole",CriteriaSpecification.LEFT_JOIN);
			dc.add(Restrictions.eq("ssoUser.loginId", getLoginId()));
			setList(getPeStudentService().getList(dc));
		}
		setUrl("/sso/admin/teacherStudentManagerOper_showStudentList.action");
		
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		setTitle(this.getText("test.student"));
		
		return "showright";
	}
	
	/**
	 *  设置教师所属权限组;
	 */
	public String changeAdminGroup()throws EntityException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		
		List list = getPePriRoleService().getList(dc);
		request.getSession().setAttribute("list", list);
		
		PeTeacher manager = getPeTeacherService().getById(getManagerId());
		request.getSession().setAttribute("manager", manager);
		
		
		return "change_teacher_group";
	}
	
	/**
	 *  设置学生所属权限组;
	 */
	public String changeStudentGroup()throws EntityException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		
		List list = getPePriRoleService().getList(dc);
		request.getSession().setAttribute("list", list);
		
		PeStudent manager = getPeStudentService().getById(getManagerId());
		request.getSession().setAttribute("manager", manager);
		
		return "change_student_group";
	}
	
	/**
	 * 更新管理员权限组
	 */
	public String updateAdminGroup()throws SsoException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String checkedId = request.getParameter("admin_id");
		PePriRole role = getPePriRoleService().getById(checkedId);
		
		SsoUser user = getSsoUserService().getById(managerId);
		user.setPePriRole(role);
		
		try{
			user = getSsoUserService().save(user);
		}catch(SsoException e){
			throw new SsoException(this.getText("error.sso.changeAdminGroup"));
		}
		
		setLoginId(getLoginId());
		
		String type = request.getParameter("type");
		if("stu".equals(type)){
			return "show_student";
		}else{
			return "show_teacher";
		}
		
	}	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<PeTeacher> getList() {
		return list;
	}

	public void setList(List<PeTeacher> list) {
		this.list = list;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	

}
