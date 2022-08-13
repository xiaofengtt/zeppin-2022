package com.whaty.platform.entity.web.action.teaching.result;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class ScoreSearchAction extends MyBaseAction<PrTchStuElective> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("成绩列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig()
				.addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("年级"),
				"peStudent.peGrade.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name", true, false, true, "TextField", false,
				50);
		//课程名注释掉，前一个页面已经选择了课程名（20090423 龚妮娜）
		this.getGridConfig().addColumn(this.getText("课程名"),
			"prTchOpencourse.peTchCourse.name", false, false, true,
				"TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("平时成绩"), "scoreUsual", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("平时成绩", "{if (${value}=='') return '未录入';return ${value}}", "scoreUsual");
		
		this.getGridConfig().addColumn(this.getText("作业成绩"), "scoreHomework", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("作业成绩", "{if (${value}=='') return '未录入';return ${value}}", "scoreHomework");
		
		this.getGridConfig().addColumn(this.getText("考试成绩"), "scoreExam", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("考试成绩", "{if (${value}=='') return '未录入';return ${value}}", "scoreExam");

		this.getGridConfig().addColumn(this.getText("成绩"), "scoreTotal",true,true,false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("成绩", "{if (${value}=='') return '未合成';return ${value}}", "scoreTotal");

		this.getGridConfig().addColumn(this.getText("成绩状态"),
		"enumConstByFlagScoreStatus.name");
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/scoreSearch";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent", "peStudent").createAlias("peSite",
				"peSite").createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor").createAlias("peGrade", "peGrade").createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		dc.createCriteria("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peTchCourse", "peTchCourse").createAlias("peSemester", "peSemester");
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}
	
	public void setBean(PrTchStuElective instance) {
		super.superSetBean(instance);
	}

	public PrTchStuElective getBean() {
		return super.superGetBean();
	}
	@Override
	public void checkBeforeUpdate() throws EntityException {
		if (!(this.getBean().getPeStudent().getEnumConstByFlagStudentStatus().getCode().equals("4")&&this.getBean().getPrTchOpencourse().getPeSemester().getFlagActive().equals("1"))) {
			throw new EntityException("只能修改当前学期且在籍的学生。");
		}
	}
}
