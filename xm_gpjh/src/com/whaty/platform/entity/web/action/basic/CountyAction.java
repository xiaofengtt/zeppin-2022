package com.whaty.platform.entity.web.action.basic;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.County;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 单位管理
 * 
 * @author 赵玉晓
 * 
 */
public class CountyAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("县级管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("编号"), "code");//,true, true, true, "regex: new RegExp(/^\\d{4}$/),regexText:'编号必须为4位数字', ");
		this.getGridConfig().addColumn(this.getText("所属地区"), "city.name",true,true,true,"TextField",false,20);//this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",false,20);
		this.getGridConfig().addColumn(this.getText("所属省份"), "city.peProvince.name",true,false,true,"TextField",false,20);
		this.getGridConfig().addColumn(this.getText("是否是集中连片特困地区县"), "isPoor");
		this.getGridConfig().addColumn(this.getText("是否是国家级贫困县"), "isCountryPoor");
		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextArea", true, 2000);
		

	}
	@Override
	public void checkBeforeAdd() throws EntityException{
	}
	@Override
	public void checkBeforeUpdate() throws EntityException{
	}
	

	@Override
	public void setEntityClass() {
		this.entityClass = County.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/countyAction";
	}

	public void setBean(County instance) {
		super.superSetBean(instance);
	}

	public County getBean() {
		return (County) super.superGetBean();
	}


	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(County.class);
		dc.createCriteria("city", "city", DetachedCriteria.LEFT_JOIN);
//		dc.createAlias("city", "city", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("city.peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
