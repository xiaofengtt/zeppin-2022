package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class StudentLogin extends MyBaseAction {

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/studentLogin";
	}
	
	private String edutype;
	
	public String getEdutype() {
		return edutype;
	}

	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}

	public String toLeftMenu() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeStudent.class);
		peStudentDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List studentList = new ArrayList();
		try {
			studentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent peStudent = (PeStudent) studentList.get(0);
		if (peStudent.getPeEdutype().getName().indexOf("本") > 0) {
			this.setEdutype("zsb");
		} else {
			this.setEdutype("gqz");
		}
		
		return "toLeftMenu";
	}

}
