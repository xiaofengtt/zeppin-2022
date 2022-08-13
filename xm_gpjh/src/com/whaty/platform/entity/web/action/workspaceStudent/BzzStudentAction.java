package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class BzzStudentAction extends MyBaseAction {
	private PeVotePaper peVotePaper; // 调查问卷
	private String loginErrMessage;

	public void initGrid() {
	}

	public void setEntityClass() {
	}

	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/bzzstudent";
	}

	public String getVoteList() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria FlagType = dc.createCriteria("peProApplyno", "peProApplyno");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		FlagType.add(Restrictions.eq("code", us.getLoginId().substring(9, 10)));
		FlagType.add(Restrictions.like("year", us.getLoginId().substring(0, 2),MatchMode.END));
//				Restrictions.eq("code", "1")));
		dc.addOrder(Order.desc("foundDate"));
		try {
			List itiList = this.getGeneralService().getList(dc);
			if(itiList!=null&&!itiList.isEmpty()){
				setPeVotePaper((PeVotePaper) itiList.get(0));
			}else{
				this.loginErrMessage = "暂时没有开放的调查问卷！";
				return "back";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "voteList";
	}

	public PeVotePaper getPeVotePaper() {
		return peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public String getLoginErrMessage() {
		return loginErrMessage;
	}

	public void setLoginErrMessage(String loginErrMessage) {
		this.loginErrMessage = loginErrMessage;
	}
}