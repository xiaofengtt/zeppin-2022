package com.whaty.platform.entity.web.action.information;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrMeetPerson;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrMeetPersonAction extends MyBaseAction {

	private String note;


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true);
		this.getGridConfig().setTitle(this.getText("参会人员"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name");
		this.getGridConfig().addColumn(this.getText("民族"), "folk");
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng");
		this.getGridConfig().addColumn(this.getText("单位"), "peUnit.name",true,false,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("备注"), "note",false,true,true,"Textarea",true,500);
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.go(-1);}");
	}

	
	//得到当前用户所在单位
	public PeUnit getcurrentUserUnit(){
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		 PeUnit unit=null;
		 DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		 dc.createCriteria("ssoUser","ssoUser");
		 dc.createCriteria("peUnit","peUnit");
		 dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
		 List list=new LinkedList();
		 try {
			list=this.getGeneralService().getList(dc);
			 if(list!=null&&list.size()>0){
				 PeManager mgr=(PeManager) list.get(0);
				 unit=mgr.getPeUnit();
			 }
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return unit;
	 }
	@Override
	public void checkBeforeAdd() throws EntityException {
	    PeUnit unit = this.getcurrentUserUnit();
	    this.getBean().setPeUnit(unit);
			this.getGeneralService().save(this.getBean());
	}
		
	@Override
	public void setEntityClass() {
		this.entityClass = PrMeetPerson.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/prMeetPerson";

	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
    	DetachedCriteria dc=DetachedCriteria.forClass(PrMeetPerson.class);
    	dc.createAlias("enumConstByFkGender", "enumConstByFkGender");
    	dc.createAlias("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN);
    	dc.createAlias("peMeeting", "peMeeting");
    	UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
    	if(!us.getRoleType().equals("4")){
    		dc.add(Restrictions.eq("peUnit", getcurrentUserUnit()));
    	}
    	return dc;
	}
    public void setBean(PrMeetPerson instance) {
		super.superSetBean(instance);
	}

	public PrMeetPerson getBean() {
		return (PrMeetPerson) super.superGetBean();
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
