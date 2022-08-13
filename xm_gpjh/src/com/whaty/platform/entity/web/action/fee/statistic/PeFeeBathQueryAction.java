package com.whaty.platform.entity.web.action.fee.statistic;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeFeeBatch;
import com.whaty.platform.entity.service.fee.PeFeeBatchService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeFeeBathQueryAction extends MyBaseAction<PeFeeBatch> {

	private PeFeeBatchService peFeeBatchService;
	
	private static final long serialVersionUID = -9209898945045718364L;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("交费批次查询"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("批次号"),"name");
		this.getGridConfig().addColumn(this.getText("上报人"), "peSitemanager.trueName");
		this.getGridConfig().addColumn(this.getText("上报时间"), "inputDate");
		this.getGridConfig().addColumn(this.getText("总金额"), "feeAmountTotal");
		
		this.getGridConfig().addRenderFunction(this.getText("学生交费明细"), "<a href=\"peFeePiCi.action?bean.peFeeBatch.id=${value}\" >查看详细信息</a>", "id");
			
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeFeeBatch.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/fee/peFeeBathQuery";
	}
	public void setBean(PeFeeBatch instance) {
		super.superSetBean(instance);
	}
	
	public PeFeeBatch getBean(){
		return super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeBatch.class);
		dc.createAlias("peSitemanager", "peSitemanager", DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck");
		dc.add(Restrictions.or(Restrictions.eq("enumConstByFlagFeeCheck.code", "1"), Restrictions.eq("enumConstByFlagFeeCheck.code", "2")));
		return dc;
	}

	public PeFeeBatchService getPeFeeBatchService() {
		return peFeeBatchService;
	}

	public void setPeFeeBatchService(PeFeeBatchService peFeeBatchService) {
		this.peFeeBatchService = peFeeBatchService;
	}
}
