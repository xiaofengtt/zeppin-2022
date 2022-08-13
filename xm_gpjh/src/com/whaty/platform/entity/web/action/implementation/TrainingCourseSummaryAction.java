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
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrProSummary;
import com.whaty.platform.entity.bean.PrProgramUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;


/**
 * 总结报告
 * @author 侯学龙
 *
 */
public class TrainingCourseSummaryAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private File upload;
	
	private String uploadFileName;
	
	private String savePath;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("提交总结报告");
		this.getGridConfig().addColumn(this.getText("ID"), "id",false);
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "peProApplyno.code");
		this.getGridConfig().addColumn(this.getText("所属年度"), "peProApplyno.year");
		this.getGridConfig().addColumn(this.getText("项目类型"), "peProApplyno.enumConstByFkProgramType.name");
		this.getGridConfig().addColumn(this.getText("申报时限"), "peProApplyno.deadline",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "peProApplyno.feeStandard",false,true,true,Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "peProApplyno.limit",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("上传时间"), "inputDate",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "peProApplyno.note",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("ads"), "summaryFile",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("查看总结报告"), "{if (${value}==null||${value}=='') return '未提交'; else return '<a href='+${value}+' target=\\'_blank\\'>查看</a>';}", "summaryFile");
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
		script1.append("        var filefield = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'总结报告*',  ");//文本框标题   
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
        script1.append("       filefield,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '提交总结报告',");
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
		script1.append(" 		 		        	url:'/entity/implementation/trainingCourseSummary_saveSummary.action?' ,");
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
		this.getGridConfig().addMenuScript(this.getText("提交总结报告（绩效自评报告）"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/trainingCourseSummary";
	}
	public String saveSummary(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "总结报告上传失败");
		
		PrProSummary prProSummary = null;
		try {
			prProSummary = (PrProSummary)this.getGeneralService().getById(PrProSummary.class,this.getIds());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(this.getUpload()!=null){
			if(this.getUploadFileName().indexOf(".")==-1){
				map.put("success", "false");
				map.put("info", "上传文件格式错误！");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json(); 
			}
			this.setMsg("总结报告上传失败！");
			String filename = prProSummary.getPeProApplyno().getCode() + prProSummary.getPeUnit().getName() + prProSummary.getPeProApplyno().getYear() +"年总结报告"+this.getUploadFileName().substring(this.getUploadFileName().lastIndexOf("."));
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
				this.setMsg("总结报告上传成功！");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (this.getMsg().equals("总结报告上传成功！")) {
				prProSummary.setSummaryFile(uploadLink);
				prProSummary.setInputDate(new Date());
				try {
					this.getGeneralService().save(prProSummary);
					map.put("info", "总结报告上传成功");
					map.put("success","true");
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();  
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrProSummary.class;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
//		StringBuffer sql_a = new StringBuffer();
//		sql_a.append("select distinct applyno.id       as id                                   ");
//		sql_a.append("  from pe_pro_apply apply                                              ");
//		sql_a.append(" inner join pe_pro_applyno applyno on apply.fk_applyno = applyno.id    ");
//		sql_a.append(" inner join enum_const ec1 on applyno.fk_program_type = ec1.id         ");
//		sql_a.append(" inner join enum_const ec2 on apply.fk_check_final = ec2.id          ");
//		sql_a.append(" where apply.fk_unit = '"+this.getPeUnit().getId()+"'                  ");
//		sql_a.append("   and applyno.year = '"+Const.getYear()+"'                             ");
//		sql_a.append("   and ec2.code='1001'    ");
//		
//		
//		DetachedCriteria dc = DetachedCriteria.forClass(PrProgramUnit.class);
//		DetachedCriteria dcApplyno = dc.createCriteria("peProApplyno","peProApplyno");
//		dc.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
//		dcApplyno.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
//		dcApplyno.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus");
//		dcApplyno.createAlias("enumConstByFkProgramType", "enumConstByFkProgramType");
//		DetachedCriteria dcPeUnit = dc.createCriteria("peUnit", "peUnit");
//		dcPeUnit.add(Restrictions.eq("id", this.getPeUnit().getId()));
//		dcApplyno.add(Restrictions.sqlRestriction("peproapply1_.id in ("+sql_a.toString()+")"));
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrProSummary.class);
		DetachedCriteria dcpeProApplyno = dc.createCriteria("peProApplyno", "peProApplyno");
		dc.createAlias("peUnit", "peUnit");
		dcpeProApplyno.createAlias("enumConstByFkProgramType", "enumConstByFkProgramType");
//		dcpeProApplyno.add(Restrictions.eq("year", Const.getYear()));
		if( this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			dc.add(Restrictions.eq("peUnit", this.getPeUnit()));
		}
		return dc;
	}
	
	

	protected PeUnit getPeUnit(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			return manager.getPeUnit();
		} catch (EntityException e) {
			e.printStackTrace();
			return null;
		}
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
