package com.whaty.platform.entity.web.action.implementation;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBriefReport;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 查看工作简报
 * @author 侯学龙
 *
 */
public class BriefReportViewAction extends MyBaseAction {
	

	private String id_;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false);
		PeProApply peProApply = loadPeProImplemt().getPeProApply();
		this.getGridConfig().setTitle(this.getText(peProApply.getPeUnit().getName()+"-"+peProApply.getPeProApplyno().getName()+"-简报"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("简报名称"), "reportName");
		this.getGridConfig().addColumn(this.getText("上传日期"), "uploadDate",true,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("sdf"), "reportFile",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("简报内容"), "<a href='${value}' target='_blank'>下载</a>", "reportFile");
//		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		this.getGridConfig().addMenuScript(this.getText("关闭"), "{window.close();}");
	}
	
	private PeProImplemt loadPeProImplemt(){
		return (PeProImplemt) this.getGeneralService().getGeneralDao().getById(PeProImplemt.class,this.getId_());
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeBriefReport.class;
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBriefReport.class);
		dc.createAlias("peProApply", "peProApply");
		dc.add(Restrictions.eq("peProApply", loadPeProImplemt().getPeProApply()));
		return dc;
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/briefReportView";
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		for(int i=0;i<idList.size();i++){
			PeBriefReport peBriefReport = (PeBriefReport)this.getGeneralService().getById(PeBriefReport.class,idList.get(i).toString());
			File file = new File(ServletActionContext.getRequest().getRealPath(peBriefReport.getReportFile()));
			if(file.isFile()){
				file.delete();
			}
		}
	}
	
	public void afterDelete() throws EntityException{
		PeProImplemt peProImplemt = loadPeProImplemt();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBriefReport.class);
		dc.createAlias("peProApply", "peProApply");
		dc.add(Restrictions.eq("peProApply", peProImplemt.getPeProApply()));
		dc.setProjection(Projections.max("uploadDate"));
		List<Date> list = this.getGeneralService().getList(dc);
		if (list != null && list.size() > 0) {
			peProImplemt.setLastUploadDate (list.get(0));
		}else {
			peProImplemt.setLastUploadDate (null);
		}
		this.getGeneralService().save(peProImplemt);
	}
	
	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}


}
