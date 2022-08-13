
package com.whaty.platform.entity.web.action.information;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeJob;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrJobUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PrJobUnitInitAction extends MyBaseAction {

	private String jobCheck;
	private PeUnit peUnit;
	private String checkNote;
	private boolean flag;
	private File upload1;//上传的资料
	private String upload1FileName;//上传资料名字
	private String reply;
//	private String id_;
	private String note;
	private String name;

	@Override
	public void initGrid() {
		
		this.getGridConfig().setTitle(this.getText(getJobBean().getName()+"-任务管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("上传时间"), "createDate",false);
		this.getGridConfig().addColumn(this.getText("所属单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("任务状态"), "enumConstByFkJobStatus.name");
		this.getGridConfig().addColumn(this.getText("审核评语"), "checkNote");
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
		this.getGridConfig().addColumn(this.getText("sdfas"), "uploadFile",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("任务资料"), "{if (${value}==null||${value}=='') return '未提交'; else return '<a href='+${value}+' target=\\'_blank\\'>下载</a>' ;}", "uploadFile");
		this.getGridConfig().addRenderFunction(this.getText("任务回复"), "<a href='/entity/information/prJobUnitInit_viewReply.action?ids=${value}' target='_blank'>查看</a>", "id");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/flagPEO_flagPEO.action")){
			this.getGridConfig().setCapability(false, true, false, true);
			this.getGridConfig().addMenuScript(this.getText("任务审核"), getManagerCheckScript().toString());
			if(getJobBean().getScopeString().indexOf("xmzxb_")>=0){
				this.getGridConfig().addMenuScript(this.getText("提交任务资料"), getPeJobUnitScript().toString());
			}
		}else{
			this.getGridConfig().setCapability(false, false, false, true);
			this.getGridConfig().addMenuScript(this.getText("提交任务资料"), getPeJobUnitScript().toString());
		}
		this.getGridConfig().setPrepared(false);
		
	}

	
	//审核任务
	public String verifyJob() {
		PrJobUnit jobunit=null;
		try{	
			jobunit = (PrJobUnit)this.getMyListService().getById(PrJobUnit.class, this.getIds());//getById(this.getId_());
		}catch(Exception e){
			e.printStackTrace();
		}
		Map map=new HashMap();
//		if(jobunit.getName()==null||"".equals(jobunit.getName())){
//			map.put("success", "false");
//			map.put("info", "不能审核未完成的任务！");
//			this.setJsonString(JsonUtil.toJSONString(map));
//			return this.json();
//		}
		
//		map.put("success", "false");
//		map.put("info", "审核失败");
//		if(this.getJobCheck()==null||this.getCheckNote()==null){
//			this.setJsonString(JsonUtil.toJSONString(map));
//			return this.json();
//		}
		Map maps=new HashMap();
		maps.put("ids", this.getIds());
		maps.put("jobCheck", this.getJobCheck());
		maps.put("checkNote", this.getCheckNote());
		int count=0;
		try {
			String sql="update pr_job_unit t set t.fk_job_status=(select ec.id from enum_const ec where ec.namespace='FkJobStatus' and ec.name=:jobCheck) , t.check_note = :checkNote where t.id=:ids";
			this.getGeneralService().executeBySQL(sql, maps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("info", "审核成功");
		map.put("success","true");
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	//操作失败返回
	public String back(){
		this.setTogo("/entity/information/prJobUnitInit.action");
		return "msg";
	}
	public PeJob getJobBean(){
		PeJob job = (PeJob)this.getMyListService().getById(PeJob.class,this.getBean().getPeJob().getId());
		return job;
	}

	//上传任务资料
	 public String uploadJob(){
		 return "upload_job_resource";
	 }
	public String uploadResource(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "任务资料上传失败!");
		
		 PrJobUnit jobUnit = new PrJobUnit();
//			this.getGeneralService().getGeneralDao().setEntityClass();
			PeJob  job = (PeJob)this.getMyListService().getById(PeJob.class,this.getIds());
			if(!checkDate(job)){
				map.put("info", "该任务不在当前提交的时间内!");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json();
			}
			String filename = this.getUpload1FileName();
			File file = new File(ServletActionContext.getRequest().getRealPath(Const.jobFilePath+"jobId"+job.getId()+"/"+getCurrentUser().getPeUnit().getCode()+"/"));
			if(!file.exists()){
				file.mkdirs();
			}
			
			String docpath = ServletActionContext.getRequest().getRealPath(Const.jobFilePath+"jobId"+job.getId()+"/"+getCurrentUser().getPeUnit().getCode()+"/") + filename;
			try {
				FileOutputStream fos = new FileOutputStream(docpath);
				FileInputStream fis = new FileInputStream(this.getUpload1());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				this.setMsg("任务资料上传成功！");
			} catch (FileNotFoundException e) {
			    this.setMsg("任务资料上传失败！");
				e.printStackTrace();
			} catch (IOException e) {
			    this.setMsg("任务资料上传失败！");
				e.printStackTrace();
			}
			if (this.getMsg().equals("任务资料上传成功！")) {
				jobUnit.setUploadFile(Const.jobFilePath+"jobId"+job.getId()+"/"+getCurrentUser().getPeUnit().getCode()+"/"+filename);
			}
				jobUnit.setPeJob(job);
				jobUnit.setPeUnit(getCurrentUser().getPeUnit());
				jobUnit.setReply(this.getReply());
				jobUnit.setCreateDate(new Date());
				jobUnit.setName(this.getName());
		try {
			this.getGeneralService().save(jobUnit);
			map.put("success", "true");
			map.put("info", "提交任务材料成功！");
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", "提交任务材料失败");
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrJobUnit.class;
	}
	private boolean checkDate(PeJob  job){
		Date now = new Date();
		if(!now.before(job.getStartDate()) && Const.compareDate(now, job.getFinishDate())){
			return true;
		}
		return false;
	}
	@Override
	public void setServletPath() {
		 this.servletPath = "/entity/information/prJobUnitInit";
	}
	 public void setBean(PrJobUnit instance) {
			super.superSetBean(instance);
	
	}
	public PrJobUnit getBean() {
		return (PrJobUnit) super.superGetBean();
	}	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc=DetachedCriteria.forClass(PrJobUnit.class);
		dc.createAlias("peUnit", "peUnit");
        dc.createAlias("peJob", "peJob");
        dc.createAlias("enumConstByFkJobStatus", "enumConstByFkJobStatus",1);
//        dc.createAlias("enumConstByFkJobCheck", "enumConstByFkJobCheck",1);
//        if(this.getBean()!=null&&this.getBean().getId()!=null){
//        	dc.add(Restrictions.eq("peJob.id", this.getBean().getId()));
//        }d
        UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
        if(!us.getRoleType().equals("4")){
    		dc.add(Restrictions.eq("peUnit", getCurrentUser().getPeUnit()));
    	}
    	return dc;
	}
	
	public String viewReply(){
//		String sql = " select to_char(t.reply) from pr_job_unit t where t.id='"+this.getIds()+"'";
		PrJobUnit prJobUnit = null;
		try {
			prJobUnit = (PrJobUnit)this.getGeneralService().getById(PrJobUnit.class, this.getIds());
//			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(prJobUnit.getReply()!=null){
			this.setNote(prJobUnit.getReply());
		}else{
			this.setNote("尚未提交任务回复！");
		}
		return "view_Reply";
	}
	 //得到当前用户
    public PeManager getCurrentUser(){
    	UserSession usersession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
    	PeManager manager=null;
    	DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
    	dc.createAlias("ssoUser", "ssoUser");
    	dc.add(Restrictions.eq("ssoUser.id",usersession.getSsoUser().getId()));
    	List list = new LinkedList();
    	try{
    		list=this.getGeneralService().getList(dc);
    		if(list!=null&&list.size()>0){
    			manager=(PeManager)list.get(0);
    		}
    	}catch (EntityException e) {
       		e.printStackTrace();
       	}
    	return manager;
    }
    
    public StringBuilder getManagerCheckScript(){
    	List list=null;
		try {
			list = this.getMyListService().queryBySQL("select id,name from enum_const where namespace='FkJobStatus' and code in ('1807','1808')");
			if(list.size()==0){
				throw new EntityException("error");
			}
		} catch (Exception e) {
		}
		String comboboxitem = " var comboitem = new Ext.form.ComboBox({ store: new Ext.data.SimpleStore({fields: ['id', 'name'],data : [";
		for(int i = 0; i < list.size(); i++){
			Object[] s = (Object[])list.get(i);
			if(i == 0){
				comboboxitem += " ['" + s[0].toString() + "', '" + s[1].toString() + "']";
			}else{
				comboboxitem += ", ['" + s[0].toString() + "', '" + s[1].toString() + "']";
			}
		}
		comboboxitem += " ] }), valueField: 'id', displayField:'name', selectOnFocus:true, allowBlank: false, typeAhead:false," +
		"	fieldLabel: '审核通过*'," +
		"	name:'jobCheck'," +
		"	id:'jobCheck'," +
		"	triggerAction: 'all'," +
		"	editable: true," +
		"	mode:'local'," +
		"	emptyText:''," +
		"	blankText:''" +
		"});\n"; 
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("    	 	ids = m[0].get('id');");
		script1.append("    	 }else if(m.length == 0){");
		script1.append("    	 	Ext.MessageBox.alert('错误','请选择一个任务'); return;");
		script1.append("    	 }else {");
		script1.append("    	 	Ext.MessageBox.alert('错误','一次只能审核一个任务'); return;");
		script1.append("    	 }");
		script1.append(comboboxitem);
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("        labelWidth: 80,");
		script1.append("        defaultType: 'textfield',");
		script1.append(" 		autoScroll:true,");
		script1.append("        items: [    ");
		script1.append("  comboitem");
		script1.append("       ,{ fieldLabel:'审核评语*',  ");//文本框标题   
        script1.append("        xtype:'textarea',");//表单文本框   
        script1.append("        name:'checkNote',   ");
        script1.append("        id:'checkNote',   ");
        script1.append("         width:250 ,height: 80 , ");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
        script1.append("       } ,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '审核所选的任务',");
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
		script1.append(" 		 		        	url:'/entity/information/prJobUnitInit_verifyJob.action?' ,");
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
    
    public StringBuilder getPeJobUnitScript(){
    	StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append("        var fids = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'名称*',  ");//文本框标题   
        script1.append("        name:'name',   ");
        script1.append("        id:'name',   ");
        script1.append("        width:250   ,");
        script1.append("        maxLength:25   ,");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
		script1.append("           })  ;                                      ");
		script1.append("        var namefield = new Ext.form.TextArea({                  ");
		script1.append("        fieldLabel:'任务回复*',  ");//文本框标题   
        script1.append("        name:'reply',   ");
        script1.append("        id:'reply',   ");
        script1.append("        width:250   ,");
        script1.append("        height:100   ,");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
		script1.append("           })  ;                                      ");
		script1.append("        var filefield = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'任务资料*',  ");//文本框标题   
        script1.append("        inputType:'file',");//表单文本框   
        script1.append("        name:'upload1',   ");
        script1.append("        id:'upload1',   ");
        script1.append("        width:250   ,");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
		script1.append("           })  ;                                      ");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append(" 	   fileUpload:true,");
		script1.append("        labelWidth: 70,");
		script1.append("        defaultType: 'textfield',");
		script1.append(" 		autoScroll:true,");
		script1.append("        items: [    ");
        script1.append("      fids, filefield,namefield ]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '提交任务资料',");
		script1.append("        width: 450,");
		script1.append("        height: 250,");
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
		script1.append(" 		 		        	url:'/entity/information/prJobUnitInit_uploadResource.action?ids="+this.getBean().getPeJob().getId()+"' ,");
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
	public String getCheckNote() {
		return checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}


	public PeUnit getPeUnit() {
		return peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}


	public File getUpload1() {
		return upload1;
	}

	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}
	public String getUpload1FileName() {
		return upload1FileName;
	}

	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getJobCheck() {
		return jobCheck;
	}

	public void setJobCheck(String jobCheck) {
		this.jobCheck = jobCheck;
	}
}
