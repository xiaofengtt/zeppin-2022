package com.whaty.platform.entity.web.action.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBriefReport;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.JsonUtil;

/**
 * 承办单位-->提交其他材料-->简报
 * 
 * @author 侯学龙
 *
 */
public class BriefReportAction extends MaterialSubmitAction {

	private static final long serialVersionUID = 1L;
	
	private String briefReportName;
	
	private File upload;
	
	private String uploadFileName;
	
	private String savePath;
	
	@Override
	public void initGrid() {
		this.abstractInitGrid();
		this.getGridConfig().setTitle(this.getText("上传简报"));
		//this.getGridConfig().addColumn(this.getText("简报名称"), "briefReportName",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("查看简报内容"), "{ return '<a href=\\'/entity/implementation/briefReportView.action?id_='+record.data['id']+'\\' target=\\'_self\\'>查看</a>';}", "id");
		
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("          ids = m[0].get('id');  ");
		script1.append("          }else if(m.length > 1) { ");
		script1.append("    	 	Ext.MessageBox.alert('错误','最多只能选择一个项目上传简报'); return;");
		script1.append("    	 }else {");
		script1.append("    	 	Ext.MessageBox.alert('错误','请选择一个项目上传简报'); return;");
		script1.append("    	 }");
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("        var namefield = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'简报名称*',  ");//文本框标题   
        script1.append("        name:'briefReportName',   ");
        script1.append("        id:'briefReportName',   ");
        script1.append("        width:250   ,");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
		script1.append("           })  ;                                      ");
		script1.append("        var filefield = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'简报内容*',  ");//文本框标题   
        script1.append("        inputType:'file',");//表单文本框   
        script1.append("        name:'upload',   ");
        script1.append("        id:'upload',   ");
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
        script1.append("       namefield ,filefield,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '提交简报',");
		script1.append("        width: 450,");
		script1.append("        height: 200,");
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
		script1.append(" 		 		        	url:'/entity/implementation/briefReport_saveBriefReport.action?' ,");
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
		this.getGridConfig().addMenuScript(this.getText("上传简报"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/briefReport";
	}
	protected PeProImplemt loadPeProImplemt(){
//		this.getGeneralService().getGeneralDao().setEntityClass();
		PeProImplemt peProImplemt = ((PeProImplemt)this.getMyListService().getById(PeProImplemt.class,this.getIds()));
		
		return peProImplemt;
	}
	
	public String saveBriefReport(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "简报上传失败");
		if(this.getBriefReportName()==null||this.getBriefReportName().length()==0){
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		PeProImplemt peProImplemt = loadPeProImplemt();
		PeProApply peProApply = peProImplemt.getPeProApply();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBriefReport.class);
		dc.createAlias("peProApply", "peProApply");
		dc.add(Restrictions.eq("peProApply", peProApply));
		dc.addOrder(Order.desc("uploadDate"));
		List listPeBriefReport = null;
		try {
			listPeBriefReport = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String moveNo = "0";
		if(listPeBriefReport!=null&&!listPeBriefReport.isEmpty()){
			PeBriefReport peBriefReport= (PeBriefReport)listPeBriefReport.get(0);
			if((peBriefReport.getReportFile().indexOf("[")>0)&&(peBriefReport.getReportFile().indexOf("]")>0))
				moveNo  = peBriefReport.getReportFile().substring(peBriefReport.getReportFile().lastIndexOf("[")+1,peBriefReport.getReportFile().lastIndexOf("]"));
			
		}
		 int moveCount = Integer.parseInt(moveNo)+1;
		if(this.getUpload()!=null){
			if(this.getUploadFileName().indexOf(".")==-1){
				map.put("success", "false");
				map.put("info", "上传文件格式错误！");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json(); 
			}
			this.setMsg("简报上传失败！");
			String filename = peProApply.getPeProApplyno().getCode() + peProApply.getPeSubject().getName()+peProApply.getPeUnit().getName()+ peProApply.getPeProApplyno().getYear() +"年简报["+moveCount+"]"+this.getUploadFileName().substring(this.getUploadFileName().lastIndexOf("."));
			String uploadLink = this.getSavePath() + "/" + filename;
			try {
				FileOutputStream fos = new FileOutputStream(ServletActionContext.getRequest().getRealPath(uploadLink));
				FileInputStream fis = new FileInputStream(this.getUpload());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				this.setMsg("简报上传成功！");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (this.getMsg().equals("简报上传成功！")) {
				PeBriefReport peBriefReport = new PeBriefReport();
				peBriefReport.setReportFile(savePath + "/" + filename);
				peBriefReport.setReportName(this.getBriefReportName());
				peBriefReport.setPeProApply(peProApply);
				Date date = new Date();
				peBriefReport.setUploadDate(date);
				
				peProImplemt.setLastUploadDate(date);
//				peProImplemt.setBriefReportFile(savePath + "/" + filename);
//				peProImplemt.setBriefReportName(this.getBriefReportName());
				try {
					this.getGeneralService().save(peBriefReport);
					this.getGeneralService().save(peProImplemt);
					map.put("info", "简报上传成功");
					map.put("success","true");
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json(); 
	}

	public String getBriefReportName() {
		return briefReportName;
	}

	public void setBriefReportName(String briefReportName) {
		this.briefReportName = briefReportName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	
}
