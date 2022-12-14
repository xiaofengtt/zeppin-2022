package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.EnumConst;

public class PeFeeViewAction extends MyBaseAction {


	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
//		dc.createCriteria("peTrainee","peTrainee");
//		dc.createCriteria("enumConstByFlagInvoiceStatus","enumConstByFlagInvoiceStatus",DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("peManager","peManager",DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("enumConstByFlagFeeCheck","enumConstByFlagFeeCheck",DetachedCriteria.LEFT_JOIN);
		
		return dc;
	}
	
	public void setBean(PeTrainee instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTrainee getBean(){
		return  (PeTrainee)super.superGetBean();
	}
	

	@Override
	public Page list() {
		Page page=null;
		StringBuffer strb=new StringBuffer(" select tree.id,tree.name,fee.feecount from pe_trainee tree,(select feed.fk_trainee_id treeid,sum(feed.fee_amount) feecount from pe_fee_detail feed,enum_const enum where 1=1 and feed.flag_fee_check=enum.id and enum.code='1' group by feed.fk_trainee_id )fee where fee.treeid=tree.id");
		this.setSqlCondition(strb);
		try {
			page=this.getGeneralService().getByPageSQL(strb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("??????????????????????????????"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("??????"),"name");
		this.getGridConfig().addColumn(this.getText("????????????"),"feecount");
		this.getGridConfig().addRenderFunction(this.getText("????????????"), "<a href=# onclick=window.open('/entity/recruit/peFeeViewDetailAction.action?bean.peTrainee.id=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("??????")+"</a>", "id");
//		this.getGridConfig().addColumn(this.getText("?????????"),"feeAmount",true,true,true,"regex:new RegExp(/^\\d+$/),regexText:'?????????????????????',");
//		this.getGridConfig().addColumn(this.getText("????????????????????????"),"inputDate",true,false,true,"Date",false,25);
//		this.getGridConfig().addColumn(this.getText("??????"),"note",true,false,true,"");
//		this.getGridConfig().addColumn(this.getText("?????????"),"peManager.name",true,false,true,"");
		
//		
//		this.getGridConfig().addColumn(this.getText("????????????"),"enumConstByTrainingType.name");
//		this.getGridConfig().addColumn(this.getText("????????????"),"creditRequire");
//		this.getGridConfig().addColumn(this.getText("???????????????????????????"),"daysLimit",true,true,true,"TextField",true,25);
//		this.getGridConfig().addColumn(this.getText("?????????"),"enumConstByVersion.name");
//		this.getGridConfig().addColumn(this.getText("????????????"),"activeDate");
//		this.getGridConfig().addColumn(this.getText("??????"),"note",true,true,true,"TextField",true,25);
//		this.getGridConfig().addRenderFunction(this.getText("??????????????????"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseOut.action?peTrainingPlanId=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("??????")+"</a>", "id");
//		StringBuilder script = new StringBuilder();
//		script.append(" {");
//		script.append(" 	 var m = grid.getSelections();");
//		script.append("    	 var ids = '';");
//		script.append("    	 if(m.length == 1){");
//		script.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
//		script.append("    	 			var ss = m[i].get('id');");
//		script.append("    	 			if(i==0)ids = ids+ ss ;");
//		script.append("    	 			else ids = ids +','+ss;");
//		script.append("    	 		}");
//		script.append("    	 }else{");
//		script.append("    	 	Ext.MessageBox.alert('??????','????????????????????????????????????'); return;");
//		script.append("    	 }");
//		script.append("        var planname = new Ext.form.TextField({fieldLabel: '????????????*',name: 'bean.note',allowBlank:false,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'????????????????????????????????????????????????????????????????????????????????????&??????'});\n ");
//		script.append("        var fids = new Ext.form.Hidden({name:'bean.id',value:ids});");
//		script.append("    	 var roomformPanel = new Ext.form.FormPanel({");
//		script.append(" 	    frame:true,");
//		script.append("         labelWidth: 100,");
//		script.append("        	defaultType: 'textfield',");
//		script.append(" 				autoScroll:true,reader: new Ext.data.JsonReader({root: 'models'},['bean.id','bean.name']),");
//		script.append("         items: [ planname ,fids]");
//		script.append("       });  ");
////		script.append("        roomformPanel.form.load({url:'"+this.getDetailAction()+"?bean.id='+ids,waitMsg:'Loading'});  ");
//		script.append("        var saveroomModelWin = new Ext.Window({");
//		script.append("        title: '????????????',");
//		script.append("        width: 450,");
//		script.append("        height: 225,");
//		script.append("        minWidth: 300,");
//		script.append("        minHeight: 250,");
//		script.append("        layout: 'fit',");
//		script.append("        plain:true,");
//		script.append("        bodyStyle:'padding:5px;',");
//		script.append("        buttonAlign:'center',");
//		script.append("        items: roomformPanel,buttons: [{");
//		script.append(" 	            text: '??????',");
//		script.append(" 	            handler: function() {");
//		script.append(" 	                if (roomformPanel.form.isValid()) {");
//		script.append(" 		 		        roomformPanel.form.submit({");
//		script.append(" 		 		        	url:'/entity/recruit/peFeeManageAction_refuse.action?' ,");
//		script.append(" 				            waitMsg:'?????????????????????...',");
//		script.append(" 							success: function(form, action) {");
//		script.append(" 							    var responseArray = action.result;");
//		script.append(" 							    if(responseArray.success=='true'){");
//		script.append(" 							    	Ext.MessageBox.alert('??????', responseArray.info);");
//		script.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
//		script.append(" 								    saveroomModelWin.close();");
//		script.append(" 							    } else {");
//		script.append(" 							    	Ext.MessageBox.alert('??????', responseArray.info );");
//		script.append(" 							    }");
//		script.append(" 							}");
//		script.append(" 				        });");
//		script.append(" 	                } else{");
//		script.append(" 						Ext.MessageBox.alert('??????', '??????????????????????????????????????????');");
//		script.append(" 					}");
//		script.append(" 		        }");
//		script.append(" 	        },{");
//		script.append(" 	            text: '??????',");
//		script.append(" 	            handler: function(){saveroomModelWin.close();}");
//		script.append(" 	        }]");
//		script.append("        });");
//		script.append("        saveroomModelWin.show();");
//		script.append("  }");
//
//		this.getGridConfig().addMenuFunction(this.getText("????????????"), "/entity/recruit/peFeeManageAction_pass.action", false, true);
//		this.getGridConfig().addMenuFunction(this.getText("????????????"), "/entity/recruit/peFeeManageAction_back.action", false, true);
//		this.getGridConfig().addMenuScript(this.getText("????????????"), script.toString());
//		this.getGridConfig().setPrepared(false);
		
//		this.getGridConfig().addColumn(this.getText("????????????"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("??????"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("??????"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peFeeViewAction";
	}
	


}
