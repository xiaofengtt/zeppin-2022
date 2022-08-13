package com.whaty.platform.entity.web.action.implementation;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeOtherMaterial;
import com.whaty.platform.entity.bean.PrProSummary;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 查看上传的其他材料
 * @author 侯学龙
 *
 */
public class ShowOtherMaterialAction extends MyBaseAction {

	private String id_;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false);
		PrProSummary prProSummary = loadPrProgramUnit();
		this.getGridConfig().setTitle(this.getText(prProSummary.getPeUnit().getName()+"-"+prProSummary.getPeProApplyno().getName()+"-其他材料"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("材料名称"), "materialName");
		this.getGridConfig().addColumn(this.getText("上传日期"), "uploadDate",true,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("sdf"), "materialFile",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("材料内容"), "<a href='${value}' target='_blank'>下载</a>", "materialFile");
		this.getGridConfig().addMenuScript(this.getText("关闭"), "{window.close();}");
//		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
	}
	private PrProSummary loadPrProgramUnit(){
		this.getGeneralService().getGeneralDao().setEntityClass(PrProSummary.class);
		return (PrProSummary) this.getGeneralService().getGeneralDao().getById(this.getId_());
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeOtherMaterial.class;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeOtherMaterial.class);
		dc.createAlias("peProApplyno", "peProApplyno");
		dc.add(Restrictions.eq("peProApplyno", loadPrProgramUnit().getPeProApplyno()));
		dc.createAlias("peUnit", "peUnit");
		dc.add(Restrictions.eq("peUnit", loadPrProgramUnit().getPeUnit()));
		return dc;
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/showOtherMaterial";
	}
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		for(int i=0;i<idList.size();i++){
			PeOtherMaterial peOtherMaterial = (PeOtherMaterial)this.getGeneralService().getById(PeOtherMaterial.class,idList.get(i).toString());
			File file = new File(ServletActionContext.getRequest().getRealPath(peOtherMaterial.getMaterialFile()));
			if(file.isFile()){
				file.delete();
			}
		}
	}
	
	public void afterDelete() throws EntityException{
		PrProSummary proSummary = loadPrProgramUnit();
		DetachedCriteria dc = DetachedCriteria.forClass(PeOtherMaterial.class);
		dc.createAlias("peProApplyno", "peProApplyno");
		dc.add(Restrictions.eq("peProApplyno", loadPrProgramUnit().getPeProApplyno()));
		dc.createAlias("peUnit", "peUnit");
		dc.add(Restrictions.eq("peUnit", loadPrProgramUnit().getPeUnit()));
		dc.setProjection(Projections.max("uploadDate"));
		List<Date> list = this.getGeneralService().getList(dc);
		if (list != null && list.size() > 0) {
			proSummary.setLastUploadDate(list.get(0));
		}else {
			proSummary.setLastUploadDate(null);
		}
		this.getGeneralService().save(proSummary);
	}
	
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}

}
