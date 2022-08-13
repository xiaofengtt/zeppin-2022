package com.whaty.platform.entity.web.action.first;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeVoteDetail;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteAnswer;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.information.vote.PeVotePaperAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class FirstPeVotePaperAction extends PeVotePaperAction { 
	private boolean success;//是否提交成功
	List<PrVoteSuggest> prVoteSuggestList;//建议 
    private List<Object[]> traineeTeacherAndTheme;
    private List suggestList;
    private String loginErrMessage;
    private String bestTraining;
	
	 // 取得调查问卷中附加题选择的老师和专题 begin
    private String firstList;
    private String secondList;
    private String thirdList;
    private String fouthList;
    private String fifthList;
    private String firstListTheme;
    private String secondListTheme;
    private String thirdListTheme;
    private String fouthListTheme;
    private String fifthListTheme;

	public String getFirstList() {
		return firstList;
	}

	public void setFirstList(String firstList) {
		this.firstList = firstList;
	}

	public String getSecondList() {
		return secondList;
	}

	public void setSecondList(String secondList) {
		this.secondList = secondList;
	}

	public String getThirdList() {
		return thirdList;
	}

	public void setThirdList(String thirdList) {
		this.thirdList = thirdList;
	}

	public String getFouthList() {
		return fouthList;
	}

	public void setFouthList(String fouthList) {
		this.fouthList = fouthList;
	}

	public String getFifthList() {
		return fifthList;
	}

	public void setFifthList(String fifthList) {
		this.fifthList = fifthList;
	}

	public String getFirstListTheme() {
		return firstListTheme;
	}

	public void setFirstListTheme(String firstListTheme) {
		this.firstListTheme = firstListTheme;
	}

	public String getSecondListTheme() {
		return secondListTheme;
	}

	public void setSecondListTheme(String secondListTheme) {
		this.secondListTheme = secondListTheme;
	}

	public String getThirdListTheme() {
		return thirdListTheme;
	}

	public void setThirdListTheme(String thirdListTheme) {
		this.thirdListTheme = thirdListTheme;
	}

	public String getFouthListTheme() {
		return fouthListTheme;
	}

	public void setFouthListTheme(String fouthListTheme) {
		this.fouthListTheme = fouthListTheme;
	}

	public String getFifthListTheme() {
		return fifthListTheme;
	}
	//取得调查问卷中附加题选择的老师和专题 end
	public void setFifthListTheme(String fifthListTheme) {
		this.fifthListTheme = fifthListTheme;
	}

	/**
	 * 调查问卷列表
	 * @return
	 */
	public String toVoteList() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = dc
			.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagType = dc
			.createCriteria("enumConstByFlagType", "enumConstByFlagType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagType.add(Restrictions.or(Restrictions.eq("code", "0"),Restrictions.eq("code", "1")));
		dc.addOrder(Order.desc("foundDate"));
		try {
			this.setPeVotePaperList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "voteList";
	}
//	public String toSuggest(){
//		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteSuggest.class);
//		dc.createAlias("peVotePaper", "peVotePaper");
//		dc.add(Restrictions.eq("peVotePaper.id", this.getBean().getId()));
//		try {
//			List list = this.getGeneralService().getList(dc);
//			this.setSuggestList(list);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "viewsuggest";
//	}
	private String getFristVote() throws EntityException {
		DetachedCriteria firstdc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = firstdc.createCriteria(
				"enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagType = firstdc.createCriteria(
				"enumConstByFlagType", "enumConstByFlagType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagType.add(Restrictions.or(Restrictions.eq("code", "0"),
				Restrictions.eq("code", "1")));
		firstdc.addOrder(Order.desc("foundDate"));

		List<PeVotePaper> list = this.getGeneralService().getList(firstdc);
		if (list.size() > 0) {
			this.setPeVotePaper(list.get(0));
		}
		return "firstvote";
	}
	public String getVoteList() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria FlagType = dc.createCriteria("peProApplyno", "peProApplyno");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		FlagType.add(Restrictions.eq("code", us.getLoginId().substring(9, 11)));
		FlagType.add(Restrictions.like("year", us.getLoginId().substring(0, 2),MatchMode.END));
//				Restrictions.eq("code", "1")));
		dc.addOrder(Order.desc("foundDate"));
		
		try {
			List itiList = this.getGeneralService().getList(dc);
			if(itiList!=null&&!itiList.isEmpty()){
				setPeVotePaper((PeVotePaper) itiList.get(0));
			}else{
				return "back";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "voteList";
	}
	/**
	 * @description 学生工作室进入投票页面，查询题目
	 * @return
	 * @throws EntityException 
	 */
	public String toVote() throws EntityException {
		
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us==null){
			this.setLoginErrMessage("登录超时，为了您的账号安全，请重新登陆！");
			return "back";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		
		DetachedCriteria dcEnumConstByFlagIsvalid = dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		
		DetachedCriteria flagType = dc.createCriteria("peProApplyno", "peProApplyno");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		
		flagType.add(Restrictions.eq("code", us.getLoginId().substring(9, 11)));
		flagType.add(Restrictions.like("year", us.getLoginId().substring(0, 2), MatchMode.END)); //  .eq("year", "20" + us.getLoginId().substring(0, 2)));
		
		dc.addOrder(Order.desc("foundDate"));
		
		try {
			List itiList = this.getGeneralService().getList(dc);
			if(itiList!=null&&!itiList.isEmpty()){
				this.setPeVotePaper((PeVotePaper) itiList.get(0));
			}else{
				this.setLoginErrMessage("您的项目尚未发布调查问卷！");
				return "back";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
//		this.setPeVotePaper((PeVotePaper) getGeneralService().getById(PeVotePaper.class,this.getBean().getId()));
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PrVoteQuestion.class);
		dCriteria.createAlias("peVotePaper", "peVotePaper");
//		dCriteria.createAlias("enumConstByFlagQuestionType", "enumConstByFlagQuestionType");
		dCriteria.add(Restrictions.eq("peVotePaper", this.getPeVotePaper()));
		dCriteria.addOrder(Order.asc("sequencesNo"));
		List<PrVoteQuestion> listQuestList = getGeneralService().getList(dCriteria);
		this.setQuestionList(listQuestList);
		
		setTraineeTeacherAndTheme();
		setCanVote(1);
		setPastDue(0);
		
		//检查用户是否可以投票
		this.checkCanVote();

		// 判断时间是否在投票日期内
		Date now = new Date();
		if (now.before(this.getPeVotePaper().getStartDate())) {
			this.setPastDue(1);
		} else if (!Const.compareDate(now, this.getPeVotePaper().getEndDate())){
			this.setPastDue(2);
		}
		return "vote";
	}
	
	/**
	 * @description 检查用户是否还可以投票
	 * @return true为可以投票，否则为不可以
	 */
	/*private boolean checkUserCanVote() {
		boolean flag = true;
		// 如果调查问卷限制IP，则检查用户的IP是否已经投票
		if (getPeVotePaper().getEnumConstByFlagLimitDiffip().getCode().equals("1")) {
			// 得到用户的IP
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = request.getRemoteAddr();

			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
			DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
			dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
			dc.add(Restrictions.eq("ip", ip));
			try {
				List list = this.getGeneralService().getList(dc);
				if (list.size() > 0) {
					flag = false;
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		//如果调查问卷限制session，则检查用户session是否已经投票
		if (getCanVote() == 1&& getPeVotePaper().getEnumConstByFlagLimitDiffsession().getCode().equals("1")) {
			// 得到用户session
			HttpSession session = ServletActionContext.getRequest().getSession();
			String sessionId = session.getId();

			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
			DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
			dcPeVotePaper.add(Restrictions.eq("id", getPeVotePaper().getId()));
			dc.add(Restrictions.eq("userSession", sessionId));
			try {
				List list = this.getGeneralService().getList(dc);
				if (list.size() > 0) {
					flag = false;
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	*/
/*
	private StringBuffer updateCourseEnjoyNum() throws EntityException {
		Map<String,String> params = new HashMap<String,String>();
		StringBuffer qvs = new StringBuffer();
		if(this.getFirstListTheme()!=null&&this.getFirstList()!=null&&this.getFirstList().indexOf(",")>0){
			String firstvote = this.getFirstListTheme();
			String firstTeacher = this.getFirstList().split(",")[0];
			
			String firstsql = "select t.id from pe_course_plan t where t.theme=:firstvote and t.expert_name=:firstTeacher";
	//		String firstsql = "update pe_course_plan t set t.firstvote=t.firstvote+1 where t.theme=:firstvote and t.expert_name=:firstTeacher";
			
			params.put("firstvote", firstvote);
			params.put("firstTeacher", firstTeacher);
			List listFirst = this.getGeneralService().getBySQL(firstsql,params);
			if(listFirst==null || listFirst.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String firstId = listFirst.get(0).toString();
			this.getGeneralService().executeBySQL("update pe_course_plan t set t.firstvote=t.firstvote+1 where t.id='"+firstId+"'");
			qvs.append("第一名:"+firstTeacher+","+firstvote+";");
		}
		if(this.getSecondListTheme()!=null&&this.getSecondList()!=null&&this.getSecondList().indexOf(",")>0){
			String secondvote = this.getSecondListTheme();
			String secondTeacher = this.getSecondList().split(",")[0];
			String secondsql = "select t.id from pe_course_plan t where t.theme=:secondvote and t.expert_name=:secondTeacher";
	//		String secondsql = "update pe_course_plan t set t.secondvote=t.secondvote+1 where t.theme=:secondvote and t.expert_name=:secondTeacher";
			params.clear();
			params.put("secondvote", secondvote);
			params.put("secondTeacher", secondTeacher);
			List listsecond = this.getGeneralService().getBySQL(secondsql,params);
			if(listsecond==null || listsecond.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String secondId = listsecond.get(0).toString();
			
			this.getGeneralService().executeBySQL("update pe_course_plan t set t.secondvote=t.secondvote+1 where t.id='"+secondId+"'");
			qvs.append("第二名:"+secondTeacher+","+secondvote+";");
		}
		if(this.getThirdListTheme()!=null&&this.getThirdList()!=null&&this.getThirdList().indexOf(",")>0){
			String thirdvote = this.getThirdListTheme();
			String thirdTeacher = this.getThirdList().split(",")[0];
			String thirdsql = "select t.id from pe_course_plan t where t.theme=:thirdvote and t.expert_name=:thirdTeacher";
	//		String thirdsql = "update pe_course_plan t set t.thirdvote=t.thirdvote+1 where t.theme=:thirdvote and t.expert_name=:thirdTeacher";
			params.clear();
			params.put("thirdvote", thirdvote);
			params.put("thirdTeacher", thirdTeacher);
			List listthird = this.getGeneralService().getBySQL(thirdsql,params);
			if(listthird==null || listthird.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String thirdId = listthird.get(0).toString();
			this.getGeneralService().executeBySQL("update pe_course_plan t set t.thirdvote=t.thirdvote+1 where t.id='"+thirdId+"'");
			qvs.append("第三名:"+thirdTeacher+","+thirdvote+";");
		}
		if(this.getFouthListTheme()!=null&&this.getFouthList()!=null&&this.getFouthList().indexOf(",")>0){
			String fouthvote = this.getFouthListTheme();
			String fouthTeacher = this.getFouthList().split(",")[0];
			String fouthsql = "select t.id from pe_course_plan t where t.theme=:fouthvote and t.expert_name=:fouthTeacher";
	//		String fouthsql = "update pe_course_plan t set t.fouthvote=t.fouthvote+1 where t.theme=:fouthvote and t.expert_name=:fouthTeacher";
			params.clear();
			params.put("fouthvote", fouthvote);
			params.put("fouthTeacher", fouthTeacher);
			List listfouth = this.getGeneralService().getBySQL(fouthsql,params);
			if(listfouth==null || listfouth.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String fouthId = listfouth.get(0).toString();
			this.getGeneralService().executeBySQL("update pe_course_plan t set t.fouthvote=t.fouthvote+1 where t.id='"+fouthId+"'");
			qvs.append("第四名:"+fouthTeacher+","+fouthvote+";");
		}
		if(this.getFifthListTheme()!=null&&this.getFifthList()!=null&&this.getFifthList().indexOf(",")>0){
			String fifthvote = this.getFifthListTheme();
			String fifthTeacher = this.getFifthList().split(",")[0];
			String fifthsql = "select t.id from pe_course_plan t where  t.theme=:fifthvote and t.expert_name=:fifthTeacher";
	//		String fifthsql = "update pe_course_plan t set t.fifthvote=t.fifthvote+1 where t.theme=:fifthvote and t.expert_name=:fifthTeacher";
			params.clear();
			params.put("fifthvote", fifthvote);
			params.put("fifthTeacher", fifthTeacher);
			List listfifth = this.getGeneralService().getBySQL(fifthsql,params);
			if(listfifth==null || listfifth.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String fifthId = listfifth.get(0).toString();
			this.getGeneralService().executeBySQL("update pe_course_plan t set t.fifthvote=t.fifthvote+1 where  t.id='"+fifthId+"'");
			qvs.append("第四名:"+fifthTeacher+","+fifthvote+";");
		}
			
		return qvs;
	}*/
	
	/**
	 * @description 投票页面数据处理
	 * @return
	 */
	public String vote() {
		try {
			UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//			getGeneralService().getGeneralDao().setEntityClass(PeVotePaper.class);
			PeVotePaper votePaper = (PeVotePaper)getGeneralService().getById(PeVotePaper.class,getPeVotePaper().getId());
			setPeVotePaper(votePaper);
			if (!checkCanVote()) {
				this.setMsg("您已经做过本调查问卷！");
				return "result";
			}
			// 得到用户的IP及session id
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = request.getRemoteAddr();
			String sessionId = request.getSession().getId();

			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			java.sql.Date currentDate = new java.sql.Date(formatter.parse(dateString).getTime());

			// 生成并保存投票记录
			PrVoteRecord prVoteRecord = new PrVoteRecord();
			prVoteRecord.setIp(ip);
			prVoteRecord.setPeVotePaper(votePaper);
			prVoteRecord.setUserSession(sessionId);
			prVoteRecord.setVoteDate(currentDate);
			prVoteRecord.setClassIdentifier(usersession.getLoginId());
			
//			StringBuffer context = new StringBuffer();
			// 处理用户输入的意见或建议
			if ((votePaper.getEnumConstByFlagCanSuggest().getCode().equals("1"))||(this.getBestTraining() != null && getBestTraining().trim().length() > 0)) {
				if ((getSuggest() != null && getSuggest().trim().length() > 0)||(this.getBestTraining() != null && getBestTraining().trim().length() > 0)) {
					PrVoteSuggest prVoteSuggest = new PrVoteSuggest();
					prVoteSuggest.setIp(ip);
					String context = "";
					if(this.getBestTraining() != null && getBestTraining().trim().length() > 0){
						context += "效果最好的培训内容:" + this.getBestTraining()+"</br>";
					}
					if(getSuggest() != null && getSuggest().trim().length() > 0){
						context += "意见或建议:" + this.getSuggest();
					}
					prVoteSuggest.setNote(context);
					prVoteSuggest.setPeVotePaper(votePaper);
					prVoteSuggest.setFoundDate(currentDate);
					prVoteSuggest.setEnumConstByFlagCheck(getMyListService().getEnumConstByNamespaceCode("FlagCheck", "0"));
					prVoteSuggest.setClassIdentifier(usersession.getLoginId());
					prVoteSuggest = (PrVoteSuggest) this.getGeneralService().save(prVoteSuggest);
//					context.append("votesuggest:"+prVoteSuggest.getNote()+"$");
					//用 “$” 符号做分割，以防用别的符号 在内容中会出现。
					
					//保存投票记录
					prVoteRecord.setPrVoteSuggest(prVoteSuggest);
				}
			}
			
			DetachedCriteria dcQuestion = DetachedCriteria.forClass(PrVoteQuestion.class);
			dcQuestion.createAlias("peVotePaper", "peVotePaper");
//			dcQuestion.createAlias("enumConstByFlagQuestionType", "enumConstByFlagQuestionType");
			dcQuestion.add(Restrictions.eq("peVotePaper", votePaper));
//			dcQuestion.addOrder(Order.asc("enumConstByFlagQuestionType.code"));
			dcQuestion.addOrder(Order.asc("sequencesNo"));
			List<PrVoteQuestion> questionList = this.getGeneralService().getList(dcQuestion);
//			PrVoteQuestion question = null;
//			Iterator<PrVoteQuestion> iterator = getPeVotePaper().getPrVoteQuestions().iterator();
//			for(;iterator.hasNext();){
//				question = iterator.next();
			PeVoteDetail detail = new PeVoteDetail();
			int i = 1;
			for(PrVoteQuestion question : questionList){
				List listPrVoteAnswer = getGeneralService().getByHQL("from PrVoteAnswer t where t.prVoteQuestion='"+question.getId()+"' and t.classIdentifier ='"+usersession.getLoginId()+"'" );
				PrVoteAnswer answer = null;
				if(listPrVoteAnswer !=null && !listPrVoteAnswer.isEmpty()){
					answer = (PrVoteAnswer)listPrVoteAnswer.get(0);
				}else{
					answer = new PrVoteAnswer();
					answer.setPrVoteQuestion(question);
					answer.setClassIdentifier(usersession.getLoginId());
					this.getGeneralService().save(answer);
				}
				String item = "";
				if(question.getEnumConstByFlagQuestionType().getCode().equals("1")) {
					String questionValue = request.getParameter(question.getId());
					if(questionValue==null||questionValue.equals("")){
						this.setMsg("操作失败,请正确选择每一个问卷！");
						this.setTogo("back");
						return "result";
					}
//					getPeVotePaperService().operateRadioAnswer(answer, questionValue);
//					item = getQuestionItems(question,questionValue);
					item = questionValue.replaceAll("@", "");
					
				}else {
					String[] questionValues = request.getParameterValues(question.getId());
					if(questionValues==null||questionValues.length==0){
						this.setMsg("操作失败,请正确选择每一个问卷！");
						this.setTogo("back");
						return "result";
					}
					
//					getPeVotePaperService().operateCheckBoxAnswer(answer, questionValues);
					
					for(String s:questionValues){
//						item += getQuestionItems(question,s)+"||"; //多选题的答案之间用“，”分割
						item += s.replaceAll("@", "")+","; //多选题的答案之间用“，”分割
					}
				}
				detail = takeVoteDetail(detail,i,item);
				i++;
			}
			
			
			prVoteRecord.setPeVoteDetail(detail);
//			prVoteRecord.setPeVoteDetail(detail);
			// 根据投票结果更新课程表相应字段的票数
//			prVoteRecord.setContent(updateCourseEnjoyNum().toString());
			//将问题处理 放到service里面
//			for(PrVoteQuestion question : questionList){
//				List listPrVoteAnswer = getGeneralService().getByHQL("from PrVoteAnswer t where t.prVoteQuestion='"+question.getId()+"' and t.classIdentifier ='"+usersession.getLoginId()+"'" );
//				PrVoteAnswer answer = (PrVoteAnswer)listPrVoteAnswer.get(0);
//				if(question.getEnumConstByFlagQuestionType().getCode().equals("1")) {
//					String questionValue = request.getParameter(question.getId());
//					getPeVotePaperService().operateRadioAnswer(answer, questionValue);
//					
//				}else {
//					String[] questionValues = request.getParameterValues(question.getId());
//					getPeVotePaperService().operateCheckBoxAnswer(answer, questionValues);
//				}
//			}
			/*String voteArray[] = new String[10];
			voteArray[0] = this.firstListTheme;
			voteArray[1] = this.firstList;
			voteArray[2] = this.secondListTheme;
			voteArray[3] = this.secondList;
			voteArray[4] = this.thirdListTheme;
			voteArray[5] = this.thirdList;
			voteArray[6] = this.fouthListTheme;
			voteArray[7] = this.fouthList;
			voteArray[8] = this.fifthListTheme;
			voteArray[9] = this.fifthList;*/
			String voteArray[] = new String[5];
			voteArray[0] = this.firstListTheme;
			voteArray[1] = this.secondListTheme;
			voteArray[2] = this.thirdListTheme;
			voteArray[3] = this.fouthListTheme;
			voteArray[4] = this.fifthListTheme;
			this.getPeVotePaperService().saveRecord(prVoteRecord,questionList,request,voteArray);
			
			this.setMsg("提交成功，感谢您的参与！");
			this.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("back");
		}
		return "result";
	}
	
	 // 转向查看结果页面
	public String voteResult() {
//		this.toSetVotePaperQuestion();
		setVotePaperAnswer("");
//		this.maxResult();
		
		// 设置参加投票的人数
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
		DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
		dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size()>0) {
				this.setVoteNumber(list.size());
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "voteResult";
	}
	// 转向查看建议页面
	public String toSuggest() {
//		this.toSetVotePaperQuestion();
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteSuggest.class);
		DetachedCriteria dcPeVotePaper=dc.createCriteria("peVotePaper", "peVotePaper");
		dcPeVotePaper.add(Restrictions.eq("id", this.getBean().getId()));
		dc.createCriteria("enumConstByFlagCheck", "enumConstByFlagCheck")
			.add(Restrictions.eq("code", "1"));
		dc.addOrder(Order.desc("foundDate"));
		try {
			this.setPrVoteSuggestList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "suggest";
	}
	public String getAddQuestType(){
		return this.getMyListService().getSysValueByName("question_type")==null?"0":this.getMyListService().getSysValueByName("question_type");
	}
	public void setServletPath() {
		this.servletPath = "/entity/first/firstPeVotePaper";
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<PrVoteSuggest> getPrVoteSuggestList() {
		return prVoteSuggestList;
	}
	public void setPrVoteSuggestList(List<PrVoteSuggest> prVoteSuggestList) {
		this.prVoteSuggestList = prVoteSuggestList;
	}

	public List getTraineeTeacherAndTheme() {
		return traineeTeacherAndTheme;
	}

	/**
	 * 调查问卷根据登陆学员登录名(班级标识码)查询老师和培训专题List
	 */
	public void setTraineeTeacherAndTheme() {
		UserSession usersession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		if(classIdentifier.length()<11){
			return;
		}
		String year = classIdentifier.substring(0, 2);
		String unitCode = classIdentifier.substring(2, 7);
		String subjectCode = classIdentifier.substring(7, 9);
		String projectCode = classIdentifier.substring(9, 10);
		List<Object[]> list = new ArrayList<Object[]>();
		String queryExpertInfoSQL = "select distinct c.expert_name, c.zhicheng, c.fk_province, c.work_place from pe_course_plan c join pe_pro_apply a " +
				"on c.fk_pro_apply=a.id inner join pe_pro_applyno b on a.fk_applyno = b.id where a.fk_unit=:theUnitCode and " +
				" a.fk_subject=:theSubjectCode and b.code=:theProjectCode and b.year = :year ";
		Map<String,String> params = new HashMap<String,String>();
		params.put("theUnitCode", getIdByCode(unitCode, "pe_unit"));
		params.put("theSubjectCode", getIdByCode(subjectCode, "pe_subject"));
		params.put("theProjectCode", projectCode);
		params.put("year", "20"+year);
		try {
			list = this.getGeneralService().getBySQL(queryExpertInfoSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		System.out.println("queryExpertInfoSQL:"+queryExpertInfoSQL);
//		System.out.println("theUnitCode:"+getIdByCode(unitCode, "pe_unit"));
//		System.out.println("subjectCode:"+getIdByCode(subjectCode, "pe_subject"));
//		System.out.println("projectCode:"+getIdByCode(projectCode, "pe_pro_applyno"));
		this.traineeTeacherAndTheme = list;
	}
	private PeVoteDetail takeVoteDetail(PeVoteDetail detail,int i,String item){
		switch (i) {
		case 1:
			detail.setItem1(item);
			break;
		case 2:
			detail.setItem2(item);
			break;
		case 3:
			detail.setItem3(item);
			break;
		case 4:
			detail.setItem4(item);
			break;
		case 5:
			detail.setItem5(item);
			break;
		case 6:
			detail.setItem6(item);
			break;
		case 7:
			detail.setItem7(item);
			break;
		case 8:
			detail.setItem8(item);
			break;
		case 9:
			detail.setItem9(item);
			break;
		case 10:
			detail.setItem10(item);
			break;
		case 11:
			detail.setItem11(item);
			break;
		case 12:
			detail.setItem12(item);
			break;
		case 13:
			detail.setItem13(item);
			break;
		case 14:
			detail.setItem14(item);
			break;
		case 15:
			detail.setItem15(item);
			break;
		case 16:
			detail.setItem16(item);
			break;
		case 17:
			detail.setItem17(item);
			break;
		case 18:
			detail.setItem18(item);
			break;
		case 19:
			detail.setItem19(item);
			break;
		case 20:
			detail.setItem20(item);
			break;
		case 21:
			detail.setItem21(item);
			break;
		case 22:
			detail.setItem22(item);
			break;
		case 23:
			detail.setItem23(item);
			break;
		case 24:
			detail.setItem24(item);
			break;
		case 25:
			detail.setItem25(item);
			break;
		case 26:
			detail.setItem26(item);
			break;
		case 27:
			detail.setItem27(item);
			break;
		case 28:
			detail.setItem28(item);
			break;
		case 29:
			detail.setItem29(item);
			break;
		case 30:
			detail.setItem30(item);
			break;
		case 31:
			detail.setItem31(item);
			break;
		case 32:
			detail.setItem32(item);
			break;
		case 33:
			detail.setItem33(item);
			break;
		case 34:
			detail.setItem34(item);
			break;
		case 35:
			detail.setItem35(item);
			break;
		case 36:
			detail.setItem36(item);
			break;
		case 37:
			detail.setItem37(item);
			break;
		case 38:
			detail.setItem38(item);
			break;
		case 39:
			detail.setItem39(item);
			break;
		case 40:
			detail.setItem40(item);
			break;
		case 41:
			detail.setItem41(item);
			break;
		case 42:
			detail.setItem42(item);
			break;
		case 43:
			detail.setItem43(item);
			break;
		case 44:
			detail.setItem44(item);
			break;
		case 45:
			detail.setItem45(item);
			break;
		case 46:
			detail.setItem46(item);
			break;
		case 47:
			detail.setItem47(item);
			break;
		case 48:
			detail.setItem48(item);
			break;
		case 49:
			detail.setItem49(item);
			break;
		case 50:
			detail.setItem50(item);
			break;
			
		default:
			break;
		}
		return detail;
	}
	private String getQuestionItems(PrVoteQuestion question,String item){
		if(item.equals("@1")){
			return question.getItem1();
		}else if(item.equals("@2")){
			return question.getItem2();
		}else if(item.equals("@3")){
			return question.getItem3();
		}else if(item.equals("@4")){
			return question.getItem4();
		}else if(item.equals("@5")){
			return question.getItem5();
		}else if(item.equals("@6")){
			return question.getItem6();
		}else if(item.equals("@7")){
			return question.getItem7();
		}else if(item.equals("@8")){
			return question.getItem8();
		}else if(item.equals("@9")){
			return question.getItem9();
		}else if(item.equals("@10")){
			return question.getItem10();
		}else if(item.equals("@11")){
			return question.getItem11();
		}else if(item.equals("@12")){
			return question.getItem12();
		}else if(item.equals("@13")){
			return question.getItem13();
		}else if(item.equals("@14")){
			return question.getItem14();
		}else if(item.equals("@15")){
			return question.getItem15();
		}else if(item.equals("@16")){
			return question.getItem16();
		}else if(item.equals("@17")){
			return question.getItem17();
		}else if(item.equals("@18")){
			return question.getItem18();
		}else if(item.equals("@19")){
			return question.getItem19();
		}else if(item.equals("@20")){
			return question.getItem20();
		}else if(item.equals("@21")){
			return question.getItem21();
		}else if(item.equals("@22")){
			return question.getItem22();
		}else if(item.equals("@23")){
			return question.getItem23();
		}else if(item.equals("@24")){
			return question.getItem24();
		}else if(item.equals("@25")){
			return question.getItem25();
		}else if(item.equals("@26")){
			return question.getItem26();
		}else if(item.equals("@27")){
			return question.getItem27();
		}else if(item.equals("@28")){
			return question.getItem28();
		}else if(item.equals("@29")){
			return question.getItem29();
		}else if(item.equals("@30")){
			return question.getItem30();
		}else if(item.equals("@31")){
			return question.getItem31();
		}else if(item.equals("@32")){
			return question.getItem32();
		}else if(item.equals("@33")){
			return question.getItem33();
		}else if(item.equals("@34")){
			return question.getItem34();
		}else if(item.equals("@35")){
			return question.getItem35();
		}else if(item.equals("@36")){
			return question.getItem36();
		}else if(item.equals("@37")){
			return question.getItem37();
		}else if(item.equals("@38")){
			return question.getItem38();
		}else if(item.equals("@39")){
			return question.getItem39();
		}else if(item.equals("@40")){
			return question.getItem40();
		}
		
		return null;
	}
	public List getSuggestList() {
		return suggestList;
	}

	public void setSuggestList(List suggestList) {
		this.suggestList = suggestList;
	}

	public String getLoginErrMessage() {
		return loginErrMessage;
	}

	public void setLoginErrMessage(String loginErrMessage) {
		this.loginErrMessage = loginErrMessage;
	}

	public String getBestTraining() {
		return bestTraining;
	}

	public void setBestTraining(String bestTraining) {
		this.bestTraining = bestTraining;
	}
}