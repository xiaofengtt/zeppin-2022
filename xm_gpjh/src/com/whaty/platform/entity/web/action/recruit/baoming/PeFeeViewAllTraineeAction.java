package com.whaty.platform.entity.web.action.recruit.baoming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeFeeViewAllTraineeAction extends MyBaseAction {
	
	private double feeAmount;
	private String peTraineeId;
	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false,false, false, true, false);
		this.getGridConfig().setTitle(this.getText("查看所有学员"));
		

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学员姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, Const.userName_for_extjs);
		this.getGridConfig().addColumn(this.getText("性别"),
				"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true, true, true, Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否有效"), 
				"ssoUser.enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("学员状态"),
				"enumConstByStatus.name");
		this.getGridConfig().addColumn(this.getText("所属培训班"),
				"peTrainingClass.name");
		this.getGridConfig().addColumn(this.getText("培训级别"),
				"enumConstByTrainingType.name");
		this.getGridConfig().addColumn(this.getText("是否在职"),
				"enumConstByFlagInJob.name");
		this.getGridConfig().addColumn(this.getText("手机"), "mobile",false,true,true,"TextField", true,
				20, Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("家庭电话"), "phoneHome",false,true,true,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email",false,true,true,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("照片"), "photoLink", false);
		this.getGridConfig().addColumn(this.getText("家庭住址"), "address", false,true,true,"TextField", true, 50, null);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workPlace", false,true,true,"TextField", true, 50, null);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip", false,true,true,"TextField", true, 50, null);
//		this.getGridConfig().addColumn(this.getText("省"),
//				"peAreaByFkProvince.name");
//		this.getGridConfig()
//				.addColumn(this.getText("市"), "peAreaByFkCity.name");
//		this.getGridConfig().addColumn(this.getText("县"),
//				"peAreaByFkPrefecture.name");
//		this.getGridConfig().addRenderFunction(this.getText("管理该学员缴费记录"), "<a href='/entity/recruit/peFeeManageAction.action?bean.peTrainee.id=${value}'>"+this.getText("管理")+"</a>", "id");
		StringBuilder script = new StringBuilder();
		script.append(" {");
		script.append(" 	 var m = grid.getSelections();");
		script.append("    	 var ids = '';");
		script.append("    	 if(m.length == 1){");
		script.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script.append("    	 			var ss = m[i].get('id');");
		script.append("    	 			if(i==0)ids = ids+ ss ;");
		script.append("    	 			else ids = ids +','+ss;");
		script.append("    	 		}");
		script.append("    	 }else{");
		script.append("    	 	Ext.MessageBox.alert('错误','只能选择一个学员增加缴费记录'); return;");
		script.append("    	 }");
		script.append("        var planname = new Ext.form.TextField({fieldLabel: '缴费金额*',maxLength:6,name: 'feeAmount',allowBlank:false,anchor: '90%',regex:new RegExp(/^\\d{1,6}(\\.\\d{1,2})?$/),regexText:'输入格式：只能是数字'});\n ");
		script.append("        var fids = new Ext.form.Hidden({name:'peTraineeId',value:ids});");
		script.append("    	 var roomformPanel = new Ext.form.FormPanel({");
		script.append(" 	    frame:true,");
		script.append("         labelWidth: 100,");
		script.append("        	defaultType: 'textfield',");
		script.append(" 				autoScroll:true,reader: new Ext.data.JsonReader({root: 'models'},['bean.id','bean.name']),");
		script.append("         items: [ planname ,fids]");
		script.append("       });  ");
//		script.append("        roomformPanel.form.load({url:'"+this.getDetailAction()+"?bean.id='+ids,waitMsg:'Loading'});  ");
		script.append("        var saveroomModelWin = new Ext.Window({");
		script.append("        title: '驳回申请',");
		script.append("        width: 450,");
		script.append("        height: 225,");
		script.append("        minWidth: 300,");
		script.append("        minHeight: 250,");
		script.append("        layout: 'fit',");
		script.append("        plain:true,");
		script.append("        bodyStyle:'padding:5px;',");
		script.append("        buttonAlign:'center',");
		script.append("        items: roomformPanel,buttons: [{");
		script.append(" 	            text: '保存',");
		script.append(" 	            handler: function() {");
		script.append(" 	                if (roomformPanel.form.isValid()) {");
		script.append(" 		 		        roomformPanel.form.submit({");
		script.append(" 		 		        	url:'/entity/recruit/peFeeViewAllTraineeAction_addFeeDetail.action?' ,");
		script.append(" 				            waitMsg:'处理中，请稍候...',");
		script.append(" 							success: function(form, action) {");
		script.append(" 							    var responseArray = action.result;");
		script.append(" 							    if(responseArray.success=='true'){");
		script.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script.append(" 								    saveroomModelWin.close();");
		script.append(" 							    } else {");
		script.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
		script.append(" 							    }");
		script.append(" 							}");
		script.append(" 				        });");
		script.append(" 	                } else{");
		script.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
		script.append(" 					}");
		script.append(" 		        }");
		script.append(" 	        },{");
		script.append(" 	            text: '取消',");
		script.append(" 	            handler: function(){saveroomModelWin.close();}");
		script.append(" 	        }]");
		script.append("        });");
		script.append("        saveroomModelWin.show();");
		script.append("  }");
		this.getGridConfig().addMenuScript("增加缴费记录", script.toString());
		this.getGridConfig().setPrepared(false);

	}
	//增加缴费记录
	public String addFeeDetail(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "添加失败");
		boolean flag=true;
		PeFeeDetail fee=new PeFeeDetail();
		fee.setInputDate(new Date());
		fee.setFeeAmount(this.getFeeAmount());
		PeTrainee trainee=new PeTrainee();
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
		try {
			trainee=(PeTrainee) this.getGeneralService().getById(this.getPeTraineeId());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		if(trainee!=null){
			fee.setPeTrainee(trainee);
		}
		EnumConst enu=new EnumConst();
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagFeeCheck"));
		dc.add(Restrictions.eq("code", "0"));//初始导入费用
		List enuList=null;
		try {
			enuList=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		if(enuList!=null&&enuList.size()>0){
			enu=(EnumConst) enuList.get(0);
		}else{
			flag=false;
		}
		fee.setEnumConstByFlagFeeCheck(enu);
		this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
		try {
			fee=(PeFeeDetail) this.getGeneralService().save(fee);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "添加成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peFeeViewAllTraineeAction";
	}

	public PeTrainee getBean() {
		return (PeTrainee)super.superGetBean();
	}

	public void setBean(PeTrainee bean) {
		super.superSetBean(bean);
	}


	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByGender", "enumConstByGender",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByStatus", "enumConstByStatus",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByTrainingType", "enumConstByTrainingType",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagInJob", "enumConstByFlagInJob",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTrainingClass", "peTrainingClass",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peAreaByFkProvince", "peAreaByFkProvince",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peAreaByFkCity", "peAreaByFkCity",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peAreaByFkPrefecture", "peAreaByFkPrefecture",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ssoUser", "ssoUser").createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		return dc;
	}
	public double getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getPeTraineeId() {
		return peTraineeId;
	}
	public void setPeTraineeId(String peTraineeId) {
		this.peTraineeId = peTraineeId;
	}
	

}
