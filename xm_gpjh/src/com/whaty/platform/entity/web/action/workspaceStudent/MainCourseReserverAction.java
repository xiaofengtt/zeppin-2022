package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrExamOpenMaincourse;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.mainCourse.PrExamOpenMaincourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 学生工作室 主干课预约
 * @author 李冰
 *
 */
public class MainCourseReserverAction extends MyBaseAction {
	private PrExamOpenMaincourseService prExamOpenMaincourseService;
	private PeStudent peStudent; //保存学生信息
	private List courseList; //保存学生可申请的主干课程
	private PeSemester peSemester;//保存当前学期信息
	private String message; //保存提示信息
	private String checkboxId;//学生预约的课程
	private boolean disobey;
	/**
	 * 预约处理
	 * @return
	 */
	public String courseReserver() {
		this.setTogo("/entity/workspaceStudent/mainCourseReserver_toReserver.action");
		if (this.getCheckboxId()==null || this.getCheckboxId().length()==0) {
			this.setMsg("您没有选择任何课程");
			return "msg";
		}
		this.getLoginStudent();
		List checkId = this.checkboxToList();
		List<PrExamOpenMaincourse> prExamOpenMaincourseList = 
			this.getMyListService().getByIds(PrExamOpenMaincourse.class, checkId);
		if (prExamOpenMaincourseList==null || prExamOpenMaincourseList.isEmpty()) {
			this.setMsg("操作失败！");
			return "msg";
		}
		if (!this.check(checkId,this.theStuMainCourse())) {
			this.setMsg("包含已经预约的课程，请重新选择！");
			return "msg";
		}
		
		try {
			int count=this.getPrExamOpenMaincourseService().saveReserver(this.getPeStudent(), prExamOpenMaincourseList);
			this.setMsg("共成功预约"+count+"门主干课！");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("操作失败！");
		}
		return "msg";
	}
	
	/**
	 * 取得学生选择的课程放入list中
	 * @return
	 */
	private List<String> checkboxToList() {
		List<String> list = new ArrayList<String>();
		String[] strs = this.getCheckboxId().split(",");
		for (String string : strs) {
			list.add(string.trim());
		}		
		return list;
	}
	
	/**
	 * 判断是否包含不能申请的课程
	 * @param checkbox
	 * @param ids
	 * @return
	 */
	private boolean check(List<String> checkbox , List<String> ids){
		if(checkbox!=null && ids !=null && checkbox.size()>0&&ids.size()>0) {
			for (int i = 0; i < checkbox.size(); i++) {
				String str = checkbox.get(i);
				if(ids.contains(str.toString())) {
					return false;
				}
			}
		}
			return true;
	}
	/**
	 * 转向预约页面
	 * @return
	 */
	public String toReserver() {
		if (this.getLoginStudent()==null) {
		this.setMsg("无法取得您的信息请重新登录");
		return "msg";
		}
		if (!this.getPeStudent().getEnumConstByFlagStudentStatus().getCode().equals("4")) {
			this.setMsg("您的学籍状态不是注册在籍，无法进行操作");
			return "msg";
		}
		/**
		 * 判断时间是否在主干课申请时间之内
		 */
		this.theSemester();
		Date now = new Date();
		if (this.getPeSemester().getMainCourseStartDate()!=null&&
				this.getPeSemester().getMainCourseEndDate()!=null) {
			if (now.before(this.getPeSemester().getMainCourseStartDate())) {
				this.setMessage("预约还未开始");
			} else if (!Const.compareDate(now, this.getPeSemester().getMainCourseEndDate())) {
				this.setMessage("预约已经结束");
			}
		} else {
			this.setMessage("不在预约时间之内");
		}
		List<PrTchProgramCourse> prTchProgramCourseList =this.theTchProgram(this.theStuMainCourse());
		
		if (prTchProgramCourseList != null && !prTchProgramCourseList.isEmpty()) {
			this.setCourseList(this.theOpenMainCourse(this.theMaincourseId(prTchProgramCourseList)));
		}
		//判断是否有考试作弊或者违纪
		this.setDisobey(this.checkDisobey());
//		this.setCourseList(this.theTchProgram(null));
		
		return "reserver";
	}
	
	/**
	 * 判断是否有考试违纪或者作弊
	 * @return
	 */
	private boolean checkDisobey(){
		PeStudent peStudent = this.getPeStudent();
		DetachedCriteria disobeydc = DetachedCriteria.forClass(PrTchStuElective.class);
		disobeydc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus")
			.add(Restrictions.or(Restrictions.eq("enumConstByFlagScoreStatus.code", "3"), Restrictions.eq("enumConstByFlagScoreStatus.code", "4")))
			.createAlias("peStudent", "peStudent")
			.add(Restrictions.eq("peStudent.id", peStudent.getId()));
		List list =null;
		try {
			list = getGeneralService().getList(disobeydc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			return false;
		}
//		if(!peStudent.getEnumConstByFlagDisobey().getCode().equals("0")){
//			return false;
//		}
		return true;
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
			this.setPeStudent(student.get(0));
			return (PeStudent)student.get(0);
		}
		return null;
	}
	
