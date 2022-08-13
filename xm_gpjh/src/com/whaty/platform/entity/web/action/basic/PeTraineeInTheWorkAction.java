package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.util.Const;
import com.whaty.util.JsonUtil;

public class PeTraineeInTheWorkAction extends PeTraineeAction {
	private String unit;
	private String subject;
	private boolean filterUserInfo;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("计划学员管理"));
		String currentUserRoleTypeCodeString = getUserSession().getRoleType();
		
		if("2".equals(currentUserRoleTypeCodeString)){
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("姓名"), "name");
			this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",false,10,"");
			this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("民族"), "folk.name", true, true, true, "", false, 20);
			this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("学校所在区域"), "unitAttribute.name",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("学校类别"), "unitType.name",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("最高学历"), "education.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("职称"), "jobTitle.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("主要任教学段"), "mainTeachingGrade.name", true, true, true, "", false, 50);
			this.getGridConfig().addColumn(this.getText("主要任教学科"), "mainTeachingSubject.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("毕业院校"), "graduation", true,true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("所学专业"), "major", true,true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("手机"), "telephone", false, true, true, "TextField", true, 20);
//			this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,true, true, "TextField", true, 50, Const.email_for_extjs);
			this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name", true, true, true, "TextField", true, 255);
			this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",true,50);
			this.getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
//			this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", false, true, true, "", true, 20);
//			this.getGridConfig().addColumn(this.getText("是否结业"), "enumConstByFkGraduted.name", true, true, true, "TextField", true, 20);
			this.getGridConfig().addColumn(this.getText("参训状态"), "enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
			this.getGridConfig().addColumn(this.getText("证书编号"), "certificateNumber", true, false, true, "", true, 20);
		}else if ("3".equals(currentUserRoleTypeCodeString)){
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",false,50);
			this.getGridConfig().addColumn(this.getText("参训学段学科"), "subject", true, true, true, "TextField", false, 125);
			this.getGridConfig().addColumn(this.getText("姓名"), "name");
//			this.getGridConfig().addColumn(this.getText("身份证号"), "idcard", false,true, true, "TextField", false, 50, Const.cardId_for_extjs);
			this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",false,10,"");
			this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "", false, 4,Const.twoNumandSpace_for_extjs);
			this.getGridConfig().addColumn(this.getText("民族"), "folk.name", true, true, true, "", false, 20);
			this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("学校所在区域"), "unitAttribute.name",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("学校类别"), "unitType.name",true, true, true, "TextField", false, 50, null);
			this.getGridConfig().addColumn(this.getText("职务"), "zhiwu", true, true, true, "", true, 25);
//			this.getGridConfig().addColumn(this.getText("职称"), "jobTitle.name", true, true, true, "", false, 20);
//			this.getGridConfig().addColumn(this.getText("最高学历"), "education.name", true, true, true, "", false, 25);
			this.getGridConfig().addColumn(this.getText("教龄"), "workyear", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", false, 20);
//			this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true,true, true, "TextField", false, 50, Const.email_for_extjs);
//			this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", true, true, true, "", true, 20);
//			this.getGridConfig().addColumn(this.getText("主要任教学段"), "mainTeachingGrade.name", true, true, true, "", false, 50);
			this.getGridConfig().addColumn(this.getText("主要任教学科"), "mainTeachingSubject.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("毕业院校"), "graduation", true,true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("所学专业"), "major", true,true, true, "TextField", false, 50, null);
			this.getGridConfig().addColumn(this.getText("备注1"), "note",true, true, true, "TextField", true, 500);
			this.getGridConfig().addColumn(this.getText("备注2"), "notesecond",true, true, true, "TextField", true, 500);
			this.getGridConfig().setCanExcelExport(false);
		}else{
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("姓名"), "name");
//			this.getGridConfig().addColumn(this.getText("身份证号"), "idcard", false,true, true, "TextField", false, 50, Const.cardId_for_extjs);
			this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",false,10,"");
			this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("民族"), "folk.name", true, true, true, "", false, 20);
			this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", false, 50, null);
			//20170613 去除相关字段显示
//			this.getGridConfig().addColumn(this.getText("学校所在区域"), "unitAttribute.name",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("学校类别"), "unitType.name",true, true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("最高学历"), "education.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("职称"), "jobTitle.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("主要任教学段"), "mainTeachingGrade.name", true, true, true, "", false, 50);
			this.getGridConfig().addColumn(this.getText("主要任教学科"), "mainTeachingSubject.name", true, true, true, "", false, 50);
