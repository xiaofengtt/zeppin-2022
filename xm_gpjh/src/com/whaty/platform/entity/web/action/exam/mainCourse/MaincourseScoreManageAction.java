package com.whaty.platform.entity.web.action.exam.mainCourse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.util.Const;

public class MaincourseScoreManageAction extends MainCourseBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true,true,false);
		this.getGridConfig().setTitle(this.getText(this.getSemesterName() + "主干课程考试成绩管理"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prExamOpenMaincourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("考场号"),"peExamMaincourseRoom.code");
		this.getGridConfig().addColumn(this.getText("座位号"),"seatNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("考试成绩"),"score",true,true,true,Const.score_for_extjs);
//		this.getGridConfig().addColumn(this.getText("成绩状态"),"enumConstByFlagScoreStatus.name",true,true,true,"TextField",true,50);
		ColumnConfig column = new ColumnConfig(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
		this.getGridConfig().addColumn(column);
		this.getGridConfig().addMenuFunction("导入学生成绩",1);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamStuMaincourse.class;
	}

	public void checkBeforeAdd() throws EntityException{
		
		EnumConst enumConst = new EnumConst();
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		try {
			enumConst = (EnumConst)this.getGeneralService().getById(this.getBean().getEnumConstByFlagScoreStatus().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(enumConst != null && enumConst.getCode() != null){
			if(("0".equals(enumConst.getCode()) || "1".equals(enumConst.getCode()))){
				enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagScoreStatus", "1");
			}
			this.getBean().setEnumConstByFlagScoreStatus(enumConst);
		}
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/maincourseScoreManage";
	}
	public void setBean(PrExamStuMaincourse bean){
		super.superSetBean(bean);
	}
	public PrExamStuMaincourse getBean(){
		return (PrExamStuMaincourse)super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
		dc.createCriteria("peStudent", "peStudent")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.createAlias("peSite", "peSite")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("peMajor", "peMajor")
			.createAlias("peGrade", "peGrade");
		dc.createCriteria("prExamOpenMaincourse","prExamOpenMaincourse")
				.createAlias("peTchCourse", "peTchCourse")
				.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
				.createAlias("peSemester", "peSemester");
		dc.createCriteria("peExamMaincourseRoom", "peExamMaincourseRoom");
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
}
