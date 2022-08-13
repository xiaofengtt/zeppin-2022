package com.whaty.platform.entity.web.action.information.vote;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * @param
 * @version 创建时间：2009-8-3 上午10:59:18
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeVotePaperListAction extends MyBaseAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setTitle(this.getText("调查问卷列表"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "title");
		this.getGridConfig().addColumn(this.getText("类型"), "enumConstByFlagType.name");
		this.getGridConfig().addColumn(this.getText("创建日期"), "foundDate",false);
		this.getGridConfig().addColumn(this.getText("是否发布"), "enumConstByFlagIsvalid.name");
		
		this.getGridConfig().addRenderFunction(this.getText("问卷填写"),
				"<a href=\"/entity/first/firstPeVotePaper_toVote.action?bean.id=${value}\" target=\"_blank\">进入</a>",
				"id");
		this.getGridConfig().addRenderFunction(this.getText("查看结果"),
				"<a href=\"/entity/first/firstPeVotePaper_voteResult.action?bean.id=${value}\" target=\"_blank\">查看</a>",
				"id");	
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeVotePaper.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/information/peVotePaperList";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = dc
		.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagType = dc.createCriteria(
				"enumConstByFlagType", "enumConstByFlagType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagType.add(Restrictions.or(Restrictions.eq("code", "0"),
				Restrictions.eq("code", "2")));
		return dc;
	}
	
	public void setBean(PeVotePaper instance) {
		super.superSetBean(instance);

	}

	public PeVotePaper getBean() {
		return (PeVotePaper) super.superGetBean();
	}
	
}
