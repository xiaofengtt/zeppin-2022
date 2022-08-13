package com.whaty.platform.entity.web.action.information.vote;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PrVoteRecordAction extends MyBaseAction {
	
	private String applynoId;
	
	@Override
	public void initGrid() {
		PeVotePaper paper = getVotePaperByApplyno();
		this.getGridConfig().setTitle(this.getText(paper.getTitle()+"--投票记录"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("班级标识码"), "classIdentifier");
		this.getGridConfig().addColumn(this.getText("IP地址"), "ip");
		this.getGridConfig().addColumn(this.getText("会话ID"), "userSession");
		this.getGridConfig().addColumn(this.getText("投票时间"), "voteDate");
		for(int i = 1;i <= paper.getPrVoteQuestions().size();i++){
			this.getGridConfig().addColumn(this.getText("问题"+i), "peVoteDetail.item"+i);
		}
		this.getGridConfig().addColumn(this.getText("建议"), "prVoteSuggest.note",false);
		this.getGridConfig().addColumn(this.getText("老师与专题"), "content",false);
	}
//	private int getQuestionSize(){
//		String sql = "select count(t.id) from pr_vote_question t where t.fk_vote_paper_id ='"+this.getApplynoId()+"'";
//		List list = null;
//		try {
//			list = this.getGeneralService().getBySQL(sql);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		String str = list.get(0).toString();
//		return Integer.parseInt(str);
//	}
	
	private PeVotePaper getVotePaperByApplyno(){
//		DetachedCriteria dcPeVotePaper = DetachedCriteria.forClass(PeVotePaper.class);
//		dcPeVotePaper.createAlias("peProApplyno", "peProApplyno");
//		dcPeVotePaper.add(Restrictions.eq("peProApplyno.id", getApplynoId()));
//		dcPeVotePaper.addOrder(Order.desc("foundDate"));
//		List<PeVotePaper> list = null;
//		try {
//			list = this.getGeneralService().getList(dcPeVotePaper);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		if(list!=null&&!list.isEmpty()){
//			return list.get(0);
//		}else{
//			return null;
//		}
		PeVotePaper votepaper = null;
		try {
			votepaper = (PeVotePaper) this.getGeneralService().getById(PeVotePaper.class, getApplynoId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return votepaper;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
		dc.createAlias("peVoteDetail", "peVoteDetail");
		dc.createAlias("prVoteSuggest", "prVoteSuggest",DetachedCriteria.LEFT_JOIN);
//		dc.add(Restrictions.sqlRestriction("{alias}.CLASS_IDENTIFIER like '"+Const.getYear().substring(2)+"%"+getVotePaperByApplyno().getPeProApplyno().getCode()+"_'"));
		dc.createAlias("peVotePaper", "peVotePaper");
		dc.add(Restrictions.eq("peVotePaper", this.getVotePaperByApplyno()));
		return dc;
	}

	@Override
	public void setEntityClass() {

	}
	/**
	 * 取得培训项目
	 * @return
	 */
	public List<PeVotePaper> getPaperType() {
//		String sql="select id,name from pe_pro_applyno  where year=to_char(sysdate,'YYYY')and id in(select flag_type from pe_vote_paper ) order by code";
		String sql="select t.id,t.title from pe_vote_paper t";
		List<PeVotePaper> votePaper = null;
		try {
			votePaper = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return votePaper;
	}
	// 根据选择查看调查问卷列表
	public String searchToVoteRecord(){
		return "searchToVotePaper";
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/prVoteRecord";
	}

	public String getApplynoId() {
		return applynoId;
	}

	public void setApplynoId(String applynoId) {
		this.applynoId = applynoId;
	}

}
