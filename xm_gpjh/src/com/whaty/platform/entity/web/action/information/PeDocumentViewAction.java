package com.whaty.platform.entity.web.action.information;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeDocument;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrDocument;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeDocumentViewAction extends MyBaseAction{

	private void setview(){
		try {
			PrDocument prDocument = (PrDocument)this.getGeneralService().getById(this.getBean().getId());
			if(prDocument.getEnumConstByFlagRead().getCode().equals("0")){
				prDocument.setEnumConstByFlagRead(this.getMyListService().getEnumConstByNamespaceCode("FlagRead", "1"));
				prDocument = (PrDocument)this.getGeneralService().save(prDocument);
			}
			this.setBean(prDocument);
			java.util.Set<PrDocument> set = prDocument.getPeDocument().getPrDocuments();
			for(PrDocument prd:set){
				if(this.getMsg()==null)
					this.setMsg(prd.getSsoUser().getId()+",");
				else
					this.setMsg(this.getMsg()+prd.getSsoUser().getId()+",");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	public String viewDetail(){
		setview();
		return "detail";
	}
	
	public String returnDocument() {
		setview();
		this.setMsg(this.getMsg()+"reply");
		return "detail";
	}


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("我的公文"));

		this.getGridConfig().addMenuFunction(this.getText("删除"),"enumConstByFlagDel",this.getMyListService().getEnumConstByNamespaceCode("FlagDel", "1").getId());
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("公文标题"), "peDocument.title");
		this.getGridConfig().addColumn(this.getText("发送人"), "peDocument.ssoUser.loginId");
		this.getGridConfig().addColumn(this.getText("发送日期"), "peDocument.sendDate");
		this.getGridConfig().addColumn(this.getText("阅读状态"), "enumConstByFlagRead.name");
		this.getGridConfig().addRenderFunction(this.getText("查看详情"), "<a href=\"peDocumentView_viewDetail.action?bean.id=${value}\" >查看详细信息</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("回复发件人"), "<a href=\"peDocumentView_returnDocument.action?bean.id=${value}\" >回复</a>", "id");
		
	}
	
	public PrDocument getBean(){
		return (PrDocument)super.superGetBean();
	}
	
	public void setBean(PrDocument bean){
		super.superSetBean(bean);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrDocument.class;
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peDocumentView";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrDocument.class);
		dc.createAlias("enumConstByFlagRead", "enumConstByFlagRead")
			.createAlias("enumConstByFlagDel","enumConstByFlagDel").add(Restrictions.eq("enumConstByFlagDel.code", "0"))
			.add(Restrictions.eq("ssoUser",((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser()))
			.createCriteria("peDocument", "peDocument")
			.createAlias("ssoUser", "ssoUser");
		return dc;
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
}
