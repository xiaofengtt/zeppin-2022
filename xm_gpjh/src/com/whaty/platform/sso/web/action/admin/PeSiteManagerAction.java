package com.whaty.platform.sso.web.action.admin;

import java.text.SimpleDateFormat;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeSiteManagerAction extends MyBaseAction <PeSitemanager>{
	
	private GeneralService peSitemanagerService;

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("分站管理员管理"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, "", false, 25);
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
//		c_name.setAdd(true); c_name.setSearch(false); c_name.setList(false);
		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='2' and e.namespace='FlagRoleType' order by t.name");
		this.getGridConfig().addColumn(c_name);
		
//		this.getGridConfig().addColumn(this.getText("权限组"), "ssoUser.pePriRole.name");
		this.getGridConfig().addColumn(this.getText("所属站点"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("地区"), "regionName");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard");
		this.getGridConfig().addColumn(this.getText("电话"), "phone",true,true,true,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone",true,true,true,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("邮箱"), "email",true,true,true,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("通信地址及邮编"), "address", true, true, true, "", false, 150);
		this.getGridConfig().addColumn(this.getText("毕业院校、专业"), "graduationInfo");
		this.getGridConfig().addColumn(this.getText("毕业时间"), "graduationDate");
		this.getGridConfig().addColumn(this.getText("职称"), "zhiCheng");
		this.getGridConfig().addColumn(this.getText("从事成人教育时间"), "workTime");
		this.getGridConfig().addRenderFunction(this.getText("设置权限范围"), "<a href='managerRangeRight_showRangeRight.action?managerId=${value}&managerType=2' target='_self'>设置</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.location='/admin/manager_manage.jsp'}");
		
	}
	@Override
	public void checkBeforeAdd() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			if(!manage.isValidIdcard(this.getBean().getIdCard())){
				throw new EntityException("身份证号码输入错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("身份证号码输入错误！");
		}
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			if(!manage.isValidIdcard(this.getBean().getIdCard())){
				throw new EntityException("身份证号码输入错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("身份证号码输入错误！");
		}
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeSitemanager.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/peSitemanager";
	}

	public GeneralService getGeneralService() {
		return peSitemanagerService;
	}

	public void setPeSitemanagerService(GeneralService peSitemanagerService) {
		this.peSitemanagerService = peSitemanagerService;
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
		dc.createAlias("enumConstByGender", "enumConstByGender", DetachedCriteria.LEFT_JOIN)
			.createAlias("peSite", "peSite", DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN)
			.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole");
		return dc;
	}

	public PeSitemanager getBean() {
		return super.superGetBean();
	}

	public void setBean(PeSitemanager bean) {
		super.superSetBean(bean);
	}

}
