package com.whaty.platform.entity.service.imp.information;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeCoursePlan;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeVoteDetail;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteAnswer;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeVotePaperService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeVotePaperServiceImp implements PeVotePaperService {
	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public void operateQuestionAnswer(PrVoteAnswer voteAnswer, String item) {
		UserSession usersession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		getGeneralDao().setEntityClass(PrVoteAnswer.class);
		if (item.trim().equals("@1")) {
			if (voteAnswer.getAnswer1() != null && voteAnswer.getAnswer1() > 0) {
				voteAnswer.setAnswer1(voteAnswer.getAnswer1() + 1);
			} else {
				voteAnswer.setAnswer1(1L);
			}
		} else if (item.trim().equals("@2")) {
			if (voteAnswer.getAnswer2() != null && voteAnswer.getAnswer2() > 0) {
				voteAnswer.setAnswer2(voteAnswer.getAnswer2() + 1);
			} else {
				voteAnswer.setAnswer2(1L);
			}
		} else if (item.trim().equals("@3")) {
			if (voteAnswer.getAnswer3() != null && voteAnswer.getAnswer3() > 0) {
				voteAnswer.setAnswer3(voteAnswer.getAnswer3() + 1);
			} else {
				voteAnswer.setAnswer3(1L);
			}
		} else if (item.trim().equals("@4")) {
			if (voteAnswer.getAnswer4() != null && voteAnswer.getAnswer4() > 0) {
				voteAnswer.setAnswer4(voteAnswer.getAnswer4() + 1);
			} else {
				voteAnswer.setAnswer4(1L);
			}
		} else if (item.trim().equals("@5")) {
			if (voteAnswer.getAnswer5() != null && voteAnswer.getAnswer5() > 0) {
				voteAnswer.setAnswer5(voteAnswer.getAnswer5() + 1);
			} else {
				voteAnswer.setAnswer5(1L);
			}
		} else if (item.trim().equals("@6")) {
			if (voteAnswer.getAnswer6() != null && voteAnswer.getAnswer6() > 0) {
				voteAnswer.setAnswer6(voteAnswer.getAnswer6() + 1);
			} else {
				voteAnswer.setAnswer6(1L);
			}
		} else if (item.trim().equals("@7")) {
			if (voteAnswer.getAnswer7() != null && voteAnswer.getAnswer7() > 0) {
				voteAnswer.setAnswer7(voteAnswer.getAnswer7() + 1);
			} else {
				voteAnswer.setAnswer7(1L);
			}
		} else if (item.trim().equals("@8")) {
			if (voteAnswer.getAnswer8() != null && voteAnswer.getAnswer8() > 0) {
				voteAnswer.setAnswer8(voteAnswer.getAnswer8() + 1);
			} else {
				voteAnswer.setAnswer8(1L);
			}
		} else if (item.trim().equals("@9")) {
			if (voteAnswer.getAnswer9() != null && voteAnswer.getAnswer9() > 0) {
				voteAnswer.setAnswer9(voteAnswer.getAnswer9() + 1);
			} else {
				voteAnswer.setAnswer9(1L);
			}
		} else if (item.trim().equals("@10")) {
			if (voteAnswer.getAnswer10() != null
					&& voteAnswer.getAnswer10() > 0) {
				voteAnswer.setAnswer10(voteAnswer.getAnswer10() + 1);
			} else {
				voteAnswer.setAnswer10(1L);
			}
		} else if (item.trim().equals("@11")) {
			if (voteAnswer.getAnswer11() != null
					&& voteAnswer.getAnswer11() > 0) {
				voteAnswer.setAnswer11(voteAnswer.getAnswer11() + 1);
			} else {
				voteAnswer.setAnswer11(1L);
			}
		} else if (item.trim().equals("@12")) {
			if (voteAnswer.getAnswer12() != null
					&& voteAnswer.getAnswer12() > 0) {
				voteAnswer.setAnswer12(voteAnswer.getAnswer12() + 1);
			} else {
				voteAnswer.setAnswer12(1L);
			}
		} else if (item.trim().equals("@13")) {
			if (voteAnswer.getAnswer13() != null
					&& voteAnswer.getAnswer13() > 0) {
				voteAnswer.setAnswer13(voteAnswer.getAnswer13() + 1);
			} else {
				voteAnswer.setAnswer13(1L);
			}
		} else if (item.trim().equals("@14")) {
			if (voteAnswer.getAnswer14() != null
					&& voteAnswer.getAnswer14() > 0) {
				voteAnswer.setAnswer14(voteAnswer.getAnswer14() + 1);
			} else {
				voteAnswer.setAnswer14(1L);
			}
		} else if (item.trim().equals("@15")) {
			if (voteAnswer.getAnswer15() != null
					&& voteAnswer.getAnswer15() > 0) {
				voteAnswer.setAnswer15(voteAnswer.getAnswer15() + 1);
			} else {
				voteAnswer.setAnswer15(1L);
			}
		} else if (item.trim().equals("@16")) {
			if (voteAnswer.getAnswer16() != null
					&& voteAnswer.getAnswer16() > 0) {
				voteAnswer.setAnswer16(voteAnswer.getAnswer16() + 1);
			} else {
				voteAnswer.setAnswer16(1L);
			}
		} else if (item.trim().equals("@17")) {
			if (voteAnswer.getAnswer17() != null
					&& voteAnswer.getAnswer17() > 0) {
				voteAnswer.setAnswer17(voteAnswer.getAnswer17() + 1);
			} else {
				voteAnswer.setAnswer17(1L);
			}
		} else if (item.trim().equals("@18")) {
			if (voteAnswer.getAnswer18() != null
					&& voteAnswer.getAnswer18() > 0) {
				voteAnswer.setAnswer18(voteAnswer.getAnswer18() + 1);
			} else {
				voteAnswer.setAnswer18(1L);
			}
		} else if (item.trim().equals("@19")) {
			if (voteAnswer.getAnswer19() != null
					&& voteAnswer.getAnswer19() > 0) {
				voteAnswer.setAnswer19(voteAnswer.getAnswer19() + 1);
			} else {
				voteAnswer.setAnswer19(1L);
			}
		} else if (item.trim().equals("@20")) {
			if (voteAnswer.getAnswer20() != null
					&& voteAnswer.getAnswer20() > 0) {
				voteAnswer.setAnswer20(voteAnswer.getAnswer20() + 1);
			} else {
				voteAnswer.setAnswer20(1L);
			}
		} else if (item.trim().equals("@21")) {
			if (voteAnswer.getAnswer21() != null
					&& voteAnswer.getAnswer21() > 0) {
				voteAnswer.setAnswer21(voteAnswer.getAnswer21() + 1);
			} else {
				voteAnswer.setAnswer21(1L);
			}
		} else if (item.trim().equals("@22")) {
			if (voteAnswer.getAnswer22() != null
					&& voteAnswer.getAnswer22() > 0) {
				voteAnswer.setAnswer22(voteAnswer.getAnswer22() + 1);
			} else {
				voteAnswer.setAnswer22(1L);
			}
		} else if (item.trim().equals("@23")) {
			if (voteAnswer.getAnswer23() != null
					&& voteAnswer.getAnswer23() > 0) {
				voteAnswer.setAnswer23(voteAnswer.getAnswer23() + 1);
			} else {
				voteAnswer.setAnswer23(1L);
			}
		} else if (item.trim().equals("@24")) {
			if (voteAnswer.getAnswer24() != null
					&& voteAnswer.getAnswer24() > 0) {
				voteAnswer.setAnswer24(voteAnswer.getAnswer24() + 1);
			} else {
				voteAnswer.setAnswer24(1L);
			}
		} else if (item.trim().equals("@25")) {
			if (voteAnswer.getAnswer25() != null
					&& voteAnswer.getAnswer25() > 0) {
				voteAnswer.setAnswer25(voteAnswer.getAnswer25() + 1);
			} else {
				voteAnswer.setAnswer25(1L);
			}
		} else if (item.trim().equals("@26")) {
			if (voteAnswer.getAnswer26() != null
					&& voteAnswer.getAnswer26() > 0) {
				voteAnswer.setAnswer26(voteAnswer.getAnswer26() + 1);
			} else {
				voteAnswer.setAnswer26(1L);
			}
		} else if (item.trim().equals("@27")) {
			if (voteAnswer.getAnswer27() != null
					&& voteAnswer.getAnswer27() > 0) {
				voteAnswer.setAnswer27(voteAnswer.getAnswer27() + 1);
			} else {
				voteAnswer.setAnswer27(1L);
			}
		} else if (item.trim().equals("@28")) {
			if (voteAnswer.getAnswer28() != null
					&& voteAnswer.getAnswer28() > 0) {
				voteAnswer.setAnswer28(voteAnswer.getAnswer28() + 1);
			} else {
				voteAnswer.setAnswer28(1L);
			}
		} else if (item.trim().equals("@29")) {
			if (voteAnswer.getAnswer29() != null
					&& voteAnswer.getAnswer29() > 0) {
				voteAnswer.setAnswer29(voteAnswer.getAnswer29() + 1);
			} else {
				voteAnswer.setAnswer29(1L);
			}
		} else if (item.trim().equals("@30")) {
			if (voteAnswer.getAnswer30() != null
					&& voteAnswer.getAnswer30() > 0) {
				voteAnswer.setAnswer30(voteAnswer.getAnswer30() + 1);
			} else {
				voteAnswer.setAnswer30(1L);
			}
		} else if (item.trim().equals("@31")) {
			if (voteAnswer.getAnswer31() != null
					&& voteAnswer.getAnswer31() > 0) {
				voteAnswer.setAnswer31(voteAnswer.getAnswer31() + 1);
			} else {
				voteAnswer.setAnswer31(1L);
			}
		} else if (item.trim().equals("@32")) {
			if (voteAnswer.getAnswer32() != null
					&& voteAnswer.getAnswer32() > 0) {
				voteAnswer.setAnswer32(voteAnswer.getAnswer32() + 1);
			} else {
				voteAnswer.setAnswer32(1L);
			}
		}else if (item.trim().equals("@33")) {
			if (voteAnswer.getAnswer33() != null
					&& voteAnswer.getAnswer33() > 0) {
				voteAnswer.setAnswer33(voteAnswer.getAnswer33() + 1);
			} else {
				voteAnswer.setAnswer33(1L);
			}
		}else if (item.trim().equals("@34")) {
			if (voteAnswer.getAnswer34() != null
					&& voteAnswer.getAnswer34() > 0) {
				voteAnswer.setAnswer34(voteAnswer.getAnswer34() + 1);
			} else {
				voteAnswer.setAnswer34(1L);
			}
		}else if (item.trim().equals("@35")) {
			if (voteAnswer.getAnswer35() != null
					&& voteAnswer.getAnswer35() > 0) {
				voteAnswer.setAnswer35(voteAnswer.getAnswer35() + 1);
			} else {
				voteAnswer.setAnswer35(1L);
			}
		}else if (item.trim().equals("@36")) {
			if (voteAnswer.getAnswer36() != null
					&& voteAnswer.getAnswer36() > 0) {
				voteAnswer.setAnswer36(voteAnswer.getAnswer36() + 1);
			} else {
				voteAnswer.setAnswer36(1L);
			}
		}else if (item.trim().equals("@37")) {
			if (voteAnswer.getAnswer37() != null
					&& voteAnswer.getAnswer37() > 0) {
				voteAnswer.setAnswer37(voteAnswer.getAnswer37() + 1);
			} else {
				voteAnswer.setAnswer37(1L);
			}
		}else if (item.trim().equals("@38")) {
			if (voteAnswer.getAnswer38() != null
					&& voteAnswer.getAnswer38() > 0) {
				voteAnswer.setAnswer38(voteAnswer.getAnswer38() + 1);
			} else {
				voteAnswer.setAnswer38(1L);
			}
		}else if (item.trim().equals("@39")) {
			if (voteAnswer.getAnswer39() != null
					&& voteAnswer.getAnswer39() > 0) {
				voteAnswer.setAnswer39(voteAnswer.getAnswer39() + 1);
			} else {
				voteAnswer.setAnswer39(1L);
			}
		}else if (item.trim().equals("@40")) {
			if (voteAnswer.getAnswer40() != null
					&& voteAnswer.getAnswer40() > 0) {
				voteAnswer.setAnswer40(voteAnswer.getAnswer40() + 1);
			} else {
				voteAnswer.setAnswer40(1L);
			}
		}
		
		voteAnswer.setClassIdentifier(usersession.getLoginId());
		voteAnswer.setVoteDate(new Date());
		this.getGeneralDao().save(voteAnswer);
	}
	
	@Override
	public void operateRadioAnswer(PrVoteAnswer answer, String item) {
		operateQuestionAnswer(answer, item);
	}

	@Override
	public void operateCheckBoxAnswer(PrVoteAnswer answer, String[] items) {
		for (int i = 0; i < items.length; i++) {
			operateQuestionAnswer(answer, items[i]);
		}
	}

	@Override
	public void copyVotePaper(PeVotePaper votePaper,PeProApplyno peProApplyno) {
		PeVotePaper votePaperNew = new PeVotePaper();
		votePaperNew.setEndDate(votePaper.getEndDate());
		votePaperNew.setEnumConstByFlagCanSuggest(votePaper.getEnumConstByFlagCanSuggest());
		votePaperNew.setEnumConstByFlagIsvalid(votePaper.getEnumConstByFlagIsvalid());
		votePaperNew.setEnumConstByFlagLimitDiffip(votePaper.getEnumConstByFlagLimitDiffip());
		votePaperNew.setEnumConstByFlagLimitDiffsession(votePaper.getEnumConstByFlagLimitDiffsession());
		votePaperNew.setEnumConstByFlagViewSuggest(votePaper.getEnumConstByFlagViewSuggest());
		votePaperNew.setFoundDate(votePaper.getFoundDate());
		votePaperNew.setNote(votePaper.getNote());
		votePaperNew.setPictitle(votePaper.getPictitle());
		votePaperNew.setStartDate(votePaper.getStartDate());
		votePaperNew.setTitle(votePaper.getTitle());
		votePaperNew.setPeProApplyno(peProApplyno);
		
		votePaperNew = (PeVotePaper) this.getGeneralDao().save(votePaperNew);
		
		DetachedCriteria dcQuestions = DetachedCriteria.forClass(PrVoteQuestion.class);
		dcQuestions.createAlias("peVotePaper", "peVotePaper");
		dcQuestions.add(Restrictions.eq("peVotePaper", votePaper));
		List<PrVoteQuestion>  listQusts = this.getGeneralDao().getList(dcQuestions);
		if(listQusts!=null&&!listQusts.isEmpty()){
			for(PrVoteQuestion question : listQusts){
				PrVoteQuestion questionNew = new PrVoteQuestion();
				questionNew.setPeVotePaper(votePaperNew);
				questionNew.setEnumConstByFlagQuestionType(question.getEnumConstByFlagQuestionType());
				questionNew.setQuestionNote(question.getQuestionNote());
				questionNew.setItem1(question.getItem1());
				questionNew.setItem2(question.getItem2());
				questionNew.setItem3(question.getItem3());
				questionNew.setItem4(question.getItem4());
				questionNew.setItem5(question.getItem5());
				questionNew.setItem6(question.getItem6());
				questionNew.setItem7(question.getItem7());
				questionNew.setItem8(question.getItem8());
				questionNew.setItem9(question.getItem9());
				questionNew.setItem10(question.getItem10());
				questionNew.setItem11(question.getItem11());
				questionNew.setItem12(question.getItem12());
				questionNew.setItem13(question.getItem13());
				questionNew.setItem14(question.getItem14());
				questionNew.setItem15(question.getItem15());
				questionNew.setItem16(question.getItem16());
				questionNew.setItem17(question.getItem17());
				questionNew.setItem18(question.getItem18());
				questionNew.setItem19(question.getItem19());
				questionNew.setItem20(question.getItem20());
				questionNew.setItem21(question.getItem21());
				questionNew.setItem22(question.getItem22());
				questionNew.setItem23(question.getItem23());
				questionNew.setItem24(question.getItem24());
				questionNew.setItem25(question.getItem25());
				questionNew.setItem26(question.getItem26());
				questionNew.setItem27(question.getItem27());
				questionNew.setItem28(question.getItem28());
				questionNew.setItem29(question.getItem29());
				questionNew.setItem30(question.getItem30());
				questionNew.setItem31(question.getItem31());
				questionNew.setItem32(question.getItem32());
				questionNew.setItem33(question.getItem33());
				questionNew.setItem34(question.getItem34());
				questionNew.setItem35(question.getItem35());
				questionNew.setItem36(question.getItem36());
				questionNew.setItem37(question.getItem37());
				questionNew.setItem38(question.getItem38());
				questionNew.setItem39(question.getItem39());
				questionNew.setItem40(question.getItem40());
				questionNew.setSequencesNo(question.getSequencesNo());
				this.getGeneralDao().save(questionNew);
			}
		}		
	}
	@Deprecated
	private StringBuffer updateCourseEnjoyNum(String firstvote,String firstList,String secondvote,String secondList,String thirdvote,String thirdList,String fouthvote,String fouthList,String fifthvote,String fifthList) throws EntityException {
		
		Map<String,String> params = new HashMap<String,String>();
		StringBuffer qvs = new StringBuffer();
		if(firstvote!=null&&firstList!=null&&firstList.indexOf(",")>0){
			String firstTeacher = firstList.split(",")[0];
			
			String firstsql = "select t.id from pe_course_plan t where t.theme=:firstvote and t.expert_name=:firstTeacher ";
			
			params.put("firstvote", firstvote);
			params.put("firstTeacher", firstTeacher);
			List listFirst = this.getGeneralDao().getBySQL(firstsql,params);
			if(listFirst==null || listFirst.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String firstId = listFirst.get(0).toString();
			this.getGeneralDao().executeBySQL("update pe_course_plan t set t.firstvote=t.firstvote+1 where t.id='"+firstId+"'");
			qvs.append("第一名:"+firstTeacher+","+firstvote+";");
		}
		if(secondvote!=null&&secondList!=null&&secondList.indexOf(",")>0){
			String secondTeacher = secondList.split(",")[0];
			String secondsql = "select t.id from pe_course_plan t where t.theme=:secondvote and t.expert_name=:secondTeacher";
			params.clear();
			params.put("secondvote", secondvote);
			params.put("secondTeacher", secondTeacher);
			List listsecond = this.getGeneralDao().getBySQL(secondsql,params);
			if(listsecond==null || listsecond.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String secondId = listsecond.get(0).toString();
			
			this.getGeneralDao().executeBySQL("update pe_course_plan t set t.secondvote=t.secondvote+1 where t.id='"+secondId+"'");
			qvs.append("第二名:"+secondTeacher+","+secondvote+";");
		}
		if(thirdvote!=null&&thirdList!=null&&thirdList.indexOf(",")>0){
			String thirdTeacher = thirdList.split(",")[0];
			String thirdsql = "select t.id from pe_course_plan t where t.theme=:thirdvote and t.expert_name=:thirdTeacher";
			params.clear();
			params.put("thirdvote", thirdvote);
			params.put("thirdTeacher", thirdTeacher);
			List listthird = this.getGeneralDao().getBySQL(thirdsql,params);
			if(listthird==null || listthird.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String thirdId = listthird.get(0).toString();
			this.getGeneralDao().executeBySQL("update pe_course_plan t set t.thirdvote=t.thirdvote+1 where t.id='"+thirdId+"'");
			qvs.append("第三名:"+thirdTeacher+","+thirdvote+";");
		}
		if(fouthvote!=null&&fouthList!=null&&fouthList.indexOf(",")>0){
			String fouthTeacher = fouthList.split(",")[0];
			String fouthsql = "select t.id from pe_course_plan t where t.theme=:fouthvote and t.expert_name=:fouthTeacher";
			params.clear();
			params.put("fouthvote", fouthvote);
			params.put("fouthTeacher", fouthTeacher);
			List listfouth = this.getGeneralDao().getBySQL(fouthsql,params);
			if(listfouth==null || listfouth.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String fouthId = listfouth.get(0).toString();
			this.getGeneralDao().executeBySQL("update pe_course_plan t set t.fouthvote=t.fouthvote+1 where t.id='"+fouthId+"'");
			qvs.append("第四名:"+fouthTeacher+","+fouthvote+";");
		}
		if(fifthvote!=null&&fifthList!=null&&fifthList.indexOf(",")>0){
			String fifthTeacher = fifthList.split(",")[0];
			String fifthsql = "select t.id from pe_course_plan t where  t.theme=:fifthvote and t.expert_name=:fifthTeacher";
			params.clear();
			params.put("fifthvote", fifthvote);
			params.put("fifthTeacher", fifthTeacher);
			List listfifth = this.getGeneralDao().getBySQL(fifthsql,params);
			if(listfifth==null || listfifth.isEmpty()){
				throw new EntityException("教师或专题选择错误！");
			}
			String fifthId = listfifth.get(0).toString();
			this.getGeneralDao().executeBySQL("update pe_course_plan t set t.fifthvote=t.fifthvote+1 where  t.id='"+fifthId+"'");
			qvs.append("第五名:"+fifthTeacher+","+fifthvote+";");
		}
			
		return qvs;
	}
	
private StringBuffer updateCourseEnjoyNum(String firstvote,String secondvote,String thirdvote,String fouthvote,String fifthvote) throws EntityException {
		
		Map<String,String> params = new HashMap<String,String>();
		StringBuffer qvs = new StringBuffer();
		if(firstvote!=null && !firstvote.equals("")){
			PeCoursePlan plan = (PeCoursePlan) this.getGeneralDao().getById(PeCoursePlan.class, firstvote);
			if(plan == null){
				throw new EntityException("教师或专题选择错误！");
			}
			plan.setFirstvote(plan.getFirstvote()+1);
			this.getGeneralDao().save(plan);
			qvs.append("第一名:"+plan.getExpertName()+","+plan.getTheme()+";");
		}
		if(secondvote!=null && !secondvote.equals("")){
			PeCoursePlan plan = (PeCoursePlan) this.getGeneralDao().getById(PeCoursePlan.class, secondvote);
			if(plan == null){
				throw new EntityException("教师或专题选择错误！");
			}
			plan.setSecondvote(plan.getSecondvote()+1);
			this.getGeneralDao().save(plan);
			qvs.append("第二名:"+plan.getExpertName()+","+plan.getTheme()+";");
		}
		if(thirdvote!=null && !thirdvote.equals("")){
			PeCoursePlan plan = (PeCoursePlan) this.getGeneralDao().getById(PeCoursePlan.class, thirdvote);
			if(plan == null){
				throw new EntityException("教师或专题选择错误！");
			}
			plan.setThirdvote(plan.getThirdvote()+1);
			this.getGeneralDao().save(plan);
			qvs.append("第三名:"+plan.getExpertName()+","+plan.getTheme()+";");
		}
		if(fouthvote!=null && !fouthvote.equals("")){
			PeCoursePlan plan = (PeCoursePlan) this.getGeneralDao().getById(PeCoursePlan.class, fouthvote);
			if(plan == null){
				throw new EntityException("教师或专题选择错误！");
			}
			plan.setFouthvote(plan.getFouthvote()+1);
			this.getGeneralDao().save(plan);
			qvs.append("第四名:"+plan.getExpertName()+","+plan.getTheme()+";");
		}
		if(fifthvote!=null && !fifthvote.equals("")){
			PeCoursePlan plan = (PeCoursePlan) this.getGeneralDao().getById(PeCoursePlan.class, fifthvote);
			if(plan == null){
				throw new EntityException("教师或专题选择错误！");
			}
			plan.setFifthvote(plan.getFifthvote()+1);
			this.getGeneralDao().save(plan);
			qvs.append("第五名:"+plan.getExpertName()+","+plan.getTheme()+";");
		}
			
		return qvs;
	}
	public void saveRecord(PrVoteRecord record,List<PrVoteQuestion> questionList,HttpServletRequest request, String[] voteArray) throws EntityException{
		for(PrVoteQuestion question : questionList){
			List listPrVoteAnswer = this.getGeneralDao().getByHQL("from PrVoteAnswer t where t.prVoteQuestion='"+question.getId()+"' and t.classIdentifier ='"+record.getClassIdentifier()+"'" );
			PrVoteAnswer answer = (PrVoteAnswer)listPrVoteAnswer.get(0);
			if(question.getEnumConstByFlagQuestionType().getCode().equals("1")) {
				String questionValue = request.getParameter(question.getId());
				operateRadioAnswer(answer, questionValue);
				
			}else {
				String[] questionValues = request.getParameterValues(question.getId());
				operateCheckBoxAnswer(answer, questionValues);
			}
		}
		record.setContent(updateCourseEnjoyNum(voteArray[0],voteArray[1],voteArray[2],voteArray[3],voteArray[4]).toString());
		PeVoteDetail detail = (PeVoteDetail) this.getGeneralDao().save(record.getPeVoteDetail());
		record.setPeVoteDetail(detail);
		
		this.getGeneralDao().save(record);
	}

	
}