package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.bean.UnitAttribute;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 单位管理
 * 
 * @author 赵玉晓
 * 
 */
public class UnitAttributeAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("省份管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
//		this.getGridConfig().addColumn(this.getText("编号"), "code");//,true, true, true, "regex: new RegExp(/^\\d{4}$/),regexText:'编号必须为4位数字', ");
//		this.getGridConfig().addColumn(this.getText("所属地区"), "peProvince.name",true,true,true,"TextField",false,20);//this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",false,20);
//		this.getGridConfig().addColumn(this.getText("是否是集中连片特困地区县"), "isPoor");
//		this.getGridConfig().addColumn(this.getText("是否是国家级贫困县"), "isCountryPoor");
//		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextArea", true, 2000);
		

	}
	@Override
	public void checkBeforeAdd() throws EntityException{
	}
	@Override
	public void checkBeforeUpdate() throws EntityException{
	}
	

	@Override
	public void setEntityClass() {
		this.entityClass = UnitAttribute.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/unitAttributeAction";
	}

	public void setBean(UnitAttribute instance) {
		super.superSetBean(instance);
	}

	public UnitAttribute getBean() {
		return (UnitAttribute) super.superGetBean();
	}


	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(UnitAttribute.class);
//		dc.createCriteria("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
