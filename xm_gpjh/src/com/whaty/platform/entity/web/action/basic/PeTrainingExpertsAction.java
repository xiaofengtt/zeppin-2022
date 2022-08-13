package com.whaty.platform.entity.web.action.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeTrainExpert;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeTrainingExpertsAction extends MyBaseAction {
	private String status;//专家状态
	private PeTrainExpert expert;//专家
	private List genderList;
	private List provinceList;
	private List subjectList;
	private List statusList;
	private String birthYearMonth;

	public List getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("培训教学专家信息管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",false,10);
//		this.getGridConfig().addColumn(this.getText("登录名"), "loginId");
//		this.getGridConfig().addColumn(this.getText("单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace",true,true,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",true,1,Const.sex_for_extjs);
		ColumnConfig cc=new ColumnConfig(this.getText("学科"), "peSubject.name",true,true,true,"TextField",false,50,"");
		cc.setComboSQL("select id,name from pe_subject order by name");
		cc.setAllowBlank(true);
		this.getGridConfig().addColumn(cc);
//		this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("推荐类别"), "enumConstByFkStatus.name");
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false, true, true,"",true,25,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk",false, true, true,"",true,10);
		this.getGridConfig().addColumn(this.getText("出生年月"), "birthdate",false, true, true,"Date",true,15);
		this.getGridConfig().addColumn(this.getText("所学专业"), "major",false, true, true,"",true,15);
		this.getGridConfig().addColumn(this.getText("email"), "email",false, true, true,"",true,50,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",false, true, true,"",true,15,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("年龄"),"age",false,true,true,"",true,10,Const.number_for_extjs);
		
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("研究专长"), "researchArea",false,true,false,"",true,100);
		this.getGridConfig().addColumn(this.getText("教学专长"), "trainingArea",false,true,false,"",true,125);
		this.getGridConfig().addColumn(this.getText("身份证号"), "idcard",false,true,false,"",true,20,Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",false,true,false,"",true,50);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",false,true,false,"",true,6,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("住宅电话"), "homePhone",false,true,true,"",true,50,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,"",true,50,Const.phone_number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("入库时间"), "birthdate",false, true, true,"Date",true,200);
//		this.getGridConfig().addColumn(this.getText("个人简历"), "personalResume",false,false,false,"TextEditor",true,10000);
//		this.getGridConfig().addColumn(this.getText("教育教学成果"), "trainingResult",false,false,false,"TextEditor",true,10000);
//		this.getGridConfig().addColumn(this.getText("教师培训经验"), "trainingExperience",false,false,false,"TextEditor",true,10000);
//		this.getGridConfig().addColumn(this.getText("其他需要说明事项"), "otherItems",false,false,false,"TextEditor",true,10000);
//		this.getGridConfig().addColumn(this.getText("所在单位意见"), "unitComment",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("推荐部门意见"), "recommendComment",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教育部师范司组织专家审核意见"), "finalComment",false,false,false,"TextArea",true,10000);
		this.getGridConfig().addColumn(this.getText("查看次数"), "searchCount", true, false, true, "", true, 5);
		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("入库时间"), "inputDate", false,true,false, "Date", true, 50);
		this.getGridConfig().addRenderFunction(this.getText("修改"),
				"<a href=\"#\" onclick=\"window.open('/entity/basic/peTrainingExpertsAction_edit.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes')\">修改</a>&nbsp;&nbsp;","id");
		this.getGridConfig().addRenderFunction(this.getText("浏览"),
				"<a href=\"#\" onclick=\"window.open('/entity/basic/peTrainingExpertsAction_browseDetail.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes')\">浏览</a>","id");
		this.getGridConfig().addMenuScript(this.getText("添加"), "{window.open('/entity/basic/peTrainingExpertsAction_preAddExpert.action','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes,top=10,left=100')}");
		
		//用于修改所选专家的状态
		List list=null;
		try {
			list = this.getMyListService().queryBySQL("select id,name from enum_const where namespace='FkStatus' ");
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
		script1.append(" 		 		        	url:'/entity/basic/peTrainingExpertsAction_modifyStatus.action?' ,");
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
	public void initDatas(){
		try {
			this.setGenderList(this.getGeneralService().getBySQL("select id,name from enum_const where namespace='FkGender'"));
			this.setProvinceList(this.getGeneralService().getBySQL("select id,name from pe_province"));
			this.setSubjectList(this.getGeneralService().getBySQL("select id,name from pe_subject order by name"));
			this.setStatusList(this.getGeneralService().getBySQL("select id,name from enum_const where namespace = 'FkStatus'"));
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	
	//修改专家信息
	public String edit(){
		this.initDatas();
		try {
			 this.setBean((PeTrainExpert)this.getGeneralService().getById(PeTrainExpert.class,this.getBean().getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "epedit";
	}
	
	//修改专家信息后保存
	public String saveEdit(){
		PeTrainExpert expert=new PeTrainExpert();
//		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainExpert.class);
		try {
			expert=(PeTrainExpert) this.getGeneralService().getById(PeTrainExpert.class,this.getBean().getId());
			PeTrainExpert expert1 = assemblExpert(expert);
			expert=(PeTrainExpert) this.getGeneralService().save(expert1);
		} catch (EntityException e) {
			e.printStackTrace();
		} 
		this.setMsg("修改成功");
		return this.edit();
	}
	//组装专家实例
	public PeTrainExpert assemblExpert(PeTrainExpert expert) {
		expert.setName(this.getBean().getName());
		expert.setAddress(this.getBean().getAddress());
		try {
			if(this.getBirthYearMonth()!=null&&this.getBirthYearMonth().length()>0){
				expert.setBirthdate(new SimpleDateFormat("yyyy-MM").parse(getBirthYearMonth()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		expert.setEnumConstByFkGender(this.getBean().getEnumConstByFkGender().equals("")?null:getBean().getEnumConstByFkGender());
		expert.setWorkplace(this.getBean().getWorkplace());
		expert.setIdcard(this.getBean().getIdcard());
		expert.setPeSubject(this.getBean().getPeSubject().equals("")?null:getBean().getPeSubject());
		expert.setPeProvince(this.getBean().getPeProvince().equals("")?null:getBean().getPeProvince());
		expert.setEnumConstByFkStatus(this.getBean().getEnumConstByFkStatus());
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
	public String modifyStatus(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "修改失败");
		if(this.getStatus()==null||this.getStatus().length()==0){
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		String ids[]=this.getIds().split(",");
		String sql_status="select id from enum_const where name=:name and namespace='FkStatus'";
		Map map_status=new HashMap();
		map_status.put("name", this.getStatus());
		List list_status=new LinkedList();
		String statusId="";
		try {
			list_status=this.getGeneralService().getBySQL(sql_status, map_status);
			if(list_status!=null&&list_status.size()>0){
				statusId=(String) list_status.get(0);
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		Map maps=new HashMap();
		maps.put("sta", statusId);
		
		int count=0;
		try {
			for(int i=0;i<ids.length;i++){
				String id=ids[i];
				maps.put("ids", id);
				String sql="update pe_train_expert set fk_status=:sta where id=:ids";
				int flag=this.getGeneralService().executeBySQL(sql, maps);
				count+=flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count==ids.length){
		}
		map.put("info", "修改成功");
		map.put("success","true");
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	@Override
	public void checkBeforeAdd() throws EntityException{
		if(this.getBean().getEnumConstByFkStatus()==null){
			EnumConst enu_status =null;
			DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("namespace", "FkStatus"));
			dc.add(Restrictions.eq("isDefault", "1"));
			List list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				enu_status=(EnumConst) list.get(0);
				this.getBean().setEnumConstByFkStatus(enu_status);
			}
		}
		this.getBean().setInputDate(new java.util.Date());
//		if(this.getBean().getAddress()!=null&&this.getBean().getAddress().length()>50){
//			throw new EntityException("通讯地址不能超过50个字符！");
//		}
//		if(this.getBean().getAddress()!=null&&this.getBean().getAddress().length()>50){
//			throw new EntityException("通讯地址不能超过50个字符！");
//		}
	}
	/**
	 * 添加专家
	 * @return
	 */
	public String addExpert() {
		PeTrainExpert expert = new PeTrainExpert();
		this.getGeneralService().getGeneralDao().setEntityClass(expert.getClass());
		assemblExpert(expert);
		expert.setInputDate(new java.util.Date());
		try {
			this.getGeneralService().save(expert);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("添加成功\\n继续添加吗？");
		this.initDatas();
		return "addSuccess";
	}
	/**
	 * 添加专家前对添加页面初始化信息
	 * @return
	 */
	public String preAddExpert(){
		this.initDatas();
		return "preAddExpert";
	}
	/**
	 * 修改专家信息
	 * @return
	 */
	public String editExpert(){
//		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainExpert.class);
		PeTrainExpert expert=null;
		try {
			expert=(PeTrainExpert) this.getGeneralService().getById(PeTrainExpert.class,this.getIds());
			if(expert!=null){
//				this.setExpert(expert);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "editExpert";
	}
	
	//查看专家详细信息
	public String browseDetail() {
		try {
			setBean((PeTrainExpert) getGeneralService().getById(PeTrainExpert.class,getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("flag");
		return "browseDetail";
	}
	
	// 修改专家信息后保存
	public String editExpertSave(){
//		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainExpert.class);
		PeTrainExpert expert=null;
		try {
			expert=(PeTrainExpert) this.getGeneralService().getById(PeTrainExpert.class,this.getIds());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return editExpert();
	}
	@Override
	public void checkBeforeUpdate() throws EntityException{
	}
	
	@Override
	protected void initGrid4BatchAddExcel() {
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",false,10);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace",true,true,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("性别（男、女）"), "enumConstByFkGender.name",true,true,true,"TextField",true,1,Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name",true,true,true,"TextField",true,50,"");
		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("推荐类别（待审核，一般推荐，重点推荐，待复审）"), "enumConstByFkStatus.name");
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false, true, true,"",true,25,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk",false, true, true,"",true,10);
		this.getGridConfig().addColumn(this.getText("出生年月"), "birthdate",false, true, true,"Date",true,15);
		this.getGridConfig().addColumn(this.getText("所学专业"), "major",false, true, true,"",true,15);
		this.getGridConfig().addColumn(this.getText("email"), "email",false, true, true,"",true,50,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",false, true, true,"",true,15,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("年龄"),"age",false,true,false,"",true,10,Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,false,"",true,25);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng",true,true,true,"",true,25);
		this.getGridConfig().addColumn(this.getText("研究专长"), "researchArea",false,true,false,"",true,100);
		this.getGridConfig().addColumn(this.getText("教学专长"), "trainingArea",false,true,false,"",true,125);
		this.getGridConfig().addColumn(this.getText("身份证号"), "idcard",false,true,false,"",true,20,Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",false,true,false,"",true,50);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",false,true,false,"",true,6,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("住宅电话"), "homePhone",false,true,true,"",true,50,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,"",true,50,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("个人简历"), "personalResume",false,true,false,"TextEditor",true,10000);
		this.getGridConfig().addColumn(this.getText("教育教学成果"), "trainingResult",false,true,false,"TextEditor",true,10000);
		this.getGridConfig().addColumn(this.getText("教师培训经验"), "trainingExperience",false,true,false,"TextEditor",true,10000);
		this.getGridConfig().addColumn(this.getText("其他需要说明事项"), "otherItems",false,true,false,"TextEditor",true,10000);
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainExpert.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTrainingExpertsAction";
	}

	public void setBean(PeTrainExpert instance) {
		super.superSetBean(instance);
	}

	public PeTrainExpert getBean() {
		return (PeTrainExpert) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainExpert.class);
		dc.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peSubject", "peSubject",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peProvince", "peProvince",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkStatus", "enumConstByFkStatus",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PeTrainExpert getExpert() {
		return expert;
	}
	public void setExpert(PeTrainExpert expert) {
		this.expert = expert;
	}
	public List getGenderList() {
		return genderList;
	}
	public void setGenderList(List genderList) {
		this.genderList = genderList;
	}
	public List getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}
	public List getStatusList() {
		return statusList;
	}
	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}
	public String getBirthYearMonth() {
		return birthYearMonth;
	}
	public void setBirthYearMonth(String birthYearMonth) {
		this.birthYearMonth = birthYearMonth;
	}
}