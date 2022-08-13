package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSubject;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 单位管理
 * 
 * @author 赵玉晓
 * 
 */
public class PeSubjectAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("学科管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("编号"), "code",true, true, true, "regex: new RegExp(/^\\d{3}$/),regexText:'编号必须为3位数字', ");
		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextArea", true, 2000);
		

	}
	@Override
	public void checkBeforeAdd() throws EntityException {
		if(this.getBean().getName().length()>25){
			throw new EntityException("名称不能超过25个字符！");
		}
	}
	

	@Override
	public void setEntityClass() {
		this.entityClass = PeSubject.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peSubjectAction";
	}

	public void setBean(PeSubject instance) {
		super.superSetBean(instance);
	}

	public PeSubject getBean() {
		return (PeSubject) super.superGetBean();
	}


	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSubject.class);
//		dc.createCriteria("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		dc.addOrder(Order.asc("name"));
		return dc;
	}
}
