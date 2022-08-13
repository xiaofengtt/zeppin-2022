package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrRecPatrolSetting;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.service.recruit.recExam.PrRecExamStuCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ExamInspectorAction extends MyBaseAction {
	private PrRecExamStuCourseService prRecExamStuCourseService;
	public PrRecExamStuCourseService getPrRecExamStuCourseService() {
		return prRecExamStuCourseService;
	}

	public void setPrRecExamStuCourseService(
			PrRecExamStuCourseService prRecExamStuCourseService) {
		this.prRecExamStuCourseService = prRecExamStuCourseService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("巡考查询");
		this.getGridConfig().setCapability(false, false, true,true);
		
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("招生批次"), "peRecruitplan.name", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("巡考人姓名"), "peExamPatrol.name");
		ColumnConfig column1 = new ColumnConfig(this.getText("巡考人姓名"),"peExamPatrol.name");
		column1.setComboSQL("select t.id ,t.name  from pe_exam_patrol t ,enum_const con where t.flag_is_xunkao=con.id and con.code='1' ");
		this.getGridConfig().addColumn(column1);
	}
	
	public String autoInspector() {
		try {
			this.setTogo("back");
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
			dc.add(Restrictions.eq("flagActive", "1"));
			List<PeRecruitplan> peRecruitplan = this.getGeneralService()
					.getList(dc);
			Date start = peRecruitplan.get(0).getStartDate();
			Date end = peRecruitplan.get(0).getExamStartDate();
			Date examEnd = peRecruitplan.get(0).getExamEndDate();
			Date now = new Date();
			if (!start.before(now)) {
				this.setMsg("招生考试批次还未开始，无法自动分配");
				return "msg";
			}
			if (!now.before(examEnd)) {
				this.setMsg("考试已经结束，无法自动分配");
				return "msg";
			}
			if (!now.before(end)) {
				this.setMsg("考试已经开始，无法自动分配");
				return "msg";
			}
			
			// 根据session判断所进行的操作
			PeSite peSite = new PeSite();
			UserSession us = (UserSession)ServletActionContext
					.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			
			String infor = "";
			if (us.getUserLoginType().equals("3")) {
			infor += this.getPrRecExamStuCourseService().saveInspector();
			} else if (us.getUserLoginType().equals("2")) {
				infor += this.getPrRecExamStuCourseService().saveInvigilator();
			} else {
				this.setMsg("无操作权限");
			}
		this.setMsg(infor);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("分配失败");
		}

		return "msg";
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecPatrolSetting.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examInspector";
	}
	
	public void setBean(PrRecPatrolSetting instance) {
		super.superSetBean(instance);
		
	}
	
	public PrRecPatrolSetting getBean(){
		return (PrRecPatrolSetting) super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrRecPatrolSetting.class);
		dc.createCriteria("peExamPatrol","peExamPatrol");
		dc.createCriteria("peSite","peSite");
		dc.createCriteria("peRecruitplan","peRecruitplan")
			.add(Restrictions.eq("flagActive", "1"));
		return dc;
	}

}
