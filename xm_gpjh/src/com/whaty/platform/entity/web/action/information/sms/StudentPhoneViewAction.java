package com.whaty.platform.entity.web.action.information.sms;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

public class StudentPhoneViewAction extends PeStudentInfoAction{

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生信息列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("手机号"), "prStudentInfo.mobilephone");
		this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagStudentStatus.name");
		this.getGridConfig().addColumn(this.getText(""), "enumConstByFlagStudentStatus.code",false,false,false,"");
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
				" 					var ss =  m[i].get('prStudentInfo.mobilephone');         " +
				" 					if(i==0)mobiles = mobiles + ss ;       " +
				" 					else mobiles = mobiles + ',' + ss;     " +
				" 				}                         " +
				" 				mobiles=mobiles+',';    " +
				" 				 var openerObj = opener.sms.mobilePhone;  openerObj.value += mobiles; " +
				"		Ext.MessageBox.alert('" + this.getText("test.info") + "', '" + this.getText("操作成功!") + "');  "  +
				" 			}        " +
				" 		});     " +
				" 	 }          " +
				"  else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("请至少选择一个学员!") + "');  "  +                                              
				"	}}"
				);
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/studentPhoneView";
	}


}
