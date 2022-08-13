package com.whaty.platform.entity.web.action.basic;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.City;
import com.whaty.platform.entity.bean.County;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTraineeAdu;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.util.Const;
import com.whaty.util.JsonUtil;

public class PeTraineeInTrainingAction extends PeTraineeAction
{
  private boolean filterUserInfo;
  private PeTrainee oldTrainee;

  public void initGrid()
  {
    getGridConfig().setCapability(false, false, true, true, false);
    getGridConfig().setTitle(getText("参训学员管理"));
    
    if ("2".equals(getUserSession().getRoleType())) {
//		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("姓名"), "name");
//		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,false,true,"TextField",false,10,"");
//		this.getGridConfig().addColumn(this.getText("年龄"), "age", true, false, true, "TextField", false, 20);
////		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 20);
//		this.getGridConfig().addColumn(this.getText("民族"), "folk.name", true, false, true, "", false, 20);
//		this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, false, true, "TextField", false, 50, null);
//		this.getGridConfig().addColumn(this.getText("学校所在区域"), "unitAttribute.name",true, false, true, "TextField", false, 50, null);
//		this.getGridConfig().addColumn(this.getText("学校类别"), "unitType.name",true, false, true, "TextField", false, 50, null);
////		this.getGridConfig().addColumn(this.getText("学历"), "education", true, true, true, "", true, 20);
////		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng", true, true, true, "", false, 25);
//		this.getGridConfig().addColumn(this.getText("最高学历"), "education.name", true, false, true, "", false, 50);
//		this.getGridConfig().addColumn(this.getText("职称"), "jobTitle.name", true, false, true, "", false, 50);
//		this.getGridConfig().addColumn(this.getText("主要任教学段"), "mainTeachingGrade.name", true, false, true, "", false, 50);
//		this.getGridConfig().addColumn(this.getText("主要任教学科"), "mainTeachingSubject.name", true, false, true, "", false, 50);
//		this.getGridConfig().addColumn(this.getText("毕业院校"), "graduation", true,false, true, "TextField", false, 50, null);
//		this.getGridConfig().addColumn(this.getText("所学专业"), "major", true,false, true, "TextField", false, 50, null);
	  initPublicGrid();
	  getGridConfig().addColumn(getText("职务"), "zhiwu", true, true, true, "", true, 25);
	  getGridConfig().addColumn(getText("教龄"), "workyear", true, true, true, "", false, 50);
      getGridConfig().addColumn(getText("手机"), "telephone", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("电子邮箱"), "email", false, true, true, "TextField", false, 50, Const.email_for_extjs);
      getGridConfig().addColumn(getText("学科"), "peSubject.name", true, false, true, "TextField", false, 255);
      getGridConfig().addColumn(getText("培训项目"), "peProApplyno.name", true, false, true, "TextField", true, 50);
      getGridConfig().addColumn(getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("办公电话"), "officePhone", false, true, true, "", true, 20);
      getGridConfig().addColumn(getText("是否结业"), "enumConstByFkGraduted.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("参训状态"), "enumConstByFkStatusTraining.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("证书编号"), "certificateNumber", true, true, true, "", true, 20);
      getGridConfig().addColumn(getText("开始时间"), "startDate", false, true, true, "Date", true, 20);
      getGridConfig().addColumn(getText("结束时间"), "endDate", false, true, true, "Date", true, 20);
      getGridConfig().addColumn(getText("条件要求得分"), "yaoqiudefen", true, true, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("学习过程得分"), "guochengdefen", true, true, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("学习成效得分"), "chengxiaodefen", true, true, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("评价意见"), "yijian", true, true, true, "TextField", true, 100);
      getGridConfig().addColumn(getText("备注1"), "note", true, true, true, "TextField", true, 50);
      getGridConfig().addColumn(getText("备注2"), "notesecond", true, true, true, "TextField", true, 50);
      getGridConfig().addMenuFunction(getText("学员未报到"), getServletPath() + "_traineeNotRegist.action", false, true);
      getGridConfig().addMenuScript(getText("替换学员"), getReplaceScript());
    } else if ("3".equals(getUserSession().getRoleType())) {
      initPublicGrid();
      getGridConfig().setCapability(false, false, true, true, false);
      getGridConfig().setCanExcelExport(true);
      getGridConfig().addColumn(getText("职务"), "zhiwu", true, true, true, "", true, 25);
	  getGridConfig().addColumn(getText("教龄"), "workyear", true, true, true, "", false, 50);
      getGridConfig().addColumn(getText("手机"), "telephone", false, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("电子邮箱"), "email", false, true, true, "TextField", false, 50, Const.email_for_extjs);
      getGridConfig().addColumn(getText("学科"), "peSubject.name", true, false, true, "TextField", true, 255);
      getGridConfig().addColumn(getText("培训项目"), "peProApplyno.name", true, false, true, "TextField", true, 50);
      getGridConfig().addColumn(getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("办公电话"), "officePhone", false, true, true, "", true, 20);

      ColumnConfig cc = new ColumnConfig(getText("培训单位"), "peUnitByFkTrainingUnit.name");
      cc.setAdd(false);
      cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code='1526'");
      getGridConfig().addColumn(cc);
      getGridConfig().addColumn(getText("是否结业"), "enumConstByFkGraduted.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("参训学段学科"), "subject", true, false, true, "TextField", true, 255);
      getGridConfig().addColumn(getText("参训状态"), "enumConstByFkStatusTraining.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("证书编号"), "certificateNumber", true, false, true, "", true, 20);
      getGridConfig().addColumn(getText("条件要求得分"), "yaoqiudefen", true, false, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("学习过程得分"), "guochengdefen", true, false, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("学习成效得分"), "chengxiaodefen", true, false, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("评价意见"), "yijian", true, false, true, "TextField", true, 100);
      getGridConfig().addColumn(getText("备注1"), "note", true, true, true, "TextField", true, 50);
      getGridConfig().addColumn(getText("备注2"), "notesecond", true, true, true, "TextField", true, 50);
      getGridConfig().addMenuFunction(getText("学员未报到"), getServletPath() + "_traineeNotRegist.action", false, true);
      getGridConfig().addMenuScript(getText("替换学员"), getReplaceScript());
    } else { 
      initPublicGrid();
      getGridConfig().addColumn(getText("职务"), "zhiwu", true, true, true, "", true, 25);
      getGridConfig().addColumn(getText("教龄"), "workyear", true, true, true, "", false, 50);
      getGridConfig().addColumn(getText("手机"), "telephone", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("电子邮箱"), "email", true, true, true, "TextField", false, 50, Const.email_for_extjs);
      getGridConfig().addColumn(getText("培训项目"), "peProApplyno.name", true, false, true, "TextField", true, 50, null, "WhatyComboBox");
      getGridConfig().addColumn(getText("办公电话"), "officePhone", true, true, true, "", true, 20);
      getGridConfig().addColumn(getText("参训状态"), "enumConstByFkStatusTraining.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("学科"), "peSubject.name", true, false, true, "TextField", true, 255);
      getGridConfig().addColumn(this.getText("所在地区（省）"), "peProvince.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(this.getText("所在地区（市）"), "city.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(this.getText("所在地区（县）"), "county.name", true, true, true, "TextField", false, 20);
      getGridConfig().addColumn(getText("是否结业"), "enumConstByFkGraduted.name", true, false, true, "TextField", true, 20);
      getGridConfig().addColumn(getText("参训学段学科"), "subject", true, true, true, "TextField", true, 255);

      ColumnConfig cc = new ColumnConfig(getText("培训单位"), "peUnitByFkTrainingUnit.name");
      cc.setAdd(false);
      cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code='1526'");
      getGridConfig().addColumn(cc);
      getGridConfig().addColumn(getText("填报单位"), "peUnitByFkUnitFrom.name", true, false, true, "TextField", true, 50);
      getGridConfig().addColumn(getText("证书编号"), "certificateNumber", true, true, true, "", true, 20);
      getGridConfig().addColumn(getText("开始时间"), "startDate", false, true, true, "Date", true, 20);
      getGridConfig().addColumn(getText("结束时间"), "endDate", false, true, true, "Date", true, 20);
      getGridConfig().addColumn(getText("条件要求得分"), "yaoqiudefen", true, true, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("学习过程得分"), "guochengdefen", true, true, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("学习成效得分"), "chengxiaodefen", true, true, true, "TextField", true, 30, Const.number_for_extjs);
      getGridConfig().addColumn(getText("评价意见"), "yijian", true, true, true, "TextField", true, 100);
      getGridConfig().addColumn(getText("备注1"), "note", true, true, true, "TextField", true, 50);
      getGridConfig().addColumn(getText("备注2"), "notesecond", true, true, true, "TextField", true, 50);
      getGridConfig().addMenuFunction(getText("学员未报到"), getServletPath() + "_traineeNotRegist.action", false, true);
      getGridConfig().addMenuScript(getText("替换学员"), getReplaceScript());
      getGridConfig().addMenuFunction(getText("审核已结业"), new String[] { "graduate" });
      getGridConfig().addMenuFunction(getText("审核未结业"), new String[] { "ungraduate" });
    }

    StringBuilder script1 = new StringBuilder();
    script1.append(" {");
    script1.append(" var downloadexcel = new Ext.Action({                                                           ");
    script1.append("         text: '下载标准Excel格式',                                                             ");
    script1.append("         handler: function(){                                                                   ");
    script1.append(" \t\t\tvar tmp = \"<form target='_blank' action='/test/importCertificateTemplate.jsp' name='exportExcel'></form>\"; ");
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
    script1.append("        title: '导入证书',");
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
    script1.append(" \t\t \t\t        \turl:'" + getServletPath() + "_importCertificate.action' ,");
    script1.append(" \t\t\t\t            waitMsg:'处理中，请稍候...',");
    script1.append(" \t\t\t\t\t\t\tsuccess: function(form, action) {");
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
    if (("2".equals(getUserSession().getRoleType())) || ("4".equals(getUserSession().getRoleType()))) {
      getGridConfig().addMenuScript(getText("导入证书"), script1.toString());
    }
    getGridConfig().setPrepared(false);
  }

  public String getReplaceScript()
  {
    return 
      "{ var m = grid.getSelections();  if(m.length == 1){\t         Ext.MessageBox.confirm(\"请确认\",\"是否真的要替换选定的学员吗?\",function(button,text){  if(button=='yes'){  \tvar jsonData = '';       \t\tvar ss =  m[0].get('id');\t  openReplaceTrainingWin(ss);                       }}); } else if(m.length == 0){                    \tExt.MessageBox.alert('" + 
      getText("test.error") + "', '请先选择一个学员进行替换!');  " + 
      "} else{ " + 
      "\tExt.MessageBox.alert('" + getText("test.error") + "', '一次最多只能替换一个学员!');  " + 
      "}}                         ";
  }

  @SuppressWarnings("rawtypes")
public void checkBeforeAdd()
    throws EntityException
  {
    PeTrainee peTrainee = (PeTrainee)getGeneralService().getById(PeTrainee.class, getBean().getId());
    if (peTrainee == null) {
      throw new EntityException("选择学员错误，请重新选择！");
    }
    if (!peTrainee.getEnumConstByFkStatusTraining().getCode().equals("002")) {
      throw new EntityException("只能替换未报到的学员，请先将该学员设置为未报到！");
    }
    
    /*
     * 20170609
     * 新增：增加对原学员的替换资格审查，即原学员在替换记录表中存在数据的 不能再次成为被替换学员
     */
    getGeneralService().getGeneralDao().setEntityClass(PeTraineeAdu.class);
	String queryAllPeProvinceSQL = "select id,fk_old_trainee from pe_trainee_adu where fk_old_trainee='"+getBean().getId()+"'";
	List resultList = null;
	try {
		resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL);
	} catch (EntityException e) {
		e.printStackTrace();
	}
	if(resultList != null && resultList.size() > 0){
		 throw new EntityException("该学员已经被替换，请选择其他学员进行替换！");
	}
	
    this.oldTrainee = peTrainee;
    getBean().setId(null);
    getBean().setPeProApplyno(peTrainee.getPeProApplyno());
    getBean().setPeUnitByFkTrainingUnit(peTrainee.getPeUnitByFkTrainingUnit());
    getBean().setPeSubject(peTrainee.getPeSubject());
    getBean().setSubject(peTrainee.getSubject());
    
    if(getBean().getProvince() != null) {
    	if(!getBean().getProvince().equals(this.oldTrainee.getPeProvince().getName())){
    		throw new EntityException("该学员省份设置错误，请设置与原学员相同的省份！");
    	}
		compareAndOperateProvince();	//设置学员的省份
	}else{
		if(this.getBean().getPeProvince() != null && !getBean().getPeProvince().getName().equals(this.oldTrainee.getPeProvince().getName())){
    		throw new EntityException("该学员省份设置错误，请设置与原学员相同的省份！");
    	}
		
		this.getBean().setPeProvince(this.oldTrainee.getPeProvince());
		
//		if(this.getUserSession().getRoleType().equals("3")){//省厅
//			this.getBean().setPeProvince(this.getCurrentUnitProvince());
//		} else if(this.getUserSession().getRoleType().equals("2")){//院校
//			this.getBean().setPeProvince(this.oldTrainee.getPeProvince());
//		} else {
//			this.getBean().setPeProvince(this.oldTrainee.getPeProvince());
//		}
	}
	if(this.getBean().getPeProvince() != null && this.getBean().getCity() != null){
		getGeneralService().getGeneralDao().setEntityClass(City.class);
		queryAllPeProvinceSQL = "select id,name from city where fk_province='"+this.getBean().getPeProvince().getId()+"'";
		resultList = null;
		try {
			resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL);
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
		queryAllPeProvinceSQL = "select id,name from county where fk_city='"+getBean().getCity().getId()+"'";
		resultList = null;
		try {
			resultList = getGeneralService().getBySQL(queryAllPeProvinceSQL);
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
    
    /*
     * 20170609
     * 更新：新增替换学员的审核状态 需要根据不同管理员角色来设置 
     * 项目执行办管理员--审核通过
     * 省厅管理员--省厅审核通过
     * 承训院校管理员--学校审核通过
     * 
     */
//    getBean().setEnumConstByFkCheckedTrainee(peTrainee.getEnumConstByFkCheckedTrainee());
    EnumConst checked = null;
    if("2".equals(getUserSession().getRoleType())) {//承办单位
    	checked = getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65234");
    } else if ("3".equals(getUserSession().getRoleType())) {//省厅
    	checked = getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65235");
    } else if ("4".equals(getUserSession().getRoleType()) || "5".equals(getUserSession().getRoleType())){//项目办or班主任项目
    	checked = getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65230");
    }else{//默认
    	checked = getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65231");
    }
    getBean().setEnumConstByFkCheckedTrainee(checked);
    
    EnumConst isGraduate = getMyListService().getEnumConstByNamespaceCode("FkGraduted", "0");
    getBean().setEnumConstByFkGraduted(isGraduate);
    
    //更新：新替换的学员 参训状态为“新增” --20170609
//    if (getBean().getEnumConstByFkStatusTraining() == null) {
//      getBean().setEnumConstByFkStatusTraining(trainingStatus);
//    }
    EnumConst trainingStatus = getMyListService().getEnumConstByNamespaceCode("FkStatusTraining", "003");
    getBean().setEnumConstByFkStatusTraining(trainingStatus);
    setTraineeUnitFrom();
    if ((getBean().getTelephone() == null) || (getBean().getTelephone().trim().length() == 0)) {
      DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
      List list = null;
      String tempTelephone = getRandomString();
      while (true) {
        dc.add(Restrictions.eq("loginId", tempTelephone));
        list = getGeneralService().getList(dc);
        if ((list == null) || (list.size() == 0))
          break;
        tempTelephone = getRandomString();
      }
      getBean().setLoginId(tempTelephone);
    } else {
      getBean().setLoginId(getBean().getTelephone().trim());
    }

    //更新 按照手机号、身份证号、项目 进行排他检查  --20170609
    //更新 增加 按照参训状态筛选（参训状态为正常或新增--不包含未报到 条件）--20171115
    List list = getGeneralService().getBySQL("select t.id from pe_trainee t where (t.login_id ='" + getBean().getLoginId() + "' or t.idcard='"+getBean().getIdcard()+"') "
    		+ " and t.fk_pro_applyno='" + getBean().getPeProApplyno().getId() + "'"
    		+ " and t.fk_status_training <> '20100826002' ");
    if ((list != null) && (!list.isEmpty()))
      throw new EntityException("系统中存在相同的培训项目、手机号和身份证号！学员已存在！");
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
			/*
			 * 20171011
			 * 修改分角色判断逻辑
			 * 承办单位没有更改学员姓名的权利
			 * 已结业的不让修改任何信息
			 */
			if(oldBean.getEnumConstByFkGraduted() != null && oldBean.getEnumConstByFkGraduted().getCode().equals("1")){
				throw new EntityException("学员已结业！");
			}
			
			String code = oldBean.getEnumConstByFkCheckedTrainee() == null ? "" : oldBean.getEnumConstByFkCheckedTrainee().getCode();
			if(!"".equals(code) && "65235".equals(code)){//省厅审核
				String idcard = oldBean.getIdcard() == null ? "" : oldBean.getIdcard();
				if(!oldBean.getName().equals(getBean().getName()) || !idcard.equals(getBean().getIdcard())) {
					throw new EntityException("不允许承办单位修改学员姓名和身份证号！");
				}
//				throw new EntityException("学员"+oldBean.getName()+"已被省厅审核通过，您无权编辑！");
			}
			if(!"".equals(code) && "65230".equals(code)){//项目办审核
				String idcard = oldBean.getIdcard() == null ? "" : oldBean.getIdcard();
				if(!oldBean.getName().equals(getBean().getName()) || !idcard.equals(getBean().getIdcard())) {
					throw new EntityException("不允许承办单位修改学员姓名和身份证号！");
				}
//				throw new EntityException("学员"+oldBean.getName()+"已被项目办审核通过，您无权编辑！");
			}
		} else if ("3".equals(getUserSession().getRoleType())) {//省厅
			/*
			 * 20171011
			 * 修改分角色判断逻辑
			 * 已结业的不让修改任何信息
			 */
			if(oldBean.getEnumConstByFkGraduted() != null && oldBean.getEnumConstByFkGraduted().getCode().equals("1")){
				throw new EntityException("学员已结业！");
			}
			String code = oldBean.getEnumConstByFkCheckedTrainee() == null ? "" : oldBean.getEnumConstByFkCheckedTrainee().getCode();
			if(!"".equals(code) && "65230".equals(code)){//项目办审核
				throw new EntityException("学员"+oldBean.getName()+"已被项目办审核通过，您无权编辑！");
			}
//			if(!"".equals(code) && "65233".equals(code)){//项目办审核
//				throw new EntityException("学员"+oldBean.getName()+"已被项目办审核未通过，您无权编辑！");
//			}
		} else if("4".equals(getUserSession().getRoleType()) || "5".equals(getUserSession().getRoleType())){
		} else {
			throw new EntityException("无操作权限！");
		}
		
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
		if(getBean().getTelephone() != null && getBean().getTelephone().trim().length() > 0) {
		      getBean().setLoginId(getBean().getTelephone().trim());
		}
		getBean().setPeProApplyno(oldBean.getPeProApplyno());
		List list = getGeneralService().getBySQL("select t.id from pe_trainee t where t.id<>'"+getBean().getId()+"' and t.idcard='"+getBean().getIdcard()+"' "
	    		+ " and t.fk_pro_applyno='" + getBean().getPeProApplyno().getId() + "'"
	    		+ " and t.fk_status_training <> '20100826002' ");
	    if ((list != null) && (!list.isEmpty()))
	    	 throw new EntityException("系统中存在相同的培训项目、手机号和身份证号！学员已存在！");
	     
		getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
	}
  
	private void modifyEndDates() {
		//modify bean's endDate property
		try {
			//get bean from action
			Method getBeanMethod = this.getClass().getMethod("getBean", null);
			Object tempBean = getBeanMethod.invoke(this, null);
			//modify tempBean's property
			List<ColumnConfig> list = this.getGridConfig().getListColumnConfig();
			for(int i = 0; i < list.size(); i++){
				ColumnConfig column = list.get(i);
				if(column.isAdd()
						&& column.getDataIndex().toLowerCase().endsWith("enddate")
						&& column.getDataIndex().indexOf(".") < 0){
					modifyEndDate(tempBean, column.getDataIndex());
				}
			}
			//set bean back to Action
			Class[] parameterTypes = new Class[1];
			parameterTypes[0] = tempBean.getClass();
			Method setBeanMethod = this.getClass().getMethod("setBean", parameterTypes);
			setBeanMethod.invoke(this, tempBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void modifyEndDate(Object bean, String fieldName){
		Method getMethod;
		Method setMethod;
		String getMethodName;
		String setMethodName;
		Date value;

		getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			getMethod = bean.getClass().getMethod(getMethodName, null);
			value = (Date) getMethod.invoke(bean, null);
			value.setTime(value.getTime() + 24*60*60*1000 -1);
			Class[] parameterTypes = new Class[1];
			parameterTypes[0] = Date.class;
			setMethod = bean.getClass().getMethod(setMethodName, parameterTypes);
			setMethod.invoke(bean, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String abstractAdd() {
		this.modifyEndDates();
		Map map = addReplace();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info", this.getText(("Add method is not implemented in Action")));
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map addReplace() {
		Map map = new HashMap();
		this.superSetBean((PeTrainee)setSubIds(this.getBean()));
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			e1.printStackTrace();
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		PeTrainee instance = null;
		try {
			instance = (PeTrainee) this.getGeneralService().save(this.getBean());
			
			PeTraineeAdu adu = new PeTraineeAdu();
			try {
				SsoUser currentSsoUser = getCurrentSsoUser();
				adu.setCreateTime(new Date());
				adu.setSsoUser(currentSsoUser);
				adu.setTraineeByFkNewTrainee(instance);
				adu.setTraineeByFkOldTrainee(this.oldTrainee);
				getGeneralService().getGeneralDao().setEntityClass(PeTraineeAdu.class);
				adu = (PeTraineeAdu) this.getGeneralService().save(adu);
				
				map.put("success", "true");
				map.put("info", "添加成功");
				this.operateLog("添加" + this.getDataInfo() + "成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				this.operateLog("添加" + this.getDataInfo() + "失败");
				return this.checkAlternateKey(e, "添加");
			}
			map.put("success", "true");
			map.put("info", "添加成功");
			this.operateLog("添加" + this.getDataInfo() + "成功");
		}catch (Exception e) {
			e.printStackTrace();
			this.operateLog("添加" + this.getDataInfo() + "失败");
			return this.checkAlternateKey(e, "添加");
		}
		return map;
	}

  public String traineeNotRegist()
  {
    Map msgMap = new HashMap();
    msgMap.put("success", "false");
    msgMap.put("info", "操作失败");

    EnumConst enumConst = getMyListService().getEnumConstByNamespaceCode("FkStatusTraining", "002");
    int successNum = 0;
    String message = "";
    if ((getIds() != null) && (getIds().length() != 0)) {
      String[] ids = getIds().split(",");
      List traineeList = null;
      DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PeTrainee.class);
      detachedCriteria.add(Restrictions.in("id", ids));
      try {
        traineeList = getGeneralService().getList(detachedCriteria);
      } catch (EntityException e) {
        e.printStackTrace();
      }
      for (int i = 0; i < traineeList.size(); i++) {
        PeTrainee trainee = (PeTrainee)traineeList.get(i);
        if ((trainee.getEnumConstByFkGraduted() != null) && (trainee.getEnumConstByFkGraduted().getCode().equals("1"))) {
          message = message + "学员" + trainee.getName() + ":已经结业！<br>";
        }
        else if ((trainee.getEnumConstByFkStatusTraining() != null) && (trainee.getEnumConstByFkStatusTraining().getCode().equals("003"))) {
          message = message + "学员" + trainee.getName() + ":是新增学员！<br>";
        }
        else {
          successNum++;
          trainee.setEnumConstByFkStatusTraining(enumConst);
          traineeList.set(i, trainee);
        }
      }
      if (message.length() > 0) {
        msgMap.put("success", "false");
        msgMap.put("info", "操作失败，失败原因：<br>" + message);
        setJsonString(JsonUtil.toJSONString(msgMap));
        return json();
      }
      try {
        getGeneralService().saveList(traineeList);
      } catch (EntityException e) {
        e.printStackTrace();
        setJsonString(JsonUtil.toJSONString(msgMap));
        return json();
      }
    }

    msgMap.put("success", "true");
    msgMap.put("info", successNum + "条数据操作成功！");
    setJsonString(JsonUtil.toJSONString(msgMap));
    return json();
  }

  public DetachedCriteria initDetachedCriteria()
  {
    DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
    dc.createCriteria("enumConstByFkGender", "enumConstByFkGender", 1);
    dc.createCriteria("enumConstByFkCheckedTrainee", "enumConstByFkCheckedTrainee", 1);
    dc.createCriteria("enumConstByFkGraduted", "enumConstByFkGraduted", 1);
    dc.createCriteria("enumConstByFkModifyChecked", "enumConstByFkModifyChecked", 1);
    dc.createCriteria("peProApplyno", "peProApplyno", 1);
    dc.createCriteria("peSubject", "peSubject", 1);
	dc.createAlias("folk", "folk", DetachedCriteria.LEFT_JOIN);
	dc.createAlias("education", "education", DetachedCriteria.LEFT_JOIN);
	dc.createAlias("jobTitle", "jobTitle", DetachedCriteria.LEFT_JOIN);
	dc.createAlias("unitAttribute", "unitAttribute", DetachedCriteria.LEFT_JOIN);
	dc.createAlias("mainTeachingGrade", "mainTeachingGrade", DetachedCriteria.LEFT_JOIN);
	dc.createAlias("mainTeachingSubject", "mainTeachingSubject", DetachedCriteria.LEFT_JOIN);
	dc.createAlias("unitType", "unitType", DetachedCriteria.LEFT_JOIN);
    String currentUserRoleTypeCodeString = getUserSession().getRoleType();
    if ("3".equals(currentUserRoleTypeCodeString)) {
    	dc.createAlias("peProvince", "peProvince");
		dc.add(Restrictions.eq("peProvince",this.getCurrentUnitProvince()));
		dc.createAlias("county", "county",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("county.city", "city",DetachedCriteria.LEFT_JOIN);
      dc.add(Restrictions.eq("peProvince", getCurrentUnitProvince()));
    } else {
    	dc.createAlias("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
    	dc.createAlias("county", "county",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("county.city", "city",DetachedCriteria.LEFT_JOIN);
    }
    if ("2".equals(currentUserRoleTypeCodeString)) {
      dc.createCriteria("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit");
      dc.add(Restrictions.eq("peUnitByFkTrainingUnit", getCurrentUserBelongUnit()));
    } else {
      dc.createCriteria("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit", 1);
    }
    dc.add(Restrictions.isNotNull("peUnitByFkTrainingUnit"));
    dc.createCriteria("peUnitByFkUnitFrom", "peUnitByFkUnitFrom", 1);
    dc.createCriteria("enumConstByFkStatusTraining", "enumConstByFkStatusTraining", 1);
    return dc;
  }

  public String importCertificate()
  {
    Map msgMap = new HashMap();
    int successNum;
    try
    {
      successNum = getPeTraineeService().saveCertificateNo(getFile());
    }
    catch (EntityException e)
    {
      
      msgMap.put("success", "false");
      msgMap.put("info", e.getMessage());
      setJsonString(JsonUtil.toJSONString(msgMap));
      return json();
    }
    msgMap.put("success", "true");
    msgMap.put("info", successNum + "条数据操作成功！");
    setJsonString(JsonUtil.toJSONString(msgMap));
    return json();
  }

  public void setEntityClass()
  {
    this.entityClass = PeTrainee.class;
  }

  public void setServletPath()
  {
    this.servletPath = "/entity/basic/peTraineeInTrainingAction";
  }

  public PeTrainee getBean() {
    return (PeTrainee)super.superGetBean();
  }

  public void setBean(PeTrainee bean) {
    super.superSetBean(bean);
  }

  public boolean isFilterUserInfo() {
    return this.filterUserInfo;
  }

  public void setFilterUserInfo(boolean filterUserInfo) {
    this.filterUserInfo = filterUserInfo;
  }

public PeTrainee getOldTrainee() {
	return oldTrainee;
}

public void setOldTrainee(PeTrainee oldTrainee) {
	this.oldTrainee = oldTrainee;
}
  
  
}