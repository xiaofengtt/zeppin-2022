package com.whaty.platform.entity.web.action.studentStatus.register;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 预交费管理，提醒交费的功能。
 * @author Administrator
 *
 */
public class PrRecPriPayAction extends PeStudentInfoAction {

	private static final long serialVersionUID = 1222443374043213368L;

	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction(this.getText("prRecPriPay.remind"), "/entity/studentStatus/prRecPriPay_sendSms.action?msg=003", false, true);
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("prRecPriPay.title"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("peRecStudent.examCardNum"), "peRecStudent.examCardNum");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "trueName");
		this.getGridConfig().addColumn(this.getText("招生考试批次"), "peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peMajor.name");
		ColumnConfig column = new ColumnConfig(this.getText("学生状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code<'4'");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name");

	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/prRecPriPay";
		
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.and(Restrictions.ge("enumConstByFlagStudentStatus.code", "0"), Restrictions.lt("enumConstByFlagStudentStatus.code", "4")))
			.createCriteria("peRecStudent", "peRecStudent", DetachedCriteria.LEFT_JOIN)
				//添加考试批次关联信息
				.createCriteria("prRecPlanMajorSite","prRecPlanMajorSite", DetachedCriteria.LEFT_JOIN)
				//.createAlias("peSite", "peSite",DetachedCriteria.LEFT_JOIN)
				.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype",DetachedCriteria.LEFT_JOIN)
					//.createAlias("peMajor", "peMajor", DetachedCriteria.LEFT_JOIN)
					//.createAlias("peEdutype","peEdutype", DetachedCriteria.LEFT_JOIN)
					.createCriteria("peRecruitplan", "peRecruitplan", DetachedCriteria.LEFT_JOIN)
				;
		return dc;
	}
}
