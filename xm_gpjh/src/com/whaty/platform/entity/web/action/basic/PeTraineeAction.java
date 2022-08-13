package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.City;
import com.whaty.platform.entity.bean.County;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.bean.PeSubject;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeTraineeService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.IdCardUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeTraineeAction extends MyBaseAction {
	private File file;
	private String fileFileName;
	private String fileContentType;
	private PeTraineeService peTraineeService;
	
	@Override
	public void initGrid() {
		UserSession us = getUserSession();
		this.getGridConfig().setCapability(isAddMenuShouldShow(), true, true, true, false);
		this.getGridConfig().setTitle(this.getText("推荐学员管理"));
		if("2".equals(us.getRoleType())){
			this.initPublicGrid();
			this.getGridConfig().addColumn(this.getText("手机"), "telephone", false, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,true, true, "TextField", false, 50, Const.email_for_extjs);
			this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name", true, true, true, "TextField", true, 255);
			this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",true,50);
			this.getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", false, true, true, "", true, 20);
			this.getGridConfig().addColumn(this.getText("参训状态"), "enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
		}else if ("3".equals(us.getRoleType())){
			initSTGrid();
			this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
		}else{
			this.initPublicGrid();
			this.getGridConfig().addColumn(this.getText("职务"), "zhiwu", true, true, true, "", true, 25);
			this.getGridConfig().addColumn(this.getText("教龄"), "workyear", true, true, true, "", false, 50);
			this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true,true, true, "TextField", false, 50, Const.email_for_extjs);
			this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",false,50);
			this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", true, true, true, "", true, 20);
			this.getGridConfig().addColumn(this.getText("参训状态"), "enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
			this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name", true, true, true, "TextField", true, 50);
			this.getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
			this.getGridConfig().addColumn(this.getText("参训学段学科"), "subject", true, true, true, "TextField", true, 50);
			this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnitByFkTrainingUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setAdd(true);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code='1526'");
			this.getGridConfig().addColumn(cc);
			this.getGridConfig().addColumn(this.getText("填报单位"), "peUnitByFkUnitFrom.name", true, false, true, "TextField", true, 50);
			this.getGridConfig().addColumn(this.getText("备注1"), "note",true, true, true, "TextField", true, 500);
			this.getGridConfig().addColumn(this.getText("备注2"), "notesecond",true, true, true, "TextField", true, 500);
			this.getGridConfig().addMenuFunction(this.getText("审核通过"), "checkupPass");
			this.getGridConfig().addMenuFunction(this.getText("审核不通过"), "checkupNotPass");
		}
//		this.getGridConfig().addRenderFunction(this.getText("操作"),
//				"<a href=\"#\" onclick=\"window.open('/entity/basic/peTraineeAction_browseDetail.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes')\">浏览</a>",
//				"id");
	    StringBuilder script1 = new StringBuilder();
	    script1.append(" {");
	    script1.append(" var downloadexcel = new Ext.Action({                                                           ");
	    script1.append("         text: '下载标准Excel格式',                                                             ");
	    script1.append("         handler: function(){                                                                   ");
	    script1.append(" \t\t\tvar tmp = \"<form target='_blank' action='/test/importPeTraineeTemplate.jsp' name='exportExcel'></form>\"; ");
	    script1.append(" \t\t\texportexcel.innerHTML=tmp;                                                     ");
	    script1.append(" \t\t\tdocument.exportExcel.submit();               \t\t\t\t         \t                 ");
	    script1.append("         },                                                                                      ");
	    script1.append("         iconCls: 'excelModel'                                                                   ");
	    script1.append(" });\t\t                                                                                         ");
	    script1.append("    \tvar fileUpload = new Ext.form.TextField({");
	    script1.append(" \t    \tfieldLabel:'导入文件',");
	    script1.append("        \tname: 'file',");
	    script1.append("        \twidth: 300,");
	    script1.append("        \tregex:new RegExp(/^(.*)(\\.xls)$/),");
	    script1.append("        \tregexText:'文件格式错误！',");
	    script1.append(" \t\t\tinputType:'file'");
	    script1.append("       });");
	    script1.append("    \tvar formPanel = new Ext.form.FormPanel({");
	    script1.append(" \t    \tframe:true,");
	    script1.append("        \tlabelWidth: 75,");
	    script1.append("        \tdefaultType: 'textfield',");
	    script1.append("        \tfileUpload:true,");
	    script1.append(" \t\t\tautoScroll:true,");
	    script1.append("        \titems:[new Ext.Button(downloadexcel),fileUpload]");
	    script1.append("       });");
	    script1.append("        var addModelWin = new Ext.Window({");
	    script1.append("        title: 'Excel导入',");
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
	    script1.append(" \t            text: '导入',");
	    script1.append(" \t            handler: function() {");
	    script1.append(" \t                if (formPanel.form.isValid()) {");
	    script1.append(" \t\t \t\t        formPanel.form.submit({");
	    script1.append(" \t\t \t\t        \turl:'" + getServletPath() + "_batchAddExcel.action' ,");
	    script1.append(" \t\t\t\t            waitMsg:'处理中，请稍候...',");
	    script1.append(" \t\t\t\t\t\t\t success: function(form, action) {");
	    script1.append(" \t\t\t\t\t\t\t    var responseArray = action.result;");
	    script1.append(" \t\t\t\t\t\t\t    if(responseArray.success=='true'){");
	    script1.append(" \t\t\t\t\t\t\t    \tExt.MessageBox.alert('提示', responseArray.info);");
	    script1.append(" \t\t\t\t\t\t\t    \tstore.load({params:{start:g_start,limit:g_limit" + getGridConfig().getFieldsFilter() + "}});                      ");
	    script1.append(" \t\t\t\t\t\t\t\t    addModelWin.close();");
	    script1.append(" \t\t\t\t\t\t\t    } else {");
	    script1.append(" \t\t\t\t\t\t\t    \tExt.MessageBox.alert('错误', responseArray.info );");
	    script1.append(" \t\t\t\t\t\t\t    }");
	    script1.append(" \t\t\t\t\t\t\t}");
	    script1.append(" \t\t\t\t        });");
	    script1.append(" \t                } else{");
	    script1.append(" \t\t\t\t\t\tExt.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
	    script1.append(" \t\t\t\t\t}");
	    script1.append(" \t\t        }");
	    script1.append(" \t        },{");
	    script1.append(" \t            text: '取消',");
	    script1.append(" \t            handler: function(){addModelWin.close();}");
	    script1.append(" \t        }]");
	    script1.append("        });");
	    script1.append("        addModelWin.show();");
	    script1.append("  }");
	    if (("3".equals(getUserSession().getRoleType())) || ("4".equals(getUserSession().getRoleType()))) {
	      getGridConfig().addMenuScript(getText("Excel导入"), script1.toString());
	    }
	    getGridConfig().setPrepared(false);
	}
	//公共字段
	protected void initPublicGrid(){
//		UserSession us = getUserSession();
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
//		if(!"2".equals(us.getRoleType())){
			this.getGridConfig().addColumn(this.getText("身份证号"), "idcard", false,true, true, "TextField", false, 50, Const.cardId_for_extjs);
//		}
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",false,10,"");
		this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "TextField", false, 20);
//		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 20);
		this.getGridConfig().addColumn(this.getText("民族"), "folk.name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("学校所在区域"), "unitAttribute.name",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("学校类别"), "unitType.name",true, true, true, "TextField", false, 50, null);
//		this.getGridConfig().addColumn(this.getText("学历"), "education", true, true, true, "", true, 20);
//		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng", true, true, true, "", false, 25);
		this.getGridConfig().addColumn(this.getText("最高学历"), "education.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("职称"), "jobTitle.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("主要任教学段"), "mainTeachingGrade.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("主要任教学科"), "mainTeachingSubject.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("毕业院校"), "graduation", true,true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("所学专业"), "major", true,true, true, "TextField", false, 50, null);
//		this.getGridConfig().addColumn(this.getText("入职时间"), "hireDate", true,true, true, "TextField", false, 50,null);

//		this.get
	}
	
	// 省厅师范处需要特别显示的项目
	protected void initSTGrid(){
//		this.getGridConfig().addColumn(this.getText("手机"), "telephone", false, true, true, "TextField", true, 20);
//		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,true, true, "TextField", true, 50, Const.email_for_extjs);
//		this.getGridConfig().addColumn(this.getText("学段学科"), "subject", true, true, true, "TextField", true, 255);
//		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",true,50);
//		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", false, true, true, "", true, 20);
//		this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
//		ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnitByFkTrainingUnit.name");
//		cc.setAdd(false);
//		cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code='1526'");
//		this.getGridConfig().addColumn(cc);
//		this.getGridConfig().addColumn(this.getText("参训状态"), "enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("参训学段学科"), "subject", true, true, true, "TextField", false, 125);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idcard", false,true, true, "TextField", false, 50, Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",false,10,"");
		this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "", false, 4,Const.twoNumandSpace_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk.name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("学校所在区域"), "unitAttribute.name",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("学校类别"), "unitType.name",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu", true, true, true, "", true, 25);
		this.getGridConfig().addColumn(this.getText("职称"), "jobTitle.name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("最高学历"), "education.name", true, true, true, "", false, 25);
		this.getGridConfig().addColumn(this.getText("教龄"), "workyear", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true,true, true, "TextField", false, 50, Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", true, true, true, "", true, 20);
		this.getGridConfig().addColumn(this.getText("主要任教学段"), "mainTeachingGrade.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("主要任教学科"), "mainTeachingSubject.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("毕业院校"), "graduation", true,true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("所学专业"), "major", true,true, true, "TextField", false, 50, null);
//		this.getGridConfig().addColumn(this.getText("入职时间"), "hireDate", true,true, true, "TextField", false, 50,null);
		this.getGridConfig().addColumn(this.getText("备注1"), "note",true, true, true, "TextField", true, 500);
		this.getGridConfig().addColumn(this.getText("备注2"), "notesecond",true, true, true, "TextField", true, 500);
	}
	@Override
	protected void initGrid4BatchAddExcel() {
		initSTGrid();
		//this.getGridConfig().addColumn(this.getText("备注1"), "note",false, true, false, "TextField", true, 500);
		//this.getGridConfig().addColumn(this.getText("备注2"), "notesecond",false, true, false, "TextField", true, 500);
	}
	
	/**
	 * @description 是否可以批量添加学员，管理员和省厅用户角色可以
	 * @return true表示可以，即“Excel导入”按钮显示，否则不显示
	 */
	private boolean isBatchAddMenuShow() {
		boolean flag = false;
		if("4".equals(getUserSession().getRoleType()) || "3".equals(getUserSession().getRoleType())) {
			flag = true;
		}
		return flag;
	}
	
	public UserSession getUserSession(){
		return (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	}

	/**
	 * @description 判断添加新学员按钮是否应该显示出来
	 * 
	 * @return true表示显示，false表示不显示出来
	 */
	private boolean isAddMenuShouldShow() {
		boolean flag = true;
		
		String getNotComeTraineeSQL = "select count(t.id) from pe_trainee t,enum_const e where t.FK_STATUS_TRAINING=e.id and e.code='002' and fk_training_unit=:theUnitId and t.fk_unit_from<>:theUnitId";
		String getNewTraineeSQL = "select count(t.id) from pe_trainee t,enum_const e where t.FK_STATUS_TRAINING=e.id and e.code='003' and fk_training_unit=:theUnitId";
		List<BigDecimal> resultList = null;
		int notComeTraineeNum=0,newTrainee=0;
		Map<String, String> params = new HashMap<String, String>();
		params.put("theUnitId", getCurrentUserBelongUnit().getId());
		//params.put("theUnitId", getCurrentUserBelongUnit().getId());
		try {
			resultList = getGeneralService().getBySQL(getNotComeTraineeSQL,params);
			notComeTraineeNum = resultList.get(0).intValue();
			resultList = getGeneralService().getBySQL(getNewTraineeSQL,params);
			newTrainee = resultList.get(0).intValue();
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		SsoUser currentUser = getCurrentSsoUser();
		String code = getUserSession().getRoleType();
		if(notComeTraineeNum <= newTrainee && code.equals("2")) {
			flag = false;
		}
		return flag;
	}
//	// 获取当前登录用户对应角色在常量表中的特征码
//	public String getCurrentUserRoleEnumCode(SsoUser currentUser) {
//		String getCurrentUserRoleEnumCodeSQL = "select e.code from sso_user s,pe_pri_role r,enum_const e where s.fk_role_id=r.id and r.flag_role_type=e.id and s.id=:theCurrentUserId";
//		Map<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("theCurrentUserId",currentUser.getId());
//		List<String> codeList = null;
//		try {
//			 codeList = getGeneralService().getBySQL(getCurrentUserRoleEnumCodeSQL, paramsMap);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		String code = "";
//		if(codeList != null && codeList.size() != 0) {
//			code = codeList.get(0);
//		}
//		return code;
//	}
	
	/**
	 * @description 审核通过或者审核不通过、修改后是否通过
	 */
	@Override
	public Map<String,String> updateColumn() {
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("success", "false");
		msgMap.put("info", "操作失败");
		
		String action = this.getColumn();
		if(getIds() != null && getIds().length() != 0) {
			String[] ids = this.getIds().split(",");
			List<PeTrainee> traineeList = null;
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PeTrainee.class);
			detachedCriteria.add(Restrictions.in("id", ids));
			try {
				traineeList = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			EnumConst enumConst = null;
			for (int i = 0; i < traineeList.size(); i++) {
				if("checkupPass".equals(action)) {
//					updateSsoInvalidFlag(traineeList.get(i).getId(), true);
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65230");
					traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
				}else if("checkupNotPass".equals(action)) {
//					updateSsoInvalidFlag(traineeList.get(i).getId(), false);
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65233");
					traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
				}else if("graduate".equals(action)){
					if(traineeList.get(i).getEnumConstByFkStatusTraining().getCode().equals("002")){
						msgMap.put("info", "无法将未报到的学员设置结业！");
						return msgMap;
					}
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkGraduted", "1");
					traineeList.get(i).setEnumConstByFkGraduted(enumConst);
				}else if("ungraduate".equals(action)){
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkGraduted", "0");
					traineeList.get(i).setEnumConstByFkGraduted(enumConst);
				}
				
			}
			try {
				this.getGeneralService().saveList(traineeList);
			} catch (EntityException e) {
				e.printStackTrace();
				return msgMap;
			}
			if("checkupPass".equals(action)) {
				msgMap.put("info", traineeList.size()+"个学员审核通过。");
			}else if("checkupNotPass".equals(action)){
				msgMap.put("info", traineeList.size()+"个学员审核不通过，请重新添加"+traineeList.size()+"个学员。");
			}else {
				msgMap.put("info", traineeList.size()+"个学员操作成功");
			}
		}
		msgMap.put("success", "true");
		
		return msgMap;
	}

	/**
	 * @description 在修改学员状态时根据审核是否通过修改对应Sso_User是否有效
	 * @param id 将要修改学员的id号
	 * @param flag 审核是否通过，true为审核通过，否则为未通过或未审核
	 */
	public void updateSsoInvalidFlag(String id, boolean flag) {
		String getSsoIdSQL = "select FK_SSO_USER_ID from pe_trainee where id=:theId";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theId", id);
		List<String> resultList = null;
		try {
			resultList = this.getGeneralService().getBySQL(getSsoIdSQL, paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String ssoId = "";
		if(resultList != null && resultList.size() != 0) {
			ssoId = resultList.get(0);
		}
		String updateValidFlagSQL = "update sso_user set FLAG_ISVALID=:theFlag where id=:theId";
		paramsMap.clear();
		if(flag) {
			paramsMap.put("theFlag", "2");
		}else {
			paramsMap.put("theFlag", "3");
		}
		paramsMap.put("theId", ssoId);
		try {
			this.getGeneralService().executeBySQL(updateValidFlagSQL, paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	protected String getRandomString() {
		Random random = new Random();
		String randomString = "";
		for(int i=0;i<8;i++) {
			randomString += random.nextInt(10);
		}
		return randomString;
	}

	/**
	 * @description 添加用户前检查用户名是否存在、设置对应的sso_user，设置报送单位等
	 */
	@Override
	public void checkBeforeAdd() throws EntityException {
		//去掉手机号码的唯一性约束
//		if(getBean().getTelephone() == null || getBean().getTelephone().trim().length() == 0) {
//			DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
//			List list = null;
//			String tempTelephone = getRandomString();
//			while(true) {
//				dc.add(Restrictions.eq("loginId", tempTelephone));
//				list = getGeneralService().getList(dc);
//				if(list == null || list.size() == 0)
//					break;
//				tempTelephone = getRandomString();
//			}
//			getBean().setLoginId(tempTelephone);
//		}else {
//			this.getBean().setLoginId(this.getBean().getTelephone().trim());
//		}
//		List list = this.getGeneralService().getBySQL("select t.id from pe_trainee t where t.login_id ='"+getBean().getLoginId()+"' and t.fk_pro_applyno='"+this.getBean().getPeProApplyno().getId()+"'");
//		if(list!=null && !list.isEmpty()){
//			throw new EntityException("系统中存在相同的培训项目和手机号！");
//		}
		
		if(!isAddMenuShouldShow()) {
			throw new EntityException("本单位新增学员已达到未报到学员数，不允许再添加！");
		}
//		System.out.println(this.getBean().getPeProApplyno().getName());
		if(this.getBean().getPeProApplyno()!=null){//System.out.println(this.getBean().getPeProApplyno().getName()+"==========");
			
//			this.getGeneralService().getGeneralDao().setEntityClass();
			String sql="select t.year from pe_pro_applyno t where t.name ='"+this.getBean().getPeProApplyno().getName()+"'";
//			PeProApplyno applyno = (PeProApplyno)this.getMyListService().getIdByName(bean, name)(PeProApplyno.class,this.getBean().getPeProApplyno().getId());
			List list2 = this.getGeneralService().getBySQL(sql);
			if(!Const.getYear().equals(list2.get(0).toString())){
					throw new EntityException("添加失败，只能申报当前年度的项目!");
				}
			}
		if(this.getBean().getIdcard() != null){
			String str = IdCardUtil.IDCardValidate(this.getBean().getIdcard().toLowerCase());
			if(!"".equals(str)){
				throw new EntityException("添加失败，"+str+"!");
			}
//			String sql="select t.year from pe_pro_applyno t where t.name ='"+this.getBean().getPeProApplyno().getName()+"'";
			String sql = "select t.name from pe_trainee t,pe_pro_applyno p where t.fk_pro_applyno=p.id and t.idcard='"+this.getBean().getIdcard()+"'";
			sql += " and p.name='"+this.getBean().getPeProApplyno().getName()+"'";
			List list2 = this.getGeneralService().getBySQL(sql);
			if(list2 != null && list2.size()>0){
				throw new EntityException("添加失败，已经提交过该学员信息!");
			}
		}
		setTraineeUnitFrom();		//设置学员报送单位
		if(getBean().getProvince() != null) {
			compareAndOperateProvince();	//设置学员的省份
		}else{
			if(this.getUserSession().getRoleType().equals("3")){
				this.getBean().setPeProvince(this.getCurrentUnitProvince());
			}
		}
		if(this.getBean().getPeProvince() != null && this.getBean().getCity() != null){
			getGeneralService().getGeneralDao().setEntityClass(City.class);
			String queryAllPeProvinceSQL = "select id,name from city where fk_province='"+this.getBean().getPeProvince().getId()+"'";
			List resultList = null;
			try {
				resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL );
			} catch (EntityException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			String inputProvince = getBean().getCity().getName();
			String tempProvince = "";
			for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
				Object[] tempObjects = (Object[]) iterator.next();
				tempProvince = (String) tempObjects[1];
				if((tempProvince.indexOf(inputProvince) != -1 || inputProvince.indexOf(tempProvince) != -1) && tempProvince.equals(inputProvince)) {
					City city =  (City)getMyListService().getById(City.class,(String) tempObjects[0]);
					getBean().setCity(city);
					flag = true;
				}
			}
			if(!flag){
				throw new EntityException("添加失败，市区信息错误!");
			}
		}
		if(getBean().getCounty() != null && this.getBean().getCity() != null){
			getGeneralService().getGeneralDao().setEntityClass(County.class);
			String queryAllPeProvinceSQL = "select id,name from county where fk_city='"+getBean().getCity().getId()+"'";
			List resultList = null;
			try {
				resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL );
			} catch (EntityException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			String inputProvince = getBean().getCounty().getName();
			String tempProvince = "";
			for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
				Object[] tempObjects = (Object[]) iterator.next();
				tempProvince = (String) tempObjects[1];
				if((tempProvince.indexOf(inputProvince) != -1 || inputProvince.indexOf(tempProvince) != -1) && tempProvince.equals(inputProvince)) {
					County city =  (County)getMyListService().getById(County.class,(String) tempObjects[0]);
					getBean().setCounty(city);
					flag = true;
				}
			}
			if(!flag){
				throw new EntityException("添加失败，县区信息错误!");
			}
		}
		//校验参训学段学科是否符合标准字段
		if(this.getBean().getSubject() != null){
			getGeneralService().getGeneralDao().setEntityClass(PeSubject.class);
			String queryAllPeSubjectSQL = "select id,name from pe_subject ps where ps.name='"+this.getBean().getSubject()+"'";
			List resultList = null;
			try {
				resultList = getGeneralService().getBySQL(queryAllPeSubjectSQL);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(resultList != null && resultList.size() > 0){
				
			}else{
				throw new EntityException("添加失败，参训学段学科信息有误!");
			}
		}
//		 setTraineeProvince();
		setNewTraineeStatusTraining();	// 根据操作用户的权限设置学员培训状态
		
		//如果添加学员单位是承办单位，则设置学员培训单位为当前用户所在单位
		if("2".equals(getUserSession().getRoleType())) {
			getBean().setPeUnitByFkTrainingUnit(getCurrentUserBelongUnit());
		}
		EnumConst isGraduate = this.getMyListService().getEnumConstByNamespaceCode("FkGraduted", "0");
		EnumConst isCheckPass = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65231");
		EnumConst trainingStatus = this.getMyListService().getEnumConstByNamespaceCode("FkStatusTraining", "001");
		//设置学员默认是未结业、未通过审核、培训状态为正常
		if(getBean().getEnumConstByFkCheckedTrainee()==null){
			getBean().setEnumConstByFkCheckedTrainee(isCheckPass);
		}
		if(getBean().getEnumConstByFkGraduted()==null){
			getBean().setEnumConstByFkGraduted(isGraduate);
		}
		if(getBean().getEnumConstByFkStatusTraining()==null){
			getBean().setEnumConstByFkStatusTraining(trainingStatus);
		}
		
	}
	
	/**
	 * 对比输入省份与数据库中的省份是否相符，相符则作相应操作
	 */
	public void compareAndOperateProvince() {
		getGeneralService().getGeneralDao().setEntityClass(PeProvince.class);
		String queryAllPeProvinceSQL = "select id,name from pe_province";
		List resultList = null;
		try {
			resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL );
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String inputProvince = getBean().getProvince();
		String tempProvince = "";
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Object[] tempObjects = (Object[]) iterator.next();
			tempProvince = (String) tempObjects[1];
			if(tempProvince.indexOf(inputProvince) != -1 || inputProvince.indexOf(tempProvince) != -1) {
				setTraineeProvince((String) tempObjects[0]);
			}
		}
	}
	

	/**
	 * @description 设置学员省份
	 * @param peProvinceId 省份id
	 */
	private void setTraineeProvince(String peProvinceId) {
		PeProvince province = (PeProvince) getMyListService().getById(PeProvince.class,peProvinceId);
		getBean().setPeProvince(province);
	}


	/**
	 * 根据操作用户的权限设置新增学员的培训状态
	 */
	private void setNewTraineeStatusTraining() {
		SsoUser currentUser = getCurrentSsoUser();
		String currentUserRoleEnumCodeString = getUserSession().getRoleType();
		EnumConst enumConst = null;
		if(currentUserRoleEnumCodeString.equals("2")) {
			enumConst = getMyListService().getEnumConstByNamespaceCode("FkStatusTraining", "003");
		}else if(currentUserRoleEnumCodeString.equals("4") || currentUserRoleEnumCodeString.equals("3")) {
			enumConst = getMyListService().getEnumConstByNamespaceCode("FkStatusTraining", "001");
		}
		getBean().setEnumConstByFkStatusTraining(enumConst);
	}

	//添加学员时如果没有设置省份则设置学员省份为当前登录用户所在的省份
//	private void setTraineeProvince() {
//		SsoUser currentSsoUser = getCurrentSsoUser();
//		String getManagerUnitFKSQL = "select FK_UNIT from pe_manager where FK_SSO_USER_ID=:theSsoUserId";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("theSsoUserId", currentSsoUser.getId());
//		List<String> result = null;
//		try {
//			result = this.getGeneralService().getBySQL(getManagerUnitFKSQL, params);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		String managerUnitFK = "";
//		if(result != null && result.size() != 0) {
//			managerUnitFK = result.get(0);
//		}
//		String getUnitFKToProvinceSQL = "select FK_PROVINCE from pe_unit where id=:theUnitId";
//		params.clear();
//		params.put("theUnitId", managerUnitFK);
//		try {
//			result = getGeneralService().getBySQL(getUnitFKToProvinceSQL, params);
//		} catch (EntityException e1) {
//			e1.printStackTrace();
//		}
//		String managerUnitProvinceFK = "";
//		if (result != null && result.size() != 0) {
//			managerUnitProvinceFK = result.get(0);
//		}
//		try {
//			getGeneralService().getGeneralDao().setEntityClass(PeProvince.class);
//			PeProvince province = (PeProvince) getGeneralService().getById(managerUnitProvinceFK);
//			getBean().setPeProvince(province);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//	}

	//设置报送单位为当前登录用户所在的单位
	protected void setTraineeUnitFrom() {
		SsoUser currentSsoUser = getCurrentSsoUser();
		String getManagerUnitFKSQL = "select FK_UNIT from pe_manager where FK_SSO_USER_ID=:theSsoUserId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theSsoUserId", currentSsoUser.getId());
		List<String> result = null;
		try {
			result = this.getGeneralService().getBySQL(getManagerUnitFKSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String managerUnitFK = "";
		if(result != null && result.size() != 0) {
			managerUnitFK = result.get(0);
		}
		PeUnit unit = null;
		try {
			unit = (PeUnit) getGeneralService().getById(PeUnit.class, managerUnitFK);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getBean().setPeUnitByFkUnitFrom(unit);
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		String currentUserRoleCode = getUserSession().getRoleType();
		if("3".equals(currentUserRoleCode)){
			for(int i=0;i<idList.size();i++){
				String id = idList.get(i).toString();
				PeTrainee oldBean = (PeTrainee) this.getGeneralService().getById(PeTrainee.class,id);
				if(oldBean.getEnumConstByFkCheckedTrainee().getCode().equals("65230")){
					throw new EntityException("包含已经通过审核的学员，无法删除！");
				}
			}
		}
		
	}
	/**
	 * @description 更新记录前检查
	 */
	@Override
	public void checkBeforeUpdate() throws EntityException {
		PeTrainee oldBean = (PeTrainee) this.getGeneralService().getById(PeTrainee.class,
				this.getBean().getId());
		// 承办单位没有更改学员姓名的权利
		String currentUserRoleCode = getUserSession().getRoleType();
//		if("2".equals(currentUserRoleCode) && !oldBean.getName().equals(getBean().getName())) {
//			throw new EntityException("不允许承办单位修改学员姓名！");
//		}
		if("3".equals(currentUserRoleCode) && oldBean.getEnumConstByFkCheckedTrainee().getCode().equals("65230")){
			throw new EntityException("无法修改已经通过审核的学员！");
		}
		
		/*
		 * 20170609
		 * 新增分角色权限判断
		 * 上级管理员审核过后 本级管理员不能进行学员编辑
		 */
		if("2".equals(getUserSession().getRoleType())){//承办单位
			String code = oldBean.getEnumConstByFkCheckedTrainee() == null ? "" : oldBean.getEnumConstByFkCheckedTrainee().getCode();
			if(!"".equals(code) && "65235".equals(code)){//省厅审核
				throw new EntityException("学员"+oldBean.getName()+"已被省厅审核通过，您无权编辑！");
			}
			if(!"".equals(code) && "65230".equals(code)){//项目办审核
				throw new EntityException("学员"+oldBean.getName()+"已被项目办审核通过，您无权编辑！");
			}
		} else if ("3".equals(getUserSession().getRoleType())) {//省厅
			String code = oldBean.getEnumConstByFkCheckedTrainee() == null ? "" : oldBean.getEnumConstByFkCheckedTrainee().getCode();
			if(!"".equals(code) && "65230".equals(code)){//项目办审核
				throw new EntityException("学员"+oldBean.getName()+"已被项目办审核通过，您无权编辑！");
			}
		} else if("4".equals(getUserSession().getRoleType()) || "5".equals(getUserSession().getRoleType())){
		} else {
			throw new EntityException("无操作权限！");
		}
//		System.out.println(getBean().getProvince());
//		if(getBean().getProvince() != null) {
//			compareAndOperateProvince();	//设置学员的省份
//		}else{
//			if(this.getUserSession().getRoleType().equals("3")){
//				this.getBean().setPeProvince(this.getCurrentUnitProvince());
//			}
//		}
	    if(getBean().getProvince() != null) {
	    	if(!getBean().getProvince().equals(oldBean.getPeProvince().getName())){
	    		throw new EntityException("该学员省份设置错误，请设置与原学员相同的省份！");
	    	}
			compareAndOperateProvince();	//设置学员的省份
		}else{
			if(this.getBean().getPeProvince() != null && !getBean().getPeProvince().getName().equals(oldBean.getPeProvince().getName())){
	    		throw new EntityException("该学员省份设置错误，请设置与原学员相同的省份！");
	    	}
			
			this.getBean().setPeProvince(oldBean.getPeProvince());
			
//			if(this.getUserSession().getRoleType().equals("3")){//省厅
//				this.getBean().setPeProvince(this.getCurrentUnitProvince());
//			} else if(this.getUserSession().getRoleType().equals("2")){//院校
//				this.getBean().setPeProvince(this.oldTrainee.getPeProvince());
//			} else {
//				this.getBean().setPeProvince(this.oldTrainee.getPeProvince());
//			}
		}
		if(this.getBean().getPeProvince() != null && this.getBean().getCity() != null){
			getGeneralService().getGeneralDao().setEntityClass(City.class);
			String queryAllPeProvinceSQL = "select c.id,c.name from city c left join pe_province p on c.fk_province=p.id where p.name='"+this.getBean().getPeProvince().getName()+"'";
			List resultList = null;
			try {
				resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL );
			} catch (EntityException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			String inputProvince = getBean().getCity().getName();
			String tempProvince = "";
			for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
				Object[] tempObjects = (Object[]) iterator.next();
				tempProvince = (String) tempObjects[1];
				if((tempProvince.indexOf(inputProvince) != -1 || inputProvince.indexOf(tempProvince) != -1) && tempProvince.equals(inputProvince)) {
					City city =  (City)getMyListService().getById(City.class,(String) tempObjects[0]);
					getBean().setCity(city);
					flag = true;
				}
			}
			if(!flag){
				throw new EntityException("添加失败，市区信息错误!");
			}
		}
		if(getBean().getCounty() != null && this.getBean().getCity() != null){
			getGeneralService().getGeneralDao().setEntityClass(County.class);
			String queryAllPeProvinceSQL = "select id,name from county where fk_city='"+getBean().getCity().getId()+"'";
			List resultList = null;
			try {
				resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL );
			} catch (EntityException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			String inputProvince = getBean().getCounty().getName();
			String tempProvince = "";
			for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
				Object[] tempObjects = (Object[]) iterator.next();
				tempProvince = (String) tempObjects[1];
				if((tempProvince.indexOf(inputProvince) != -1 || inputProvince.indexOf(tempProvince) != -1) && tempProvince.equals(inputProvince)) {
					County city =  (County)getMyListService().getById(County.class,(String) tempObjects[0]);
					getBean().setCounty(city);
					flag = true;
				}
			}
			if(!flag){
				throw new EntityException("添加失败，县区信息错误!");
			}
		}
		//校验参训学段学科是否符合标准字段
		if(this.getBean().getSubject() != null){
			getGeneralService().getGeneralDao().setEntityClass(PeSubject.class);
			String queryAllPeSubjectSQL = "select id,name from pe_subject ps where ps.name='"+this.getBean().getSubject()+"'";
			List resultList = null;
			try {
				resultList = getGeneralService().getBySQL(queryAllPeSubjectSQL);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(resultList != null && resultList.size() > 0){
				
			}else{
				throw new EntityException("添加失败，参训学段学科信息有误!");
			}
		}
		getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
	}

	/**
	 * 在修改学员信息之后需要将学员“修改是否通过”状态改成默认值(未通过)
	 */
	@Override
	public Map update() {
		Map resultMap = super.update();
		setTraineeModifyStatusToDefault();
		return resultMap;
	}
	// 将学员“修改是否通过”状态改成默认值(未通过)
	public void setTraineeModifyStatusToDefault() {
		EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkModifyChecked", "1985");
		String updateTraineeFKModify = "update pe_trainee set FK_MODIFY_CHECKED=:theDefaultValue where id=:theTraineeId";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theDefaultValue", enumConst.getId());
		paramsMap.put("theTraineeId", getBean().getId());
		try {
			getGeneralService().executeBySQL(updateTraineeFKModify, paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @description 在更新学员信息前更新与之关联的Sso_user对象Loginid
	 * @param loginId 更改后的登录名
	 * @param id 将要更改信息的学员的id号
	 */
	public void updateSso(String loginId, String id) {
		String getSsoIdSQL = "select FK_SSO_USER_ID from pe_trainee where id=:theId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theId", id);
		List<String> result = null;
		try {
			 result = this.getGeneralService().getBySQL(getSsoIdSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String traineeFKToSso = "";
		if(result != null && result.size() != 0) {
			traineeFKToSso = result.get(0);
		}
		String updateSQL = "update sso_user set login_id=:theLoginId where id=:theId";
		params.clear();
		params.put("theLoginId", loginId);
		params.put("theId", traineeFKToSso);
		try {
			this.getGeneralService().executeBySQL(updateSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	

	// 获取当前登录用户对应的Sso_User
	public SsoUser getCurrentSsoUser() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser currentSsoUser = userSession.getSsoUser();
		
		return currentSsoUser;
	}
	/**
	 * 获取当前用户所在单位的省份
	 * @return 当前用户所在单位的省份
	 */
	public PeProvince getCurrentUnitProvince() {
		SsoUser sso=this.getCurrentSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createAlias("ssoUser","ssoUser");
		DetachedCriteria dcpeUnit = dc.createCriteria("peUnit","peUnit");
		dcpeUnit.createAlias("peProvince","peProvince");
		dc.add(Restrictions.eq("ssoUser", sso));
		List list=new LinkedList();
		PeManager mgr=null;
		try {
			list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				mgr=(PeManager) list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(mgr!=null&&mgr.getPeUnit()!=null){
			return mgr.getPeUnit().getPeProvince();
		}
		return null;
	}
	
//	@Override
//	public int excelAdd()throws EntityException {
//		Workbook work = null;
//		File file = this.get_upload();
//		try {
//			work = Workbook.getWorkbook(file);
//		} catch (Exception e){
//			e.printStackTrace();
//			throw new EntityException("Excel表格读取异常！导入失败！<br/>");
//		}
//		Sheet sheet = work.getSheet(0);
//		int rows = sheet.getRows();			//获取Excel表格的行数
//		if(rows<2){
//			throw new EntityException("表格为空！<br/>");
//		}
//		String message="";
//		String temp = "";
//		int j= 0;
//		List<ColumnConfig> columns = this.getGridConfig().getListColumnConfig();
//		/**
//		 * i从1开始，跳过第一列id
//		 */
//		for (int i=1; i<columns.size(); i++) {
//			ColumnConfig columnConfig = columns.get(i);
//			if(!columnConfig.isAdd()){
//				continue;
//			}
//			try {
//				temp = sheet.getCell(j, 0).getContents().trim();
//				j++;
//				if(temp!=null&&temp.length()>0&&temp.indexOf(columnConfig.getName())>=0){
//				} else {
//					message+="第1行第"+(j)+"列单元格内容错误<br/>";
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new EntityException("Excel表格读取异常！导入失败！<br/>");
//			}
//		}
//		if(message.length()>0){
//			throw new EntityException(message+"<br/>请检查Excel表格正确后重新上传。");
//		}
//		List list = excelToBean(sheet);
///*		for (int i = 0; i < list.size(); i++) {
//			list.set(i, setSubIds(list.get(i)));
//		}*/
//		list = this.checkBeforeBatchAdd(list);
//		try {
//			this.getPeTraineeService().saveList(list);
//		} catch (Exception e) {
//			throw new EntityException(this.checkAlternateKey(e, "保存").get("info").toString());
//		}
//		work.close();
//		return list.size();
//	}
	
	public String batchAddExcel(){
		this.setTogo("back");
		init();
		initGrid4BatchAddExcel();
		int count;
		Map map = new HashMap();
		try {
			this.set_upload(getFile());
			count = excelAdd();
		} catch (EntityException e) {
			this.operateLog("Excel上传" + this.getExcelInfo() + "失败");
			map.put("success", "false");
			map.put("info", e.getMessage());
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		} catch (Exception e) {
			this.operateLog("Excel上传" + this.getExcelInfo() + "失败");
			map.put("success", "false");
			map.put("info", "文件上传失败");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		
		map.put("success", "true");
		map.put("info", "导入成功<br/> 共上传"+count+"条记录");
		this.setJsonString(JsonUtil.toJSONString(map));
		
		this.operateLog("Excel上传" + this.getExcelInfo() + "成功");
		return json();
	}
	
	/**
	 * 获取当前登录用户所在单位
	 * @return
	 */
	public PeUnit getCurrentUserBelongUnit() {
		SsoUser sso=this.getCurrentSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createCriteria("peUnit","peUnit");
		dc.add(Restrictions.eq("ssoUser", sso));
		List<PeManager> managerList = null;
		try {
			managerList = getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(managerList != null && managerList.size() != 0) {
			return managerList.get(0).getPeUnit();
		}
		return null;
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByFkGender", "enumConstByFkGender",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkCheckedTrainee", "enumConstByFkCheckedTrainee",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkGraduted", "enumConstByFkGraduted",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkModifyChecked", "enumConstByFkModifyChecked",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peProApplyno", "peProApplyno",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peSubject", "peSubject", DetachedCriteria.LEFT_JOIN);
		String currentUserRoleTypeCodeString = getUserSession().getRoleType();
		if("3".equals(currentUserRoleTypeCodeString)){//省厅师范处
			dc.createAlias("folk", "folk", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("peProvince", "peProvince");
			dc.add(Restrictions.eq("peProvince",this.getCurrentUnitProvince()));
			dc.createAlias("county", "county",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("county.city", "city",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("education", "education", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("jobTitle", "jobTitle", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("unitAttribute", "unitAttribute", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("unitType", "unitType", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("mainTeachingGrade", "mainTeachingGrade", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("mainTeachingSubject", "mainTeachingSubject", DetachedCriteria.LEFT_JOIN);
		}else{
			dc.createAlias("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("folk", "folk", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("county", "county",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("county.city", "city",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("education", "education", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("jobTitle", "jobTitle", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("unitAttribute", "unitAttribute", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("unitType", "unitType", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("mainTeachingGrade", "mainTeachingGrade", DetachedCriteria.LEFT_JOIN);
			dc.createAlias("mainTeachingSubject", "mainTeachingSubject", DetachedCriteria.LEFT_JOIN);
			
		}
		if("2".equals(currentUserRoleTypeCodeString)){// 承办单位
			dc.createCriteria("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit");
			dc.add(Restrictions.eq("peUnitByFkTrainingUnit", getCurrentUserBelongUnit()));
		}else{
			dc.createCriteria("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit",
					DetachedCriteria.LEFT_JOIN);
		}
		dc.createCriteria("peUnitByFkUnitFrom", "peUnitByFkUnitFrom",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkStatusTraining", "enumConstByFkStatusTraining", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

//	public Map add() {
//		Map map = new HashMap();
//		String linkUrl = null;
//		this.superSetBean((PeTrainee)setSubIds(this.getBean()));
//		try {
//			checkBeforeAdd();
//		} catch (EntityException e1) {
//			map.put("success", "false");
//			map.put("info", e1.getMessage());
//			return map;
//		}
//		PeTrainee instance = null;
//		try {
//			instance = (PeTrainee) this.getPeTraineeService().save(this.getBean());
//			map.put("success", "true");
//			map.put("info", "添加成功");
//			this.operateLog("添加" + this.getDataInfo() + "成功");
//		}catch (Exception e) {
//			this.operateLog("添加" + this.getDataInfo() + "失败");
//			return this.checkAlternateKey(e, "添加");
//		}
//		return map;
//	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTraineeAction";
	}

	public PeTrainee getBean() {
		return (PeTrainee) super.superGetBean();
	}

	public void setBean(PeTrainee bean) {
		super.superSetBean(bean);
	}

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public PeTraineeService getPeTraineeService() {
		return peTraineeService;
	}
	public void setPeTraineeService(PeTraineeService peTraineeService) {
		this.peTraineeService = peTraineeService;
	}


	
}