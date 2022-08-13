package com.whaty.platform.entity.web.action.basic;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 分站管理员查看
 * 
 * @author 李冰
 * 
 */
public class PeSiteManagerAction extends MyBaseAction<PeSitemanager> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("站点管理员管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("用户名"), "name");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("地区"), "regionName");
		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone");
		this.getGridConfig().addColumn(this.getText("电话"), "phone");
		this.getGridConfig().addColumn(this.getText("邮箱"), "email");
		this.getGridConfig().addColumn(this.getText("身份证"), "idCard");
		this.getGridConfig().addColumn(this.getText("职称"), "zhiCheng");
		this.getGridConfig().addColumn(this.getText("登录名"), "loginId");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
//		this
//		.getGridConfig()
//		.addRenderScript(
//				this.getText("是否为站长"),
//				"{if (${value}=='0') return '不是'; if (${value}=='1') return '是';}",
//				"flagActive");
//		this.getGridConfig().addColumn(this.getText("备注"), "note");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeSitemanager.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peSiteManager";

	}

	public void setBean(PeSitemanager instance) {
		super.superSetBean(instance);
	}

	public PeSitemanager getBean() {
		return super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
		dc.createCriteria("peSite", "peSite");
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("enumConstByGender", "enumConstByGender",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",
				DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
