package com.whaty.platform.entity.web.action.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.ScormCourseInfo;
import com.whaty.platform.entity.bean.ScormStuCourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeApplyAction extends MyBaseAction {

	private List applyList;// 所有申请列表
	private SystemApply systemApply;
	private boolean forbidApply;// 是否到达最高级别培训类型，是的话就不能再申请了
	private boolean canUpTrain;
	private boolean canGetCertificate;
	private boolean canEndCourse;
	private String msg;// 提示信息
	private String applyType;// 申请类型
	private String applyId;//申请id
	private String applyTypeStr;//申请类型中文显示
	private int totalPage;
	private int curPage = 1;
	private int totalSize;
	private static int SIZE_PER_PAGE = 10;//每页显示条数
	private static String UPTRAIN_APPLY = "20";
	private static String CERTIFICATE_APPLY = "22";
	private static String ENDCOURSE_APPLY = "21";
	private boolean noCompletion;
	private boolean noCertificate;
	private boolean noUpTrain;

	public String getList() {

		this.setCanUpTrain(this.checkCanUpTrain());
		this.setCanGetCertificate(this.checkCanGetCertificate());
		this.setCanEndCourse(this.checkCanEndCourse());
		this.setNoUpTrain(!this.checkCanUpTrain());
		this.setNoCertificate(!this.checkCanGetCertificate());
		this.setNoCompletion(!this.checkCanEndCourse());
		
		UserSession us = (UserSession) ActionContext.getContext().getSession()
				.get(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGeneralService().getGeneralDao().setEntityClass(
				SystemApply.class);
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("peTrainee", "peTrainee");
		dc.createCriteria("enumConstByFlagApplyStatus",
				"enumConstByFlagApplyStatus");
		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.in("enumConstByApplyType.code", Arrays.asList(UPTRAIN_APPLY,CERTIFICATE_APPLY,ENDCOURSE_APPLY,"23","24","25","26","27")));// 类型为
																	// 培训级别申请
		dc.add(Restrictions.eq("peTrainee.loginId", us.getSsoUser()
				.getLoginId()));// 学员只能看到自己的申请记录

		// if(!this.checkTraineeForLevelUp(us.getSsoUser().getLoginId())){//查看当前用户是否已结业
		// this.setForbidApply(true);
		// return "showApplyList";
		// }
		try {
			List list = this.getGeneralService().getList(dc);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dc, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setApplyList(p.getItems());
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "showApplyList";
	}
	
	//获取申请的详细信息
	public String getDetail() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("peTrainee", "peTrainee");
		dc.createCriteria("enumConstByFlagApplyStatus",
				"enumConstByFlagApplyStatus");
		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("id", applyId));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				
				DetachedCriteria dc1=DetachedCriteria.forClass(SystemApply.class);
				dc1.createCriteria("enumConstByFlagApplyStatus","enumConstByFlagApplyStatus",DetachedCriteria.LEFT_JOIN);
			    dc1.createCriteria("enumConstByApplyType", "enumConstByApplyType");
			    dc1.add(Restrictions.eq("id",applyId));
//				dc1.setProjection(Projections.projectionList()
//						.add(Projections.property("applyInfo"))
//						.add(Projections.property("SystemApplyS.applyinfo"))
//								);
				List details=new LinkedList();
				try {
					details=this.getGeneralService().getList(dc1);
					SystemApply sa = (SystemApply)details.get(0);
					
					this.setSystemApply(sa);
					
				} catch (EntityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
//				SystemApply systemApply = (SystemApply)list.get(0);
//				this.setSystemApply(systemApply);
//			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "apply_detail";
		
		
	}

	private void setApplyNote(String sr) {
		// TODO Auto-generated method stub
		
	}

	private void setApplyInfo(String st) {
		// TODO Auto-generated method stub
		
	}

	private void setDetail(String st) {
		// TODO Auto-generated method stub
		
	}

	// 检查该学员是否结束了培训计划
	private boolean checkTraineeForLevelUp(String loginId) {
		this.getGeneralService().getGeneralDao()
				.setEntityClass(PeTrainee.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.eq("loginId", loginId));
		PeTrainee trainee = null;
		List traineeList = null;
		try {
			traineeList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (traineeList != null && traineeList.size() > 0) {
			trainee = (PeTrainee) traineeList.get(0);
		} else {
			;
		}
		if (true) {// 判断条件
			return true;
		}
		return false;
	}

	public String addApply() {

//		UserSession us = (UserSession) ActionContext.getContext().getSession()
//				.get(SsoConstant.SSO_USER_SESSION_KEY);
//		List<String> resultList = new LinkedList<String>();
//		try {
//			resultList = this.getGeneralService().canApplyHigherTrainingType(
//					us.getSsoUser().getLoginId());
//		} catch (EntityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		if (resultList != null && resultList.size() > 0) {
//			StringBuffer strb = new StringBuffer();
//			for (int i = 0; i < resultList.size(); i++) {
//				strb.append(resultList.get(i));
//				strb.append("\\n");
//			}
//			strb.deleteCharAt(strb.length() - 1);
//			strb.deleteCharAt(strb.length() - 1);
//			strb.deleteCharAt(strb.length() - 1);
//			this.setForbidApply(true);
//			this.setMsg(strb.toString());
//			return "newApply";
//		}
//
//		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
//		dc.add(Restrictions.eq("loginId", us.getSsoUser().getLoginId()));
//		List traineeList = null;
//		PeTrainee trainee = null;
//		try {
//			traineeList = this.getGeneralService().getList(dc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (traineeList != null && traineeList.size() > 0) {
//			trainee = (PeTrainee) traineeList.get(0);
//			if (trainee.getEnumConstByTrainingType().getCode().equals("003")) {
//				this.setForbidApply(true);
//				this.setMsg("您当前不能申请!");
//			}
//		}
		
		if(this.checkRePost()){
			this.setMsg("请不要重复申请");			
			return "repost";
		}else{
			if("shengji".equals(this.applyType)) {
				this.setApplyTypeStr("申请更高级别的培训");
				if(!this.checkCanUpTrain()) {
					this.setForbidApply(true);
					if(this.msg == null || "".equals(this.msg)) {
						this.setMsg("您当前培训还没结业，不能申请升级培训!");
					}
				}
			} else if("zhengshu".equals(this.applyType)) {
				this.setApplyTypeStr("申请证书");
				if(!this.checkCanGetCertificate()) {
					this.setForbidApply(true);
					this.setMsg("您当前培训还没结业，不能申请证书!");
				}
			} else if("jieye".equals(this.applyType)) {
				this.setApplyTypeStr("申请结业");
				if(!this.checkCanEndCourse()) {
					this.setForbidApply(true);
					this.setMsg("您当前不能申请结业!");
				}
			}
		}
			
		return "newApply";
	}

	public String saveNewApply() {
		// -------查找当前用户----------
		UserSession us = (UserSession) ActionContext.getContext().getSession()
				.get(SsoConstant.SSO_USER_SESSION_KEY);

		List<String> resultList = new LinkedList<String>();
		try {
			resultList = this.getGeneralService().canApplyHigherTrainingType(
					us.getSsoUser().getLoginId());// 查看当前学员是否可以提交申请更高培训级别的申请
		} catch (EntityException e2) {
			e2.printStackTrace();
		}
		if (resultList != null && resultList.size() > 0) {// 但钱学员不能提交更高级别的申请
			StringBuffer strb = new StringBuffer();
			for (int i = 0; i < resultList.size(); i++) {
				strb.append(resultList.get(i));
				strb.append("\\n");
			}
			strb.deleteCharAt(strb.length() - 1);
			strb.deleteCharAt(strb.length() - 1);
			strb.deleteCharAt(strb.length() - 1);
			this.setMsg(strb.toString());
			return "addNewSucce";
		}

		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.eq("loginId", us.getSsoUser().getLoginId()));
		List traineeList = null;
		PeTrainee trainee = null;
		try {
			traineeList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (traineeList != null && traineeList.size() > 0) {
			trainee = (PeTrainee) traineeList.get(0);
			this.getBean().setPeTrainee(trainee);
		}// -------查找当前用户----------
		// --------------查找申请培训级别的状态-----------------
		
		
	
		this.getBean().setEnumConstByFlagApplyStatus(this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
		// --------------查找申请培训级别的状态-----------------
		// --------------查找申请培训级别的类型-----------------
		this.getBean().setEnumConstByApplyType(this.getCetiType());
		
		
		this.getBean().setApplyDate(new Date());
		this.getGeneralService().getGeneralDao().setEntityClass(
				SystemApply.class);
		try {
			this.getGeneralService().save(this.getBean());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("添加成功");
		return "addNewSucce";
	}

	// 删除用户自己删除未待审核的申请
	public String delApply() {
		String[] ids = this.getIds().split(",");
		this.getGeneralService().getGeneralDao().setEntityClass(
				SystemApply.class);
		int delSucc = 0;
		for (int i = 0; i < ids.length; i++) {
			SystemApply apply = null;
			try {
				apply = (SystemApply) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (apply.getEnumConstByFlagApplyStatus().getCode().equals("0")) {// 只有为审核的请求才能删除
				try {
					this.getGeneralService().delete(apply);
					delSucc++;
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.setMsg(delSucc + "个删除成功");
		return this.getList();
	}
	//验证申请是否多次提交或申请出错
	public boolean checkRePost(){
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		if(this.getCetiType()==null)
			return true;
		dc.add(Restrictions.eq("peTrainee", this.getCurTrainee()));
		dc.add(Restrictions.eq("enumConstByApplyType", this.getCetiType()));
		dc.add(Restrictions.or(Restrictions.eq("enumConstByFlagApplyStatus", this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "1")), Restrictions.eq("enumConstByFlagApplyStatus", this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"))));
		List sysList = null;
		PeTrainee trainee = null;
		try {
			sysList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (sysList != null && sysList.size() > 0) {
			return true;
		}// -----
		return false;
	}
	//获得当前用户的状态对应的申请类型
	public EnumConst getCetiType(){
		EnumConst esta = null;
		String status = this.getCurTrainee().getEnumConstByStatus().getCode();
		if(this.applyType.equals("zhengshu")){
			if(status!=null&&!"".equals(status)){
				if(status.equals("4")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "22");
				}
				if(status.equals("5")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "23");
				}
				if(status.equals("6")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "24");
				}
			}
		}
		if(this.applyType.equals("shengji")){			
			if(status!=null&&!"".equals(status)){
				if(status.equals("4")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "20");
				}
				if(status.equals("5")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "27");
				}			
			}
		}
		if(this.applyType.equals("jieye")){
			if(status!=null&&!"".equals(status)){
				if(status.equals("1")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "21");
				}
				if(status.equals("2")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "25");
				}
				if(status.equals("3")){
					esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "26");
				}
			}
		}
		return esta;
	}
	//检查该结业用户是否获得对应的证书
	public boolean checkGetCeti(String status){		
		EnumConst esta = null;
		if(status!=null&&!"".equals(status)){
			if(status.equals("4")){
				esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "22");
			}
			if(status.equals("5")){
				esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "23");
			}
			if(status.equals("6")){
				esta = this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "24");
			}
		}
		if(esta!=null){
			this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
			DetachedCriteria dc=DetachedCriteria.forClass(SystemApply.class);
			dc.add(Restrictions.eq("peTrainee", this.getCurTrainee()));
			dc.add(Restrictions.eq("enumConstByApplyType", esta));
			dc.add(Restrictions.eq("enumConstByFlagApplyStatus", this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "1")));
			List enuList=null;
			try {
				enuList=this.getGeneralService().getList(dc);
			} catch (EntityException e) {			
				e.printStackTrace();			
			}
			if(enuList!=null&&enuList.size()>0){
				return true;
			}
		}
		return false;
	}
	//检查是否能升级培训
	public boolean checkCanUpTrain() {
		
		PeTrainee peTrainee = this.getCurTrainee();
		String status = peTrainee.getEnumConstByStatus().getCode();
		if(status != null) {
			if(("4".equals(status) || "5".equals(status))&&checkGetCeti(status)) {
				return true;
			} else if ("6".equals(status)) {
				this.setMsg("您当前已经是最高级别的培训，不能再申请！");
			}
		}
		
		return false;
	}
	
	//检查是否能获取证书
	public boolean checkCanGetCertificate() {
		
		PeTrainee peTrainee = this.getCurTrainee();
		String status = peTrainee.getEnumConstByStatus().getCode();
		if(status != null && ("4".equals(status) || "5".equals(status) || "6".equals(status))) {
			return true;
		}
		
		return false;
	}
	
	//检查是否能结业
	public boolean checkCanEndCourse() {
		double planCredit = 0.0;
		double totalCredit = 0.0;
		PeTrainingPlan pePlan = this.getCurTrainee().getPeTrainingPlan();
		planCredit = pePlan.getCreditRequire();
		this.getGeneralService().getGeneralDao().setEntityClass(PrTraingCourse.class);
		DetachedCriteria dcp = DetachedCriteria.forClass(PrTraingCourse.class);		
		dcp.add(Restrictions.eq("peTrainingPlan", pePlan));
		List l = null;
		try {
			l = this.getGeneralService().getList(dcp);
		} catch (EntityException e) {			
			e.printStackTrace();
		}
		if(l != null && l.size() > 0) {
			List idList = new ArrayList();
			for(Object o : l) {
				PrTraingCourse ptc = (PrTraingCourse)o;
				idList.add(ptc.getPeTchCourse().getId());
			}
			if(idList.size() > 0) {
					for(int i = 0; i < idList.size(); i++) {
						String id = (String)idList.get(i);
						totalCredit += this.getCourseCredit(id);
					}
			}
		}
			
//		System.out.println(totalCredit+"____"+planCredit);
		if(totalCredit>=0&&totalCredit >= planCredit) {
			return true;
		}
		return false;
	}
	
	//根据课程id，获取所得学分
	private double getCourseCredit(String id) {
		
		double credit = 0.0;
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourseware.class);
		DetachedCriteria dc=DetachedCriteria.forClass(PeTchCourseware.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.add(Restrictions.eq("peTchCourse.id", id));
		List idList = new ArrayList();
		try {
			List list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				List codeList = new ArrayList();
				for(int i = 0; i < list.size(); i++) {
					codeList.add(((PeTchCourseware)list.get(i)).getCode());
				}
				
				this.getGeneralService().getGeneralDao().setEntityClass(ScormCourseInfo.class);
				DetachedCriteria dcs=DetachedCriteria.forClass(ScormCourseInfo.class);
				if(codeList != null && codeList.size() > 0) {
					dcs.add(Restrictions.in("courseCode", codeList));
					List scormList = this.getGeneralService().getList(dcs);
					
					if(scormList != null && scormList.size() > 0) {
						List courseWareNameList = new ArrayList();
						for(int j = 0; j < scormList.size(); j++) {
							idList.add(((ScormCourseInfo)scormList.get(j)).getId());
							courseWareNameList.add(((ScormCourseInfo)scormList.get(j)).getTitle());
						}
						
						this.getGeneralService().getGeneralDao().setEntityClass(ScormStuCourse.class);
						DetachedCriteria dcssc=DetachedCriteria.forClass(ScormStuCourse.class);
						dcssc.createCriteria("scormCourseInfo", "scormCourseInfo");
						dcssc.add(Restrictions.eq("studentId", us.getSsoUser().getId()));
						dcssc.add(Restrictions.in("scormCourseInfo.id", idList));
						List l = this.getGeneralService().getList(dcssc);
						if(l != null && l.size() > 0) {
							for(Object o : l) {
								ScormStuCourse ssc = (ScormStuCourse)o;
								if("incomplete".equals(ssc.getLessonStatus())) {
									return 0.0;
								}
							}
							this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
							PeTchCourse c = (PeTchCourse)this.getGeneralService().getById(id);
							if(c != null) {
								credit = c.getCredit();
							}
						}
					}
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return credit;
		
	}

	/**
	 * 获取当前session中的学员
	 * 
	 * @return
	 */
	private PeTrainee getCurTrainee() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByStatus", "enumConstByStatus");
		dc.add(Restrictions.eq("ssoUser", ssoUser));
		List peTraineeList = new ArrayList();
		try {
			peTraineeList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(peTraineeList != null && peTraineeList.size() > 0) {
			return (PeTrainee) peTraineeList.get(0);
		}
		 return null;
		 
	}
	
	public List getApplyList() {
		return applyList;
	}

	public void setApplyList(List applyList) {
		this.applyList = applyList;
	}

	public boolean isForbidApply() {
		return forbidApply;
	}

	public void setForbidApply(boolean forbidApply) {
		this.forbidApply = forbidApply;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public void setBean(SystemApply instance) {
		super.superSetBean(instance);
		
	}
	
	public SystemApply getBean(){
		return  (SystemApply)super.superGetBean();
	}
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	public String getApplyTypeStr() {
		return applyTypeStr;
	}

	public void setApplyTypeStr(String applyTypeStr) {
		this.applyTypeStr = applyTypeStr;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public SystemApply getSystemApply() {
		return systemApply;
	}

	public void setSystemApply(SystemApply systemApply) {
		this.systemApply = systemApply;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public boolean isCanUpTrain() {
		return canUpTrain;
	}

	public void setCanUpTrain(boolean canUpTrain) {
		this.canUpTrain = canUpTrain;
	}

	public boolean isCanGetCertificate() {
		return canGetCertificate;
	}

	public void setCanGetCertificate(boolean canGetCertificate) {
		this.canGetCertificate = canGetCertificate;
	}

	public boolean isCanEndCourse() {
		return canEndCourse;
	}

	public void setCanEndCourse(boolean canEndCourse) {
		this.canEndCourse = canEndCourse;
	}

	public boolean isNoCompletion() {
		return noCompletion;
	}

	public void setNoCompletion(boolean noCompletion) {
		this.noCompletion = noCompletion;
	}

	public boolean isNoCertificate() {
		return noCertificate;
	}

	public void setNoCertificate(boolean noCertificate) {
		this.noCertificate = noCertificate;
	}

	public boolean isNoUpTrain() {
		return noUpTrain;
	}

	public void setNoUpTrain(boolean noUpTrain) {
		this.noUpTrain = noUpTrain;
	}

}
