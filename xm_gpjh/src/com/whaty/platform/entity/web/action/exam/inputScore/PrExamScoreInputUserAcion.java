package com.whaty.platform.entity.web.action.exam.inputScore;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeExamScoreInputUser;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.SystemVariables;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.CreatInputUserService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrExamScoreInputUserAcion extends MyBaseAction<PeTchCourse> {
	
	private CreatInputUserService creatInputUserService;	

	@Override
	public void initGrid() {
		
		this.getGridConfig().addMenuFunction(this.getText("修改课程登分账户为别的登分帐户"), "/entity/exam/prexamscoreinputuser_gotoEdit.action", true, false);
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("课程登分帐户数据信息"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourseCode");
		this.getGridConfig().addColumn(this.getText("课程"), "combobox_peTchCourse.peTchCourseName");
		this.getGridConfig().addColumn(this.getText("考试总人数"), "cnum");
		ColumnConfig c1 = new ColumnConfig(this.getText("登分帐户A"),"combobox_PeExamScoreInputUser.aName");
		c1.setComboSQL("select id,name from pe_exam_score_input_user where name like '%a'");
		this.getGridConfig().addColumn(c1);
		//this.getGridConfig().addColumn(this.getText("登分帐户A"), "combobox_PeExamScoreInputUser.aName");		
		this.getGridConfig().addColumn(this.getText("A密码"), "aPassword",false);
		ColumnConfig c2 = new ColumnConfig(this.getText("登分帐户B"),"combobox_PeExamScoreInputUser.bName");
		c2.setComboSQL("select id,name from pe_exam_score_input_user where name like '%b'");
		this.getGridConfig().addColumn(c2);
		//this.getGridConfig().addColumn(this.getText("登分帐户B"), "combobox_PeExamScoreInputUser.bName");
		this.getGridConfig().addColumn(this.getText("B密码"), "bPassword",false);
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/prexamscoreinputuser";
	}
	
	private StringBuffer getSQL(){
		StringBuffer sql = new StringBuffer();
		sql.append("select peTchCourse.Id,                                                 ");
		sql.append("       peTchCourse.Code peTchCourseCode,                               ");
		sql.append("       peTchCourse.Name peTchCourseName,                               ");
		sql.append("       prTchCourseSumofStu.cnum,                                       ");
		sql.append("       peExamScoreInputa.Name aName,                                   ");
		sql.append("       peExamScoreInputa.Password aPassword,                           ");
		sql.append("       peExamScoreInputb.Name bName,                                   ");
		sql.append("       peExamScoreInputb.Password bPassword                            ");
		sql.append("from pe_tch_course peTchCourse,                                        ");
		sql.append("     pe_exam_score_input_user peExamScoreInputa,                       ");
		sql.append("     pe_exam_score_input_user peExamScoreInputb,                       ");
	//	sql.append("     --统计课程考试人数                                                ");
		sql.append("(select prTchOpencourse.Fk_Course_Id cid,                              ");
		sql.append("          count(prTchOpencourse.Fk_Course_Id) cnum                     ");
		sql.append("  from  pe_exam_no peExamNo,                                           ");
		sql.append("        pe_exam_room peExamRoom,                                       ");
		sql.append("        pr_exam_booking prExamBooking,                                 ");
		sql.append("        pe_semester peSemester,                                        ");
		sql.append("        pr_tch_stu_elective prTchStuElective,                          ");
		sql.append("        pr_tch_opencourse prTchOpencourse                              ");
		sql.append("  where peSemester.Flag_Active='1' and                                 ");
	//	sql.append("        --考试场次表与学期表                                           ");
		sql.append("        peExamNo.Fk_Semester_Id=peSemester.Id and                      ");
	//	sql.append("        --考试预约表与学生选课表                                       ");
		sql.append("        prExamBooking.Fk_Tch_Stu_Elective_Id=prTchStuElective.Id and   ");
	//	sql.append("          --学生选课表和开课表                                         ");
		sql.append("          prTchStuElective.Fk_Tch_Opencourse_Id=prTchOpencourse.Id and ");         
	//	sql.append("        --考试预约表和考场表                                           ");
		sql.append("        prExamBooking.Fk_Exam_Room_Id=peExamRoom.Id and                ");
	//	sql.append("          --考场表和考试场次表                                         ");
		sql.append("          peExamRoom.Fk_Exam_No=peExamNo.Id                            ");
		sql.append("  group by prTchOpencourse.Fk_Course_Id ) prTchCourseSumofStu          ");
		sql.append("where prTchCourseSumofStu.cid=peTchCourse.Id and                       ");
	//	sql.append("      --课程表和登分人A                                                ");
		sql.append("      peTchCourse.Fk_Exam_Score_Input_Usera_Id=peExamScoreInputa.Id and");
	//	sql.append("      --课程表和登分人B                                                ");
		sql.append("      peTchCourse.Fk_Exam_Score_Input_Userb_Id=peExamScoreInputb.Id    ");
		return sql;
	}
	
	/**
	 * 转向修改页面
	 * @return
	 */
	public String gotoEdit(){
		PeTchCourse c = null;
		try {
			c  = this.getGeneralService().getById(this.getIds().substring(0, this.getIds().length()-1));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setBean(c);
		return "edit";
	}
	
	public String editexe(){
		try{
			this.getBean().getPeExamScoreInputUserByFkExamScoreInputUseraId().setId(
					this.getMyListService().getIdByName("PeExamScoreInputUser", 
							this.getBean().getPeExamScoreInputUserByFkExamScoreInputUseraId().getName()));
			this.getBean().getPeExamScoreInputUserByFkExamScoreInputUseraId().setName(null);
			this.getBean().getPeExamScoreInputUserByFkExamScoreInputUserbId().setId(
					this.getMyListService().getIdByName("PeExamScoreInputUser",
							this.getBean().getPeExamScoreInputUserByFkExamScoreInputUserbId().getName()));
			this.getBean().getPeExamScoreInputUserByFkExamScoreInputUserbId().setName(null);
			if(this.update().get("success").equals("true")){
				this.setMsg("修改成功");
				this.setTogo("/entity/exam/prexamscoreinputuser.action");
			}else{
				this.setMsg("修改失败");
				this.setTogo("back");
			}			
		}catch(Throwable e){
			e.printStackTrace();
			this.setMsg("修改失败");
			this.setTogo("back");
		}
		return "msg";
	}
	
	public String setinputstatus(){
		try{
			ActionContext ac = ActionContext.getContext();
			String sv = (String)ac.getSession().get("inputpri");
			String sql = "select value from system_variables where name = 'examScoreInputUserPri'";
			if(sv==null){
				return "input";
			}else if(sv.equals("1")){
				sql = "update system_variables set value='0' where name = 'examScoreInputUserPri'";
				ac.getSession().put("inputpri","0");
			}else{
				sql = "update system_variables set value='1' where name = 'examScoreInputUserPri'";
				ac.getSession().put("inputpri","1");
			}
			int i = this.getGeneralService().executeBySQL(sql);
			if(i==1) this.setMsg("设置成功！");
			else this.setMsg("设置失败！");
		}catch(Throwable e){
			this.setMsg("出错，设置失败！");
		}
		this.setTogo("/entity/exam/prexamscoreinputuser_gotosetinputstatus.action");
		return "msg";
	}
	
	/**
	 * 将考试成绩同步到学生选课表
	 * @return
	 */
	public String setElectiveScore(){
		this.setTogo("/entity/exam/prexamscoreinputuser_gotosetinputstatus.action");
		try {
			String str = this.getCreatInputUserService().saveExamScore();
			this.setMsg(str);
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
		}
		return "msg";
	}
	/**
	 * 转向登分人能否登分管理页面
	 * @return
	 */
	public String gotosetinputstatus(){
		ActionContext ac = ActionContext.getContext();
		String sv = this.getMyListService().getSysValueByName("examScoreInputUserPri");		
		ac.getSession().put("inputpri",sv);
		return "setinputstatus";
	}
	
	/**
	 * 转向创建帐户的页面
	 * @return
	 */
	public String creatInputUsers(){
		return "careatinputusers";
	}
	
	/**
	 * 执行创建帐户
	 * @return
	 */
	public String creatInputUsersexe(){
		try {
			if(this.getMyListService().getSysValueByName("examScoreInputUserPri").equals("0")){
				String info = this.getCreatInputUserService().save_CreatInputUser();
				this.setMsg(info);
			}else{
				this.setMsg("登分已经开始，不能重新分配帐户");
			}
		} catch (EntityException e) {
			this.setMsg("创建帐户失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setTogo("back");
		return "msg";
	}

	
	@Override
	public Page list() {
		StringBuffer sql = getSQL();		      
		Page page = null;
		this.setSqlCondition(sql);
		try {
			page = getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return page;
	}

	public CreatInputUserService getCreatInputUserService() {
		return creatInputUserService;
	}

	public void setCreatInputUserService(CreatInputUserService creatInputUserService) {
		this.creatInputUserService = creatInputUserService;
	}
	
	public void setBean(PeTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeTchCourse getBean() {
		return (PeTchCourse) super.superGetBean();
	}
}
