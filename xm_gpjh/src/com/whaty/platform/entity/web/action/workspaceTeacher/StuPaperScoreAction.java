package com.whaty.platform.entity.web.action.workspaceTeacher;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PrTchPaperTitle;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 查看学生论文成绩
 * @author Administrator
 *
 */
public class StuPaperScoreAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学生论文成绩"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "prTchPaperTitle.peSemester.name");
		this.getGridConfig().addColumn(this.getText("题目"), "prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学生姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("终稿成绩"), "finalScore");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/stuPaperScore";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		DetachedCriteria dcTitle = dc.createCriteria("prTchPaperTitle","prTchPaperTitle");
		dc.createCriteria("peStudent", "peStudent").createAlias("peSite", "peSite");
		dcTitle.createCriteria("peSemester", "peSemester");
		dcTitle.createCriteria("peTeacher", "peTeacher");
		dcTitle.add(Restrictions.eq("peTeacher.ssoUser", ((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser()));
		return dc;
	}
}
