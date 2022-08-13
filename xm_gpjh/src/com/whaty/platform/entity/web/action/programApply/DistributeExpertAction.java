package com.whaty.platform.entity.web.action.programApply;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 管理员 分配专家
 * 
 * @author 侯学龙
 *
 */
public class DistributeExpertAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("分配专家"));
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
		this.getGridConfig().addRenderFunction(this.getText("查看已分配专家"), "<a href='/entity/programApply/searchExpertForDistribute.action?applyIds=${value}&method=get' target='_self'>查看</a>", "id");
//		this.getGridConfig().addMenuFunction(this.getText("指定评审专家"),"/entity/programApply/searchExpertForDistribute.action");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/searchExpertForDistribute.action")){
			this.getGridConfig().addMenuScript(this.getText("指定评审专家"), 
					"{var m = grid.getSelections();  "+
						"if(m.length > 0){	         "+
						"Ext.MessageBox.confirm(\"请确认\",\"是否真的要给指定的项目分配专家?\",function(button,text){ " +
						" if(button=='yes'){  "+
						"	var jsonData = '';       "+
						"	for(var i = 0, len = m.length; i < len; i++){"+
						"		var ss =  m[i].get('id');"+
						"		if(i==0)	jsonData = jsonData + ss ;"+
						"		else	jsonData = jsonData + ',' + ss;"+
						"	}                        "+
						"	document.getElementById('user-defined-content').style.display='none'; "+
						"	document.getElementById('user-defined-content').innerHTML=\"<form target='_self' action='/entity/programApply/searchExpertForDistribute.action' method='post' name='formx1' style='display:none'>" +
						"   <input name=applyIds type=hidden value='\"+jsonData+\"' />"+
						"   <input name=method type=hidden value='set' /></form>\";"+
						"	document.formx1.submit();"+
						"	document.getElementById('user-defined-content').innerHTML=\"\";  "+
						" }});"+
						"} else {                    "+
						"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
			"}}                         ");
		}
	
	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/distributeExpert";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		dc.createAlias("peUnit", "peUnit");
		dc.createCriteria("peProApplyno", "peProApplyno");
		dc.createAlias("peSubject", "peSubject");
		dc.createAlias("enumConstByFkCheckResultProvince", "enumConstByFkCheckResultProvince",1);
		dc.createAlias("enumConstByFkCheckNational", "enumConstByFkCheckNational",1);
		dc.add(Restrictions.eq("enumConstByFkCheckNational.code", "1"));
//		dc.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
		return dc;
	}
}
