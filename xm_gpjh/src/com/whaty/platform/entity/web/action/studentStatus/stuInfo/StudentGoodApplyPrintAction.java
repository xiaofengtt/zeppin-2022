package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.register.PrRecPriPayApplyAction;
/**
 * 打印评优申请表
 * @author 李冰
 *
 */
public class StudentGoodApplyPrintAction extends PrRecPriPayApplyAction {
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生评优列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "peStudent.prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
		ColumnConfig column1 = new ColumnConfig(this.getText("申请类型"), "enumConstByApplyType.name");
		column1.setComboSQL(" select t.id,t.name from enum_const t where t.namespace='ApplyType' and t.code in('1','2')    ");
		this.getGridConfig().addColumn(column1);
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"), "checkDate");
//		this.getGridConfig().addMenuFunction(this.getText("打印评优申请表"), "/entity/studentStatus/studentGoodApplyPrint_print.action", false, false);
		if(this.getGridConfig().checkBeforeAddMenu("/entity/studentStatus/studentGoodApplyPrint_print.action")){
		this.getGridConfig().addMenuScript(this.getText("打印评优申请表"), 
				"{var m = grid.getSelections();  "+
					"if(m.length > 0){	         "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        "+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/studentStatus/studentGoodApplyPrint_print.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ></form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";"+
					"} else {                    "+
//					"	Ext.MessageBox.alert('<s:text name=\"test.error\"/>','<s:text name=\"test.pleaseSelectAtLeastOneItem\"/>');"+
					"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
		"}}                         ");	
		}
	}
	
	public String print(){
		
		return "print_good_apply";
	}

	public List getApplys(){
		try{
			String[] printIds = this.getIds().split(",");
			DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
			dc.add(Restrictions.in("id", printIds));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List getCourse(String id){
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
			dc.createCriteria("peStudent", "peStudent").add(Restrictions.eq("id", id));
			dc.add(Restrictions.or(Restrictions.isNotNull("scoreTotal"), Restrictions.gt("scoreTotal", 0d)));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/studentGoodApplyPrint";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		String[] code = new String[]{"1","2"};
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus").add(Restrictions.eq("enumConstByFlagApplyStatus.code", "1"))
		  .createAlias("enumConstByApplyType", "enumConstByApplyType")
		  .add(Restrictions.and(Restrictions.eq("enumConstByApplyType.namespace", "ApplyType"), Restrictions.in("enumConstByApplyType.code", code)))
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")//TODO 学生状态
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "4"))
			;
		return dc;
	}


	public double getAverageScore(String totalScore , String n) {
		double averageScore = new BigDecimal(totalScore).divide(new BigDecimal(n)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return averageScore;
	}


	
}
