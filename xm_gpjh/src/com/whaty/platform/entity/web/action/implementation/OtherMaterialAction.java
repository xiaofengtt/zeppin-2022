package com.whaty.platform.entity.web.action.implementation;

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

import com.whaty.platform.entity.bean.PeOtherMaterial;
import com.whaty.platform.entity.bean.PrProSummary;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 上传其他材料
 * 
 * @author 侯学龙
 *
 */
public class OtherMaterialAction extends TrainingCourseSummaryAction {
	
	private static final long serialVersionUID = 1L;
	
	private String materialName;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id");
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "peProApplyno.code");
		this.getGridConfig().addColumn(this.getText("所属年度"), "peProApplyno.year");
		this.getGridConfig().addColumn(this.getText("项目类型"), "peProApplyno.enumConstByFkProgramType.name");
		this.getGridConfig().addColumn(this.getText("申报时限"), "peProApplyno.deadline",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "peProApplyno.feeStandard",false,true,true,Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "peProApplyno.limit",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "peProApplyno.note",false,false,true,"",true,200);
		this.getGridConfig().addRenderScript(this.getText("查看其他材料"), "{ return '<a href=\\'/entity/implementation/showOtherMaterial.action?id_='+record.data['id']+'\\' target=\\'_blank\\'>查看</a>';}", "id");
		this.getGridConfig().setTitle(this.getText("上传其他材料"));
		
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("          ids = m[0].get('id');  ");
		script1.append("          }else if(m.length > 1) { ");
		script1.append("    	 	Ext.MessageBox.alert('错误','最多只能选择一个项目上传材料'); return;");
		script1.append("    	 }else {");
		script1.append("    	 	Ext.MessageBox.alert('错误','请选择一个项目上传材料'); return;");
		script1.append("    	 }");
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("        var namefield = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'材料名称*',  ");//文本框标题   
        script1.append("        name:'materialName',   ");
        script1.append("        id:'materialName',   ");
        script1.append("        width:250   ,");
        script1.append("        allowBlank:false, "); //不能为空
        script1.append("        blankText:'不能为空，请填写' ");
		script1.append("           })  ;                                      ");
		script1.append("        var filefield = new Ext.form.TextField({                  ");
		script1.append("        fieldLabel:'上传附件*',  ");//文本框标题   
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
		script1.append("        title: '提交材料',");
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
		script1.append(" 		 		        	url:'/entity/implementation/otherMaterial_saveOtherMaterial.action?' ,");
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
		this.getGridConfig().addMenuScript(this.getText("上传其他材料(如培训指南、问卷调查、学习心得等)"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/otherMaterial";
	}
	
	public String saveOtherMaterial(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "材料上传失败");
		PrProSummary prProSummary = null;
		try {
			prProSummary = (PrProSummary)this.getGeneralService().getById(PrProSummary.class,this.getIds());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
//		PeProApply peProApply = prProgramUnit.getPeProApply();
		DetachedCriteria dc = DetachedCriteria.forClass(PeOtherMaterial.class);
		dc.createAlias("peProApplyno", "peProApplyno");
		dc.add(Restrictions.eq("peProApplyno", prProSummary.getPeProApplyno()));
		dc.createAlias("peUnit", "peUnit");
		dc.add(Restrictions.eq("peUnit", prProSummary.getPeUnit()));
		dc.addOrder(Order.desc("uploadDate"));
		List listOther = null;
		try {
			listOther = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		String moveNo = "0";
		if(listOther!=null&&!listOther.isEmpty()){
			PeOtherMaterial peOtherMaterial= (PeOtherMaterial)listOther.get(0);
			if((peOtherMaterial.getMaterialFile().indexOf("[")>0)&&(peOtherMaterial.getMaterialFile().indexOf("]")>0))
				moveNo  = peOtherMaterial.getMaterialFile().substring(peOtherMaterial.getMaterialFile().lastIndexOf("[")+1,peOtherMaterial.getMaterialFile().lastIndexOf("]"));
		}
		int moveNo_ = Integer.parseInt(moveNo)+1;
		if(this.getUpload()!=null){
			if(this.getUploadFileName().indexOf(".")==-1){
				map.put("success", "false");
				map.put("info", "上传文件格式错误！");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json(); 
			}
			this.setMsg("材料上传失败！");
			String filename = prProSummary.getPeProApplyno().getCode() + prProSummary.getPeUnit().getName()+ prProSummary.getPeProApplyno().getYear() +"年其他材料[" + moveNo_ + "]"+this.getUploadFileName().substring(this.getUploadFileName().lastIndexOf("."));
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
				this.setMsg("材料上传成功！");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (this.getMsg().equals("材料上传成功！")) {
				PeOtherMaterial peOtherMaterial = new PeOtherMaterial();
				peOtherMaterial.setMaterialFile(this.getSavePath() + "/" + filename);
				peOtherMaterial.setMaterialName(this.getMaterialName());
				peOtherMaterial.setPeProApplyno(prProSummary.getPeProApplyno());
				peOtherMaterial.setPeUnit(prProSummary.getPeUnit());
				Date date = new Date();
				peOtherMaterial.setUploadDate(date);
//				peOtherMaterial.setPeProApply(peProImplemt.getPeProApply());
				
				prProSummary.setLastUploadDate(date);
//				peProImplemt.setBriefReportFile(savePath + "/" + filename);
//				peProImplemt.setBriefReportName(this.getBriefReportName());
				try {
					this.getGeneralService().save(peOtherMaterial);
					map.put("info", "材料上传成功");
					map.put("success","true");
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json(); 
	}

	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
}
