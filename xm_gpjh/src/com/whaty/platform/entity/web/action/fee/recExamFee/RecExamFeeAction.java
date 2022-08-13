package com.whaty.platform.entity.web.action.fee.recExamFee;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 入学考试费管理
 * @author 李冰
 *
 */
public class RecExamFeeAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("入学考试费用管理"));
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().addMenuFunction("导入交费数据", 1);
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("招生批次"),"prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addColumn(this.getText("金额"), "recExamFee",true, true, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("收据号"), "recExamFeeInvoice", true, true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("交费日期"), "recExamFeeDate",true,false,true,"");

	}

	public void checkBeforeUpdate() throws EntityException{
		if(this.getBean().getRecExamFeeInvoice()!=null&&this.getBean().getRecExamFeeInvoice().length()>0){
			this.getBean().setRecExamFeeDate(new Date());
		}
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/recExamFee";
	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias(
				"peSite", "peSite").createCriteria("prRecPlanMajorEdutype",
				"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createAlias("peRecruitplan", "peRecruitplan");
		return dc;
	}

}
