package com.whaty.platform.entity.web.action.information.sms;

import com.whaty.platform.entity.bean.PeEmailHistory;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 评审专家邮件查询
 * 
 * @author hangaosheng 2011-07-19
 *
 */
public class Email4PevalueExpertSearchAction extends Email4PeManagerSearchAction {

	private static final long serialVersionUID = 13133834282051158L;

	public void initGrid() {
		this.getGridConfig().setTitle("评审专家邮件查询");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("邮件主题"), "title",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("发送时间"), "send_time",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("发送人"), "sender",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("状态"), "Combobox_enumConstByFkStatusValuate.valuate");
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace");
		this.getGridConfig().addColumn(this.getText("性别"), "Combobox_enumConstByGender.gender");
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
		this.getGridConfig().addRenderFunction(this.getText("查看详情"),"<a href=\""+this.getServletPath()+"_showDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>","id");
		this.getGridConfig().setCapability(false, false, false);
	}
	
	public Page list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select note.id,							");
		sql.append("       val.name           name,         ");
		sql.append("       note.title,                  ");
		sql.append("       note.send_time,                  ");
		sql.append("       sender.name sender,                 ");
		sql.append("       valuate.name       valuate,      ");
		sql.append("       val.workplace,                   ");
		sql.append("       gender.name        gender,       ");
		sql.append("       val.folk,                        ");
		sql.append("       val.telephone,                   ");
		sql.append("       val.birthyearmonth,              ");
		sql.append("       val.email,                       ");
		sql.append("       val.major,                       ");
		sql.append("       val.age,                         ");
		sql.append("       val.education,                   ");
		sql.append("       val.politics,                    ");
		sql.append("       val.zhiwu,                       ");
		sql.append("       val.zhicheng,                    ");
		sql.append("       val.fax                          ");
		sql.append("  from pe_email_history note,            ");
		sql.append("       pe_valua_expert val,             ");
		sql.append("       pe_manager      sender,          ");
		sql.append("       enum_const      valuate,         ");
		sql.append("       enum_const      gender           ");
		sql.append(" where note.receiver_id = val.id        ");
		sql.append("   and note.sender_id = sender.id       ");
		sql.append("   and val.fk_gender = gender.id        ");
		sql.append("   and val.fk_status = valuate.id       ");
		return iniSqllist(sql);
	}

	public void setEntityClass() {
		this.entityClass = PeEmailHistory.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/email4PeValueExpertSearch";
	}
}
