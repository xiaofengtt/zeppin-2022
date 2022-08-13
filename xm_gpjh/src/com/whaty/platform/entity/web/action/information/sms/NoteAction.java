/*
 * @(#)NoteAction.java 3:38:57 PM
 * All Rights Reserved,Copyright(C) 2009,北京网梯科技发展有限公司
 * Aug 30, 2010 应用项目部 zhangyihui
 */ 
package com.whaty.platform.entity.web.action.information.sms;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.Const;

/**
 * @author <a href="mailto: zhangyihui@whaty.com">zhangyihui</a>
 * 
 */
public class NoteAction extends Msg4PeTraineeAction {
	

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("联系人短信与邮件管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",true,20);
		this.getGridConfig().addColumn(this.getText("登录名"), "loginId");
		this.getGridConfig().addColumn(this.getText("单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",true,200,"");
//		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFkStatus.name");
		this.getGridConfig().addColumn(this.getText("部门"), "department",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",true, true, true,"",true,200,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("email"), "email",true, true, true,"",true,200,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",true, true, true,"",true,200,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("生日"), "birthday",true,true,true,"Date",true,200);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",true,true,true,"",true,200,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("账号属性"), "enumConstByFlagProperty.name",true,true,true,"TextField",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextArea", true, 2000);
		
//		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("姓名"), "name");
//		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",true,20,"");
//		this.getGridConfig().addColumn(this.getText("学历"), "education", true, true, true, "TextField", true, 20);
//		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics", true, true, true, "", true, 50);
//		this.getGridConfig().addColumn(this.getText("联系地址"), "address", true, true, true, "TextField", true, 20);
//		this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", true, 20);
//		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", true, true, true, "", true, 20);
//		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 20);
//		this.getGridConfig().addColumn(this.getText("学历"), "education", true, true, true, "", true, 4);
//		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,true, true, "TextField", true, 50, Const.email_for_extjs);
		
		initMsgGridConfig();
		initMailGridConfig();
	}
	
	
	@Override
	public List<String> getTelephoneByIdSQL(String ids) {
		String getTelephoneSQL = "";
		getTelephoneSQL = "select telephone from pe_manager where id in ("+ids+")";
		List resultList = null;
		try {
			resultList = getGeneralService().getBySQL(getTelephoneSQL);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		resultList.remove(null);
		return resultList;
	}
	@Override
	public String toSendMailPage(){
		String peTraineeIds[] = this.getIds().split(",");
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.add(Restrictions.in("id", peTraineeIds));
		dc.add(Restrictions.isNotNull("email"));
		dc.setProjection(Projections.projectionList()
				.add(Property.forName("email"))
				.add(Property.forName("id")));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list==null||list.isEmpty()){
				this.setMsg("您所选的联系人没有邮箱信息！");
				this.setTogo("back");
				return "msg";
			}
			ActionContext.getContext().getSession().put("targetList", list);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "sendMailPage";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createAlias("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcUnit = dc.createCriteria("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createAlias("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dcUnit.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		dc.add(Restrictions.isNotNull("name"));
		dc.createCriteria("enumConstByFlagProperty", "enumConstByFlagProperty",DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/information/noteAction";
	}
}