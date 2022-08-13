package com.whaty.platform.entity.web.action.exam.inputScore.scoreinputlogin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeExamScoreInputUser;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class ScoreInputAction extends MyBaseAction {
	
	private String courseExamRoom;
	
	private String score;
	private String scorestatus;

	private PrExamBookingService prExamBookingService;
	
	public PrExamBookingService getPrExamBookingService() {
		return prExamBookingService;
	}

	public void setPrExamBookingService(PrExamBookingService prExamBookingService) {
		this.prExamBookingService = prExamBookingService;
	}

	public String getCourseExamRoom() {
		return courseExamRoom;
	}

	public void setCourseExamRoom(String courseExamRoom) {
		this.courseExamRoom = courseExamRoom;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScorestatus() {
		return scorestatus;
	}

	public void setScorestatus(String scorestatus) {
		this.scorestatus = scorestatus;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("期末考试成绩查询修改"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学号"),"prTchStuElective.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学生姓名"),"prTchStuElective.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prTchStuElective.prTchProgramCourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prTchStuElective.peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prTchStuElective.peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prTchStuElective.peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"prTchStuElective.peStudent.peGrade.name");
		if(getUserAFlag()){
			this.getGridConfig().addColumn(this.getText("录入成绩"),"scoreExamA",false,true,true,Const.score_for_extjs);		
			ColumnConfig columna = new ColumnConfig(this.getText("录入成绩状态"),"enumConstByFlagScoreStatusa.name");
			columna.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
			columna.setAdd(true);
			columna.setSearch(false);
			this.getGridConfig().addColumn(columna);
			this.getGridConfig().addColumn(this.getText("已录入成绩"),"scoreExamB",false,false,true,Const.score_for_extjs);	
			ColumnConfig columnb = new ColumnConfig(this.getText("已录入成绩状态"),"enumConstByFlagScoreStatusb.name");
			columnb.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
			columnb.setAdd(false);
			columnb.setSearch(false);
			this.getGridConfig().addColumn(columnb);
		}else{
			this.getGridConfig().addColumn(this.getText("录入成绩"),"scoreExamB",false,true,true,Const.score_for_extjs);	
			ColumnConfig columnb = new ColumnConfig(this.getText("录入成绩状态"),"enumConstByFlagScoreStatusb.name");
			columnb.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4' and code>'0'");
			columnb.setAdd(true);
			columnb.setSearch(false);
			this.getGridConfig().addColumn(columnb);
			this.getGridConfig().addColumn(this.getText("已录入成绩"),"scoreExamA",false,false,true,Const.score_for_extjs);		
			ColumnConfig columna = new ColumnConfig(this.getText("已录入成绩状态"),"enumConstByFlagScoreStatusa.name");
			columna.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4' and code>'0'");
			columna.setAdd(false);
			columna.setSearch(false);
			this.getGridConfig().addColumn(columna);
		}
	}	
	
	@Override
	public Map update() {
		Map map = new HashMap();
		try {
			String[] allids = {this.getBean().getId()};
			String[] allscore = {this.getBean().getScoreExamA().toString()};
			DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("name", this.getBean().getEnumConstByFlagScoreStatusa().getName()))
				.add(Restrictions.eq("namespace", "FlagScoreStatus"));			
			EnumConst status = (EnumConst)this.getGeneralService().getList(dc).get(0);
			String[] allscorestatus = {status.getId()};
			String info = this.getPrExamBookingService().saveScore(getUserAFlag(), allids, allscore, allscorestatus);
			map.put("success", "true");
			map.put("info", info);
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
		}
		return map;
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createAlias("enumConstByFlagScoreStatusa", "enumConstByFlagScoreStatusa",DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagScoreStatusb", "enumConstByFlagScoreStatusb",DetachedCriteria.LEFT_JOIN)
			.createCriteria("peSemester", "peSemester")
			.add(Restrictions.eq("peSemester.flagActive", "1"));
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("peStudent", "peStudent")
				.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype")
				.createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
						.createCriteria("peTchCourse", "peTchCourse")
						.createAlias("peExamScoreInputUserByFkExamScoreInputUseraId", "peExamScoreInputUserByFkExamScoreInputUseraId")
						.createAlias("peExamScoreInputUserByFkExamScoreInputUserbId", "peExamScoreInputUserByFkExamScoreInputUserbId");
		if(getUserAFlag()){
			dcPrTchStuElective.add(Restrictions.eq("peExamScoreInputUserByFkExamScoreInputUseraId.id", getUser().getId()));
		}else{
			dcPrTchStuElective.add(Restrictions.eq("peExamScoreInputUserByFkExamScoreInputUserbId.id", getUser().getId()));
		}
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}
	
	@Override
	public void setServletPath() {
		this.servletPath="/score/input/scoreinput";
	}
	
	public void setBean(PrExamBooking instance){
		this.superSetBean(instance);
	}
	
	public PrExamBooking getBean(){
		return (PrExamBooking)this.superGetBean();
	}
	
	public String getstulist(){
		return "ajax";
	}
	
	public List getstuList(){
		List list = null;
		if(this.getCourseExamRoom()!=null){
			int n = this.getCourseExamRoom().indexOf('-');
			String c_id = this.getCourseExamRoom().substring(0, n);
			String r_id = this.getCourseExamRoom().substring(n+1);
			StringBuffer sql = new StringBuffer();
			sql.append("select prExamBooking.Id,                                                    ");
			sql.append("       peStudent.Reg_No peStudentRegNo,                                     ");
			sql.append("       peStudent.True_Name peStudentName,                                   ");
			sql.append("       prStudentInfo.Card_No prStudentInfoCardNo,                           ");
			sql.append("       prExamBooking.Seat_No prExamBookingSeatNo,                           ");
			if(getUserAFlag()){
				sql.append("       prExamBooking.Score_Exam_a,                                          ");
				sql.append("       prExamBooking.Flag_Score_Statusa,                                    ");
				sql.append("       prExamBooking.Score_Exam_b,                                          ");
				sql.append("       prExamBooking.Flag_Score_Statusb                                     ");
			}else{
				sql.append("       prExamBooking.Score_Exam_b,                                          ");
				sql.append("       prExamBooking.Flag_Score_Statusb,                                    ");
				sql.append("       prExamBooking.Score_Exam_a,                                          ");
				sql.append("       prExamBooking.Flag_Score_Statusa                                     ");
			}
			sql.append("  from pe_student          peStudent,                                       ");
			sql.append("       pr_student_info     prStudentInfo,                                   ");
			sql.append("       pe_tch_course       peTchCourse,                                     ");
			sql.append("       pe_exam_no          peExamNo,                                        ");
			sql.append("       pe_exam_room        peExamRoom,                                      ");
			sql.append("       pr_exam_booking     prExamBooking,                                   ");
			sql.append("       pe_semester         peSemester,                                      ");
			sql.append("       pr_tch_stu_elective prTchStuElective,                                ");
			sql.append("       pr_tch_opencourse   prTchOpencourse                                  ");
			sql.append(" where peSemester.Flag_Active = '1'                                         ");
			sql.append("   and peExamNo.Fk_Semester_Id = peSemester.Id                              ");
			sql.append("   and prExamBooking.Fk_Tch_Stu_Elective_Id = prTchStuElective.Id           ");
			sql.append("   and prTchStuElective.Fk_Stu_Id = peStudent.Id                            ");
			sql.append("   and peStudent.Fk_Student_Info_Id = prStudentInfo.Id                      ");
			sql.append("   and prTchStuElective.Fk_Tch_Opencourse_Id = prTchOpencourse.Id           ");
			sql.append("   and prTchOpencourse.Fk_Course_Id = peTchCourse.Id                        ");
			sql.append("   and prExamBooking.Fk_Exam_Room_Id = peExamRoom.Id                        ");
			sql.append("   and peExamRoom.Fk_Exam_No = peExamNo.Id                                  ");
			if(getUserAFlag()){
				sql.append("   and peTchCourse.Fk_Exam_Score_Input_Usera_Id='"+getUser().getId()+"'     ");
			}else{
				sql.append("   and peTchCourse.Fk_Exam_Score_Input_Userb_Id='"+getUser().getId()+"'     ");
			}
			sql.append("   and peTchCourse.Id='"+c_id+"'                                            ");
			sql.append("   and peExamRoom.Id='"+r_id+"'                                             ");
			sql.append(" order by peTchCourse.Code,peExamNo.Id,peExamRoom.Code,prExamBooking.Seat_No");
			try {
				list = this.getGeneralService().getBySQL(sql.toString());
			} catch (EntityException e) {
			}
		}
		return list;
	}
	
	public List getScoreStatus(){
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagScoreStatus"));
		dc.add(Restrictions.gt("code", "0"));
		dc.add(Restrictions.lt("code", "5"));
		dc.addOrder(Order.asc("code"));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {}
		return list;
	}
	
	/**
	 * 登分记录到平台
	 * @return
	 */
	public String updateScore(){
		if(this.getIds()!=null){
			String[] allids = this.getIds().split(",");
			String[] allscore = this.getScore().split(",");
			String[] allscorestatus = this.getScorestatus().split(",");
			try{
				this.setMsg(this.getPrExamBookingService().saveScore(getUserAFlag(), allids, allscore, allscorestatus));
			}catch(EntityException e){
				this.setMsg(e.getMessage());
			}
		}
		return "msg";
	}
	
	/**
	 * 跳转到成绩登录页面
	 * @return
	 */
	public String gotoinput(){/*
		PeExamScoreInputUser user = (PeExamScoreInputUser)ActionContext.getContext().getSession().get("user");
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus")
			.createAlias("enumConstByFlagScoreStatusa", "enumConstByFlagScoreStatusa",DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagScoreStatusb", "enumConstByFlagScoreStatusb",DetachedCriteria.LEFT_JOIN)
			.createAlias("peSemester", "peSemester")
				.add(Restrictions.eq("peSemester.flagActive", "1"))
			.createAlias("peExamNo", "peExamNo")
			.createCriteria("peExamRoom", "peExamRoom")
				.createAlias("peSite", "peSite");
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
						.createCriteria("peTchCourse", "peTchCourse");
		if(ActionContext.getContext().getSession().get("flag").equals("a"))
			dcPrTchStuElective.createAlias("peExamScoreInputUserByFkExamScoreInputUseraId", "peExamScoreInputUserByFkExamScoreInputUseraId")
						.equals(Restrictions.eq("peExamScoreInputUserByFkExamScoreInputUseraId.id", user.getId()));
		else
			dcPrTchStuElective.createAlias("peExamScoreInputUserByFkExamScoreInputUserbId", "peExamScoreInputUserByFkExamScoreInputUserbId")
						.equals(Restrictions.eq("peExamScoreInputUserByFkExamScoreInputUserbId.id", user.getId()));
		*/
		return "input";
	}
	
	/**
	 * 跳转到成绩修改页面
	 * @return
	 */
	public String gotoinputedit(){
		return "inputedit";
	}
	
	/**
	 * 跳转到成绩查询页面
	 * @return
	 */
	public String gotosearch(){
		return "search";
	}
	
	private static PeExamScoreInputUser getUser(){
		return (PeExamScoreInputUser)ActionContext.getContext().getSession().get("user");
	}
	
	private static boolean getUserAFlag(){
		if(ActionContext.getContext().getSession().get("flag").equals("a"))
			return true;
		else
			return false;
	}
	
}
