package com.whaty.platform.entity.web.action.information.vote;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 调查问卷建议管理
 *
 */
public class PrVoteSuggestAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("建议列表"));
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("班级标识码"), "classIdentifier");
		this.getGridConfig().addColumn(this.getText("发表时间"), "foundDate");
		this.getGridConfig().addColumn(this.getText("IP地址"), "ip");
		this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFlagCheck.name");
		this.getGridConfig().addRenderFunction(this.getText("查看详情"),
				"<a href=\"prVoteSuggest_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>", "id");
		this.getGridConfig().addMenuFunction(this.getText("审核通过"), "enumConstByFlagCheck.id" ,
				this.getMyListService().getEnumConstByNamespaceCode("FlagCheck", "1").getId());	
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"), "enumConstByFlagCheck.id" ,
				this.getMyListService().getEnumConstByNamespaceCode("FlagCheck", "2").getId());
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.history.back()}");
	}	
	
	/**
	 * 查看建议的详情
	 * @return
	 */
	public String viewDetail() {
		try {
			this.setBean((PrVoteSuggest)this.getGeneralService().getById(PrVoteSuggest.class,this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("操作失败！"); 
			return "msg";
		}
		return "view";
	}

	public void checkBeforeDelete(List idList) {
//		this.getGeneralService().getGeneralDao().setEntityClass(PrVoteSuggest.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteSuggest.class);
		dc.createCriteria("peVotePaper", "peVotePaper");
		dc.add(Restrictions.in("id", idList));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				List idl = new ArrayList();
				List ipl = new ArrayList();
				for (Object o : list) {
					PrVoteSuggest pvs = (PrVoteSuggest)o;
					idl.add(pvs.getPeVotePaper());
					ipl.add(pvs.getIp());
				}
				if(idl != null && idl.size() > 0) {
//					this.getGeneralService().getGeneralDao().setEntityClass(PrVoteRecord.class);
					DetachedCriteria dcp = DetachedCriteria.forClass(PrVoteRecord.class);
					dcp.createCriteria("peVotePaper", "peVotePaper");
					dcp.add(Restrictions.in("peVotePaper", idl));
					if(ipl != null && ipl.size() > 0) {
						dcp.add(Restrictions.in("ip", ipl));
					}
					List l = this.getGeneralService().getList(dcp);
					if(l != null && l.size() > 0) {
						List idds = new ArrayList();
						for (Object o : l) {
							PrVoteRecord pvr = (PrVoteRecord)o;
							idds.add(pvr.getId());
						}
						if(idds != null && idds.size() > 0) {
							this.getGeneralService().deleteByIds(idds);
						}
					}
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		this.getGeneralService().getGeneralDao().setEntityClass(PrVoteSuggest.class);
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass =  PrVoteSuggest.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/prVoteSuggest";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		//JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteSuggest.class);
		dc.createAlias("peVotePaper", "peVotePaper");
//		.add(Restrictions.eq("peVotePaper.id", this.getBean().getPeVotePaper().getId()));
		dc.createCriteria("enumConstByFlagCheck", "enumConstByFlagCheck");
		return dc;
	}
	
	public void setBean( PrVoteSuggest instance) {
		super.superSetBean(instance); 
	}

	public  PrVoteSuggest getBean() {
		return ( PrVoteSuggest) super.superGetBean();
	}
}