//			this.getGridConfig().addColumn(this.getText("毕业院校"), "graduation", true,true, true, "TextField", false, 50, null);
//			this.getGridConfig().addColumn(this.getText("所学专业"), "major", true,true, true, "TextField", false, 50, null);
			this.getGridConfig().addColumn(this.getText("职务"), "zhiwu", true, true, true, "", true, 25);
			this.getGridConfig().addColumn(this.getText("教龄"), "workyear", true, true, true, "", true, 50);
//			this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", true, 20);
//			this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true,true, true, "TextField", true, 50, Const.email_for_extjs);
			this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",true,50);
//			this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", true, true, true, "", true, 20);
			this.getGridConfig().addColumn(this.getText("参训状态"), "enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
			this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name", true, false, true, "TextField", true, 255);
			this.getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
//			this.getGridConfig().addColumn(this.getText("是否结业"), "enumConstByFkGraduted.name", true, true, true, "TextField", true, 20);
			this.getGridConfig().addColumn(this.getText("参训学段学科"), "subject", true, true, true, "TextField", true, 255);
			this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, true, true, "TextField", true, 20);
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnitByFkTrainingUnit.name");
			cc.setAdd(false);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code='1526'");
			this.getGridConfig().addColumn(cc);
			this.getGridConfig().addColumn(this.getText("填报单位"), "peUnitByFkUnitFrom.name", true, true, true, "TextField", true, 50);
			//20170613 去除相关字段显示
