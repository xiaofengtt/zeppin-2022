package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzCourseFeedback;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.BzzAssessService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PebzzManagerAssessAction extends MyBaseAction {

	private String courseid;
	private List<PeBzzAssess> peBzzAssesslist;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private List<PeBzzCourseFeedback> FeedBacklist;
	private PeBzzSuggestion peBzzSuggestion;
	private BzzAssessService bzzAssessService;
	private String yaoqiu;
	private String[] itm0;
	private String[] itm1;
	private String[] itm2;
	private String[] itm3;
	private String[] itm4;
	private String[] itm5;
	private String[] itm6;
	private String[] itm7;
	private String[] itm8;
	private String[] itm9;
	private String[] itm10;
	private String[] itm11;
	private String[] totle = new String[8];
	private String suggestion;
	private String opencourseid;
	private String sugid;

	public String updateAssess() {
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();

		String[] temp = new String[12];
		temp[0] = itm0[0];
		temp[1] = itm1[0];
		temp[2] = itm2[0];
		temp[3] = itm3[0];
		temp[4] = itm4[0];
		temp[5] = itm5[0];
		temp[6] = itm6[0];
		temp[7] = itm7[0];
		temp[8] = itm8[0];
		temp[9] = itm9[0];
		temp[10] = itm10[0];
		temp[11] = itm11[0];

		try {
			PeBzzSuggestion peBzzSuggestion = this.bzzAssessService
					.getPeBzzSuggestion(sugid);
			peBzzSuggestion.setDataDate(new Date());
			peBzzSuggestion.setContent(suggestion);
			peBzzSuggestion.setYaoQiu(yaoqiu);
			this.bzzAssessService.updatepeBzzSuggestion(peBzzSuggestion);

			DetachedCriteria tedc = DetachedCriteria
					.forClass(PeBzzAssess.class);
			DetachedCriteria teccdc = tedc.createCriteria(
					"peBzzCourseFeedback", "peBzzCourseFeedback");
			tedc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			tedc.add(Restrictions.eq("prBzzTchOpencourse.id", opencourseid));
			tedc.add(Restrictions.eq("ssoUser", ssoUser));
			teccdc.add(Restrictions
					.sqlRestriction("1=1 order by to_number(px)"));

			List<PeBzzAssess> dclist = new ArrayList<PeBzzAssess>();
			dclist = this.getGeneralService().getList(tedc);
			Iterator<PeBzzAssess> it = dclist.iterator();
			int num = 0;
			while (it.hasNext()) {
				PeBzzAssess assess = it.next();
				assess.setDatadate(new Date());
				assess.setAssess(temp[num]);
				this.bzzAssessService.updatePeBzzAssess(assess);
				num++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateAssess";
	}

	public String Assessing() {
		String target = "";

		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();

		String[] temp = new String[12];
		temp[0] = itm0[0];
		temp[1] = itm1[0];
		temp[2] = itm2[0];
		temp[3] = itm3[0];
		temp[4] = itm4[0];
		temp[5] = itm5[0];
		temp[6] = itm6[0];
		temp[7] = itm7[0];
		temp[8] = itm8[0];
		temp[9] = itm9[0];
		temp[10] = itm10[0];
		temp[11] = itm11[0];

		try {
			PrBzzTchOpencourse opencourse = new PrBzzTchOpencourse();
			opencourse.setId(opencourseid);
			PeBzzSuggestion peBzzSuggestion = new PeBzzSuggestion();
			peBzzSuggestion.setContent(suggestion);
			peBzzSuggestion.setSsoUser(ssoUser);
			peBzzSuggestion.setYaoQiu(yaoqiu);
			peBzzSuggestion.setPrBzzTchOpencourse(opencourse);
			peBzzSuggestion.setDataDate(new Date());
			this.getGeneralService().save(peBzzSuggestion);
			DetachedCriteria tedc = DetachedCriteria
					.forClass(PeBzzCourseFeedback.class);
			tedc.add(Restrictions.sqlRestriction("1=1 Order by to_number(px)"));
			List<PeBzzCourseFeedback> list = new ArrayList<PeBzzCourseFeedback>();
			list = this.getGeneralService().getList(tedc);
			Iterator<PeBzzCourseFeedback> it = list.iterator();
			int num = 0;
			while (it.hasNext()) {
				PeBzzAssess peBzzAssess = new PeBzzAssess();
				peBzzAssess.setPeBzzCourseFeedback(it.next());
				peBzzAssess.setSsoUser(ssoUser);
				peBzzAssess.setPrBzzTchOpencourse(opencourse);
				peBzzAssess.setAssess(temp[num]);
				peBzzAssess.setDatadate(new Date());
				num++;
				this.getGeneralService().save(peBzzAssess);
			}
			target = "Assessing";

		} catch (EntityException e) {
			e.printStackTrace();
		}
		return target;
	}

	public String toAssess() {
		String target = "";

		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();

		DetachedCriteria checkdc = DetachedCriteria.forClass(PeBzzAssess.class);
		checkdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		checkdc.add(Restrictions.eq("ssoUser", ssoUser));
		checkdc.add(Restrictions.eq("prBzzTchOpencourse.id", courseid));
		List<PeBzzAssess> asslist = new ArrayList<PeBzzAssess>();
		try {
			asslist = this.getGeneralService().getList(checkdc);

			if (asslist.size() > 0) {
				// 已经提交过
				DetachedCriteria detaildc = DetachedCriteria.forClass(PeBzzAssess.class);
				DetachedCriteria te1dc = detaildc.createCriteria("peBzzCourseFeedback","peBzzCourseFeedback");
				detaildc.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse");
				detaildc.add(Restrictions.eq("prBzzTchOpencourse.id", courseid));
				detaildc.add(Restrictions.eq("ssoUser", ssoUser));
				te1dc.add(Restrictions.sqlRestriction("1=1 order by to_number(px)"));
				this.setPeBzzAssesslist(this.getGeneralService().getList(
						detaildc));

				DetachedCriteria pdc = DetachedCriteria
						.forClass(PrBzzTchOpencourse.class);
				pdc.add(Restrictions.eq("id", courseid));
				List<PrBzzTchOpencourse> list = new ArrayList<PrBzzTchOpencourse>();
				list = this.getGeneralService().getList(pdc);
				this.setPrBzzTchOpencourse(list.get(0));

				DetachedCriteria detachedCriteria = DetachedCriteria
						.forClass(PeBzzSuggestion.class);
				detachedCriteria.createCriteria("prBzzTchOpencourse",
						"prBzzTchOpencourse");
				detachedCriteria.add(Restrictions.eq("ssoUser", ssoUser));
				detachedCriteria.add(Restrictions.eq("prBzzTchOpencourse.id",
						courseid));

				PeBzzSuggestion bzzSuggestion = (PeBzzSuggestion) this
						.getGeneralService().getList(detachedCriteria).get(0);

				this.setPeBzzSuggestion(bzzSuggestion);

				target = "todetailAssessing";
			} else {
				// 尚未提交

				DetachedCriteria pdc = DetachedCriteria
						.forClass(PrBzzTchOpencourse.class);
				pdc.add(Restrictions.eq("id", courseid));
				List<PrBzzTchOpencourse> list = new ArrayList<PrBzzTchOpencourse>();
				list = this.getGeneralService().getList(pdc);
				this.setPrBzzTchOpencourse(list.get(0));

				DetachedCriteria FeeDback = DetachedCriteria
				.forClass(PeBzzCourseFeedback.class);
				FeeDback.add(Restrictions.sqlRestriction("1=1 Order by to_number(px)"));

				this
						.setFeedBacklist(this.getGeneralService().getList(
								FeeDback));
				target = "toAssess";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return target;
	}
	

	public void initGrid() {
	}

	public void setEntityClass() {
		this.servletPath = "/entity/teaching/pebzzManagerAssess";
	}

	public void setServletPath() {
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public List<PeBzzAssess> getPeBzzAssesslist() {
		return peBzzAssesslist;
	}

	public void setPeBzzAssesslist(List<PeBzzAssess> peBzzAssesslist) {
		this.peBzzAssesslist = peBzzAssesslist;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public List<PeBzzCourseFeedback> getFeedBacklist() {
		return FeedBacklist;
	}

	public void setFeedBacklist(List<PeBzzCourseFeedback> feedBacklist) {
		FeedBacklist = feedBacklist;
	}

	public PeBzzSuggestion getPeBzzSuggestion() {
		return peBzzSuggestion;
	}

	public void setPeBzzSuggestion(PeBzzSuggestion peBzzSuggestion) {
		this.peBzzSuggestion = peBzzSuggestion;
	}

	public BzzAssessService getBzzAssessService() {
		return bzzAssessService;
	}

	public void setBzzAssessService(BzzAssessService bzzAssessService) {
		this.bzzAssessService = bzzAssessService;
	}

	public String[] getItm0() {
		return itm0;
	}

	public void setItm0(String[] itm0) {
		this.itm0 = itm0;
	}

	public String[] getItm1() {
		return itm1;
	}

	public void setItm1(String[] itm1) {
		this.itm1 = itm1;
	}

	public String[] getItm2() {
		return itm2;
	}

	public void setItm2(String[] itm2) {
		this.itm2 = itm2;
	}

	public String[] getItm3() {
		return itm3;
	}

	public void setItm3(String[] itm3) {
		this.itm3 = itm3;
	}

	public String[] getItm4() {
		return itm4;
	}

	public void setItm4(String[] itm4) {
		this.itm4 = itm4;
	}

	public String[] getItm5() {
		return itm5;
	}

	public void setItm5(String[] itm5) {
		this.itm5 = itm5;
	}

	public String[] getItm6() {
		return itm6;
	}

	public void setItm6(String[] itm6) {
		this.itm6 = itm6;
	}

	public String[] getItm7() {
		return itm7;
	}

	public void setItm7(String[] itm7) {
		this.itm7 = itm7;
	}

	public String[] getItm8() {
		return itm8;
	}

	public void setItm8(String[] itm8) {
		this.itm8 = itm8;
	}

	public String[] getTotle() {
		return totle;
	}

	public void setTotle(String[] totle) {
		this.totle = totle;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getOpencourseid() {
		return opencourseid;
	}

	public void setOpencourseid(String opencourseid) {
		this.opencourseid = opencourseid;
	}

	public String getSugid() {
		return sugid;
	}

	public void setSugid(String sugid) {
		this.sugid = sugid;
	}

	public String[] getItm9() {
		return itm9;
	}

	public String[] getItm10() {
		return itm10;
	}

	public String[] getItm11() {
		return itm11;
	}

	public void setItm9(String[] itm9) {
		this.itm9 = itm9;
	}

	public void setItm10(String[] itm10) {
		this.itm10 = itm10;
	}

	public void setItm11(String[] itm11) {
		this.itm11 = itm11;
	}

	public String getYaoqiu() {
		return yaoqiu;
	}

	public void setYaoqiu(String yaoqiu) {
		this.yaoqiu = yaoqiu;
	}

}
