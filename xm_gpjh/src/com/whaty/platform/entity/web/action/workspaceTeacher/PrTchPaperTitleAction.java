package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrTchPaperTitle;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PrTchPaperTitleAction extends MyBaseAction<PrTchPaperTitle>{

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("设置论文题目"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name");
		this.getGridConfig().addColumn(this.getText("题目"), "title");
		this.getGridConfig().addColumn(this.getText("最多选题人数"), "stuCountLimit",true,true,true,Const.scoreLine_for_extjs);
		this.getGridConfig().addColumn(this.getText("题目备注"), "titleMemo", true, true, true, "TextArea", true, 1000);
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchPaperTitle.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/prTchPaperTitle";
	}
	
	public void setBean(PrTchPaperTitle instance) {
		super.superSetBean(instance);
		
	}
	
	public PrTchPaperTitle getBean(){
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperTitle.class);
		dc.createCriteria("peSemester", "peSemester");
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.createCriteria("peTeacher", "peTeacher");
		dc.add(Restrictions.eq("peTeacher.ssoUser", ((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser()));
		return dc;
	}

	
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
		dc.add(Restrictions.eq("ssoUser", ((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
		if(peTeacher.getStuCountLimit()==null){
			throw new EntityException("教师指导学生最大人数没有设置。");
		}
		if (this.getBean().getStuCountLimit() > peTeacher.getStuCountLimit()) {
			throw new EntityException("题目最大学生人数不能超过教师指导学生最大人数。");
		}
		
		DetachedCriteria dcPaperTitle = this.initDetachedCriteria();
		List paperTitleList = new ArrayList();
		try {
			paperTitleList = this.getGeneralService().getList(dcPaperTitle);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException();
		}
		long stuCount = this.getBean().getStuCountLimit();
		for (Iterator iter = paperTitleList.iterator(); iter.hasNext();) {
			PrTchPaperTitle paperTitle = (PrTchPaperTitle) iter.next();
			if (paperTitle.equals(this.getBean())) {
				continue;
			}
			stuCount += paperTitle.getStuCountLimit();
		}
		if (stuCount > peTeacher.getStuCountLimit()) {
			throw new EntityException("所有论文题目学生人数之和不能超过教师指导学生最大人数");
		}
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		this.checkBeforeAdd();
	}

	@Override
	public Map add() {
		Map map = new HashMap();
		this.setBean((PrTchPaperTitle)super.setSubIds(this.getBean()));
		
		DetachedCriteria dcTeacher = DetachedCriteria.forClass(PeTeacher.class);
		dcTeacher.add(Restrictions.eq("ssoUser", ((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(dcTeacher);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
		
		DetachedCriteria dcActiveSemester = DetachedCriteria.forClass(PeSemester.class);
		dcActiveSemester.add(Restrictions.eq("flagActive", "1"));
		List semesterList = new ArrayList();
		try {
			semesterList = this.getGeneralService().getList(dcActiveSemester);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeSemester peSemester = (PeSemester) semesterList.get(0);
		
		this.getBean().setPeTeacher(peTeacher);
		this.getBean().setPeSemester(peSemester);
		
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		
		try {
			this.getGeneralService().save(this.getBean());
			map.put("success", "true");
			map.put("info", "添加成功");
		} catch (Exception e) {
			return this.checkAlternateKey(e, "添加");
		}
		return map;
	}

}
