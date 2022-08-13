package com.whaty.platform.entity.web.action.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeJob;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrJobUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.EmailSendUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrJobUnitAction extends MyBaseAction {

	
	private PrJobUnit prJobUnit;
	private PeJob peJob;
	private String scopeString;
	private String send;

	@Override
	public void initGrid() {

	}

	// 分配任务
	

	public List<PeUnit> getUnitById(PeJob job,String[] unitId) {
		DetachedCriteria dcexp = DetachedCriteria.forClass(PrJobUnit.class);
		dcexp.createAlias("peUnit", "peUnit");
		dcexp.createAlias("peJob", "peJob");
		dcexp.add(Restrictions.eq("peJob", job));
		dcexp.setProjection(Projections.distinct(Property.forName("peUnit.id")));
		DetachedCriteria dc = DetachedCriteria.forClass(PeUnit.class);
		dc.add(Restrictions.in("code", unitId));
		dc.add(Subqueries.propertyNotIn("id", dcexp));
		try {
			List<PeUnit> list = this.getGeneralService().getList(dc);
			return list;
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 修改任务
	public String editJob() {
		PeJob job = (PeJob) this.getMyListService().getById(PeJob.class,this.getIds());
		this.setPeJob(job);
		return "show_add_job";
	}


	// 得到当前用户的单位
	public PeManager getCurrentUser() {
		UserSession usersession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("peUnit", "peUnit");
		dc.add(Restrictions.eq("ssoUser.id", usersession.getSsoUser().getId()));
		List list = new LinkedList();
		try {
			list = this.getGeneralService().getList(dc);
			if (list != null && list.size() > 0) {
				PeManager mgr = (PeManager) list.get(0);
				return mgr;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setEntityClass() {
		this.entityClass = PrJobUnit.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/prJobUnit";
	}

	public void setBean(PeJob instance) {
		super.superSetBean(instance);
	}

	

	public AbstractBean getBean() {
		return (AbstractBean) super.superGetBean();
	}
	public List getJobPriority() {
		String sql = "select id,name from enum_const where namespace = 'FkJobPriority'";
		List jobPriorityList = new ArrayList();
		try {
			jobPriorityList = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobPriorityList;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrJobUnit.class);
		dc.createCriteria("peJob", "peJob", DetachedCriteria.LEFT_JOIN)
				.createAlias("enumConstByFkJobPriority",
						"enumConstByFkJobPriority", 1);

		return dc;
	}
	public String showAddJob() {
		return "show_add_job";
	}
	public PrJobUnit getPrJobUnit() {
		return prJobUnit;
	}

	public void setPrJobUnit(PrJobUnit prJobUnit) {
		this.prJobUnit = prJobUnit;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public PeJob getPeJob() {
		return peJob;
	}

	public void setPeJob(PeJob peJob) {
		this.peJob = peJob;
	}

	public String getScopeString() {
		return scopeString;
	}

	public void setScopeString(String scopeString) {
		this.scopeString = scopeString;
	}


}
