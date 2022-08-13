package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.HomeworkHistory;
import com.whaty.platform.entity.bean.HomeworkInfo;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class InteractionHomeworkAction extends MyBaseAction {

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {
		this.entityClass = HomeworkInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/interactionHomework";
	}
	
	private List homeworkList;
	private String start; 				// 列表开始的位置
	private String limit; 				// 每页要显示的信息个数
	private int curPage;				//当前页
	private int totalPage;				//总页数
	private int pageNo;					//从输入框中获取的页码数
	private int isLimit;				//判断请求是否由设定页面信息个数所发出的，1是，0否
	private String teachclassId;
	private String startDate;
	private String startHour;
	private String startMinute;
	private String startSecond;
	private String endDate;
	private String endHour;
	private String endMinute;
	private String endSecond;
	private String note;
	private String groupId;
	private String title;
	private String totalCount;
	private String link;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}

	public String getEndSecond() {
		return endSecond;
	}

	public void setEndSecond(String endSecond) {
		this.endSecond = endSecond;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}

	public String getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(String startSecond) {
		this.startSecond = startSecond;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(int isLimit) {
		this.isLimit = isLimit;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTeachclassId() {
		return teachclassId;
	}

	public void setTeachclassId(String teachclassId) {
		this.teachclassId = teachclassId;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List getHomeworkList() {
		
		return homeworkList;
	}

	public void setHomeworkList(List homeworkList) {
		this.homeworkList = homeworkList;
	}

	private void setDevide(DetachedCriteria dc) {
		this.setDividepage();
		this.setLink("&teachclassId="+this.getTeachclassId());
		if(this.getIsLimit() == 1){
			this.setStart("0");
			this.setCurPage(1);
		} else {
			this.setLimit("10");
		}
		
		if(this.getPageNo() != 0){
			this.setCurPage(this.getPageNo());
		}
		
		/**
		 * 首先设定默认开始页为第一页
		 */
		if(this.getCurPage() == 0){
			this.setCurPage(1);
		}
		/**
		 * 设定start
		 */
		if(this.getCurPage() > 1){
			Integer temp = new Integer(0);
			temp = (this.getCurPage()-1)*Integer.parseInt(this.getLimit());
			this.setStart(temp.toString());
		}else {
			this.setStart("0");
		}
		
		
		try {
			Page page = this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			/**
			 * 设置总页数
			 */
			this.setTotalCount(page.getTotalCount()+"");
			if(page.getTotalCount() <= 1.0){
				this.setTotalPage(1);
			}else if(page.getTotalCount() > 1.0 && page.getTotalCount() < 2.0){
				this.setTotalPage(2);
			}else{
				if(page.getTotalCount()%Integer.parseInt(this.getLimit()) > 0){
					this.setTotalPage(page.getTotalCount()/Integer.parseInt(this.getLimit())+1);
				}else
					this.setTotalPage(page.getTotalCount()/Integer.parseInt(this.getLimit()));
			}
			this.setHomeworkList(page.getItems());
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	public String toHomeworkList() {
		
		this.setDividepage();
		
		DetachedCriteria homeworkDC = DetachedCriteria.forClass(HomeworkInfo.class);
		homeworkDC.add(Restrictions.eq("groupId", this.getTeachclassId()));
		
		this.setDevide(homeworkDC);
		
		return "toHomeworkList";
	}
	
	public String toHomeworkList_student() {
		DetachedCriteria homeworkDC = DetachedCriteria.forClass(HomeworkInfo.class);
		homeworkDC.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		homeworkDC.add(Restrictions.eq("groupId", this.getTeachclassId()));
		homeworkDC.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		
		this.setDevide(homeworkDC);
		
		return "toHomeworkList_student";
	}
	
	public String toAdd() {
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer");
	    ctx.getSession().put("path", path);
	    
		return "toAdd";
	}
	
	public String addExe() {
		
		ActionContext ctx = ActionContext.getContext();
		String interactionTogo = (String) ctx.getSession().get("path");
		
		HomeworkInfo hw = new HomeworkInfo();
		hw.setCreatdate(new Date());
		hw.setTitle(this.getTitle());
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date startDate = format.parse(this.getStartDate()+" "+this.getStartHour()+":"+this.getStartMinute()+":"+this.getStartSecond());
			Date startDate = format.parse(this.getStartDate());
			hw.setStartdate(startDate);
//			Date endDate = format.parse(this.getEndDate()+" "+this.getEndHour()+":"+this.getEndMinute()+":"+this.getEndSecond());
			Date endDate = format.parse(this.getEndDate());
			hw.setEnddate(endDate);

		} catch (ParseException e) {
			e.printStackTrace();
			this.setInteractionMsg("时间转换出错了。请与管理员联系。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		hw.setGroupId(this.getGroupId());
		hw.setNote(this.getNote());
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria teacherDC = DetachedCriteria.forClass(PeTeacher.class);
		teacherDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(teacherDC);
			hw.setPeTeacher((PeTeacher) teacherList.get(0));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("取教师信息出错了。请与管理员联系。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		EnumConst enumConstByFlagIsvalid = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
		hw.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
		try {
			this.getGeneralService().save(hw);
		} catch (Exception e) {
			e.printStackTrace();
			this.setInteractionMsg("保存作业失败。请与管理员联系。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		this.setInteractionMsg("保存成功。");
		this.setInteractionTogo(interactionTogo);
		return "interactionMsg";
	}
	
	private String homeworkInfoId;
	private List homeworkHistoryList;
	private HomeworkInfo homeworkInfo;
	
	public HomeworkInfo getHomeworkInfo() {
		return homeworkInfo;
	}

	public void setHomeworkInfo(HomeworkInfo homeworkInfo) {
		this.homeworkInfo = homeworkInfo;
	}

	public List getHomeworkHistoryList() {
		return homeworkHistoryList;
	}

	public void setHomeworkHistoryList(List homeworkHistoryList) {
		this.homeworkHistoryList = homeworkHistoryList;
	}

	public String getHomeworkInfoId() {
		return homeworkInfoId;
	}

	public void setHomeworkInfoId(String homeworkInfoId) {
		this.homeworkInfoId = homeworkInfoId;
	}

	public String toHomeworkHistoryList() {
		
		this.setHomeworkInfo((HomeworkInfo) this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId()));
		
		DetachedCriteria dc1 = DetachedCriteria.forClass(HomeworkHistory.class);
		dc1.add(Restrictions.eq("homeworkInfo", this.getHomeworkInfo()));
		this.setLink("&homeworkInfoId="+this.getHomeworkInfoId());
		try {
			this.setHomeworkHistoryList(this.getGeneralService().getList(dc1));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("fail,connect with the manager.");
			return "msg";
		}
		
		this.setDividepage();
		if(this.getIsLimit() == 1){
			this.setStart("0");
			this.setCurPage(1);
		} else {
			this.setLimit("10");
		}
		
		if(this.getPageNo() != 0){
			this.setCurPage(this.getPageNo());
		}
		
		/**
		 * 首先设定默认开始页为第一页
		 */
		if(this.getCurPage() == 0){
			this.setCurPage(1);
		}
		/**
		 * 设定start
		 */
		if(this.getCurPage() > 1){
			Integer temp = new Integer(0);
			temp = (this.getCurPage()-1)*Integer.parseInt(this.getLimit());
			this.setStart(temp.toString());
		}else {
			this.setStart("0");
		}
		
		
		try {
			Page page = this.getGeneralService().getByPage(dc1, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			/**
			 * 设置总页数
			 */
			this.setTotalCount(page.getTotalCount()+"");
			if(page.getTotalCount() <= 1.0){
				this.setTotalPage(1);
			}else if(page.getTotalCount() > 1.0 && page.getTotalCount() < 2.0){
				this.setTotalPage(2);
			}else{
				if(page.getTotalCount()%Integer.parseInt(this.getLimit()) > 0){
					this.setTotalPage(page.getTotalCount()/Integer.parseInt(this.getLimit())+1);
				}else
					this.setTotalPage(page.getTotalCount()/Integer.parseInt(this.getLimit()));
			}
			this.setHomeworkHistoryList(page.getItems());
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toHomeworkHistoryList";
	}
	
	public boolean isExpired(String id) {
		HomeworkInfo homeworkInfo = (HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, id);
		Date today = new Date();
		if (today.getTime()<homeworkInfo.getStartdate().getTime() || today.getTime()>homeworkInfo.getEnddate().getTime()) {
			return true;
		}
		return false;
	}
	
	private PeStudent initStudent() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria studentDC = DetachedCriteria.forClass(PeStudent.class);
		studentDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		PeStudent peStudent = new PeStudent();
		try {
			peStudent = (PeStudent) this.getGeneralService().getList(studentDC).get(0);
			return peStudent;
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isFinished(String id) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(HomeworkHistory.class);
		dc.createCriteria("homeworkInfo", "homeworkInfo");
		dc.add(Restrictions.eq("peStudent", this.initStudent()));
		dc.add(Restrictions.eq("homeworkInfo.id", id));
		try {
			List homeworkHistoryList = this.getGeneralService().getList(dc);
			if (homeworkHistoryList.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isChecked(String id) {
		DetachedCriteria dc = DetachedCriteria.forClass(HomeworkHistory.class);
		dc.createCriteria("homeworkInfo", "homeworkInfo");
		dc.add(Restrictions.eq("peStudent", this.initStudent()));
		dc.add(Restrictions.eq("homeworkInfo.id", id));
		try {
			List homeworkHistoryList = this.getGeneralService().getList(dc);
			if(((HomeworkHistory)homeworkHistoryList.get(0)).getEnumConstByFlagIscheck().getCode().equals("1")) {
				return true;
			} else {
				return false;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String uri;
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	private void setDividepage() {
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    this.setUri(request.getRequestURI());
//	    this.setLink("&teachclassId="+this.getTeachclassId());
	}
	
	public String viewDetail() {
		
		this.setHomeworkInfo((HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId()));
		return "viewDetail";
	}
	
	public String toFinishHomework() {
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer");
	    ctx.getSession().put("path", path);
		this.setHomeworkInfo((HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId()));
		return "toFinishHomework";
	}
	
	public String finishHomework() {
		ActionContext ctx = ActionContext.getContext();
		String interactionTogo = (String) ctx.getSession().get("path");
		HomeworkInfo homeworkInfo = (HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId());
		Date today = new Date();
		EnumConst enumConstByFlagIscheck = this.getMyListService().getEnumConstByNamespaceCode("FlagIscheck", "0");
		HomeworkHistory homeworkHistory = new HomeworkHistory();
		homeworkHistory.setEnumConstByFlagIscheck(enumConstByFlagIscheck);
		homeworkHistory.setHomeworkInfo(homeworkInfo);
		homeworkHistory.setNote(this.getNote());
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria studentDC = DetachedCriteria.forClass(PeStudent.class);
		studentDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		PeStudent peStudent = new PeStudent();
		try {
			peStudent = (PeStudent) this.getGeneralService().getList(studentDC).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("保存失败。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		homeworkHistory.setPeStudent(peStudent);
		homeworkHistory.setTestdate(today);
		try {
			this.getGeneralService().save(homeworkHistory);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("保存失败");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		this.setInteractionMsg("保存成功。");
		this.setInteractionTogo(interactionTogo);
		return "interactionMsg";
	}
	
	private HomeworkHistory homeworkHistory;
	
	public HomeworkHistory getHomeworkHistory() {
		return homeworkHistory;
	}

	public void setHomeworkHistory(HomeworkHistory homeworkHistory) {
		this.homeworkHistory = homeworkHistory;
	}

	public String toModifyHomework() {
		
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer");
	    ctx.getSession().put("path", path);
		
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria studentDC = DetachedCriteria.forClass(PeStudent.class);
		studentDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		PeStudent peStudent = new PeStudent();
		try {
			peStudent = (PeStudent) this.getGeneralService().getList(studentDC).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("系统出错，请与管理员联系。");
			this.setInteractionTogo(path);
			return "interactionMsg";
		}
		
		this.setHomeworkInfo((HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId()));
		DetachedCriteria dc = DetachedCriteria.forClass(HomeworkHistory.class);
		dc.add(Restrictions.eq("homeworkInfo", this.getHomeworkInfo()));
		dc.add(Restrictions.eq("peStudent", peStudent));
		try {
			this.setHomeworkHistory((HomeworkHistory)this.getGeneralService().getList(dc).get(0));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("系统出错了，请与管理员联系。");
			this.setInteractionTogo(path);
			return "interactionMsg";
		}
		return "toModifyHomework";
	}
	
	private String homeworkHistoryId;
	
	public String getHomeworkHistoryId() {
		return homeworkHistoryId;
	}

	public void setHomeworkHistoryId(String homeworkHistoryId) {
		this.homeworkHistoryId = homeworkHistoryId;
	}
	
	public String modifyHomework() {
		
		ActionContext ctx = ActionContext.getContext();
		String interactionTogo = (String) ctx.getSession().get("path");
		
		Date today = new Date();
		HomeworkHistory homeworkHistory = (HomeworkHistory)this.getMyListService().getById(HomeworkHistory.class, this.getHomeworkHistoryId());
		homeworkHistory.setTestdate(today);
		homeworkHistory.setNote(this.getNote());
		try {
			this.getGeneralService().save(homeworkHistory);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("修改失败。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		this.setInteractionMsg("修改成功。");
		this.setInteractionTogo(interactionTogo);
		return "interactionMsg";
	}
	
	public String toModifyHomeworkInfo() {
		this.setHomeworkInfo((HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId()));
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer");
	    ctx.getSession().put("path", path);
		return "toModifyHomeworkInfo";
	}
	
	public String toPigai() {
//		this.setHomeworkInfo((HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId()));
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path1 = request.getHeader("Referer");
	    ctx.getSession().put("path1", path1);
		this.setHomeworkHistory((HomeworkHistory)this.getMyListService().getById(HomeworkHistory.class, this.getHomeworkHistoryId()));
		return "toPigai";
	}
	
	private String score;
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String pigaiExe() {
		ActionContext ctx = ActionContext.getContext();
		String interactionTogo = (String) ctx.getSession().get("path1");
		HomeworkHistory homeworkHistory = (HomeworkHistory)this.getMyListService().getById(HomeworkHistory.class, this.getHomeworkHistoryId());
		EnumConst enumConstByFlagIscheck = this.getMyListService().getEnumConstByNamespaceCode("FlagIscheck", "1");
		homeworkHistory.setEnumConstByFlagIscheck(enumConstByFlagIscheck);
		homeworkHistory.setScore(this.getScore());
		try {
			this.getGeneralService().save(homeworkHistory);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("保存失败。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		this.setInteractionMsg("保存成功。");
		this.setInteractionTogo(interactionTogo);
		return "interactionMsg";
	}
	
	public String viewFinal() {
		
		HomeworkInfo homeworkInfo = (HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId());
		DetachedCriteria dc = DetachedCriteria.forClass(HomeworkHistory.class);
		dc.add(Restrictions.eq("peStudent", this.initStudent()));
		dc.add(Restrictions.eq("homeworkInfo", homeworkInfo));
		try {
			this.setHomeworkHistory((HomeworkHistory)(this.getGeneralService().getList(dc).get(0)));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("系统出错，请与管理员联系。");
			return "msg";
		}
		
		return "viewFinal";
	}
	
	public String delHomeworkHistory() {
		
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer"); 
	    this.setInteractionTogo(path);
		try {
			this.getGeneralService().delete((HomeworkHistory)this.getMyListService().getById(HomeworkHistory.class, this.getHomeworkHistoryId()));
		} catch (EntityException e) {
			e.printStackTrace();
		    this.setInteractionTogo(path);
			this.setInteractionMsg("删除失败。");
			return "interactionMsg";
		}
	    this.setInteractionTogo(path);
		this.setInteractionMsg("删除成功。");
		return "interactionMsg";
	}
	
	public String modifyHomeworkInfoExe() {
		ActionContext ctx = ActionContext.getContext();
		String interactionTogo = (String) ctx.getSession().get("path");
		HomeworkInfo homeworkInfo = (HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId());
		homeworkInfo.setTitle(this.getTitle());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate;
		try {
			startDate = format.parse(this.getStartDate());
			Date endDate = format.parse(this.getEndDate());
			homeworkInfo.setStartdate(startDate);
			homeworkInfo.setEnddate(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
			this.setInteractionMsg("系统出错。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		homeworkInfo.setNote(this.getNote());
		try {
			this.getGeneralService().save(homeworkInfo);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("系统出错。");
			this.setInteractionTogo(interactionTogo);
			return "interactionMsg";
		}
		this.setInteractionMsg("修改成功");
		this.setInteractionTogo(interactionTogo);
		return "interactionMsg";
	}
	
	public String delHomeworkInfo() {
		HomeworkInfo homeworkInfo = (HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId());
		try {
			this.getGeneralService().delete(homeworkInfo);
		} catch (Exception e) {
			e.printStackTrace();
			this.setInteractionMsg("已有人完成该作业，不能删除。");
			ActionContext ctx = ActionContext.getContext();        
		    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    String path=request.getHeader("Referer"); 
		    this.setInteractionTogo(path);
			return "interactionMsg";
		}
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer"); 
	    this.setInteractionTogo(path);
		this.setInteractionMsg("删除成功。");
		return "interactionMsg";
	}
	
	public String changeValid() {
		HomeworkInfo homeworkInfo = (HomeworkInfo)this.getMyListService().getById(HomeworkInfo.class, this.getHomeworkInfoId());
		EnumConst enumConstByFlagIsvalid;
		if (homeworkInfo.getEnumConstByFlagIsvalid().getCode().equals("1")) {
			enumConstByFlagIsvalid = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
		} else {
			enumConstByFlagIsvalid = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
		}
		homeworkInfo.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
		try {
			this.getGeneralService().save(homeworkInfo);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setInteractionMsg("状态改变失败。");
			ActionContext ctx = ActionContext.getContext();        
		    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    String path=request.getHeader("Referer"); 
		    this.setInteractionTogo(path);
			return "interactionMsg";
		}
		ActionContext ctx = ActionContext.getContext();        
	    HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String path=request.getHeader("Referer"); 
	    this.setInteractionTogo(path);
		this.setInteractionMsg("状态改变成功");
		
		return "interactionMsg";
	}
}