	/**
	 * 取得学生的教学计划主干课程课程 (除去已经不能申请的主干课程)
	 * @param list 学生不能申请的主干课
	 * @return
	 */
	private List<PrTchProgramCourse> theTchProgram(List<String> list) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		
		DetachedCriteria dcPeTchCourse = dc.createCriteria("peTchCourse","peTchCourse");
		DetachedCriteria dcFlagIsMainCourse = dc.createCriteria("enumConstByFlagIsMainCourse", "enumConstByFlagIsMainCourse");
		dcFlagIsMainCourse.add(Restrictions.eq("code", "1"));
		dc.createCriteria("peTchProgramGroup","peTchProgramGroup")
			.createCriteria("peTchProgram", "peTchProgram")
			.add(Restrictions.eq("peGrade", this.getPeStudent().getPeGrade()))
			.add(Restrictions.eq("peMajor", this.getPeStudent().getPeMajor()))
			.add(Restrictions.eq("peEdutype", this.getPeStudent().getPeEdutype()))
			.add(Restrictions.eq("enumConstByFlagMajorType", this.getPeStudent().getEnumConstByFlagMajorType()));
		if (list != null) {
			dcPeTchCourse.add(Restrictions.not(Restrictions.in("id", list)));
		}
		List<PrTchProgramCourse> prTchProgramCourseList = null;
		try {
			prTchProgramCourseList =this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return prTchProgramCourseList;
	}
	
	/**
	 * 取得备选主干课的id （未判断学期及开课）
	 * @param prTchProgramCourseList 备选主干课
	 * @return
	 */
	private List<String> theMaincourseId(List<PrTchProgramCourse> prTchProgramCourseList) {
		List<String> list  = new ArrayList<String>();
		for (PrTchProgramCourse prTchProgramCourse : prTchProgramCourseList) {
			list.add(prTchProgramCourse.getPeTchCourse().getId());
		}
		return list;
	}
	
	/**
	 * 取得当前学期可选的主干课
	 * @param list 备选的主干课id
	 * @return
	 */
	private List<PrExamOpenMaincourse> theOpenMainCourse(List<String> list) {
		
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamOpenMaincourse.class);
		
			DetachedCriteria dcCourse = dc.createCriteria("peTchCourse", "peTchCourse");
			dcCourse.add(Restrictions.in("id", list));
		
		dc.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
			.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		List<PrExamOpenMaincourse> prExamOpenMaincourseList = null;
		try {
			prExamOpenMaincourseList =this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return prExamOpenMaincourseList;
	}
	
	/**
	 *这个学生这个学期选过的主干课和考试通过的主干课 (也就是不可以再申请的主干课)
	 */
	private List<String> theStuMainCourse() {
		String sql = "   select openMaincourse.Fk_Course_Id 																			"	
		+ "     from pr_exam_stu_maincourse  stuMaincourse,                           "
		+ "          PR_EXAM_OPEN_MAINCOURSE openMaincourse,                          "
		+ "          PE_EXAM_MAINCOURSE_NO   maincourseNo,                            "
		+ "          PE_SEMESTER             semester,                                "
		+ "          enum_const              enum                                     "
		+ "    where stuMaincourse.Fk_Exam_Open_Maincourse_Id = openMaincourse.Id     "
		+ "      and openMaincourse.Fk_Exam_Maincourse_No_Id = maincourseNo.Id        "
		+ "      and maincourseNo.Fk_Semester_Id = semester.id                        "
		+ "      and stuMaincourse.Fk_Student_Id = " + "'" + this.getPeStudent().getId() + "'"
		+ "      and enum.namespace = 'FlagScoreStatus'                               "
		+ "      and (semester.flag_active = '1'                                      "
		+ "            or (stuMaincourse.Score >= 60 and enum.code = '1'))            ";
		try {
			List<String> list = this.getGeneralService().getBySQL(sql);
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i));
				}
				return list;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存当前学期信息
	 */
	private void theSemester() {
		DetachedCriteria dc =DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPeSemester(list.get(0));
	}
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	public PeStudent getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}
	public List getCourseList() {
		return courseList;
	}
	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}
	public PeSemester getPeSemester() {
		return peSemester;
	}
	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCheckboxId() {
		return checkboxId;
	}
	public void setCheckboxId(String checkboxId) {
		this.checkboxId = checkboxId;
	}

	public PrExamOpenMaincourseService getPrExamOpenMaincourseService() {
		return prExamOpenMaincourseService;
	}

	public void setPrExamOpenMaincourseService(
			PrExamOpenMaincourseService prExamOpenMaincourseService) {
		this.prExamOpenMaincourseService = prExamOpenMaincourseService;
	}

	public boolean isDisobey() {
		return disobey;
	}

	public void setDisobey(boolean disobey) {
		this.disobey = disobey;
	}
}
