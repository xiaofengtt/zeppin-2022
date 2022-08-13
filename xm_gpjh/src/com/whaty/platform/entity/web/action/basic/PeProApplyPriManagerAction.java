package com.whaty.platform.entity.web.action.basic;

import com.whaty.platform.entity.web.action.programApply.DistributeExpertAction;

/**
 * 项目权限分配
 * 
 * @author 韩高升
 *
 */
@SuppressWarnings("unchecked")
public class PeProApplyPriManagerAction extends DistributeExpertAction {

	private static final long serialVersionUID = 1L;
	
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("项目分配管理员"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("承办单位"), "peUnit.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peSubject.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申报时间"), "declareDate",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("省厅师范处审核结果"), "enumConstByFkCheckResultProvince.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("项目执行办审核结果"), "enumConstByFkCheckNational.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("sdf"), "declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
		this.getGridConfig().addRenderFunction(this.getText("查看已分配管理员"), "<a href='/entity/basic/prSsoProAction.action?applyIds=${value}&method=get' target='_self'>查看</a>", "id");
			this.getGridConfig().addMenuScript(this.getText("指定项目管理员"), 
					"{var m = grid.getSelections();  "+
						"if(m.length > 0){	         "+
						"Ext.MessageBox.confirm(\"请确认\",\"是否真的要给指定的项目分配项目管理员?\",function(button,text){ " +
						" if(button=='yes'){  "+
						"	var jsonData = '';       "+
						"	for(var i = 0, len = m.length; i < len; i++){"+
						"		var ss =  m[i].get('id');"+
						"		if(i==0)	jsonData = jsonData + ss ;"+
						"		else	jsonData = jsonData + ',' + ss;"+
						"	}                        "+
						"	document.getElementById('user-defined-content').style.display='none'; "+
						"	document.getElementById('user-defined-content').innerHTML=\"<form target='_self' action='/entity/basic/prSsoProAction.action' method='post' name='formx1' style='display:none'>" +
						"   <input name=applyIds type=hidden value='\"+jsonData+\"' />"+
						"   <input name=method type=hidden value='set' /></form>\";"+
						"	document.formx1.submit();"+
						"	document.getElementById('user-defined-content').innerHTML=\"\";  "+
						" }});"+
						"} else {                    "+
						"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
			"}}                         ");
	}
	
	public void setServletPath() {
		this.servletPath = "/entity/basic/peProApplyPriManager";
	}

}
