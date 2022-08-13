package com.whaty.platform.entity.web.action.information.vote;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrVoteAnswer;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeVotePaperService;
import com.whaty.platform.entity.util.AnalyseClassType;
import com.whaty.platform.entity.util.ExpressionParse;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeVotePaperAction extends PeVotePaperInitAction {

	private List<PeVotePaper> peVotePaperList; // 调查问卷列表
	private List<PeProApplyno> proApplyno;
	private PeVotePaper peVotePaper; // 添加或修改时传递参数
	private String active; // 是否发布
	private String canSuggest; // 是否可以添加建议
	private String viewSuggest; // 是否可以查看建议
	private String limitIp; // 是否限制ip
	private String limitSession; // 是否限制会话
	private List<PrVoteAnswer> prVoteAnswerList; // 问卷问题对应的答案及包含的问题
	private int canVote;// 用户是否能够提交调查问卷 0不可以 1可以
	private int pastDue;// 调查问卷日期是否有效 0有效 1还未开始 2已经过期
	private String suggest;// 用户所填建议
	private long voteNumber; // 参加投票的人数
	private String startDate;// 调查问卷开始时间
	private String endDate;// 调查问卷结束时间
	private List<PeProApplyno> applynoList;
	private List questionList;
	private String exportFileName;

	private String userLoginId;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	// 根据选择查看调查问卷列表
	public String searchToVotePaper() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		return "searchToVotePaper";
	}

	// 根据选择查看调查问卷列表
	public void searchToVotePaperNew() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/wn_gpjh/paper_list.action?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择查看调查问卷列表
	public void unitNormal() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/wn_gpjh/unitNormal_forward.action?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择查看调查问卷列表
	public void checkExcel() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/checkExcel.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择查看调查问卷列表
	public void importExcel() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/importExcel.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择查看调查问卷列表
	public void traineeNormal() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		String role = usersession.getRoleType();
		if (role.equals("3")) {
			role = "1";
		} else if (role.equals("4")) {
			role = "2";
		}

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeManager.jsp?userid=" + classIdentifier + "&role=" + role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void traineeJh() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/trainee_forwardJHUpload?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void traineeJhManager() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		String role = usersession.getRoleType();
		if (role.equals("3")) {
			role = "1";
		} else if (role.equals("4")) {
			role = "2";
		}

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/jhTraineeManager.jsp?userid=" + classIdentifier + "&role=" + role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeHistory() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/trainee_historyUpload?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeHistoryManager() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		String role = usersession.getRoleType();
		if (role.equals("3")) {
			role = "1";
		} else if (role.equals("4")) {
			role = "2";
		}

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/historyTrainee.jsp?userid=" + classIdentifier + "&role=" + role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trainecx() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/trainee_forwardUpload?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void trainecxold() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/trainee_forwardUploadOld?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void traineecxManager() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		String role = usersession.getRoleType();
		if (role.equals("3")) {
			role = "1";
		} else if (role.equals("4")) {
			role = "2";
		}

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeManager.jsp?userid=" + classIdentifier + "&role=" + role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void traineeTJ() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeLv.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeTJNUMFORMW() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeNumForSF.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeTJNUMFORSFLB() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeNumForSFLB.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeTJNUMFORSFPKX() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeNumForSFPKX.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeTJNUMFORSFQY() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeNumForSFQY.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void traineeTJNUMFORALLPKX() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminTraineeNumForAllPKX.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择查看调查问卷列表
	public void pcProjectNormal() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/project_forward.action?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择查看调查问卷列表
	public void wenNormal() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/paper_list.action?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cailiaoAdu() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminDeclareFirst.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mwwenpaiming() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		// this.setApplynoList(this.getPaperType());// 初始化培训项目列表
		// return "searchToVotePaper";
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/province_huizong.action?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void admChild() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminChildManager.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void admDeclare() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminDeclareManager.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void admDecalareList() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/adminDeclareAdu.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void proChild() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/proChildManager.jsp?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void proDeclare() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/proChildDeclare.jsp?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void proAdu() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/adu_forward.action?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trainingUpload() {
		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/trainee_forwardUpload?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void expAdu() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/expertScore.jsp?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void proChildAdu() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/mw_gpjh/vote/proChildAdu.jsp?userid=" + classIdentifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void yusuan() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_yusuanList.jsp?userid=" + classIdentifier + "&role=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actual() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_actualList.jsp?userid=" + classIdentifier + "&role=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void yusuan2() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_yusuanList2.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actual2() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_actualList2.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void yusuanNew() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_yusuanListNew.jsp?userid=" + classIdentifier + "&role=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualNew() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_actualListNew.jsp?userid=" + classIdentifier + "&role=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void yusuan2New() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_yusuanList2_new.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actual2New() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_actualList2_new.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void yusuanNewTwo() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_yusuanListNew2.jsp?userid=" + classIdentifier + "&role=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualNewTwo() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_actualListNew2.jsp?userid=" + classIdentifier + "&role=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void yusuan2NewTwo() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_yusuanList2_new2.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actual2NewTwo() {

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		System.out.println(usersession.getRoleType());

		this.setUserLoginId(classIdentifier);
		try {
			ServletActionContext.getResponse().sendRedirect("/buget_gpjh/fee_actualList2_new2.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转向添加调查问卷
	 * 
	 * @return
	 */
	public String toAddVotePaper() {
		this.setApplynoList(this.getPaperType());
		return "addVotePaper";
	}

	/**
	 * 查询教师对应专题
	 */
	public void searchTheme() {
		try {
			ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String data = ServletActionContext.getRequest().getQueryString();
		data = data.substring(data.indexOf("=") + 1);

		UserSession usersession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String classIdentifier = usersession.getLoginId();
		String year = classIdentifier.substring(0, 2);
		String unitCode = classIdentifier.substring(2, 7);
		String subjectCode = classIdentifier.substring(7, 9);
		String projectCode = classIdentifier.substring(9, 10);

		StringBuffer strb = new StringBuffer();
		try {
			String dataFinal = URLDecoder.decode(data, "UTF-8");
			String[] theme = dataFinal.split(",");
			String sql = "select t.id,t.theme from pe_course_plan t join pe_pro_apply a " + "on t.fk_pro_apply = a.id where a.fk_unit=:theUnitCode and a.fk_subject=:theSubjectCode and a.fk_applyno=:theProjectCode"
					+ "  and t.expert_name=:theme0 and t.zhicheng=:theme1 and t.fk_province=:theme2 and t.work_place=:theme3";
			Map map = new HashMap();
			map.put("theUnitCode", getIdByCode(unitCode, "pe_unit"));
			map.put("theSubjectCode", getIdByCode(subjectCode, "pe_subject"));
			map.put("theProjectCode", getprojectId(projectCode, year));
			map.put("theme0", theme[0]);
			map.put("theme1", theme[1]);
			map.put("theme2", theme[2]);
			map.put("theme3", theme[3]);
			List list = new LinkedList();
			list = this.getGeneralService().getBySQL(sql, map);
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object object[] = (Object[]) obj;
				strb.append(object[0].toString() + "," + object[1].toString()).append(";");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.write(strb.toString().length() == 0 ? "" : strb.toString().substring(0, strb.length() - 1));
	}

	/**
	 * 取得调查问卷类型
	 * 
	 * @return
	 */
	public List<PeProApplyno> getPaperType() {
		String sql = "select id,name from pe_pro_applyno /* where year=to_char(sysdate,'YYYY')*/ order by code";
		List<PeProApplyno> proApplyno = null;
		try {
			proApplyno = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return proApplyno;
	}

	private String getprojectId(String code, String year) {
		String getCodeSQL = "select id from pe_pro_applyno where code=:theCode and year = :year";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theCode", code);
		params.put("year", "20" + year);
		List<String> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(getCodeSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String queryCode = "";
		if (resultList != null && resultList.size() != 0) {
			queryCode = resultList.get(0);
		}
		return queryCode;
	}

	/**
	 * 查看调查问卷
	 * 
	 * @return
	 */
	public String viewDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String classIdentifier = request.getParameter("suffix");

		setVotePaperAnswer(classIdentifier);
		// 设置参加投票的人数
		// DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
		// DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper",
		// "peVotePaper");
		// dcPeVotePaper.add(Restrictions.eq("id", getBean().getId()));
		setVoteNumber(classIdentifier);
		return "detail";
	}

	/**
	 * 
	 * @return
	 */
	public String exportVotePaper() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String classIdentifier = request.getParameter("suffix");
		setVoteNumber(classIdentifier);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		exportFileName = "export_" + getServletPath().substring(getServletPath().lastIndexOf("/") + 1, getServletPath().length() - 6) + "_" + dateFormat.format(new Date()) + ".xls";
		// 打开文件
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(new File(ServletActionContext.getServletContext().getRealPath("/test/" + exportFileName)));
			WritableSheet sheet = book.createSheet("调查询卷结果", 0);
			WritableFont wf0 = new WritableFont(WritableFont.TIMES, 12);
			WritableCellFormat wcf = new WritableCellFormat(wf0);
			// 水平居中
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			// 垂直居中
			wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// 边框
			wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

			setExcelTitle(sheet, wcf);
			List<PrVoteQuestion> questList = getPrVoteQuestionList();
			int row = 1;
			// 记录问题最大长度
			int maxTitleLen = 0;
			int maxCheckLen = 0;
			for (PrVoteQuestion quest : questList) {
				Object answerNum[] = getAnswerNums(classIdentifier, quest);
				// 记录问题选项个数，用于合并单元格
				int checkNum = 0;
				for (int i = 1; i <= 32; i++) {
					String item = null;
					Method method = PrVoteQuestion.class.getMethod("getItem" + i, null);
					if (method != null) {
						item = (String) method.invoke(quest, null);
					}
					if (item != null) {
						if (quest.getQuestionNote().length() > maxTitleLen) {
							maxTitleLen = quest.getQuestionNote().length();
						}
						if (item.length() > maxCheckLen) {
							maxCheckLen = item.length();
						}

						Label label = null;
						if (checkNum == 0) {
							label = new Label(0, row, quest.getQuestionNote());
							label.setCellFormat(wcf);
							sheet.addCell(label);
						}
						label = new Label(1, row, item);
						label.setCellFormat(wcf);
						sheet.addCell(label);

						long papers = ((BigDecimal) answerNum[i - 1]).longValue();
						label = new Label(2, row, papers > 0 ? String.valueOf(papers) : "0");
						label.setCellFormat(wcf);
						sheet.addCell(label);

						label = new Label(3, row, papers > 0 ? String.valueOf(Const.div(papers * 100, (double) this.getVoteNumber(), Const.percentScale)) : "0");
						label.setCellFormat(wcf);
						sheet.addCell(label);
						checkNum++;
						row++;
					}
				}
				sheet.mergeCells(0, row - checkNum, 0, row - 1);
			}
			sheet.setColumnView(0, maxTitleLen + 6);
			sheet.setColumnView(1, maxCheckLen + 6);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("导出失败");
			return "msg";
		} finally {
			try {
				book.write();
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "excel";
	}

	public void setExcelTitle(WritableSheet sheet, WritableCellFormat wcf) throws RowsExceededException, WriteException {
		Label label = new Label(0, 0, "问题");
		label.setCellFormat(wcf);
		sheet.addCell(label);

		label = new Label(1, 0, "答案");
		label.setCellFormat(wcf);
		sheet.addCell(label);

		label = new Label(2, 0, "得票总数");
		label.setCellFormat(wcf);
		sheet.addCell(label);

		label = new Label(3, 0, "所占百分比");
		label.setCellFormat(wcf);
		sheet.addCell(label);
	}

	/**
	 * 转向问题管理页面
	 * 
	 * @return
	 * @throws EntityException
	 */
	public String toVoteQuestion() throws EntityException {
		PeVotePaper paper = (PeVotePaper) getGeneralService().getById(PeVotePaper.class, getBean().getId());
		setPeVotePaper(paper);
		DetachedCriteria dcQuestion = DetachedCriteria.forClass(PrVoteQuestion.class);
		dcQuestion.createAlias("peVotePaper", "peVotePaper");
		// dcQuestion.createAlias("enumConstByFlagQuestionType",
		// "enumConstByFlagQuestionType");
		dcQuestion.add(Restrictions.eq("peVotePaper", paper));
		dcQuestion.addOrder(Order.asc("sequencesNo"));
		List<PrVoteQuestion> questList = this.getGeneralService().getList(dcQuestion);
		this.setQuestionList(questList);
		return "voteQuestion";
	}

	/**
	 * 检查用户是否已经参加过投票，设置 int canVote;//用户是否能够提交调查问卷 0不可以 1可以
	 */
	public boolean checkCanVote() {

		boolean flag = true;
		/**
		 * 如果调查问卷限制IP，则检查用户的IP是否已经投票
		 */
		if (this.getPeVotePaper() != null && this.getPeVotePaper().getEnumConstByFlagLimitDiffip() != null && "1".equals(this.getPeVotePaper().getEnumConstByFlagLimitDiffip().getCode())) {
			// 得到用户的IP
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = request.getRemoteAddr();
			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
			DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
			dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
			dc.add(Restrictions.eq("ip", ip));
			try {
				List list = this.getGeneralService().getList(dc);
				// 如果IP已经存在则设置为不能投票
				if (list.size() > 0) {
					this.setCanVote(0);
					flag = false;
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		if (this.getCanVote() == 1) {
			/**
			 * 如果调查问卷限制session，则检查用户session是否已经投票
			 */
			if (this.getPeVotePaper() != null && this.getPeVotePaper().getEnumConstByFlagLimitDiffsession() != null && this.getPeVotePaper().getEnumConstByFlagLimitDiffsession().getCode().equals("1")) {
				// 得到用户session
				HttpSession session = ServletActionContext.getRequest().getSession();
				String sessionId = session.getId();
				DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
				DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
				dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
				dc.add(Restrictions.eq("userSession", sessionId));
				try {
					List list = this.getGeneralService().getList(dc);
					// 如果session已经存在则设置为不能投票
					if (list.size() > 0) {
						this.setCanVote(0);
						flag = false;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 转向修改页面
	 */
	public String toEditVotePaper() {
		this.setProApplyno(this.getPaperType());
		// this.setMap(this.semesterAndCourse());
		try {
			this.setPeVotePaper((PeVotePaper) this.getGeneralService().getById(PeVotePaper.class, this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "editVotePaper";
	}

	/**
	 * @description 
	 *              根据ID设置peVotePaper，并依据传入的班级标识码及调查问卷id号查询设置答案列表prVoteAnswerLIst
	 * @param classIdentifier
	 *            班级标识码(不含最后一位随机数字)
	 */
	protected void setVotePaperAnswer(String classIdentifier) {
		try {
			// 设置调查问卷
			PeVotePaper votePaper = (PeVotePaper) getGeneralService().getById(PeVotePaper.class, getBean().getId());

			setPeVotePaper(votePaper);

			List<PrVoteQuestion> questList = getPrVoteQuestionList();
			List voteAnswerList = new ArrayList();
			for (PrVoteQuestion quest : questList) {
				Object answerNum[] = getAnswerNums(classIdentifier, quest);
				PrVoteAnswer prVoteAnswer = new PrVoteAnswer();
				prVoteAnswer.setAnswer1(((BigDecimal) answerNum[0]).longValue());
				prVoteAnswer.setAnswer2(((BigDecimal) answerNum[1]).longValue());
				prVoteAnswer.setAnswer3(((BigDecimal) answerNum[2]).longValue());
				prVoteAnswer.setAnswer4(((BigDecimal) answerNum[3]).longValue());
				prVoteAnswer.setAnswer5(((BigDecimal) answerNum[4]).longValue());
				prVoteAnswer.setAnswer6(((BigDecimal) answerNum[5]).longValue());
				prVoteAnswer.setAnswer7(((BigDecimal) answerNum[6]).longValue());
				prVoteAnswer.setAnswer8(((BigDecimal) answerNum[7]).longValue());
				prVoteAnswer.setAnswer9(((BigDecimal) answerNum[8]).longValue());
				prVoteAnswer.setAnswer10(((BigDecimal) answerNum[9]).longValue());
				prVoteAnswer.setAnswer11(((BigDecimal) answerNum[10]).longValue());
				prVoteAnswer.setAnswer12(((BigDecimal) answerNum[11]).longValue());
				prVoteAnswer.setAnswer13(((BigDecimal) answerNum[12]).longValue());
				prVoteAnswer.setAnswer14(((BigDecimal) answerNum[13]).longValue());
				prVoteAnswer.setAnswer15(((BigDecimal) answerNum[14]).longValue());
				prVoteAnswer.setAnswer16(((BigDecimal) answerNum[15]).longValue());
				prVoteAnswer.setAnswer17(((BigDecimal) answerNum[16]).longValue());
				prVoteAnswer.setAnswer18(((BigDecimal) answerNum[17]).longValue());
				prVoteAnswer.setAnswer19(((BigDecimal) answerNum[18]).longValue());
				prVoteAnswer.setAnswer20(((BigDecimal) answerNum[19]).longValue());
				prVoteAnswer.setAnswer21(((BigDecimal) answerNum[20]).longValue());
				prVoteAnswer.setAnswer22(((BigDecimal) answerNum[21]).longValue());
				prVoteAnswer.setAnswer23(((BigDecimal) answerNum[22]).longValue());
				prVoteAnswer.setAnswer24(((BigDecimal) answerNum[23]).longValue());
				prVoteAnswer.setAnswer25(((BigDecimal) answerNum[24]).longValue());
				prVoteAnswer.setAnswer26(((BigDecimal) answerNum[25]).longValue());
				prVoteAnswer.setAnswer27(((BigDecimal) answerNum[26]).longValue());
				prVoteAnswer.setAnswer28(((BigDecimal) answerNum[27]).longValue());
				prVoteAnswer.setAnswer29(((BigDecimal) answerNum[28]).longValue());
				prVoteAnswer.setAnswer30(((BigDecimal) answerNum[29]).longValue());
				prVoteAnswer.setAnswer31(((BigDecimal) answerNum[30]).longValue());
				prVoteAnswer.setAnswer32(((BigDecimal) answerNum[31]).longValue());
				prVoteAnswer.setAnswer33(((BigDecimal) answerNum[32]).longValue());
				prVoteAnswer.setAnswer34(((BigDecimal) answerNum[33]).longValue());
				prVoteAnswer.setAnswer35(((BigDecimal) answerNum[34]).longValue());
				prVoteAnswer.setAnswer36(((BigDecimal) answerNum[35]).longValue());
				prVoteAnswer.setAnswer37(((BigDecimal) answerNum[36]).longValue());
				prVoteAnswer.setAnswer38(((BigDecimal) answerNum[37]).longValue());
				prVoteAnswer.setAnswer39(((BigDecimal) answerNum[38]).longValue());
				prVoteAnswer.setAnswer40(((BigDecimal) answerNum[39]).longValue());
				prVoteAnswer.setPrVoteQuestion(quest);
				voteAnswerList.add(prVoteAnswer);// System.out.println(prVoteAnswer.getAnswer1()+"==========="+quest.getId()+"========"+classIdentifier);
			}
			this.setPrVoteAnswerList(voteAnswerList);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	protected void setVoteNumber(String classIdentifier) {
		StringBuffer sql_t = new StringBuffer();
		sql_t.append(" select  count(t.id) from pr_vote_record t  ");
		sql_t.append("  where t.FK_VOTE_PAPER_ID = '" + getBean().getId() + "'                              ");
		if (classIdentifier.length() == 3) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + "%" + classIdentifier.substring(2) + "_'                ");
		} else if (classIdentifier.length() == 5) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + classIdentifier.substring(3) + "%" + classIdentifier.substring(2, 3) + "_'   ");
		} else if (classIdentifier.length() == 8) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + classIdentifier.substring(3, 8) + "%" + classIdentifier.substring(2, 3) + "_'   ");
		} else if (classIdentifier.length() == 10) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + classIdentifier.substring(3, 10) + "%" + classIdentifier.substring(2, 3) + "_'   ");
		}
		System.out.println(sql_t);
		try {
			// List list = this.getGeneralService().getList(dc);
			// if (list.size()>0) {
			// this.setVoteNumber(list.size());
			// }
			List list = this.getGeneralService().getBySQL(sql_t.toString());
			this.setVoteNumber(Integer.parseInt(list.get(0).toString()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	private List<PrVoteQuestion> getPrVoteQuestionList() throws EntityException {
		DetachedCriteria dcQuestion = DetachedCriteria.forClass(PrVoteQuestion.class);
		dcQuestion.createAlias("peVotePaper", "peVotePaper");
		dcQuestion.add(Restrictions.eq("peVotePaper.id", getBean().getId()));
		dcQuestion.addOrder(Order.asc("sequencesNo"));
		return getGeneralService().getList(dcQuestion);
	}

	private Object[] getAnswerNums(String classIdentifier, PrVoteQuestion quest) throws EntityException {
		StringBuffer sql_t = new StringBuffer();
		sql_t.append(" select nvl(sum(t.answer1),0), nvl(sum(t.answer2),0),nvl(sum(t.answer3),0), nvl(sum(t.answer4),0), nvl(sum(t.answer5),0),     ");
		sql_t.append("        nvl(sum(t.answer6),0),  nvl(sum(t.answer7),0), nvl(sum(t.answer8),0), nvl(sum(t.answer9),0), nvl(sum(t.answer10),0),  ");
		sql_t.append("        nvl(sum(t.answer11),0), nvl(sum(t.answer12),0),nvl(sum(t.answer13),0),nvl(sum(t.answer14),0), nvl(sum(t.answer15),0),  ");
		sql_t.append("        nvl(sum(t.answer16),0), nvl(sum(t.answer17),0),nvl(sum(t.answer18),0),nvl(sum(t.answer19),0), nvl(sum(t.answer20),0),  ");
		sql_t.append("        nvl(sum(t.answer21),0), nvl(sum(t.answer22),0),nvl(sum(t.answer23),0),nvl(sum(t.answer24),0), nvl(sum(t.answer25),0),  ");
		sql_t.append("        nvl(sum(t.answer26),0), nvl(sum(t.answer27),0),nvl(sum(t.answer28),0),nvl(sum(t.answer29),0), nvl(sum(t.answer30),0),  ");
		sql_t.append("        nvl(sum(t.answer31),0), nvl(sum(t.answer32),0), nvl(sum(t.answer33),0), nvl(sum(t.answer34),0), nvl(sum(t.answer35),0), ");
		sql_t.append("        nvl(sum(t.answer36),0), nvl(sum(t.answer37),0), nvl(sum(t.answer38),0), nvl(sum(t.answer39),0), nvl(sum(t.answer40),0) ");
		sql_t.append("   from pr_vote_answer t                                                                   ");
		sql_t.append("  where t.pr_vote_question = '" + quest.getId() + "'                              ");
		if (classIdentifier.length() == 3) {
			sql_t.append("    and t.class_identifier like '" + classIdentifier.substring(0, 2) + "%" + classIdentifier.substring(2) + "_'                ");
		} else if (classIdentifier.length() == 5) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + classIdentifier.substring(3) + "%" + classIdentifier.substring(2, 3) + "_'   ");
		} else if (classIdentifier.length() == 8) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + classIdentifier.substring(3, 8) + "%" + classIdentifier.substring(2, 3) + "_'   ");
		} else if (classIdentifier.length() == 10) {
			sql_t.append("    and t.CLASS_IDENTIFIER like '" + classIdentifier.substring(0, 2) + classIdentifier.substring(3, 10) + "%" + classIdentifier.substring(2, 3) + "_'   ");
		}
		List objList = this.getGeneralService().getBySQL(sql_t.toString());
		return (Object[]) objList.get(0);
	}

	/**
	 * 添加调查问卷
	 * 
	 * @return
	 */
	public String addVotePaper() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String applynos[] = this.getApplyno().split("or");
		// for (int i=0;i<applynos.length;i++){
		PeVotePaper votePaper = new PeVotePaper();
		try {
			votePaper.setStartDate(sdf.parse(this.getStartDate()));
			votePaper.setEndDate(sdf.parse(this.getEndDate()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// 设置类型
		PeProApplyno apply = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApplyno.class);
		dc.add(Restrictions.eq("name", this.getApplyno().trim()));
		try {
			apply = (PeProApplyno) (this.getGeneralService().getList(dc).get(0));
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		// 每个批次只能有一个问卷
		DetachedCriteria dcPaper = DetachedCriteria.forClass(PeVotePaper.class);
		dcPaper.createAlias("peProApplyno", "peProApplyno");
		dcPaper.add(Restrictions.eq("peProApplyno", apply));
		List paperList = null;
		try {
			paperList = this.getGeneralService().getList(dcPaper);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (paperList != null && !paperList.isEmpty()) {
			this.setMsg("该培训项目下已经有调查问卷，不能重复添加！");
			this.setTogo("/entity/information/peVotePaper.action?search1_peProApplyno.id=" + apply.getId());
			return "msg";
		}

		votePaper.setTitle(this.getPeVotePaper().getTitle());
		votePaper.setPictitle(this.getPeVotePaper().getPictitle());
		votePaper.setNote(this.getPeVotePaper().getNote());
		votePaper.setPeProApplyno(apply);
		votePaper.setEnumConstByFlagCanSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagCanSuggest", this.getCanSuggest()));
		votePaper.setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", this.getActive()));
		votePaper.setEnumConstByFlagLimitDiffip(this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffip", this.getLimitIp()));
		votePaper.setEnumConstByFlagLimitDiffsession(this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffsession", this.getLimitSession()));
		votePaper.setEnumConstByFlagViewSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagViewSuggest", this.getViewSuggest()));
		votePaper.setFoundDate(new Date());
		try {
			this.getGeneralService().save(votePaper);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("添加失败！");
		}
		// }
		if (this.getMsg() == null)
			this.setMsg("添加成功！");
		this.setTogo("/entity/information/peVotePaper.action?search1_peProApplyno.id=" + apply.getId());
		return "msg";
	}

	/**
	 * 调查问卷修改
	 * 
	 * @return
	 */
	public String editVotePaper() {
		// 设置类型
		PeProApplyno apply = null;
		try {
			apply = (PeProApplyno) this.getGeneralService().getById(PeProApplyno.class, this.getApplyno());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.getPeVotePaper().setPeProApplyno(apply);
		// 根据类型设置关键字
		this.getPeVotePaper().setEnumConstByFlagCanSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagCanSuggest", this.getCanSuggest()));
		this.getPeVotePaper().setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", this.getActive()));
		this.getPeVotePaper().setEnumConstByFlagLimitDiffip(this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffip", this.getLimitIp()));
		this.getPeVotePaper().setEnumConstByFlagLimitDiffsession(this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffsession", this.getLimitSession()));
		this.getPeVotePaper().setEnumConstByFlagViewSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagViewSuggest", this.getViewSuggest()));
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeVotePaper.class);
			PeVotePaper vote = (PeVotePaper) this.getGeneralService().getById(PeVotePaper.class, this.getPeVotePaper().getId());
			this.superSetBean((PeVotePaper) setSubIds(vote, this.getPeVotePaper()));
			this.getGeneralService().save(vote);
			setTogo("/entity/information/peVotePaper.action?search1_peProApplyno.id=" + apply.getId() + "&peUnit=&peSubject=");
			this.setMsg("修改成功!");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("修改失败！");
		}
		return "msg";
	}

	/**
	 * @description 根据编码code查询对应的id(针对培训单位和学科及培训项目等)
	 * @param code
	 *            条件编码code
	 * @param tableName
	 *            操作的表名
	 * @return 查询所得的id号
	 */
	protected String getIdByCode(String code, String tableName) {
		String getCodeSQL = "select id from " + tableName + " where code=:theCode";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theCode", code);
		List<String> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(getCodeSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String queryCode = "";
		if (resultList != null && resultList.size() != 0) {
			queryCode = resultList.get(0);
		}
		return queryCode;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeVotePaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peVotePaper";
	}

	public PeVotePaper getPeVotePaper() {
		return peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCanSuggest() {
		return canSuggest;
	}

	public void setCanSuggest(String canSuggest) {
		this.canSuggest = canSuggest;
	}

	public String getViewSuggest() {
		return viewSuggest;
	}

	public void setViewSuggest(String viewSuggest) {
		this.viewSuggest = viewSuggest;
	}

	public String getLimitIp() {
		return limitIp;
	}

	public void setLimitIp(String limitIp) {
		this.limitIp = limitIp;
	}

	public String getLimitSession() {
		return limitSession;
	}

	public void setLimitSession(String limitSession) {
		this.limitSession = limitSession;
	}

	public int getPastDue() {
		return pastDue;
	}

	public void setPastDue(int pastDue) {
		this.pastDue = pastDue;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public int getCanVote() {
		return canVote;
	}

	public void setCanVote(int canVote) {
		this.canVote = canVote;
	}

	public long getVoteNumber() {
		return voteNumber;
	}

	public void setVoteNumber(long voteNumber) {
		this.voteNumber = voteNumber;
	}

	public List<PeVotePaper> getPeVotePaperList() {
		return peVotePaperList;
	}

	public void setPeVotePaperList(List<PeVotePaper> peVotePaperList) {
		this.peVotePaperList = peVotePaperList;
	}

	public List<PeProApplyno> getProApplyno() {
		return proApplyno;
	}

	public void setProApplyno(List<PeProApplyno> proApplyno) {
		this.proApplyno = proApplyno;
	}

	public List<PeProApplyno> getApplynoList() {
		return applynoList;
	}

	public void setApplynoList(List<PeProApplyno> applynoList) {
		this.applynoList = applynoList;
	}

	public List<PrVoteAnswer> getPrVoteAnswerList() {
		return prVoteAnswerList;
	}

	public void setPrVoteAnswerList(List<PrVoteAnswer> prVoteAnswerList) {
		this.prVoteAnswerList = prVoteAnswerList;
	}

	public List getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List questionList) {
		this.questionList = questionList;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

}