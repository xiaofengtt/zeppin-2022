package com.whaty.platform.entity.web.action.implementation;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.exception.EntityException;


/**
 * 承办单位提交开班通知
 * 
 * @author 侯学龙
 *
 */
public class TrainingCourseNoticeAction extends MaterialSubmitAction {

	
	private static final long serialVersionUID = 1L;

	private String applyIds;
	
	private String id_;
	
	private PeProImplemt peProImplemt;
	
	@Override
	public void initGrid() {
		this.abstractInitGrid();
		this.getGridConfig().setTitle(this.getText("提交开班通知"));
		this.getGridConfig().addColumn(this.getText("sadfas"), "noticeName",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("ewqr"), "noticeContent",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("开班通知"), "{if (${value}==null||${value}=='') return '未提交'; else return ${value};}", "noticeName");
		this.getGridConfig().addRenderScript(this.getText("查看通知内容"), "{if (${value}==null||${value}=='') return '未提交'; else return '<a href=\\'/entity/implementation/trainingCourseNotice_viewDetail.action?id_='+record.data['id']+'\\' target=\\'_blank\\'>查看</a>';}", "noticeName");
//		this.getGridConfig().addMenuScript(this.getText("提交开班通知"), "{window.open('/entity/implementation/trainingCourseNotice_uploadNotice.action')}");
		this.getGridConfig().addMenuScript(this.getText("提交开班通知"), 
				"{var m = grid.getSelections();  "+
					"if(m.length == 1){	         "+
//					"Ext.MessageBox.confirm(\"请确认\",\"是否真的要给指定的项目提交开班通知?\",function(button,text){ " +
//					" if(button=='yes'){  "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        "+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/implementation/trainingCourseNotice_uploadNotice.action' method='post' name='formx1' style='display:none'>" +
					"   <input name=applyIds type=hidden value='\"+jsonData+\"' />"+
					"   </form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";  "+
//					" }});"+
					" } else if(m.length == 0){                    "+
					"	Ext.MessageBox.alert('" + this.getText("test.error") + "', '请先选择一个项目!');  "  +                    
					"} else{" +
					"	Ext.MessageBox.alert('" + this.getText("test.error") + "', '一次最多只能选择一个项目!');  " +
					"}}                         ");
	
	}
	public String uploadNotice(){
		ActionContext.getContext().getSession().put("applyIds", this.getApplyIds());
		return "showFCKeditor";
	}
	public String uploadNoticeExe(){
		this.setTogo("back");
		String applyId = (String)ActionContext.getContext().getSession().get("applyIds");
		String applys [] = applyId.split(",");
		for(int i=0;i<applys.length;i++){
			try {
				PeProImplemt imp = (PeProImplemt)this.getGeneralService().getById(PeProImplemt.class,applys[i]);
				imp.setNoticeName(this.getBean().getNoticeName());
				imp.setNoticeContent(this.getBean().getNoticeContent());
				this.getGeneralService().save(imp);
				this.setMsg("提交成功!");
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("提交失败!");
			}
		}
		return "msg";
	}
	public String viewDetail(){
		PeProImplemt peProImplemt = null;
		try {
//			 list = this.getGeneralService().getBySQL("select to_char(t.NOTICE_CONTENT) from PE_PRO_IMPLEMT t where t.id = '"+this.getId_()+"'");
			peProImplemt = (PeProImplemt)this.getGeneralService().getById(PeProImplemt.class, this.getId_());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		if(peProImplemt!=null){
			this.setPeProImplemt(peProImplemt);
		}
//		return "msg";
		return "note_detail";
	}
	
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/trainingCourseNotice";
	}
	public String getApplyIds() {
		return applyIds;
	}
	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
	}
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public PeProImplemt getPeProImplemt() {
		return peProImplemt;
	}
	public void setPeProImplemt(PeProImplemt peProImplemt) {
		this.peProImplemt = peProImplemt;
	}

}
