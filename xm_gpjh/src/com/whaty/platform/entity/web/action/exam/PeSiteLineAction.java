package com.whaty.platform.entity.web.action.exam;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PrEduMajorSiteFeeLevelService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 站点线路管理
 * @author 李冰
 *
 */
public class PeSiteLineAction extends MyBaseAction<PeSite> {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,true,true);
		this.getGridConfig().setTitle(this.getText("站点线路管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("编号"), "code", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("省份"), "province", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("所属地级市"), "city", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("邮政编码"), "zipCode", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("学习中心地址"), "address", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("负责人姓名"), "manager", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("负责人办公电话"), "managerPhoneOffice", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("负责人手机"), "managerMobilephone", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("线路号"), "line", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("顺序号"), "sequence", true, true, true, "TextField", true, 25);

	}


	@Override
	public void setEntityClass() {
		this.entityClass = PeSite.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peSiteLine";
	}

	public void setBean(PeSite instance) {
		super.superSetBean(instance);
	}

	public PeSite getBean() {
		return super.superGetBean();
	}


	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSite.class);
		return dc;
	}

}
