package com.whaty.platform.entity.web.action.basic;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.MainTeachingSubject;
import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 单位管理
 * 
 * @author 赵玉晓
 * 
 */
public class MainTeachingSubjectAction extends MyBaseAction {

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
		this.entityClass = MainTeachingSubject.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/mainTeachingSubjectAction";
	}

	public void setBean(MainTeachingSubject instance) {
		super.superSetBean(instance);
	}

	public MainTeachingSubject getBean() {
		return (MainTeachingSubject) super.superGetBean();
	}


	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(MainTeachingSubject.class);
//		dc.createCriteria("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
