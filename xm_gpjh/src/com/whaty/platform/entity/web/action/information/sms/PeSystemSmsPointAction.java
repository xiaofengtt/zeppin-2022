package com.whaty.platform.entity.web.action.information.sms;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PeSmsSystempoint;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeSystemSmsPointAction extends MyBaseAction<PeSmsSystempoint>{

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("系统短信点管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("编号"), "code",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("名称"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("内容"), "note", true, true, true, "TextArea", false, 2000);
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagSmsStatus.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagIsauto.name",true,false,true,"");
		
		this.getGridConfig().addMenuFunction(this.getText("通过审核"), "enumConstByFlagSmsStatus.id",this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagSmsStatus", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("自动"), "enumConstByFlagIsauto.id",this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsauto", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("手动"), "enumConstByFlagIsauto.id",this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsauto", "0").getId());
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeSmsSystempoint.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peSystemSmsPoint";
	}

	public void setBean(PeSmsSystempoint instance){
		this.superSetBean(instance);
	}
	
	public PeSmsSystempoint getBean(){
		return (PeSmsSystempoint)this.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSmsSystempoint.class);
		dc.createAlias("enumConstByFlagSmsStatus", "enumConstByFlagSmsStatus");
		dc.createAlias("enumConstByFlagIsauto", "enumConstByFlagIsauto");
		return dc;
	}
}
