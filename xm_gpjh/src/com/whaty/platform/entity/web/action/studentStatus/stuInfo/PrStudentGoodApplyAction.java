package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.register.PrRecPriPayApplyAction;

/**
 * 评优管理
 * @author Administrator
 *
 */
public class PrStudentGoodApplyAction extends PrRecPriPayApplyAction {
	private SystemApplyService systemApplyService;
	@Override
	public void initGrid() {
//		this.getGridConfig().addMenuScript(this.getText("通过申请"),
//				"{	var m = grid.getSelections();	if(m.length > 0)	{		Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', 			'" + this.getText("test.sureTodo") + "',	    	function(btn) {			if(btn == 'yes')			{		for(var i = 0, len = m.length; i < len; i++){var status = m[i].get('enumConstByFlagFeeCheck.name');  if(status != '已上报') {Ext.MessageBox.alert('错误', '只能操作已上报状态的交费批次');  return;}}			var jsonData = '';				for(var i = 0, len = m.length; i < len; i++){        							var ss =  m[i].get('id');					if(i==0)				        	jsonData = jsonData + ss ;				   	else						jsonData = jsonData + ',' + ss;					        }				        jsonData=jsonData+',';					Ext.Ajax.request({						url:'/entity/fee/auditFeeBatch_abstractUpdateColumn.action?column=flagFeeCheck&value=2',						params:{ids:jsonData},					        method:'post',					        waitMsg:'" + this.getText("test.inProcessing") + "',							success: function(response, options) {							    var responseArray = Ext.util.JSON.decode(response.responseText);  							    if(responseArray.success=='true'){							    	Ext.MessageBox.alert('提示', '操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    } else {							    	Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    }						    	store.load({params:{start:g_start,limit:g_limit}});							}					        });					 }			    } 			);		    }   else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} 	}");
//		this.getGridConfig().addMenuScript(this.getText("不通过申请"),
//				"{	var m = grid.getSelections();	if(m.length > 0)	{		Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', 			'" + this.getText("test.sureTodo") + "',	    	function(btn) {			if(btn == 'yes')			{		for(var i = 0, len = m.length; i < len; i++){var status = m[i].get('enumConstByFlagFeeCheck.name');  if(status != '已上报') {Ext.MessageBox.alert('错误', '只能操作已上报状态的交费批次');  return;}}			var jsonData = '';				for(var i = 0, len = m.length; i < len; i++){        							var ss =  m[i].get('id');					if(i==0)				        	jsonData = jsonData + ss ;				   	else						jsonData = jsonData + ',' + ss;					        }				        jsonData=jsonData+',';					Ext.Ajax.request({						url:'/entity/fee/auditFeeBatch_abstractUpdateColumn.action?column=flagFeeCheck&value=2',						params:{ids:jsonData},					        method:'post',					        waitMsg:'" + this.getText("test.inProcessing") + "',							success: function(response, options) {							    var responseArray = Ext.util.JSON.decode(response.responseText);  							    if(responseArray.success=='true'){							    	Ext.MessageBox.alert('提示', '操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    } else {							    	Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    }						    	store.load({params:{start:g_start,limit:g_limit}});							}					        });					 }			    } 			);		    }   else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} 	}");
		
		this.getGridConfig().addMenuFunction("申请通过", "CheckForPass","");
		this.getGridConfig().addMenuFunction("取消申请通过", "CancelForPass", "");
		this.getGridConfig().addMenuFunction("申请不通过", "CheckForNoPass", "");
		this.getGridConfig().addMenuFunction("取消申请不通过", "CancelForNoPass", "");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生评优申请列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "peStudent.prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
//		this.getGridConfig().addColumn(this.getText("申请类型"), "enumConstByApplyType.name");	
		ColumnConfig column1 = new ColumnConfig(this.getText("申请类型"), "enumConstByApplyType.name");
		column1.setComboSQL(" select t.id,t.name from enum_const t where t.namespace='ApplyType' and t.code in('1','2')    ");
		this.getGridConfig().addColumn(column1);
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagApplyStatus.name");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"), "checkDate");
//		this.getGridConfig().addRenderFunction(this.getText("查看申请说明"),
//				"<a href=\"prStudentGoodApply_viewNote.action?bean.id=${value}\" target=\"_blank\">申请说明</a>",
//				"id");
	}
	
	public String viewNote() {
		try {
			this.setBean((SystemApply)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "viewNote";
	}
	
	public Map updateColumn() {
		
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {
			
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				if (this.getColumn().equals("CheckForPass")) {
					count = this.getSystemApplyService().updateExcellentPass(idList);
				} else if (this.getColumn().equals("CancelForPass")) {
					count = this.getSystemApplyService().updateExcellentCancelPass(idList);
				} else if (this.getColumn().equals("CheckForNoPass")) {
					count = this.getSystemApplyService().updateExcellentNoPass(idList);
				} else if (this.getColumn().equals("CancelForNoPass")) {
					count = this.getSystemApplyService().updateExcellentCancelNoPass(idList);
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", this.getText(String.valueOf(count)+"条记录操作成功"));

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/prStudentGoodApply";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		String[] code = new String[]{"1","2"};
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
		  .createAlias("enumConstByApplyType", "enumConstByApplyType")
		  .add(Restrictions.and(Restrictions.eq("enumConstByApplyType.namespace", "ApplyType"), Restrictions.in("enumConstByApplyType.code", code)))
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")//TODO 学生状态
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "4"))
			;
		return dc;
	}

	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}
	

}
