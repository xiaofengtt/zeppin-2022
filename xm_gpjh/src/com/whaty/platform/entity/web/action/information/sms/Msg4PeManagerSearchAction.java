package com.whaty.platform.entity.web.action.information.sms;

import com.whaty.platform.entity.bean.PeNoteHistory;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 联系人短信查询
 * 
 * @author hangaosheng 2011-07-19
 *
 */
public class Msg4PeManagerSearchAction extends MyBaseAction {
	private String ajaxResult;
	private static final long serialVersionUID = -2035693135600340771L;

	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("联系人短信查询"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",true,20);
		this.getGridConfig().addColumn(this.getText("登录名"), "login_id");
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
		this.getGridConfig().addRenderFunction(this.getText("短信详细"), 
				"<a onmouseover='showinfobyurl(event,\""+this.getServletPath()+"_showinfos.action?bean.id=${value}\" )' onmouseout='showinfobyurl(event, 0)' href=\""+this.getServletPath()+"_showDetail.action?bean.id=${value}\"  target='_blank'>查看详情</a>","id");
		this.getGridConfig().setCapability(false, false, false);
	}

	public Page list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select note.id,							");
		sql.append("       man.name         name,           ");
		sql.append("       man.login_id,                    ");
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
		sql.append("  from pe_note_history note,            ");
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
	
	public String showinfos () {
		try {
			this.setBean((PeNoteHistory) this.getGeneralService().getById(this.getBean().getId()));
			this.setAjaxResult(this.getBean().getContent());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setAjaxResult("查询失败");
		}
		return "ajax";
	}
	
	public String showDetail () {
		try {
			this.setBean((PeNoteHistory) this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "smsdetail";
	}
	
	public void setEntityClass() {
		this.entityClass = PeNoteHistory.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/msg4PeManagerSearch";
	}

	public void setBean(PeNoteHistory peNoteHistory) {
		this.superSetBean(peNoteHistory);
	}
	
	public PeNoteHistory getBean() {
		return (PeNoteHistory) this.superGetBean();
	}

	public String getAjaxResult() {
		return ajaxResult;
	}

	public void setAjaxResult(String ajaxResult) {
		this.ajaxResult = ajaxResult;
	}
}
