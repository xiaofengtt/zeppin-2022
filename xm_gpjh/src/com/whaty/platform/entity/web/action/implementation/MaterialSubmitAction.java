package com.whaty.platform.entity.web.action.implementation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 提交其他材料-->开班时间
 * 
 * @author 侯学龙
 *
 */
public class MaterialSubmitAction extends MyBaseAction {
	
	private Date diliveryDate;
	
	//将公用部分提取出来 供子类使用
	protected void abstractInitGrid(){
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("承办单位"), "peProApply.peUnit.name");
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleType().equals("4")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peProApply.peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code ='1526' order by u.name");
			this.getGridConfig().addColumn(cc);
		}else if(us.getRoleType().equals("3")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peProApply.peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id  where  e.code ='1526'  and u.fk_province=(select unit.fk_province from pe_manager manager join pe_unit unit on manager.fk_unit=unit.id where manager.fk_sso_user_id='"+us.getSsoUser().getId()+"') order by u.name");
			this.getGridConfig().addColumn(cc);
		}else{
			this.getGridConfig().addColumn(this.getText("培训单位"), "peProApply.peUnit.name",false,false,true,"");
		}
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApply.peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peProApply.peSubject.name");
		this.getGridConfig().addColumn(this.getText("申报时间"), "peProApply.declareDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("sdf"), "peProApply.declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "peProApply.declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "peProApply.scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "peProApply.scheme");
	}
	@Override
	public void initGrid() {
		this.abstractInitGrid();
		this.getGridConfig().setTitle(this.getText("提交开班时间"));
//		this.getGridConfig().addColumn(this.getText("初审结果"), "enumConstByFkCheckFirst.name");
//		this.getGridConfig().addColumn(this.getText("终审结果"), "peProApply.enumConstByFkCheckFinal.name");
		this.getGridConfig().addColumn(this.getText("开班时间"), "startcourseDate",false,false,true,"Date");
		
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("          ids = m[0].get('id');  ");
		script1.append("          }else if(m.length > 1) { ");
		script1.append("    	 	Ext.MessageBox.alert('错误','最多只能选择一个项目'); return;");
		script1.append("    	 }else {");
		script1.append("    	 	Ext.MessageBox.alert('错误','请选择一个项目'); return;");
		script1.append("    	 }");
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("        var datefield = new Ext.form.DateField({                  ");
		script1.append("              id: 'diliveryDate',                        ");
		script1.append("              format: 'Y-m-d',                           ");
		script1.append("              maxValue: '2100-01-01',                   ");
		script1.append("              minValue: '1900-01-01',                    ");
//		script1.append("              disabledDays: [0, 6],                     ");
//		script1.append("             disabledDaysText: '禁止选择该日期',          ");
		script1.append("             fieldLabel: '选择日期*',                     ");
		script1.append("             width:180,                                 ");
		script1.append("             showToday:true ,                           ");
        script1.append("            allowBlank:false, "); //不能为空
        script1.append("            blankText:'不能为空，请填写' ");
		script1.append("           })  ;                                      ");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("        labelWidth: 70,");
		script1.append("        defaultType: 'textfield',");
		script1.append(" 		autoScroll:true,");
		script1.append("        items: [    ");
        script1.append("       datefield ,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '提交开班时间',");
		script1.append("        width: 360,");
		script1.append("        height: 165,");
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
		script1.append(" 		 		        	url:'/entity/implementation/materialSubmit_startTimeSet.action?' ,");
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
		this.getGridConfig().addMenuScript(this.getText("设置开班时间"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeProImplemt.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/materialSubmit";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		ActionContext ctx = ActionContext.getContext();
		UserSession userSession = (UserSession)ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeProImplemt.class);
		DetachedCriteria dcapply = dc.createCriteria("peProApply", "peProApply");
		dcapply.createAlias("peProApplyno", "peProApplyno");
		dcapply.createAlias("peSubject", "peSubject");
		dcapply.createAlias("peUnit", "peUnit");
//		dc.createAlias("enumConstByFkCheckFirst", "enumConstByFkCheckFirst");
		dcapply.createAlias("enumConstByFkCheckFinal", "enumConstByFkCheckFinal");
		dcapply.add(Restrictions.eq("enumConstByFkCheckFinal.code", "1001"));
//		dcapply.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			dcapply.add(Restrictions.eq("peUnit", manager.getPeUnit()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return dc;
	}
	public String startTimeSet(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "修改失败");
		if(this.getDiliveryDate()==null){
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		String ids[]=this.getIds().split(",");
		int count=0;
		try {
			for(int i=0;i<ids.length;i++){
				Map maps = new HashMap();
				String id=ids[i];
				maps.put("ids", id);
				maps.put("date", this.getDiliveryDate());
				String sql="update pe_pro_implemt t set t.startcourse_date = :date where t.id = :ids";
				int flag=this.getGeneralService().executeBySQL(sql, maps);
				count+=flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count==ids.length){
		}
		map.put("info", "修改成功");
		map.put("success","true");
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	public void setBean(PeProImplemt instance) {
		super.superSetBean(instance);

	}

	public PeProImplemt getBean() {
		return (PeProImplemt) super.superGetBean();
	}
	public Date getDiliveryDate() {
		return diliveryDate;
	}

	public void setDiliveryDate(Date diliveryDate) {
		this.diliveryDate = diliveryDate;
	}
}
