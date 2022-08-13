package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzTeachingLiveroom;
import com.whaty.platform.entity.exception.EntityException;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzZhiBoRoomAction extends MyBaseAction {

	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true);
		this.getGridConfig().setTitle("直播课堂列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课堂名称"), "name", true,
				true, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("课堂公告"), "note", true,
				true, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("开始时间"), "beginDate", true,
				true, true, "");
		this.getGridConfig().addColumn(this.getText("结束时间"), "endDate", true,
				true, true, "");
	}


	public Map add() {
		
		Map map = new HashMap();
		
		try {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	
		this.getBean().setSsoUser(userSession.getSsoUser());
		Date date = new Date();
		this.getBean().setDataDate(date);
		this.setBean((PeBzzTeachingLiveroom)super.setSubIds(this.getBean()));
		this.getGeneralService().save(this.getBean());	
			map.put("success", "true");
			map.put("info", "添加成功");
			logger.info("添加成功! ");
		} catch (Exception e) {
			map.clear();
			map.put("success", "false");
			map.put("info", "课堂名已存在");
			logger.error("添加失败!");
		}

		return map;
	}

	public void setEntityClass() {
		this.entityClass = PeBzzTeachingLiveroom.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzZhiBoRoom";
	}

	public void setBean(PeBzzTeachingLiveroom bzzTeachingLiveroom) {
		super.superSetBean(bzzTeachingLiveroom);
	}

	public PeBzzTeachingLiveroom getBean() {
		return (PeBzzTeachingLiveroom) superGetBean();
	}
}
