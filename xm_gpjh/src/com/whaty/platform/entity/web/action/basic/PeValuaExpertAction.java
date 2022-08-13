package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeValuaExpertAction extends MyBaseAction<PeValuaExpert>{
	private List genderList;
	private List statusValuateList;
	private String status;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, true, true, true);
		this.getGridConfig().setTitle("评审专家信息管理");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFkStatusValuate.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name", true, true, true, "TextField", true, 25);
		//this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name", true, true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 20);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false, true, true,"",true,15,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生年月"), "birthyearmonth", true, true, true, "", true, 25);
		this.getGridConfig().addColumn(this.getText("邮箱"), "email", true, true, true, "", true, 25,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("所学专业"), "major",false, true, true,"",true,25);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",false, true, true,"",true,25,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("年龄"),"age",false,true,true,"",true,10,Const.number_for_extjs);
		
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("研究专长"), "researchArea",false,true,false,"",true,100);
		this.getGridConfig().addColumn(this.getText("教学专长"), "trainingArea",false,true,false,"",true,125);
		this.getGridConfig().addColumn(this.getText("身份证号"), "idcard",false,true,false,"",true,20);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",false,true,false,"",true,50);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",false,true,false,"",true,6,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("住宅电话"), "homePhone",false,true,true,"",true,25,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,"",true,20,Const.phone_number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("个人简历"), "personalResume",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教育教学成果"), "trainingResult",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教师培训经验"), "trainingExperience",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("其他需要说明事项"), "otherItems",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("所在单位意见"), "unitComment",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("推荐部门意见"), "recommendComment",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教育部师范司组织专家审核意见"), "finalComment",false,false,false,"TextArea",true,10000);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false,false,true, "TextArea", true, 10000);
		this.getGridConfig().addColumn(this.getText("用户名"), "ssoUser.loginId", false,false,true, "", true, 25);
		this.getGridConfig().addRenderFunction(this.getText("操作"),
				"<a href=\"#\" onclick=\"window.open('/entity/basic/peValuaExpertAction_editValuaExpert.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes')\">修改</a>&nbsp;&nbsp;" +
				"<a href=\"#\" onclick=\"window.open('/entity/basic/peValuaExpertAction_browseDetail.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes')\">浏览</a>",
				"id");
		this.getGridConfig().addMenuScript("添加", "{window.open('/entity/basic/peValuaExpertAction_preAddExpert.action','','toolbar=no,menubar=no,scrollbars=yes,resizable=yes')}");
		
		//用于修改所选专家的状态
		List list=null;
		try {
			list = this.getMyListService().queryBySQL("select id,name from enum_const where namespace='FkStatusValuate' ");
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
		"	fieldLabel: '状态*'," +
		"	name:'status'," +
		"	id:'status'," +
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
		script1.append("    	 if(m.length > 0){");
		script1.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script1.append("    	 			var ss = m[i].get('id');");
		script1.append("    	 			if(i==0)ids = ids+ ss ;");
		script1.append("    	 			else ids = ids +','+ss;");
		script1.append("    	 		}");
		script1.append("    	 }else{");
		script1.append("    	 	Ext.MessageBox.alert('错误','请至少选择一个专家'); return;");
		script1.append("    	 }");
		script1.append(comboboxitem);
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("         labelWidth: 100,");
		script1.append("        	defaultType: 'textfield',");
		script1.append(" 				autoScroll:true,");
		script1.append("         items: [ comboitem ,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '修改所选专家的状态',");
		script1.append("        width: 450,");
		script1.append("        height: 225,");
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
		script1.append(" 		 		        	url:'/entity/basic/peValuaExpertAction_modifyStatus.action?' ,");
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
		this.getGridConfig().addMenuScript(this.getText("修改状态"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}
	
	@Override
	protected void initGrid4BatchAddExcel() {
		super.initGrid4BatchAddExcel();
		this.getGridConfig().addColumn(this.getText("用户名"), "ssoUser.loginId", true,false,false, "", true, 25);
	}

	public String modifyStatus() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("success", "false");
		map.put("info", "修改失败");
		
		if(getStatus()==null||getStatus().length()==0){
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		
		String[] ids = this.getIds().split(",");
		String sql_status="select id from enum_const where name='"+getStatus()+"' and namespace='FkStatusValuate'";
		List statusIdList = new LinkedList();
		try {
			statusIdList = this.getGeneralService().getBySQL(sql_status);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String idString = null;
		if(statusIdList!=null) {
			idString = (String) statusIdList.get(0);
		}
		String updateSql = null;
		for(int i=0;i<ids.length;i++) {
			updateSql = "update pe_valua_expert set fk_status='" + idString+ "' where id='"+ids[i] + "'";
			try {
				this.getGeneralService().executeBySQL(updateSql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			updateSql = null;
		}
		map.put("info", "修改成功");
		map.put("success","true");
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	//添加专家信息前初始化页面
	public String preAddExpert() {
		initData();
		return "initDataPreAdd";
	}
	
	//添加评审专家
	public String addValuationExpert() {
		PeValuaExpert valuaExpert = new PeValuaExpert();
		this.getGeneralService().getGeneralDao().setEntityClass(PeValuaExpert.class);
		assemblExpert(valuaExpert);
		valuaExpert.setInputDate(new java.util.Date());
		try {
			SsoUser ssoUser = addSsoUserBeforeAddExpert();
			valuaExpert.setSsoUser(ssoUser);
			this.getGeneralService().save(valuaExpert);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("添加成功\\n继续添加？");
		this.initData();
		return "addValuaExpert";
	}
	
	/**
	 * @description 在添加评审专家之前保存与其关联的SsoUser对象
	 * @return 与专家关联的SsoUser
	 */
	private SsoUser addSsoUserBeforeAddExpert() {
		SsoUser ssoUser = new SsoUser();
		ssoUser.setLoginId(getBean().getLoginId());
		ssoUser.setPassword(Const.FIRST_PASSWORD);
		EnumConst isValidConst = getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1");
		ssoUser.setEnumConstByFlagIsvalid(isValidConst);
		ssoUser.setLoginNum(new Long(0));
		ssoUser.setCheckedInfo("1");	// 首次登录使其不去修改信息
		List<PePriRole> roleList = null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PePriRole.class);
		detachedCriteria.add(Restrictions.eq("name", "评审专家"));
		try {
			roleList = getGeneralService().getList(detachedCriteria);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(roleList != null && roleList.size() != 0) {
			ssoUser.setPePriRole(roleList.get(0));
		}
		getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
		getGeneralService().getGeneralDao().saveSsoUser(ssoUser);
		
		return ssoUser;
	}

	//组装专家实例
	private PeValuaExpert assemblExpert(PeValuaExpert expert) {
		expert.setName(this.getBean().getName());
		expert.setLoginId(getBean().getLoginId());
		expert.setAddress(this.getBean().getAddress());
		expert.setBirthyearmonth(this.getBean().getBirthyearmonth());
		expert.setEnumConstByFkGender(this.getBean().getEnumConstByFkGender().equals("")?null:getBean().getEnumConstByFkGender());
		expert.setWorkplace(this.getBean().getWorkplace());
		expert.setIdcard(this.getBean().getIdcard());
		expert.setEnumConstByFkStatusValuate(this.getBean().getEnumConstByFkStatusValuate());
		expert.setMajor(this.getBean().getMajor());
		expert.setFolk(this.getBean().getFolk());
		expert.setPersonalResume(this.getBean().getPersonalResume());
		expert.setTelephone(this.getBean().getTelephone());
		expert.setHomePhone(this.getBean().getHomePhone());
		expert.setOfficePhone(this.getBean().getOfficePhone());
		expert.setEmail(this.getBean().getEmail());
		expert.setAge(this.getBean().getAge());
		expert.setPolitics(this.getBean().getPolitics());
		expert.setEducation(this.getBean().getEducation());
		expert.setZhicheng(this.getBean().getZhicheng());
		expert.setZhiwu(this.getBean().getZhiwu());
		expert.setTrainingArea(this.getBean().getTrainingArea());
		expert.setResearchArea(this.getBean().getResearchArea());
		expert.setTrainingExperience(this.getBean().getTrainingExperience());
		expert.setTrainingResult(this.getBean().getTrainingResult());
		expert.setFax(this.getBean().getFax());
		expert.setOtherItems(this.getBean().getOtherItems());
		expert.setNote(this.getBean().getNote());
		expert.setZip(this.getBean().getZip());
		
		return expert;
	}
	//修改专家信息
	public String editValuaExpert() {
		//this.getGeneralService().getGeneralDao().setEntityClass(PeValuaExpert.class);
		this.initData();
		try {
			//this.setBean(this.getGeneralService().getById(this.getIds()));
			this.setBean(this.getGeneralService().getById(PeValuaExpert.class,this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "editValuaExpert";
	}
	
	//保存修改后信息
	public String saveEdit() {
		PeValuaExpert peValuaExpert = new PeValuaExpert();
//		this.getGeneralService().getGeneralDao().setEntityClass(PeValuaExpert.class);
		try {
			peValuaExpert = this.getGeneralService().getById(PeValuaExpert.class,this.getBean().getId());
			PeValuaExpert peValuaExpert1 = assemblExpert(peValuaExpert);
			this.getGeneralService().save(peValuaExpert1);
		} catch (EntityException e) {
			this.setMsg("修改失败\\n关闭窗口？");
		}
		this.setMsg("修改成功\\n关闭窗口？");
		return editValuaExpert();
	}
	
	//浏览专家详细信息
	public String browseDetail() {
		try {
			this.setBean(this.getGeneralService().getById(PeValuaExpert.class,this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "browseDetail";
	}
	//初始化数据
	private void initData() {
		try {
			setGenderList(this.getGeneralService().getBySQL("select id,name from enum_const where namespace='FkGender'"));
			setStatusValuateList(this.getGeneralService().getBySQL("select id,name from enum_const where namespace = 'FkStatusValuate'"));
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PeValuaExpert.class);
		dCriteria.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		//dCriteria.createCriteria("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN);
		dCriteria.createCriteria("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		//dCriteria.createCriteria("peSubject", "peSubject",DetachedCriteria.LEFT_JOIN);
		//dCriteria.createCriteria("peProvince", "peProvince",DetachedCriteria.LEFT_JOIN);
		dCriteria.createCriteria("enumConstByFkStatusValuate", "enumConstByFkStatusValuate",DetachedCriteria.LEFT_JOIN);
		return dCriteria;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeValuaExpert.class;
	}

	public void setBean(PeValuaExpert instance) {
		super.superSetBean(instance);
	}

	public PeValuaExpert getBean() {
		return (PeValuaExpert) super.superGetBean();
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peValuaExpertAction";
	}

	public List getGenderList() {
		return genderList;
	}

	public void setGenderList(List genderList) {
		this.genderList = genderList;
	}

	public List getStatusValuateList() {
		return statusValuateList;
	}

	public void setStatusValuateList(List statusValuateList) {
		this.statusValuateList = statusValuateList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
