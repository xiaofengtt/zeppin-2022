package com.whaty.platform.entity.web.action.information.sms;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.Const;

/**
 * 评审专家 短信与邮件管理
 * 
 * @author 侯学龙 2010-9-20
 *
 */
public class Msg4PevalueExpertAction extends Msg4PeTraineeAction {

	private static final long serialVersionUID = 1L;

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeValuaExpert.class);
		dc.createAlias("enumConstByFkStatusValuate", "enumConstByFkStatusValuate",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, true, true, true);
		this.getGridConfig().setTitle("评审专家短信与邮件管理");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFkStatusValuate.name");
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name");
		this.getGridConfig().addColumn(this.getText("民族"), "folk");
		this.getGridConfig().addColumn(this.getText("手机"), "telephone");
		this.getGridConfig().addColumn(this.getText("出生年月"), "birthyearmonth");
		this.getGridConfig().addColumn(this.getText("邮箱"), "email");
		this.getGridConfig().addColumn(this.getText("所学专业"), "major");
		this.getGridConfig().addColumn(this.getText("年龄"),"age");
		this.getGridConfig().addColumn(this.getText("学历"), "education");
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics");
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu");
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng");
		this.getGridConfig().addColumn(this.getText("传真"), "fax");
		this.getGridConfig().setCapability(false, false, false);
		initMsgGridConfig();
		initMailGridConfig();
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/msg4PeValueExpert";
	}
	@Override
	public List<String> getTelephoneByIdSQL(String ids) {
		String getTelephoneSQL = "select telephone from pe_valua_expert where id in ("+ids+")";
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
		DetachedCriteria dc = DetachedCriteria.forClass(PeValuaExpert.class);
		dc.add(Restrictions.in("id", peTraineeIds));
		dc.add(Restrictions.isNotNull("email"));
		dc.setProjection(Projections.projectionList()
				.add(Property.forName("email"))
				.add(Property.forName("id")));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list==null||list.isEmpty()){
				this.setMsg("您所选的评审专家没有邮箱信息！");
				this.setTogo("back");
				return "msg";
			}
			ActionContext.getContext().getSession().put("targetList", list);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "sendMailPage";
	}
}
