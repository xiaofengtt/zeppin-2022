package com.whaty.platform.entity.web.action.fee.pay;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.PrFeeDetailService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.util.Const;

public class PrFeeDetailForRecieptAction extends PrFeeDetailPiciAction {

	private static final long serialVersionUID = 6411949690732645485L;
	private PrFeeDetailService prFeeDetailService;

	@Override
	public void initGrid() {
		//this.getGridConfig().addMenuScript(this.getText("通过"), "{	var m = grid.getSelections();	if(m.length > 0)	{		Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', 			'" + this.getText("test.sureTodo") + "',	    	function(btn) {			if(btn == 'yes')			{		for(var i = 0, len = m.length; i < len; i++){var status = m[i].get('enumConstByFlagFeeCheck.name');  if(status != '已上报') {Ext.MessageBox.alert('错误', '只能操作已上报状态的交费批次');  return;}}			var jsonData = '';				for(var i = 0, len = m.length; i < len; i++){        							var ss =  m[i].get('id');					if(i==0)				        	jsonData = jsonData + ss ;				   	else						jsonData = jsonData + ',' + ss;					        }				        jsonData=jsonData+',';					Ext.Ajax.request({						url:'/entity/fee/auditFeeBatch_abstractUpdateColumn.action?column=flagFeeCheck&value=2',						params:{ids:jsonData},					        method:'post',					        waitMsg:'" + this.getText("test.inProcessing") + "',							success: function(response, options) {							    var responseArray = Ext.util.JSON.decode(response.responseText);  							    if(responseArray.success=='true'){							    	Ext.MessageBox.alert('提示', '操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    } else {							    	Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    }						    	store.load({params:{start:g_start,limit:g_limit}});							}					        });					 }			    } 			);		    }   else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} 	}");
		//this.getGridConfig().addMenuScript(this.getText("驳回"), "{	var m = grid.getSelections();	if(m.length > 0)	{		Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', 			'" + this.getText("test.sureTodo") + "',	    	function(btn) {			if(btn == 'yes')			{		for(var i = 0, len = m.length; i < len; i++){var status = m[i].get('enumConstByFlagFeeCheck.name');  if(status != '已上报') {Ext.MessageBox.alert('错误', '只能操作已上报状态的交费批次');  return;}}			var jsonData = '';				for(var i = 0, len = m.length; i < len; i++){        							var ss =  m[i].get('id');					if(i==0)				        	jsonData = jsonData + ss ;				   	else						jsonData = jsonData + ',' + ss;					        }				        jsonData=jsonData+',';					Ext.Ajax.request({						url:'/entity/fee/auditFeeBatch_abstractUpdateColumn.action?column=flagFeeCheck&value=3',						params:{ids:jsonData},					        method:'post',					        waitMsg:'" + this.getText("test.inProcessing") + "',							success: function(response, options) {							    var responseArray = Ext.util.JSON.decode(response.responseText);  							    if(responseArray.success=='true'){							    	Ext.MessageBox.alert('提示', '操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    } else {							    	Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');							    }						    	store.load({params:{start:g_start,limit:g_limit}});							}					        });					 }			    } 			);		    }   else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} 	}");
		if(this.getMsg()==null){
			if(this.getGridConfig().checkBeforeAddMenu("/entity/fee/peFeeDetailForReciept_batch.action")){
			this.getGridConfig().addMenuScript(this.getText("批量导入发票(收据)号"), "{window.open('/entity/fee/peFeeDetailForReciept_batch.action');}");
			}
			this.getGridConfig().addMenuFunction("导入发票号", 1);
			this.getGridConfig().setCapability(false, false, true,true);
		}else{
			this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
			this.getGridConfig().setCapability(false, false, false,true);
			
		}
		this.getGridConfig().setTitle(this.getText("学生交费明细"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("明细ID（勿改）"), "id", false,false,false,"");//导入的时候用于上传
		this.getGridConfig().addColumn(this.getText("入学准考证号"),"peStudent.peRecStudent.examCardNum",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName",true, false, true, "");
//		this.getGridConfig().addColumn(this.getText("prFeeDetail.enumConstByFlagFeeType.name"), "enumConstByFlagFeeType.name",true, false, true, "");
		ColumnConfig column = new ColumnConfig(this.getText("prFeeDetail.enumConstByFlagFeeType.name"),"enumConstByFlagFeeType.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagFeeType' and code in('0','1')");
		column.setAdd(false);
		this.getGridConfig().addColumn(column);
		ColumnConfig column2 = new ColumnConfig(this.getText("费用状态"),"enumConstByFlagFeeCheck.name");
		column2.setComboSQL("select id,name from enum_const where namespace='FlagFeeCheck' and code in('1','2')");
		column2.setAdd(false);
		this.getGridConfig().addColumn(column2);
//		this.getGridConfig().addColumn(this.getText("费用状态"), "enumConstByFlagFeeCheck.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("交费日期"), "inputDate",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("交费金额"), "feeAmount",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("发票（收据）号"), "invoiceNo",true, true, true, Const.AccountingInvoice_for_extjs);
	}
	
	/**
	 * 转向条件设置页面
	 * 
	 * @return
	 */
	public String batch() {
		return "batch";
	}

	/**
	 * 转向条件设置页面
	 * 
	 * @return
	 */
	public String batchexe() {
		try {
			int i = this.getPrFeeDetailService().save_uploadInvoiceNo(this.get_upload());
			this.setMsg("共"+i+"条数据导入成功!");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		this.setTogo("back");
		return "msg";
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/peFeeDetailForReciept";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.createAlias("peFeeBatch", "peFeeBatch")
			.createAlias("enumConstByFlagFeeType", "enumConstByFlagFeeType")
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck")
			.createCriteria("peStudent", "peStudent")
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN)
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		dc.add(Restrictions.or(Restrictions.eq("enumConstByFlagFeeCheck.code", "1"), Restrictions.eq("enumConstByFlagFeeCheck.code", "2")));
		return dc;
	}

	public PrFeeDetailService getPrFeeDetailService() {
		return prFeeDetailService;
	}

	public void setPrFeeDetailService(PrFeeDetailService prFeeDetailService) {
		this.prFeeDetailService = prFeeDetailService;
	}

}
