package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PrEduMajorSiteFeeLevelService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 学习中心管理
 * 
 * @author 李冰
 * 
 */
public class PeSiteAction extends MyBaseAction<PeSite> {
	PrEduMajorSiteFeeLevelService prEduMajorSiteFeeLevelService;

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学习中心管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name", true, true,
				true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("编号"), "code",true, true, true, "regex: new RegExp(/^\\d{4}$/),regexText:'编号必须为4位数字', ");
		this.getGridConfig().addColumn(this.getText("省份"), "province");
		this.getGridConfig().addColumn(this.getText("所属地级市"), "city");
		this.getGridConfig().addColumn(this.getText("邮政编码"), "zipCode", true, true, true,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("学习中心地址"), "address");
		this.getGridConfig().addColumn(this.getText("负责人姓名"), "manager");
		this.getGridConfig().addColumn(this.getText("负责人办公电话"),
				"managerPhoneOffice", false, true, false,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("负责人家庭电话"),
				"managerPhoneHome", false, true, false, Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("负责人手机"),
				"managerMobilephone", false, true, false, Const.phone_for_extjs);
		this.getGridConfig().addColumn(this.getText("招生电话"), "recruitPhone",
				false, true, false, Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("招生传真"), "recruitFax",
				false, true, false,"TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("备案时间"), "foundDate",
				false, true, true, "Date", true, 0);
		this.getGridConfig().addColumn(this.getText("年审通过时间"),
				"lastNianshenDate", false, true, true, "Date", true, 0);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false, true,
				false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("本站点学生的考试站点"),
				"peSite.name", false, true, true, "TextField", true, 25);
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("操作"),
						"<a href=\"/entity/basic/peSiteManager.action?bean.peSite.id=${value}\" target=\"_blank\">查看站点管理员</a>",
						"id");

	}
	@Override
	public void checkBeforeAdd() throws EntityException{
		if(this.getBean().getPeSite()!=null&&this.getBean().getPeSite().getName()!=null
				&&this.getBean().getName().equals(this.getBean().getPeSite().getName())){
			throw new EntityException("考试站点如果是本学习中心请将考试站点设置为空。");
		}
	}
	@Override
	public void checkBeforeUpdate() throws EntityException{
		this.checkBeforeAdd();
	}
	
	public Map add() {
		Map map = new HashMap();
		this.superSetBean((PeSite) setSubIds(this.getBean()));
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		try {
			this.getPrEduMajorSiteFeeLevelService().saveSite(this.getBean());
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			return map;
		} catch (Exception e) {
		return super.checkAlternateKey(e, "添加");
		}
		map.put("success", "true");
		map.put("info", "添加成功");
		return map;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeSite.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peSite";
	}

	public void setBean(PeSite instance) {
		super.superSetBean(instance);
	}

	public PeSite getBean() {
		return super.superGetBean();
	}

	public PrEduMajorSiteFeeLevelService getPrEduMajorSiteFeeLevelService() {
		return prEduMajorSiteFeeLevelService;
	}

	public void setPrEduMajorSiteFeeLevelService(
			PrEduMajorSiteFeeLevelService prEduMajorSiteFeeLevelService) {
		this.prEduMajorSiteFeeLevelService = prEduMajorSiteFeeLevelService;
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSite.class);
		dc.createCriteria("peSite", "peSite", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagActive", "enumConstByFlagActive",
				DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
