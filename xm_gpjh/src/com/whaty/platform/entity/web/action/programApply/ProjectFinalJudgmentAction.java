package com.whaty.platform.entity.web.action.programApply;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PrProExpert;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class ProjectFinalJudgmentAction extends MyBaseAction {

private String score;//专家评分
	
	private String opinion;//专家意见
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("项目终审"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("承办单位"), "peProApply.peUnit.name");
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApply.peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peProApply.peSubject.name");
		this.getGridConfig().addColumn(this.getText("申报时间"), "peProApply.declareDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("项目初审结果"), "peProApply.enumConstByFkCheckFirst.name");
//		this.getGridConfig().addColumn(this.getText("项目执行办审核结果"), "enumConstByFkCheckNational.name");
		this.getGridConfig().addColumn(this.getText("sdf"), "peProApply.declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "peProApply.declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "peProApply.scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "peProApply.scheme");
//		this.getGridConfig().addRenderFunction(this.getText("查看初审意见"), "<a href='${value}' target='_blank'>查看</a>", "peProApply.scheme");
		this.getGridConfig().addColumn(this.getText("终审评分"), "resultFinal");
		this.getGridConfig().addColumn(this.getText("终审意见"), "noteFinla");
		
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("    	 	ids = m[0].get('id');");
		script1.append("    	 }else if(m.length == 0){");
		script1.append("    	 	Ext.MessageBox.alert('错误','请选择一个项目'); return;");
		script1.append("    	 }else {");
		script1.append("    	 	Ext.MessageBox.alert('错误','一次只能评审一个项目'); return;");
		script1.append("    	 }");
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("        labelWidth: 50,");
		script1.append("        defaultType: 'textfield',");
		script1.append(" 		autoScroll:true,");
		script1.append("        items: [ {   ");
		script1.append("        fieldLabel:'评分*',  ");//文本框标题   
        script1.append("        xtype:'textfield',");//表单文本框   
        script1.append("        name:'score',   ");
        script1.append("        id:'score',   ");
        script1.append("         width:250   ,");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append(Const.credit_for_extjs);
        script1.append("        blankText:'不能为空，请填写' ");
		script1.append("       },{ fieldLabel:'意见*',  ");//文本框标题   
        script1.append("        xtype:'textarea',");//表单文本框   
        script1.append("        name:'opinion',   ");
        script1.append("        id:'opinion',   ");
        script1.append("         width:250 ,height: 80 , ");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
        script1.append("       } ,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '评审所选专家的项目',");
		script1.append("        width: 400,");
		script1.append("        height: 235,");
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
		script1.append(" 		 		        	url:'/entity/programApply/projectFinalJudgment_distributeProject.action?' ,");
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
		this.getGridConfig().addMenuScript(this.getText("项目终审"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}

	@Override
	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/projectFinalJudgment";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria dc = DetachedCriteria.forClass(PrProExpert.class);
		dc.createCriteria("peValuaExpert", "peValuaExpert",DetachedCriteria.LEFT_JOIN)
			.createAlias("ssoUser", "ssoUser")
			.add(Restrictions.eq("ssoUser", ssoUser));
		dc.createCriteria("peProApply", "peProApply",DetachedCriteria.LEFT_JOIN)
			.createAlias("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN)
			.createAlias("peProApplyno", "peProApplyno",DetachedCriteria.LEFT_JOIN)
			.createAlias("peSubject", "peSubject",DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFkCheckFirst", "enumConstByFkCheckFirst", DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.eq("enumConstByFkCheckFirst.code", "1011"))
			;//.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
		return dc;
	}
	
	public String distributeProject(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "修改失败");
		if(this.getScore()==null||this.getScore().length()==0){
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		PrProExpert prProExpert = null;
		try {
			prProExpert = (PrProExpert) this.getGeneralService().getById(PrProExpert.class, this.getIds());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(!prProExpert.getPeProApply().getEnumConstByFkCheckFinal().getCode().equals("1000")){
			map.put("success", "false");
			map.put("info", "修改失败,该项目已经终审完毕！");
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		prProExpert.setResultFinal(Double.parseDouble(this.getScore()));
		prProExpert.setNoteFinla(this.getOpinion());
		try {
			this.getGeneralService().save(prProExpert);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Map maps=new HashMap();
//		maps.put("ids", this.getIds());
//		maps.put("score", Double.parseDouble(this.getScore()));
//		maps.put("opinion", this.getOpinion());
//		int count=0;
//		try {
//			String sql="update pr_pro_expert t set t.result_final=:score , t.note_finla = :opinion where t.id=:ids";
//			this.getGeneralService().executeBySQL(sql, maps);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		map.put("info", "项目评审成功！");
		map.put("success","true");
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}
