package com.whaty.platform.vote.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.vote.bean.VotePaper;
import com.whaty.platform.vote.service.VotePaperService;

public class VoteManageAction extends VoteBaseAction {
	
	private List list;
	
	private VotePaperService votePaperService;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public VotePaperService getVotePaperService() {
		return votePaperService;
	}

	public void setVotePaperService(VotePaperService votePaperService) {
		this.votePaperService = votePaperService;
	}
	
	/**
	 * 获得投票列表
	 */
	public String list(){
		DetachedCriteria dc = DetachedCriteria.forClass(VotePaper.class);
		dc.add(Restrictions.eq("active", 1L));
		dc.addOrder(Order.desc("startDate"));
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		
		if(us == null){
			dc.add(Restrictions.eq("type", "NORMAL"));
		}
		
		List list = getVotePaperService().getList(dc);
		
		this.setList(list);
		
		return "dcwj";
	}
	
}
