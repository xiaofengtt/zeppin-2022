package com.whaty.platform.entity.web.action.information.sms;

import com.whaty.platform.entity.bean.PeEmailHistory;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 联系人邮件查询
 * 
 * @author hangaosheng 2011-07-19
 *
 */
public class Email4PeManagerSearchAction extends MyBaseAction {

	private static final long serialVersionUID = 2179153445847759657L;

	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("联系人邮件查询"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",true,20);
		this.getGridConfig().addColumn(this.getText("登录名"), "login_id");
		this.getGridConfig().addColumn(this.getText("邮件主题"), "title",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("发送时间"), "send_time",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("发送人"), "sender",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("单位"), "Combobox_PeUnit.unit");
		this.getGridConfig().addColumn(this.getText("性别"), "Combobox_enumConstByGender.gender",true,true,true,"TextField",true,200,"");
		this.getGridConfig().addColumn(this.getText("部门"), "department",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",true, true, true,"",true,200,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("email"), "email",true, true, true,"",true,200,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "office_phone",true, true, true,"",true,200,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("生日"), "birthday",true,true,true,"Date",true,200);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",true,true,true,"",true,200,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("账号属性"), "Combobox_enumConstByFlagProperty.property",true,true,true,"TextField",true,200);
		
		this.getGridConfig().setCapability(false, false, false);
	}

	public Page list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select note.id,							");
		sql.append("       man.name         name,           ");
		sql.append("       man.login_id,                    ");
		sql.append("       note.title,                  ");
		sql.append("       note.send_time,                  ");
		sql.append("       sender.name      sender,         ");
		sql.append("       unit.name        unit,           ");
		sql.append("       gender.name      gender,         ");
		sql.append("       man.department,                  ");
		sql.append("       man.telephone,                   ");
		sql.append("       man.email,                       ");
		sql.append("       man.office_phone,                ");
		sql.append("       man.fax,                         ");
		sql.append("       man.birthday,                    ");
		sql.append("       man.address,                     ");
		sql.append("       man.zip,                         ");
		sql.append("       man.folk,                        ");
		sql.append("       man.politics,                    ");
		sql.append("       man.education,                   ");
		sql.append("       property.name    property        ");
		sql.append("  from pe_email_history note,            ");
		sql.append("       pe_manager      man,             ");
		sql.append("       pe_manager      sender,          ");
		sql.append("       pe_unit         unit,            ");
		sql.append("       enum_const      isvalid,         ");
		sql.append("       enum_const      gender,          ");
		sql.append("       enum_const      property         ");
		sql.append(" where note.receiver_id = man.id        ");
		sql.append("   and note.sender_id = sender.id       ");
		sql.append("   and man.fk_unit = unit.id            ");
		sql.append("   and man.fk_gender = gender.id        ");
		sql.append("   and man.flag_property = property.id  ");
		sql.append("   and unit.flag_isvalid = isvalid.id   ");
		sql.append("   and isvalid.code = '1'               ");
		return iniSqllist(sql);
	}
	
	public String showDetail () {
		try {
			this.setBean((PeEmailHistory) this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "emaildetail";
	}
	
	public void setEntityClass() {
		this.entityClass = PeEmailHistory.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/email4PeManagerSearch";
	}
	
	public void setBean(PeEmailHistory peEmailHistory) {
		this.superSetBean(peEmailHistory);
	}
	
	public PeEmailHistory getBean() {
		return (PeEmailHistory) this.superGetBean();
	}
}
