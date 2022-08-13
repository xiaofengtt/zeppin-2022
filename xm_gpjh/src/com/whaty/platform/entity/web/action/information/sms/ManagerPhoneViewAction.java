package com.whaty.platform.entity.web.action.information.sms;

import java.util.Map;

import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.admin.PeManagerAction;

public class ManagerPhoneViewAction extends PeManagerAction{

	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("总站管理员管理"));
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, "", false, 25);
//		this.getGridConfig().addColumn(this.getText("权限组"), "pePriRole.name", false, true, false, "", false, 25);
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
		c_name.setAdd(true); c_name.setSearch(false); c_name.setList(false);
		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='3' and e.namespace='FlagRoleType' order by t.name");
		this.getGridConfig().addColumn(c_name);
		
		this.getGridConfig().addColumn(this.getText("权限组"), "ssoUser.pePriRole.name");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard");

		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone");
		this.getGridConfig().addColumn(this.getText("邮箱"), "email");
		this.getGridConfig().addColumn(this.getText("通信地址及邮编"), "address");
		this.getGridConfig().addColumn(this.getText("毕业院校、专业"), "graduationInfo");
		this.getGridConfig().addColumn(this.getText("毕业时间"), "graduationDate");
		this.getGridConfig().addColumn(this.getText("职称"), "zhiCheng");
		this.getGridConfig().addColumn(this.getText("从事成人教育时间"), "workTime");
		this.getGridConfig().addMenuScript("选择", 
				" {var m = grid.getSelections();    " + 
				"  var mobiles = '';				"+
				" if(m.length > 0 ) 			   " +
				" 	{Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', '" + this.getText("test.sureTodo") + "', " +
				" 		function(btn)     " +
				" 		 {if(btn == 'yes')   " +
				" 			{                  " +
				" 				for(var i = 0, len = m.length; i < len; i++)     " +
				" 				{                   " +
				" 					var ss =  m[i].get('mobilePhone');         " +
				" 					if(i==0)mobiles = mobiles + ss ;       " +
				" 					else mobiles = mobiles + ',' + ss;     " +
				" 				}                         " +
				" 				mobiles=mobiles+',';    " +
				" 				var openerObj = opener.sms.mobilePhone;   openerObj.value += mobiles;  " +
				" 			}        " +
				"		Ext.MessageBox.alert('" + this.getText("test.info") + "', '" + this.getText("操作成功!") + "');  "  +
				" 		});     " +
				" 	 }          " +
				"  else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("请至少选择一个管理员!") + "');  "  +                                              
				"	}}"
				);
		
	}
	public void setServletPath() {
		this.servletPath = "/entity/information/managerPhoneView";
	}
}
