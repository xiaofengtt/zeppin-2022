package com.whaty.platform.entity.web.action.information;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PePolicy;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PePolicyViewAction extends MyBaseAction {

    public String viewDetail() {
		try {
		    this.setBean((PePolicy) this.getGeneralService().getById(PePolicy.class,
			    this.getBean().getId()));
		} catch (EntityException e) {
		    e.printStackTrace();
		}
		return "detail";
    }

    @Override
    public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("政策文件"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("政策文件题目"), "title");
		this.getGridConfig().addColumn(this.getText("发布日期"), "publishDate");
//		this.getGridConfig().addColumn(this.getText("发布人"), "peManager.name",false);
		ColumnConfig cc=new ColumnConfig(this.getText("发布人"), "peManager.name");
		cc.setComboSQL("select p.id,p.name from pe_manager p where p.name is not null");
		this.getGridConfig().addColumn(cc);
		// this.getGridConfig().addColumn(this.getText("发布更新时间"), "updateDate");
		this
			.getGridConfig()
			.addRenderFunction(
				this.getText("查看详情"),
				"<a href=\"pePolicyView_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
				"id");

    }

    @Override
    public void setEntityClass() {
    	this.entityClass = PePolicy.class;
    }

    @Override
    public void setServletPath() {
    	this.servletPath = "/entity/information/pePolicyView";
    }

    public void setBean(PePolicy bean) {
    	super.superSetBean(bean);
    }

    public PePolicy getBean() {
    	return (PePolicy) super.superGetBean();
    }

    @Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PePolicy.class);
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			dc.createAlias("enumConstByFlagIstop", "enumConstByFlagIstop");
			dc.createAlias("peManager", "peManager");
		dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession.getUserLoginType().equals("2")){//评审专家
			dc.add(Restrictions.like("scopeString", "valueExpert",MatchMode.ANYWHERE));
		}else if(userSession.getUserLoginType().equals("3")){ 
			
			String unitId = "";
		   	DetachedCriteria dcPeManager=DetachedCriteria.forClass(PeManager.class);
		   	dcPeManager.createCriteria("ssoUser","ssoUser");
		   	dcPeManager.createCriteria("peUnit","peUnit");
		   	dcPeManager.add(Restrictions.eq("ssoUser.id", userSession.getSsoUser().getId()));
		   	List list=new LinkedList();
		   	try {
		   		list=this.getGeneralService().getList(dcPeManager);
		   		if(list!=null&&list.size()>0){
		   			PeManager mgr=(PeManager) list.get(0);
		   			unitId = mgr.getPeUnit().getId();
		   		}
		   } catch (EntityException e) {
		   		e.printStackTrace();
		   	}
		   dc.add(Restrictions.like("scopeString", unitId, MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.desc("enumConstByFlagIstop.code"))
			.addOrder(Order.desc("publishDate"));
		return dc;
	}
}
