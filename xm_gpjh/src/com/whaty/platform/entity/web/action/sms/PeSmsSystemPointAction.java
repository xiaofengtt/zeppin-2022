package com.whaty.platform.entity.web.action.sms;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.SmsSystempoint;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeSmsSystemPointAction extends MyBaseAction {

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SmsSystempoint.class);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);//发送范围
		return dc;
	}
	

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("短信点管理"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("名称"),"name");
		this.getGridConfig().addColumn(this.getText("短信内容"),"content");
		this.getGridConfig().addColumn(this.getText("是否有效"),"enumConstByFlagIsvalid.name");
		
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "enumConstByFlagIsvalid.id",this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "enumConstByFlagIsvalid.id",this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0").getId());
		

	}


	@Override
	public void setEntityClass() {
		this.entityClass = SmsSystempoint.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peSmsSystemPointAction";
	}
	


}
