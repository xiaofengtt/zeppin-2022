package com.whaty.platform.entity.web.action.information.sms;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.action.teaching.basicInfo.PeTeacherManagerAction;
import com.whaty.platform.util.Const;

public class TeacherPhoneViewAction extends PeTeacherManagerAction{
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("教师列表"));
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教师姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard", true, true, true,  "TextField", false, 25);
		this.getGridConfig().addColumn(this.getText("所属专业"),"peMajor.name");
		this.getGridConfig().addColumn(this.getText("最高学历"), "enumConstByFlagMaxXueli.name");
		this.getGridConfig().addColumn(this.getText("最高学位"), "enumConstByFlagMaxXuewei.name");
		this.getGridConfig().addColumn(this.getText("职称"), "enumConstByFlagZhicheng.name");
		this.getGridConfig().addColumn(this.getText("毕业院校"), "graduateSchool", true, true, false,  "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("毕业专业"), "graduateMajor", true, true, false,  "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("单位电话"), "phoneOffice",  false, true, true, Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilephone", false, true, true, Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false, true, true, Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("是否带论文"), "enumConstByFlagPaper.name");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagActive.name");
		//this.getGridConfig().addColumn(this.getText("简介"), "note", true, true, false, "TextArea", true, 500);
		//this.getGridConfig().addRenderFunction(this.getText("辅导课程"), "<a href=\"/entity/teaching/teacherCourse.action?bean.peTeacher.id=${value}\" target=\"_self\">辅导课程管理</a>", "id");
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
				" 					var ss =  m[i].get('mobilephone');         " +
				" 					if(i==0)mobiles = mobiles + ss ;       " +
				" 					else mobiles = mobiles + ',' + ss;     " +
				" 				}                         " +
				" 				mobiles=mobiles+',';    " +
				" 				var openerObj = opener.sms.mobilePhone; openerObj.value += mobiles; " +
				"		Ext.MessageBox.alert('" + this.getText("test.info") + "', '" + this.getText("操作成功!") + "');  "  +
				" 			}        " +
				" 		});     " +
				" 	 }          " +
				"  else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("请至少选择一位教师!") + "');  "  +                                              
				"	}}"
				);
	}
	public void setServletPath() {
		this.servletPath = "/entity/information/teacherPhoneView";
	}
}
