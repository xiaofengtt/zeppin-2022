package com.whaty.platform.entity.web.action.information.sms;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeSmsStatisticAction extends MyBaseAction<PeSmsInfo>{

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("短信统计"));
//		this.getGridConfig().addColumn("rownum", "rownum", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("发送站点"), "site_name");
		this.getGridConfig().addColumn(this.getText("创建时间"), "creat_date",false, false, true, "");
		this.getGridConfig().addColumn(this.getText("发送时间"), "send_date",false, false, true, "");
		this.getGridConfig().addColumn(this.getText("短信内容"), "note");
		this.getGridConfig().addColumn(this.getText("短信类别"), "sms_type");
		this.getGridConfig().addColumn(this.getText("短信总条数"), "send_count");
	}

	public Page list() {
		String sql ="select * from (select peSmsInfo.Id id, nvl(max(peSite.Name), '总站') site_name,  max(peSmsInfo.Creat_Date) creat_date," +
				" max(peSmsInfo.Send_Date) send_date,  max(peSmsInfo.Note) note,  max(enumConstByFlagSmsType.Name) sms_type," +
				" nvl(count(prSmsSendStatus.id), 0) send_count from pe_sms_info peSmsInfo, pr_sms_send_status prSmsSendStatus," +
				"enum_const enumConstByFlagSmsType,pe_site peSite where peSmsInfo.id = prSmsSendStatus.Fk_Sms_Info_Id" +
				" and peSmsInfo.Flag_Sms_Type = enumConstByFlagSmsType.Id   and peSite.id(+) = peSmsInfo.Fk_Site_Id group by peSmsInfo.id) where 1=1 ";
		StringBuffer sql_sb = new StringBuffer(sql);
		this.setSqlCondition(sql_sb);
		Page page = null;
		try {
			page = this.getGeneralService().getByPageSQL(sql_sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeSmsInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		servletPath = "entity/information/peSmsStatistic";
	}

	

}
