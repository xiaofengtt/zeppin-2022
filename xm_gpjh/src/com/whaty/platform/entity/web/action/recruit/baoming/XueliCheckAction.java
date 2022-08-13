package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;

public class XueliCheckAction extends PeRecStudentBaseAction {


	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学历验证"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name");
		this.getGridConfig().addColumn(this.getText("学历验证状态"),
				"enumConstByFlagXueliCheck.name");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addRenderFunction(this.getText("操作"),
						"<a href=\"/entity/recruit/recruitStu_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
						"id");
		this.getGridConfig().addMenuFunction(this.getText("暂通过"),"enumConstByFlagXueliCheck.id",
						this.getMyListService().getEnumConstByNamespaceCode("FlagXueliCheck", "2").getId());
		this.getGridConfig().addMenuFunction(this.getText("审核通过"),"enumConstByFlagXueliCheck.id",
				this.getMyListService().getEnumConstByNamespaceCode("FlagXueliCheck", "3").getId());
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"),"enumConstByFlagXueliCheck.id",
				this.getMyListService().getEnumConstByNamespaceCode("FlagXueliCheck", "1").getId());

	}

	public void checkBeforeUpdateColumn(List idList) throws EntityException {

		EnumConst enumConst = (EnumConst) this.getMyListService().getById(
				EnumConst.class, this.getValue());
		List<PeRecStudent> peRecStudentList = this.getMyListService().getByIds(
				PeRecStudent.class, idList);
		
		for (PeRecStudent peRecStudent : peRecStudentList) {
			if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")){
				if(!enumConst.getCode().equals("3")){
					throw new EntityException("不能操作已录取的学生！");
				}
			}
		}

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/xueliCheck";
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
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate");
		dc.createCriteria("enumConstByFlagXueliCheck",
				"enumConstByFlagXueliCheck", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

}

