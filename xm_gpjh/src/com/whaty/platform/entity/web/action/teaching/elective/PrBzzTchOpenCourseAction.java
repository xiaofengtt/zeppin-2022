package com.whaty.platform.entity.web.action.teaching.elective;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.SemesterOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;

import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PrBzzTchOpenCourseAction extends MyBaseAction {
	
	private List<PeBzzTchCourse> tchCourse;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<PeBzzTchCourse> getTchCourse() {
		return tchCourse;
	}

	public void setTchCourse(List<PeBzzTchCourse> tchCourse) {
		this.tchCourse = tchCourse;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("学期列表");
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		this.getGridConfig().addColumn(this.getText("学期名称"),"name");
		this.getGridConfig().addColumn(this.getText("开始时间"),"startDate");
		this.getGridConfig().addColumn(this.getText("结束时间"),"endDate");
		this.getGridConfig().addColumn(this.getText("评估开始时间"),"evalStartDate");
		this.getGridConfig().addColumn(this.getText("评估结束时间"),"evalEndDate");
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleId().equals("3"))
		{
			this.getGridConfig().addRenderFunction(this.getText("开课设置"), "<a href=\"prBzzTchOpenCourse_getType.action?bean.id=${value}\">设置</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("批量选课"), "<a href=\"prBzzTchSelectCourses_SelectCourses.action?id=${value}&status=returnurl\">批量选课</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("查看开课"), "<a href=\"prBzzTchOpenCourseDetail.action?id=${value}\">查看</a>","id");
		}else
		{
			this
			.getGridConfig()
			.addRenderScript(this.getText("课程评估"), "{return '<a href=prBzzTchOpenCourseDetail_IsCanEval.action?id='+record.data['id']+'>进入评估</a>';}","");
			
		}
	}

	public void setEntityClass() {
		this.entityClass =PrBzzTchOpencourse.class;
	}

	public void setServletPath() {
		this.servletPath="/entity/teaching/prBzzTchOpenCourse";
		
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dcd = DetachedCriteria.forClass(PeBzzBatch.class);
		return dcd;
	}
	
	public String getType(){
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("note", "课程性质"));
		try {
			this.setTchCourse( this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "front";
	}
}
