package com.gpjh.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gpjh.model.LoginKey;
import com.gpjh.model.Project;
import com.gpjh.model.Subject;
import com.gpjh.model.TrainingUnit;
import com.gpjh.service.LoginKeyService;
import com.gpjh.service.ProjectService;
import com.gpjh.service.SubjectService;
import com.gpjh.service.TrainingUnitService;
import com.gpjh.utils.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

@Controller("UserAction")
@Scope("prototype")
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LoginKeyService loginService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TrainingUnitService trainingUnitService;

	private List<Subject> subjects;
	private List<Project> projects;
	private List<TrainingUnit> units;

	private List<LoginKey> loginkeys;
	private LoginKey loginkey;

	private PageInfo pageInfo;

	private String subject;
	private String project;
	private String unit;

	private String id;

	private String msg;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	// private ServletContext application;

	public UserAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		// application = session.getServletContext();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 返回用户列表
	 * 
	 * @return
	 */
	public String list() {

		String page = request.getParameter("page");
		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		int pageId = 1;
		if (page != null)
			pageId = Integer.parseInt(page);
		int pageSize = 20;

		String sql = "from LoginKey where 1=1";

		// 拼装sql语句
		if (project != null && !project.equals("0") && !project.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			sql += " and SUBSTRING(id, 3,4) = '" + fproid + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(subject));
			sql += " and SUBSTRING(id, 10,3) = '" + fsub + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit));
			sql += " and SUBSTRING(id, 7,3) = '" + funi + "'";
		}

		sql += " order by creattime desc";

		// 数据信息
		this.loginkeys = loginService.getListForPage(sql, pageId, pageSize);

		// 分页信息
		int totalRow = loginService.queryRowCount(sql);
		//System.out.println("totalRow ........... " + totalRow);
		this.pageInfo = new PageInfo(totalRow, pageSize, pageId);

		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();

		return "list";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	public String add() {

		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();

		return "add";
	}

	/**
	 * 添加/修改用户
	 * 
	 * @return
	 */
	public String save() {

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		String plansum = request.getParameter("plansum");
		String realsum = request.getParameter("realsum");
		String action = request.getParameter("action");
		this.id = request.getParameter("id");

//		System.out.println(this.project);
//		System.out.println(this.subject);
//		System.out.println(this.unit);

		if (action.equals("add")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			String fsub = subject.format("%03d", Integer.parseInt(subject));
			String funi = unit.format("%03d", Integer.parseInt(unit));

			String loginkey = "GP" + fproid + funi + fsub;

			LoginKey lk = loginService.get(loginkey);

			if (lk != null) {
				this.subjects = subjectService.findAll();
				this.projects = projectService.findAll();
				this.units = trainingUnitService.findAll();
				this.msg = "登陆码已经存在!";
				return "error";
			}

			lk = new LoginKey();
			lk.setId(loginkey);
//			lk.setProject(projectService.get(Integer.parseInt(project)));
//			lk.setSubject(subjectService.get(Integer.parseInt(subject)));
//			lk.setTrainingUnit(trainingUnitService.get(Integer.parseInt(unit)));
//			lk.setPlanSum(Integer.parseInt(plansum));
//			lk.setRealSum(Integer.parseInt(realsum));
			lk.setStatus((short) 1);

			loginService.add(lk);

		} else if (action.equals("modify")) {
			this.loginkey = loginService.get(id);

//			if (this.loginkey.getPlanSum() > Integer.parseInt(plansum)) {
//				this.subjects = subjectService.findAll();
//				this.projects = projectService.findAll();
//				this.units = trainingUnitService.findAll();
//				this.project = String.valueOf(this.loginkey.getProject()
//						.getId());
//				this.subject = String.valueOf(this.loginkey.getSubject()
//						.getId());
//				this.unit = String.valueOf(this.loginkey.getTrainingUnit()
//						.getId());
//
//				this.msg = "计划人数不能小于当前人数 " + this.loginkey.getPlanSum();
//				return "error";
//			}
//
//			if (this.loginkey.getRealSum() > Integer.parseInt(realsum)) {
//				this.project = String.valueOf(this.loginkey.getProject()
//						.getId());
//				this.subject = String.valueOf(this.loginkey.getSubject()
//						.getId());
//				this.unit = String.valueOf(this.loginkey.getTrainingUnit()
//						.getId());
//				this.subjects = subjectService.findAll();
//				this.projects = projectService.findAll();
//				this.units = trainingUnitService.findAll();
//				this.msg = "实到人数不能小于当前人数 " + this.loginkey.getRealSum();
//				return "error";
//			}
//
//			this.loginkey.setPlanSum(Integer.parseInt(plansum));
//			this.loginkey.setRealSum(Integer.parseInt(realsum));
//
//			loginService.update(this.loginkey);
		}

		return "success";
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public String modify() {

		String id = request.getParameter("id");
		this.loginkey = loginService.get(id);

//		this.project = String.valueOf(this.loginkey.getProject().getId());
//		this.subject = String.valueOf(this.loginkey.getSubject().getId());
//		this.unit = String.valueOf(this.loginkey.getTrainingUnit().getId());

		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();

		return "add";
	}

	public List<LoginKey> getLoginkeys() {
		return loginkeys;
	}

	public void setLoginkeys(List<LoginKey> loginkeys) {
		this.loginkeys = loginkeys;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<TrainingUnit> getUnits() {
		return units;
	}

	public void setUnits(List<TrainingUnit> units) {
		this.units = units;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LoginKey getLoginkey() {
		return loginkey;
	}

	public void setLoginkey(LoginKey loginkey) {
		this.loginkey = loginkey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
