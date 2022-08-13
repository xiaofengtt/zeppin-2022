package com.whaty.platform.entity.web.action.implementation;

import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.exception.EntityException;

/**
 * 开班计划
 * @author 侯学龙
 *
 */
public class TrainingCoursePlanAction extends MaterialSubmitAction {

	private static final long serialVersionUID = 1L;

	private String id_;
	@Override
	public void initGrid() {
		this.abstractInitGrid();
		this.getGridConfig().setTitle(this.getText("提交培训日程"));
//		this.getGridConfig().addColumn(this.getText("sadfas"), "peCoursePlan.id",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("查看培训日程"), "{ return '<a href=\\'/entity/implementation/coursePlanView.action?id_='+record.data['id']+'\\' target=\\'_blank\\'>查看</a>';}", "id");
//		this.getGridConfig().addMenuScript(this.getText("提交开班通知"), "{window.open('/entity/implementation/trainingCourseNotice_uploadNotice.action')}");
		this.getGridConfig().addMenuScript(this.getText("提交培训日程"), 
				"{var m = grid.getSelections();  "+
					"if(m.length ==1){	         "+
//					"Ext.MessageBox.confirm(\"请确认\",\"是否真的要给指定的项目提交开班通知?\",function(button,text){ " +
//					" if(button=='yes'){  "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        "+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/implementation/trainingCoursePlan_uploadTrainingCourse.action' method='post' name='formx1' style='display:none'>" +
					"   <input name=id_ type=hidden value='\"+jsonData+\"' />"+
					"   </form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";  "+
//					" }});"+
					" } else if(m.length == 0){                    "+
					"	Ext.MessageBox.alert('" + this.getText("test.error") + "', '请先选择一个项目提交培训日程!');  "  +                    
					"} else{" +
					"	Ext.MessageBox.alert('" + this.getText("test.error") + "', '一次最多只能选择一个项目提交培训日程!');  " +
					"}}                         ");
	}
	public PeProApply loadPeProApply(){
//		this.getGeneralService().getGeneralDao().setEntityClass(PeProImplemt.class);
		PeProApply peProApply = null;
		try {
			peProApply = ((PeProImplemt)this.getGeneralService().getById(PeProImplemt.class,this.getId_())).getPeProApply();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return peProApply;
	}
	public String uploadTrainingCourse(){
		return "upload_course";
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/trainingCoursePlan";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		return super.initDetachedCriteria();
	}

	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}

}
