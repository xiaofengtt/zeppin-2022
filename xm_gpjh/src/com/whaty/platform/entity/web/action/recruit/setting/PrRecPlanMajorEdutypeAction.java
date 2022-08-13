package com.whaty.platform.entity.web.action.recruit.setting;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.RecruitManageService;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 招生计划管理action
 * 
 * @author zhangyu
 * 
 */
public class PrRecPlanMajorEdutypeAction extends
		MyBaseAction<PrRecPlanMajorEdutype> {
	private RecruitManageService recruitManageService;
	private String plan1name;
	private String plan2name;
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecPlanMajorEdutype.class);
		dc.createCriteria("peMajor","peMajor");
		dc.createCriteria("peEdutype","peEdutype");
		dc.createCriteria("peRecruitplan","peRecruitplan");
		return dc;
	}

	public void setBean(PrRecPlanMajorEdutype instance) {
		super.superSetBean(instance);

	}

	public PrRecPlanMajorEdutype getBean() {
		return (PrRecPlanMajorEdutype) super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("招生计划管理"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("招生批次"), "peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("操作"),
						"<a href='/entity/recruit/prExistSite.action?bean.prRecPlanMajorEdutype.id=${value}\' +  target='_blank'>设置站点</a>",
						"id");

	}

	/**
	 * 复制招生计划
	 * @return
	 */
	public String copyPlan(){
		this.setTogo("back");
		try {
			this.getRecruitManageService().saveCopyPlan(this.getPlan1name(), this.getPlan2name());
			this.setMsg("操作成功");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "msg";
		
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PrRecPlanMajorEdutype.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/prRecPlanMajorEdutype";
	}

	public String getPlan1name() {
		return plan1name;
	}

	public void setPlan1name(String plan1name) {
		this.plan1name = plan1name;
	}

	public String getPlan2name() {
		return plan2name;
	}

	public void setPlan2name(String plan2name) {
		this.plan2name = plan2name;
	}

	public RecruitManageService getRecruitManageService() {
		return recruitManageService;
	}

	public void setRecruitManageService(RecruitManageService recruitManageService) {
		this.recruitManageService = recruitManageService;
	}

}
