package com.whaty.platform.entity.web.action.information.sms;

import com.whaty.platform.entity.bean.PeNoteHistory;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.util.Const;
/**
 * 学员短信查询
 * 
 * @author hangaosheng 2011-07-19
 *
 */
public class Msg4PeTraineeSearchAction extends Msg4PeManagerSearchAction {

	private static final long serialVersionUID = -2035693135600340771L;

	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学员短信查询"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("发送时间"), "send_time",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("发送人"), "sender",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("性别"), "Combobox_enumConstByGender.gender",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "", true, 4);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 20);
		this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", true, 50, null);
		this.getGridConfig().addColumn(this.getText("学历"), "education", true, true, true, "", true, 4);
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng", true, true, true, "", true, 4);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true,true, true, "TextField", true, 50, Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("培训项目"), "Combobox_PeProApplyno.applynoname",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("参训状态"), "Combobox_enumConstByFkStatusTraining.statusname", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("学科"), "Combobox_PeSubject.subject", true, true, true, "TextField", true, 255);
		this.getGridConfig().addColumn(this.getText("省份"), "Combobox_PeProvince.province", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("是否结业"), "Combobox_enumConstByFkGraduted.gradute", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("是否通过审核"), "Combobox_enumConstByFkCheckedTrainee.checedtee", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("培训单位"), "Combobox_PeUnit.unit");
		this.getGridConfig().addRenderFunction(this.getText("短信详细"), 
				"<a onmouseover='showinfobyurl(event,\""+this.getServletPath()+"_showinfos.action?bean.id=${value}\" )' onmouseout='showinfobyurl(event, 0)' href=\""+this.getServletPath()+"_showDetail.action?bean.id=${value}\"  target='_blank'>查看详情</a>","id");
		this.getGridConfig().setCapability(false, false, false);
	}

	public Page list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select note.id,								");
		sql.append("       tee.name       name,                 ");
		sql.append("       note.send_time,                      ");
		sql.append("       sender.name sender,              ");
		sql.append("       gender.name    gender,               ");
		sql.append("       tee.age        age,                  ");
		sql.append("       tee.folk       folk,                 ");
		sql.append("       tee.work_place,                      ");
		sql.append("       tee.education,                       ");
		sql.append("       tee.zhicheng,                        ");
		sql.append("       tee.telephone,                       ");
		sql.append("       tee.email,                           ");
		sql.append("       applyno.name   applynoname,          ");
		sql.append("       status.name    statusname,           ");
		sql.append("       subject.name   subject,              ");
		sql.append("       pro.name       province,             ");
		sql.append("       gradute.name   gradute,              ");
		sql.append("       checedtee.name checedtee,            ");
		sql.append("       unit.name      unit                  ");
		sql.append("  from pe_note_history note,                ");
		sql.append("       pe_trainee      tee,                 ");
		sql.append("       pe_manager      sender,              ");
		sql.append("       pe_pro_applyno  applyno,             ");
		sql.append("       pe_subject      subject,             ");
		sql.append("       enum_const      gender,              ");
		sql.append("       enum_const      status,              ");
		sql.append("       pe_province     pro,                 ");
		sql.append("       enum_const      gradute,             ");
		sql.append("       enum_const      checedtee,           ");
		sql.append("       pe_unit         unit                 ");
		sql.append(" where note.receiver_id = tee.id            ");
		sql.append("   and tee.fk_gender = gender.id            ");
		sql.append("   and note.sender_id = sender.id           ");
		sql.append("   and tee.fk_pro_applyno = applyno.id      ");
		sql.append("   and tee.fk_subject = subject.id          ");
		sql.append("   and tee.fk_status_training = status.id   ");
		sql.append("   and tee.fk_province = pro.id             ");
		sql.append("   and tee.fk_graduted = gradute.id         ");
		sql.append("   and tee.fk_checked_trainee = checedtee.id");
		sql.append("   and tee.fk_training_unit = unit.id       ");
		return iniSqllist(sql);
	}
	
	public void setEntityClass() {
		this.entityClass = PeNoteHistory.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/msg4PeTraineeSearch";
	}

}
