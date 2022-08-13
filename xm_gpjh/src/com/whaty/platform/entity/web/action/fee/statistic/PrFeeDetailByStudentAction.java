package com.whaty.platform.entity.web.action.fee.statistic;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrFeeDetailByStudentAction extends MyBaseAction<PrFeeDetail> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学生交消费明细"));
		this.getGridConfig().setCapability(false, false, false);
		
		if(this.getBean()!=null&&this.getBean().getPeStudent()!=null&&this.getBean().getPeStudent().getId()!=null&&this.getBean().getPeStudent().getId().length()>0){
			this.getGridConfig().addMenuScript(this.getText("test.close"), "{window.close();}");

			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		}else{
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		
			this.getGridConfig().addColumn(this.getText("学号"),"peStudent.regNo");
			this.getGridConfig().addColumn(this.getText("姓名"),"peStudent.trueName");
			this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
			this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
			this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
			this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
			this.getGridConfig().addColumn(this.getText("学生状态"),"peStudent.enumConstByFlagStudentStatus.name");
			this.getGridConfig().addColumn(this.getText("帐户余额"),"creditAmount",false);
		}

		this.getGridConfig().addColumn(this.getText("费用类型"),"enumConstByFlagFeeType.name");
		this.getGridConfig().addColumn(this.getText("费用额"),"feeAmount",false);
		this.getGridConfig().addColumn(this.getText("操作日期"),"inputDate");
		this.getGridConfig().addColumn(this.getText("备注"),"note");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/prFeeDetailByStudent";
	}

	public void setBean(PrFeeDetail instance) {
		super.superSetBean(instance);
	}
	
	public PrFeeDetail getBean(){
		return super.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.createAlias("enumConstByFlagFeeType", "enumConstByFlagFeeType")
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck")
			.add(Restrictions.eq("enumConstByFlagFeeCheck.code", "2"))
			.createCriteria("peStudent", "peStudent")
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN)
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			//.add(Restrictions.lt("enumConstByFlagStudentStatus.code", "4"))
			;
		return dc;
	}
}
