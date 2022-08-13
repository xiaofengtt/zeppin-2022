package com.whaty.platform.entity.web.action.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeDocument;
import com.whaty.platform.entity.bean.PrDocument;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeDocumentOutboxAction extends PeDocumentAction{

	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction(this.getText("删除"), "delete","");
		this.getGridConfig().addMenuFunction(this.getText("发送"), "send","");
		
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("发件箱"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("公文标题"), "title");
		//this.getGridConfig().addColumn(this.getText("收件人"), "code");
		this.getGridConfig().addColumn(this.getText("发送日期"), "sendDate");
		this.getGridConfig().addRenderScript(this.getText("状态"), "{if(record.data['sendDate']==null||(record.data['sendDate']).length<=0) return '未发送'; else return '已发送'}", "");
		this.getGridConfig().addRenderScript(this.getText("公文详情"), "{if(record.data['sendDate']==null||(record.data['sendDate']).length<=0) return '<a href=/entity/information/peDocumentOutbox_edit.action?bean.id='+record.data['id']+' >修改发送</a>'; else return '<a href=/entity/information/peDocumentOutbox_read.action?bean.id='+record.data['id']+' >查看信息</a>'}", "");
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peDocumentOutbox";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeDocument.class);
		dc.createAlias("enumConstByFlagDel","enumConstByFlagDel").add(Restrictions.eq("enumConstByFlagDel.code", "0"))
			.add(Restrictions.eq("ssoUser",((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser()))
			;
		return dc;
	}
	
	/**
	 * 指向添加公文空页面
	 * @return
	 */
	public String editnew(){
		return "editnew";
	}
	
	
	/**
	 * 指向修改公文页面
	 * @return
	 */
	public String edit(){
		try {
			this.setBean((PeDocument)this.getGeneralService().getById(this.getBean().getId()));
			java.util.Set<PrDocument> set = this.getBean().getPrDocuments();
			for(PrDocument prd:set){
				if(this.getMsg()==null)
					this.setMsg(prd.getSsoUser().getId()+",");
				else
					this.setMsg(this.getMsg()+prd.getSsoUser().getId()+",");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "editnew";		
	}
	
	/**
	 * 修改发送公文
	 * @return
	 */
	public String editSend(){
		try {
		PeDocument peDocument = (PeDocument)this.getGeneralService().getById(this.getBean().getId());
		peDocument.setTitle(this.getBean().getTitle());
		peDocument.setNote(this.getBean().getNote());
		peDocument.setSendDate(new java.util.Date());
		java.util.List list = new java.util.ArrayList();
		if(this.getManager_id()!=null&&this.getManager_id().length()>0){
			String[] ms = this.getManager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
					list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		if(this.getSitemanager_id()!=null&&this.getSitemanager_id().length()>0){
			String[] ms = this.getSitemanager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
			this.getPeDocumentService().updateforsend(peDocument, list);
			this.setMsg("发送成功");
			this.setTogo("/entity/information/peDocumentOutbox.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "msg";	
	}
	
	/**
	 * 修改保存公文
	 * @return
	 */
	public String editDocument(){
		try {
		PeDocument peDocument = (PeDocument)this.getGeneralService().getById(this.getBean().getId());
		peDocument.setTitle(this.getBean().getTitle());
		peDocument.setNote(this.getBean().getNote());
		java.util.List list = new java.util.ArrayList();
		if(this.getManager_id()!=null&&this.getManager_id().length()>0){
			String[] ms = this.getManager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		if(this.getSitemanager_id()!=null&&this.getSitemanager_id().length()>0){
			String[] ms = this.getSitemanager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
			this.getPeDocumentService().updateforsave(peDocument, list);
			this.setMsg("保存成功");
			this.setTogo("/entity/information/peDocumentOutbox.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "msg";	
	}
	
	/**
	 * 新建页面保存公文
	 * @return
	 */
	public String addDocument(){

		this.getBean().setId(null);
		this.getBean().setEnumConstByFlagDel(this.getMyListService().getEnumConstByNamespaceCode("FlagDel", "0"));
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setSsoUser(userSession.getSsoUser());
		java.util.List list = new java.util.ArrayList();
		if(this.getManager_id()!=null&&this.getManager_id().length()>0){
			String[] ms = this.getManager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		if(this.getSitemanager_id()!=null&&this.getSitemanager_id().length()>0){
			String[] ms = this.getSitemanager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		try {
			this.getPeDocumentService().saveforsave(this.getBean(), list);
			this.setMsg("保存成功");
			//this.setTogo("/entity/information/peDocumentOutbox.action");
			//保存成功,调转新建页面(20090424 龚妮娜)
			this.setTogo("/entity/information/peDocumentOutbox_editnew.action");
			
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "msg";
	}
	
	/**
	 * 新建页面直接发送公文
	 * @return
	 */
	public String sendDocument(){

		this.getBean().setId(null);
		this.getBean().setEnumConstByFlagDel(this.getMyListService().getEnumConstByNamespaceCode("FlagDel", "0"));
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setSsoUser(userSession.getSsoUser());
		this.getBean().setSendDate(new java.util.Date());
		java.util.List list = new java.util.ArrayList();
		if(this.getManager_id()!=null&&this.getManager_id().length()>0){
			String[] ms = this.getManager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		if(this.getSitemanager_id()!=null&&this.getSitemanager_id().length()>0){
			String[] ms = this.getSitemanager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		try {
			this.getPeDocumentService().saveforsend(this.getBean(), list);
			this.setMsg("发送成功");
			//this.setTogo("/entity/information/peDocumentOutbox.action");
			//发送保存成功,调转新建页面(20090424 龚妮娜)
			this.setTogo("/entity/information/peDocumentOutbox_editnew.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "msg";
	}
	/**
	 * 收件箱 回复公文
	 * @return
	 */
	public String returnDocument(){

		this.getBean().setId(null);
		this.getBean().setEnumConstByFlagDel(this.getMyListService().getEnumConstByNamespaceCode("FlagDel", "0"));
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setSsoUser(userSession.getSsoUser());
		this.getBean().setSendDate(new java.util.Date());
		java.util.List list = new java.util.ArrayList();
		if(this.getManager_id()!=null&&this.getManager_id().length()>0){
			String[] ms = this.getManager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		if(this.getSitemanager_id()!=null&&this.getSitemanager_id().length()>0){
			String[] ms = this.getSitemanager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		try {
			this.getPeDocumentService().saveforsend(this.getBean(), list);
			this.setMsg("发送成功");
			this.setTogo("/entity/information/peDocumentView.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "msg";
	}
	
	/**
	 * 收件箱保存回复公文
	 * @return
	 */
	public String saveDocument(){

		this.getBean().setId(null);
		this.getBean().setEnumConstByFlagDel(this.getMyListService().getEnumConstByNamespaceCode("FlagDel", "0"));
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setSsoUser(userSession.getSsoUser());
		java.util.List list = new java.util.ArrayList();
		if(this.getManager_id()!=null&&this.getManager_id().length()>0){
			String[] ms = this.getManager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		if(this.getSitemanager_id()!=null&&this.getSitemanager_id().length()>0){
			String[] ms = this.getSitemanager_id().split(",");
			for(String m:ms){
				if(m!=null&&m.trim().length()>0)
				list.add(this.getMyListService().getById(SsoUser.class, m.trim()));
			}
		}
		try {
			this.getPeDocumentService().saveforsave(this.getBean(), list);
			this.setMsg("保存成功");
			this.setTogo("/entity/information/peDocumentView.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "msg";
	}
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {			
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			
			if(this.getColumn().equals("delete")){
				try {
					count = this.getPeDocumentService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"条删除成功"));
				} catch (EntityException e) {
					e.printStackTrace();
					map.put("success", "false");
					map.put("info", "删除失败");
				}
			}else if(this.getColumn().equals("send")){
				try {
					count = this.getPeDocumentService().updateforsend(idList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"条发送成功"));
				} catch (EntityException e) {
					e.printStackTrace();
					map.put("success", "false");
					map.put("info", e.getMessage());
				}
			}			

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	

}