//			this.getGridConfig().addColumn(this.getText("证书编号"), "certificateNumber", true, false, true, "", true, 20);
//			this.getGridConfig().addColumn(this.getText("条件要求得分"), "yaoqiudefen", true, false, true, "TextField", true, 30);
//			this.getGridConfig().addColumn(this.getText("学习过程得分"), "guochengdefen", true, false, true, "TextField", true, 30);
//			this.getGridConfig().addColumn(this.getText("学习成效得分"), "chengxiaodefen", true, false, true, "TextField", true, 30);
//			this.getGridConfig().addColumn(this.getText("评价意见"), "yijian", true, false, true, "TextField", true, 100);
			this.getGridConfig().addColumn(this.getText("备注1"), "note",true, true, true, "TextField", true, 50);
			this.getGridConfig().addColumn(this.getText("备注2"), "notesecond",true, true, true, "TextField", true, 50);
		}
		
		if("4".equals(currentUserRoleTypeCodeString)||"5".equals(currentUserRoleTypeCodeString)) {
			this.getGridConfig().addMenuScript(this.getText("分配承办单位"), getBufferScript().toString());
		}
		this.getGridConfig().setPrepared(false);
	}


	/**
	 * @description 给学员分配培训单位,并设置参训学科
	 * @return 操作成功与否的信息
	 */
	public String assignUnit() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("success", "false");
		map.put("info", "分配失败");
		
		if(getUnit() == null) {
			map.put("success", "false");
			map.put("info", "分配失败,请选择承办单位");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		if(("存在不一致的培训项目...".equals(getUnit()))||("没有符合条件的学科...".equals(getSubject()))) {
			map.put("success", "false");
			map.put("info", "选择学员错误，分配失败！");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		String [] idStrings = this.getIds().split(",");
		String getUnitIdSQL = "select id from pe_unit where name=:theName";
		String getSubjectIdSQL = "select id from pe_subject where name=:theName";
		String updateTraineeFKToUnitSQL = "update pe_trainee set FK_TRAINING_UNIT=:destId,FK_SUBJECT=:subjectId where id=:theId";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theName", getUnit());
		List<String> resultList = null;
		List<String> subjectResultList = null;
		try {
			resultList = this.getGeneralService().getBySQL(getUnitIdSQL, paramsMap);
			paramsMap.clear();
			paramsMap.put("theName", getSubject());
			subjectResultList = getGeneralService().getBySQL(getSubjectIdSQL, paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String unitId = "";
		String subjectId = "";
		if(resultList != null && resultList.size() != 0) {
			unitId = resultList.get(0);
		}
		if(subjectResultList != null && subjectResultList.size() != 0) {
			subjectId = subjectResultList.get(0);
		}
		for(int i=0;i<idStrings.length;i++) {
			paramsMap.clear();
			paramsMap.put("destId", unitId);
			paramsMap.put("subjectId", subjectId);
			paramsMap.put("theId", idStrings[i]);
			try {
				this.getGeneralService().executeBySQL(updateTraineeFKToUnitSQL, paramsMap);
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		map.put("success", "true");
		map.put("info", "分配成功");
		
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByFkGender", "enumConstByFkGender",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkCheckedTrainee", "enumConstByFkCheckedTrainee",
				DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("enumConstByFkCheckedTrainee.code", "65230"));	//只显示审核通过的学员
		//20170613 去除相关字段显示
//		dc.createCriteria("enumConstByFkGraduted", "enumConstByFkGraduted",
//				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkModifyChecked", "enumConstByFkModifyChecked",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peProApplyno", "peProApplyno",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peSubject", "peSubject", DetachedCriteria.LEFT_JOIN);
//		SsoUser currentUser = getCurrentSsoUser();
		String currentUserRoleTypeCode = getUserSession().getRoleType();
//		if("3".equals(currentUserRoleTypeCode)){//省厅师范处登录时只显示本省学员
//			dc.createCriteria("peProvince", "peProvince");
//			dc.add(Restrictions.eq("peProvince",this.getCurrentUnitProvince()));
//		}else{
//			dc.createCriteria("peProvince", "peProvince",
//					DetachedCriteria.LEFT_JOIN);
//		}
		dc.createAlias("folk", "folk", DetachedCriteria.LEFT_JOIN);
		//20170613 去除相关字段显示
//		dc.createAlias("education", "education", DetachedCriteria.LEFT_JOIN);
//		dc.createAlias("jobTitle", "jobTitle", DetachedCriteria.LEFT_JOIN);
//		dc.createAlias("unitAttribute", "unitAttribute", DetachedCriteria.LEFT_JOIN);
//		dc.createAlias("mainTeachingGrade", "mainTeachingGrade", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("mainTeachingSubject", "mainTeachingSubject", DetachedCriteria.LEFT_JOIN);
		//20170613 去除相关字段显示
//		dc.createAlias("unitType", "unitType", DetachedCriteria.LEFT_JOIN);
		if("3".equals(currentUserRoleTypeCode)){//省厅师范处
			
			dc.createAlias("peProvince", "peProvince");
			dc.add(Restrictions.eq("peProvince",this.getCurrentUnitProvince()));
			dc.createAlias("county", "county",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("county.city", "city",DetachedCriteria.LEFT_JOIN);
		}else{
			dc.createAlias("county", "county",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("county.city", "city",DetachedCriteria.LEFT_JOIN);
			
			dc.createAlias("peProvince", "peProvince",
					DetachedCriteria.LEFT_JOIN);
		}
		if("2".equals(currentUserRoleTypeCode)){// 承办单位登录时只显示本单位学员
			dc.createCriteria("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit");
			dc.add(Restrictions.eq("peUnitByFkTrainingUnit", getCurrentUserBelongUnit()));
		}else{
			dc.createCriteria("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit",
					DetachedCriteria.LEFT_JOIN);
		}
		dc.createCriteria("peUnitByFkUnitFrom", "peUnitByFkUnitFrom",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkStatusTraining", "enumConstByFkStatusTraining", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	//用于分配所选学员的培训单位及学科
	private StringBuffer getBufferScript(){
		StringBuffer script1 = new StringBuffer();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length > 0){");
		script1.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script1.append("    	 			var ss = m[i].get('id');");
		script1.append("    	 			if(i==0)ids = ids+ ss ;");
		script1.append("    	 			else ids = ids +','+ss;");
		script1.append("    	 		}");
		script1.append("    	 }else{");
		script1.append("    	 	Ext.MessageBox.alert('错误','请至少选择一个学员'); return;");
		script1.append("    	 }");
		script1.append(" var siteDataStore = new Ext.data.Store({                                            ");
		script1.append("                baseParams: {peTraineeId:ids}, ");
		script1.append("				proxy: new Ext.data.HttpProxy({                                             ");
		script1.append("		            url: '/entity/first/firstPageCombo_getPeUnit.action',                     ");
		script1.append("		            method: 'POST'                    ");
//		script1.append("		            params: {peTraineeId:ids}          ");
		script1.append("		        }),                                                                     ");
		script1.append("				reader: new Ext.data.JsonReader({                                           ");
		script1.append("				            root: 'unit'                                                    ");
		script1.append("				        },                                                                  ");
		script1.append("				        [{name:'id'}, {name:'name'}]),                                      ");
		script1.append("		        remoteSort: true                                                        ");
		script1.append("		    });                                                                         ");
		script1.append("		    siteDataStore.load();                                                       ");
		script1.append("			                                                                              ");
		script1.append("			var siteCombo = new Ext.form.ComboBox({                                       ");
		script1.append("				store: siteDataStore,                                                       ");
		script1.append("				displayField:'name',                                                        ");
		script1.append("				valueField: 'id' ,                 ");
		script1.append(" 					fieldLabel: '承办单位*',                                         ");
		script1.append("				name:'unit',                                                              ");
		script1.append("				id:'unit',                                                              ");
		script1.append("				typeAhead: true,                                                            ");
		script1.append("				triggerAction: 'all',                                                       ");
		script1.append("				editable: true,                                                            ");
		script1.append("				selectOnFocus:true                                                          ");
		script1.append("			});                                                                           ");
		script1.append("			 var edutypeDataStore = new Ext.data.Store({                                  ");
		script1.append("				proxy: new Ext.data.HttpProxy({                                             ");
		script1.append("		            url: '/entity/first/firstPageCombo_getSubjectByUnit.action'         ");
		script1.append("		        }),                                                                     ");
		script1.append("				reader: new Ext.data.JsonReader({                                           ");
		script1.append("				            root: 'edutypes'                                                ");
		script1.append("				        },                                                                  ");
		script1.append("				        [{name:'id'}, {name:'name'}]),                                      ");
		script1.append("		        remoteSort: true                                                        ");
		script1.append("		    });                                                                         ");
		script1.append("		                                                                                ");
		script1.append("		    var edutypeCombo = new Ext.form.ComboBox({                                  ");
		script1.append("				store: edutypeDataStore,                                                       ");
		script1.append("				displayField:'name',                                                        ");
		script1.append("				valueField: 'id' ,                 ");
		script1.append(" 					fieldLabel: '学科*',                                         ");
		script1.append("				name:'subject',                                                              ");
		script1.append("				id:'subject',                                                              ");
		script1.append("				typeAhead: true,                                                            ");
		script1.append("				triggerAction: 'all',                                                       ");
		script1.append("				editable: true,                                                            ");
		script1.append("				selectOnFocus:true                                                          ");
		script1.append("			});                                                                           ");
		script1.append("		siteCombo.on('select', function() {                                             ");
		script1.append("				edutypeCombo.reset();                                                       ");
		script1.append("				Ext.apply(edutypeDataStore.baseParams, {                                    ");
		script1.append("			   		subjectId:siteCombo.getValue(),                                          ");
		script1.append("			   		peTraineeId:ids                                          ");
		script1.append("			  	});                                                                        ");
		script1.append("				edutypeDataStore.load();                                                 ");
		script1.append("		});                                                                           ");
		
		
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("         labelWidth: 100,");
		script1.append("        	defaultType: 'textfield',");
		script1.append(" 				autoScroll:true,");
		script1.append("         items: [siteCombo,edutypeCombo,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '分配学员培训单位',");
		script1.append("        width: 450,");
		script1.append("        height: 225,");
		script1.append("        minWidth: 300,");
		script1.append("        minHeight: 250,");
		script1.append("        layout: 'fit',");
		script1.append("        plain:true,");
		script1.append("        bodyStyle:'padding:5px;',");
		script1.append("        buttonAlign:'center',");
		script1.append("        items: formPanel,");
		script1.append("        buttons: [{");
		script1.append(" 	            text: '保存',");
		script1.append(" 	            handler: function() {");
		script1.append(" 	                if (formPanel.form.isValid()) {");
		script1.append(" 		 		        formPanel.form.submit({");
		script1.append(" 		 		        	url:'/entity/basic/peTraineeInTheWorkAction_assignUnit.action?' ,");
		script1.append(" 				            waitMsg:'处理中，请稍候...',");
		script1.append(" 							success: function(form, action) {");
		script1.append(" 							    var responseArray = action.result;");
		script1.append(" 							    if(responseArray.success=='true'){");
		script1.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script1.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script1.append(" 								    addModelWin.close();");
		script1.append(" 							    } else {");
		script1.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
		script1.append(" 							    }");
		script1.append(" 							}");
		script1.append(" 				        });");
		script1.append(" 	                } else{");
		script1.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
		script1.append(" 					}");
		script1.append(" 		        }");
		script1.append(" 	        },{");
		script1.append(" 	            text: '取消',");
		script1.append(" 	            handler: function(){addModelWin.close();}");
		script1.append(" 	        }]");
		script1.append("        });");
		script1.append("        addModelWin.show();");
		script1.append("  }");
		return script1;
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTraineeInTheWorkAction";
	}

	public PeTrainee getBean() {
		return (PeTrainee) super.superGetBean();
	}

	public void setBean(PeTrainee bean) {
		super.superSetBean(bean);
	}

	public boolean isFilterUserInfo() {
		return filterUserInfo;
	}

	public void setFilterUserInfo(boolean filterUserInfo) {
		this.filterUserInfo = filterUserInfo;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}