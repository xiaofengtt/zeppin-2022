package com.whaty.platform.entity.web.action.information;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeDocument;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrDocument;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeDocumentService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeDocumentAction extends MyBaseAction {

	@Override
	public void initGrid() {
		
		this.getGridConfig().setCapability(false, true, false,true);
		this.getGridConfig().setTitle(this.getText("公文管理"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("公文标题"), "title");
		this.getGridConfig().addColumn(this.getText("发件人"), "ssoUser.loginId");
		this.getGridConfig().addColumn(this.getText("发送日期"), "sendDate");
		this.getGridConfig().addRenderScript(this.getText("状态"), "{if(record.data['sendDate']==null||(record.data['sendDate']).length<=0) return '未发送'; else return '已发送'}", "");
		this.getGridConfig().addRenderScript(this.getText("公文详情"), "{return '<a href=/entity/information/peDocument_read.action?bean.id='+record.data['id']+' >查看信息</a>'}", "");
	
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peDocument";
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeDocument.class;
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeDocument.class);
			dc.createAlias("ssoUser", "ssoUser");
		return dc;
	}
	
	public PeDocument getBean(){
		return (PeDocument)super.superGetBean();
	}
	
	public void setBean(PeDocument bean){
		super.superSetBean(bean);
	}
	/**
	 * 指向修改公文页面
	 * @return
	 */
	public String read(){
		try {
			this.setBean((PeDocument)this.getGeneralService().getById(this.getBean().getId()));
			java.util.Set<PrDocument> set = this.getBean().getPrDocuments();
			for(PrDocument prd:set){
				if(this.getMsg()==null)
					this.setMsg(prd.getSsoUser().getId()+",");
				else
					this.setMsg(this.getMsg()+prd.getSsoUser().getId()+",");
			}
			this.setMsg(this.getMsg()+"readonly");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "editnew";		
	}
	public List<PeManager> getPeManagers() {
		List<PeManager> list = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<PeManager> getSiteManagers() {
		List<PeManager> list = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	private PeDocumentService peDocumentService;
	
	private String manager_id;
	
	private String sitemanager_id;
	
	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}


	public String getSitemanager_id() {
		return sitemanager_id;
	}

	public void setSitemanager_id(String sitemanager_id) {
		this.sitemanager_id = sitemanager_id;
	}
	
	public PeDocumentService getPeDocumentService() {
		return peDocumentService;
	}

	public void setPeDocumentService(PeDocumentService peDocumentService) {
		this.peDocumentService = peDocumentService;
	}

}
