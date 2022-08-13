package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

public class PeStudentInfoPrintAction extends PeStudentInfoAction {

	private static final long serialVersionUID = -6417270722812503342L;
	//TODO
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuScript(this.getText("打印学籍表"),
				"{	var m = grid.getSelections();	if(m.length > 0)	{		Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', 			'" + this.getText("test.sureTodo") + "',	    	function(btn) {			if(btn == 'yes')			{		for(var i = 0, len = m.length; i < len; i++){var status = m[i].get('enumConstByFlagFeeCheck.name');  if(status != '已上报') {Ext.MessageBox.alert('错误', '只能操作已上报状态的交费批次');  return;}}			var jsonData = '';				for(var i = 0, len = m.length; i < len; i++){        							var ss =  m[i].get('id');					if(i==0)				        	jsonData = jsonData + ss ;				   	else						jsonData = jsonData + ',' + ss;					        }				        jsonData=jsonData+',';					Ext.Ajax.request({						url:'/entity/fee/auditFeeBatch_abstractUpdateColumn.action?column=flagFeeCheck&value=2',						params:{ids:jsonData},					        method:'post',					        waitMsg:'" + this.getText("test.inProcessing") + "',							success: function(response, options) {							    var responseArray = Ext.util.JSON.decode(response.responseText);  							    if(responseArray.success=='true'){							    	Ext.MessageBox.alert('提示', '操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    } else {							    	Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    }						    	store.load({params:{start:g_start,limit:g_limit}});							}					        });					 }			    } 			);		    }   else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} 	}");
		this.getGridConfig().addMenuScript(this.getText("打印学生证"),
				"{	var m = grid.getSelections();	if(m.length > 0)	{		Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', 			'" + this.getText("test.sureTodo") + "',	    	function(btn) {			if(btn == 'yes')			{		for(var i = 0, len = m.length; i < len; i++){var status = m[i].get('enumConstByFlagFeeCheck.name');  if(status != '已上报') {Ext.MessageBox.alert('错误', '只能操作已上报状态的交费批次');  return;}}			var jsonData = '';				for(var i = 0, len = m.length; i < len; i++){        							var ss =  m[i].get('id');					if(i==0)				        	jsonData = jsonData + ss ;				   	else						jsonData = jsonData + ',' + ss;					        }				        jsonData=jsonData+',';					Ext.Ajax.request({						url:'/entity/fee/auditFeeBatch_abstractUpdateColumn.action?column=flagFeeCheck&value=2',						params:{ids:jsonData},					        method:'post',					        waitMsg:'" + this.getText("test.inProcessing") + "',							success: function(response, options) {							    var responseArray = Ext.util.JSON.decode(response.responseText);  							    if(responseArray.success=='true'){							    	Ext.MessageBox.alert('提示', '操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    } else {							    	Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    }						    	store.load({params:{start:g_start,limit:g_limit}});							}					        });					 }			    } 			);		    }   else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} 	}");	
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生信息打印"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("出身日期"), "prStudentInfo.birthday");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peStudentInfoPrint";
	}
}
