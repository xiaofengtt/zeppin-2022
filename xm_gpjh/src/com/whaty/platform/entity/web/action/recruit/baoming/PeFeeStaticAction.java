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

public class PeFeeStaticAction extends MyBaseAction {


	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		
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
		StringBuffer strb=new StringBuffer("select '1' as id,'总额' as name1,sum(fee.fee_amount) amount from pe_fee_detail fee join enum_const c on fee.flag_fee_check=c.id where c.code='1' union"
											+" select to_char(fee.input_date,'yyyy-MM') as id, to_char(fee.input_date,'yyyy-MM') as name1,sum(fee.fee_amount) amount from pe_fee_detail fee join enum_const c on fee.flag_fee_check=c.id where c.code='1' group by to_char(fee.input_date,'yyyy-MM')");
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
		this.getGridConfig().setTitle(this.getText("查看缴费统计数据"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("年月"),"name1");
		this.getGridConfig().addColumn(this.getText("费用总额"),"amount");
//		this.getGridConfig().addRenderFunction(this.getText("查看详细"), "<a href=# onclick=window.open('/entity/recruit/peFeeViewDetailAction.action?bean.peTrainee.id=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
//		this.getGridConfig().addColumn(this.getText("费用额"),"feeAmount",true,true,true,"regex:new RegExp(/^\\d+$/),regexText:'输入格式：数字',");
//		this.getGridConfig().addColumn(this.getText("费用导入平台日期"),"inputDate",true,false,true,"Date",false,25);
//		this.getGridConfig().addColumn(this.getText("备注"),"note",true,false,true,"");
//		this.getGridConfig().addColumn(this.getText("审核人"),"peManager.name",true,false,true,"");
		
//		
//		this.getGridConfig().addColumn(this.getText("培训类型"),"enumConstByTrainingType.name");
//		this.getGridConfig().addColumn(this.getText("学时要求"),"creditRequire");
//		this.getGridConfig().addColumn(this.getText("培训最长时间（天）"),"daysLimit",true,true,true,"TextField",true,25);
//		this.getGridConfig().addColumn(this.getText("版本号"),"enumConstByVersion.name");
//		this.getGridConfig().addColumn(this.getText("生效时间"),"activeDate");
//		this.getGridConfig().addColumn(this.getText("备注"),"note",true,true,true,"TextField",true,25);
//		this.getGridConfig().addRenderFunction(this.getText("添加其它课程"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseOut.action?peTrainingPlanId=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
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
//		script.append("    	 	Ext.MessageBox.alert('错误','只能选择一个申请进行驳回'); return;");
//		script.append("    	 }");
//		script.append("        var planname = new Ext.form.TextField({fieldLabel: '驳回理由*',name: 'bean.note',allowBlank:false,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
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
//		script.append("        title: '驳回申请',");
//		script.append("        width: 450,");
//		script.append("        height: 225,");
//		script.append("        minWidth: 300,");
//		script.append("        minHeight: 250,");
//		script.append("        layout: 'fit',");
//		script.append("        plain:true,");
//		script.append("        bodyStyle:'padding:5px;',");
//		script.append("        buttonAlign:'center',");
//		script.append("        items: roomformPanel,buttons: [{");
//		script.append(" 	            text: '保存',");
//		script.append(" 	            handler: function() {");
//		script.append(" 	                if (roomformPanel.form.isValid()) {");
//		script.append(" 		 		        roomformPanel.form.submit({");
//		script.append(" 		 		        	url:'/entity/recruit/peFeeManageAction_refuse.action?' ,");
//		script.append(" 				            waitMsg:'处理中，请稍候...',");
//		script.append(" 							success: function(form, action) {");
//		script.append(" 							    var responseArray = action.result;");
//		script.append(" 							    if(responseArray.success=='true'){");
//		script.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
//		script.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
//		script.append(" 								    saveroomModelWin.close();");
//		script.append(" 							    } else {");
//		script.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
//		script.append(" 							    }");
//		script.append(" 							}");
//		script.append(" 				        });");
//		script.append(" 	                } else{");
//		script.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
//		script.append(" 					}");
//		script.append(" 		        }");
//		script.append(" 	        },{");
//		script.append(" 	            text: '取消',");
//		script.append(" 	            handler: function(){saveroomModelWin.close();}");
//		script.append(" 	        }]");
//		script.append("        });");
//		script.append("        saveroomModelWin.show();");
//		script.append("  }");
//
//		this.getGridConfig().addMenuFunction(this.getText("费用审核"), "/entity/recruit/peFeeManageAction_pass.action", false, true);
//		this.getGridConfig().addMenuFunction(this.getText("取消通过"), "/entity/recruit/peFeeManageAction_back.action", false, true);
//		this.getGridConfig().addMenuScript(this.getText("驳回申请"), script.toString());
//		this.getGridConfig().setPrepared(false);
		
//		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peFeeStaticAction";
	}
	


}
