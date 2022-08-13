package com.whaty.platform.entity.web.action.recruit.stat;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 查看明细action
 * 
 * @author 李冰
 * 
 */
public class BaomingStuAction extends MyBaseAction {
	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("学生信息"));
		this.getGridConfig().setCapability(false, false, false, true);

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("准考证号"), "examCardNum");
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");

		this.getGridConfig().addColumn(this.getText("是否免试生"),
				"enumConstByFlagNoexam.name");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		this.getGridConfig().addColumn(this.getText("录取号"),
			"matriculateNum");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("查看报名信息"),
						"<a href=\"/entity/recruit/recruitStu_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看报名信息</a>",
						"id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/baomingStu";
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
				"prRecPlanMajorSite", "prRecPlanMajorSite",
				DetachedCriteria.LEFT_JOIN).createAlias("peSite", "peSite")
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createAlias("peRecruitplan", "peRecruitplan");
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

}
