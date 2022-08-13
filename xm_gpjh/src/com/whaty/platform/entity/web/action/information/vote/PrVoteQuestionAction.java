package com.whaty.platform.entity.web.action.information.vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteAnswer;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrVoteQuestionAction extends MyBaseAction {
	private String peVotePaper_id;// 调查问卷ID
	private List<EnumConst> questionTypeList;//问题类型
	private String question;//所选择的问题类型
	/**
	 * 转向添加问题页面
	 * @return
	 */
	public String toAddQuestion() {
		this.setPeVotePaper_id(this.getBean().getPeVotePaper().getId());
		this.flagQuestionTypes();
		return "addQuestion";
	}
	
	/**
	 * 取得问题类型
	 */
	public void flagQuestionTypes() {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagQuestionType"));
		try {
			List<EnumConst> questionTypeList = this.getGeneralService().getList(dc);
			this.setQuestionTypeList(questionTypeList);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加题目操作
	 * @return
	 */
	public String addQuestion() {
		try {
			String sql = "select count(t.id) from pr_vote_question t where t.fk_vote_paper_id = :paperid";
			Map map = new HashMap();
			map.put("paperid", this.getPeVotePaper_id());
			List listquest = this.getGeneralService().getBySQL(sql,map);
			long count = 1;
			if(listquest!=null&& !listquest.isEmpty()){
				count = Integer.parseInt(listquest.get(0).toString())+1;
			}
			this.getBean().setPeVotePaper((PeVotePaper)this.getMyListService().getById(PeVotePaper.class, this.getPeVotePaper_id()));
			this.getBean().setEnumConstByFlagQuestionType(
					this.getMyListService().getEnumConstByNamespaceCode("FlagQuestionType", this.getQuestion()));
			this.getBean().setSequencesNo(count);
//			PrVoteAnswer prVoteAnswer = new PrVoteAnswer();
//			prVoteAnswer.setPrVoteQuestion(this.getBean());
			this.getGeneralService().save(this.getBean());
//			this.getGeneralService().save(prVoteAnswer);
			String str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="+this.getPeVotePaper_id();
			this.setTogo(str);
			this.setMsg("添加成功！");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("添加失败！");
		}
		return "msg";
	}
	
	/**
	 * @description 删除题目操作,删除前先将关联的Answer对象删除
	 * @return
	 */
	public String delQuestion() {
		List list = new ArrayList();
		list.add(this.getBean().getId());
		try {
//			String queryAnswerIdByQuestionIdSQL = "select id from pr_vote_answer where pr_vote_question=:theQuestionId";
//			Map<String, String> paramsMap = new HashMap<String, String>();
//			paramsMap.put("theQuestionId", getBean().getId());
			// 调查问题与答案设计为一对一关系，所以可以根据问题id把对应的地Answer删掉，以免删除问题时报违反完整性约束异常
			String deleteAnswerByFKToQuestionSQL = "delete from pr_vote_answer where pr_vote_question=:theQuestionId";
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("theQuestionId", getBean().getId());
			getGeneralService().executeBySQL(deleteAnswerByFKToQuestionSQL, paramsMap);
			
			this.getGeneralService().deleteByIds(list);
			String str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="+this.getBean().getPeVotePaper().getId();
			setTogo(str);
			setMsg("删除成功");
		} catch (EntityException e) {
			e.printStackTrace();
			setMsg("删除失败");
			setTogo("back");
		}
		return "msg";
	}
	
	/**
	 * 转向修改题目页面
	 * @return
	 */
	public String toEditQuestion() {
		this.flagQuestionTypes();
		try {
			setBean((PrVoteQuestion)getGeneralService().getById(PrVoteQuestion.class,getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("操作失败！");
		}
		return "editQuestion";
	}
	
	/**
	 * 修改题目操作
	 * @return
	 */
	public String editQuestion() {
		try {
			getBean().setEnumConstByFlagQuestionType(getMyListService().getEnumConstByNamespaceCode("FlagQuestionType", getQuestion()));
			PrVoteQuestion prVoteQuestion = (PrVoteQuestion)this.getGeneralService().getById(PrVoteQuestion.class,this.getBean().getId());
			this.superSetBean((PrVoteQuestion)setSubIds(prVoteQuestion,this.getBean()));
			this.getGeneralService().save(prVoteQuestion);
			String str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="+this.getBean().getPeVotePaper().getId();
			setTogo(str);
			setMsg("修改成功");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("操作失败！");
		}
		return "msg";
	}
	
	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrVoteQuestion.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/prVoteQuestion";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteQuestion.class);
		dc.createAlias("peVotePaper", "peVotePaper");
		return dc;
	}
	
	public void setBean(PrVoteQuestion instance) {
		super.superSetBean(instance);
	}

	public PrVoteQuestion getBean() {
		return (PrVoteQuestion) super.superGetBean();
	}
	public String getPeVotePaper_id() {
		return peVotePaper_id;
	}
	public void setPeVotePaper_id(String peVotePaper_id) {
		this.peVotePaper_id = peVotePaper_id;
	}

	public List<EnumConst> getQuestionTypeList() {
		return questionTypeList;
	}

	public void setQuestionTypeList(List<EnumConst> questionTypeList) {
		this.questionTypeList = questionTypeList;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}