package com.whaty.platform.entity.web.action.information.sms;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.action.basic.PeSiteManagerAction;

public class SiteManagerPhoneViewAction extends PeSiteManagerAction{

	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false);
		this.getGridConfig().setTitle(this.getText("站点管理员管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("用户名"), "name");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("编号"), "code");
		this.getGridConfig().addColumn(this.getText("地区"), "regionName");
		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone");
		this.getGridConfig().addColumn(this.getText("所属分站点"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("身份证"), "idCard");
		this.getGridConfig().addColumn(this.getText("职称"), "zhiCheng");
		this.getGridConfig().addColumn(this.getText("登录名"), "loginId");
		this.getGridConfig().addColumn(this.getText("备注"), "note");
		
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
				" 				var openerObj = opener.sms.mobilePhone;  openerObj.value += mobiles;" +
				"		Ext.MessageBox.alert('" + this.getText("test.info") + "', '" + this.getText("操作成功!") + "');  "  +
				" 			}        " +
				" 		});     " +
				" 	 }          " +
				"  else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("请至少选择一个分站管理员!") + "');  "  +                                              
				"	}}"
				);
	}
	public void setServletPath() {
		this.servletPath = "/entity/information/siteManagerPhoneView";
	}
}
