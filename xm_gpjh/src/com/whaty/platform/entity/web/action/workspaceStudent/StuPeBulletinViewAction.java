package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingClass;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 学生工作室通知公告
 * 
 * @author 李冰
 * 
 */
public class StuPeBulletinViewAction extends MyBaseAction {
	private List<PeBulletin> bulletinList = new ArrayList<PeBulletin>(); // 保存公告
	private PeTrainee peStudent;// 保存学生信息
	private List<PeVotePaper> peVotePaper; // 加载最新的调查问卷
	private PeVotePaper votePaper;
	private List<PeBulletin> peBulletins = new ArrayList<PeBulletin>();
	private PeBulletin bulletin;
//	private List<TrainingCourseStudent> uncomlist;
//	private List<TrainingCourseStudent> comlist;
	
	private String ctype;
	
	private int startIndex; //开始数
	private int pageSize; //页面显示数
	private int pagenow ; //当前页
	private int pagetotal ; //总页数
	private int pagecount ; //总条数
	private int pagenext ; //下一页
	private int pagedown ; //上一页

	private int pageLimit; // 每页要显示的信息个数
	private int curPage; // 当前页
	private int totalPage; // 总页数
	private int pageNo; // 从输入框中获取的页码数

	public String toBulletinView() {  

		return "bulletinView";
	}

	/**
	 * 取得这个学生相关的通知公告，保存到session中。
	 */

	private void getNewBulletins() {
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String studentid = userSession.getSsoUser().getId();
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getNewBulletins(studentid);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			PeBulletin bulletin = new PeBulletin();
			bulletin.setTitle(obj[0].toString());
			bulletinList.add(bulletin);
		}
	}


	private void theBulletinList() {
		this.setPageLimit(10);
		List list = (List) ActionContext.getContext().getSession().get("stuPeBulletin");

		/*
		 * 设置当前页
		 */
		if (this.getPageNo() > 0) {
			this.setCurPage(this.getPageNo());
		}
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}

		/**
		 * 根据页数 取得所要显示的list
		 */
		if (list != null && list.size() > 0) {
			if (list.size() <= (this.getPageLimit() * this.getCurPage())) {
				List page = new ArrayList();
				for (int i = (this.getPageLimit() * (this.getCurPage() - 1)); i < list
						.size(); i++) {
					page.add(list.get(i));
				}
				this.setBulletinList(page);
			} else {
				List page = new ArrayList();
				for (int i = (this.getPageLimit() * (this.getCurPage() - 1)); i < (this
						.getPageLimit() * this.getCurPage()); i++) {
					page.add(list.get(i));
				}
				this.setBulletinList(page);
			}

			/**
			 * 求出总页数
			 */
			double tempTotalPage = (double) list.size()
					/ (double) this.getPageLimit();
			/**
			 * 设置总页数
			 */
			if (tempTotalPage <= 1.0) {
				this.setTotalPage(1);
			} else if (tempTotalPage > 1.0 && tempTotalPage < 2.0) {
				this.setTotalPage(2);
			} else {
				if (list.size() % this.getPageLimit() > 0) {
					this.setTotalPage(list.size() / this.getPageLimit() + 1);
				} else
					this.setTotalPage(list.size() / this.getPageLimit());
			}
		} else {
			this.setTotalPage(1);
		}
	}
	
	//查看详细
	
	public String DetailCourse(){
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String studentid = userSession.getSsoUser().getId();
		
		if(pageSize<=0){
			this.pageSize = 10;
		}
		if(pagenow ==0){
			this.pagenow = 1;
		}
		
		if (this.getPagenow() > 1) {

			int temp = (this.getPagenow() - 1) * this.getPageSize();
			this.setStartIndex(temp);
		} else {
			this.setStartIndex(0);
		}

		Page page = new Page(null,0,0,0);
		
		
//		try {
//			DetachedCriteria jccomdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
//			jccomdc.createCriteria("ssoUser","ssoUser");
//			DetachedCriteria opendc = jccomdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
//			DetachedCriteria tchCodc =opendc.createCriteria("peBzzTchCourse","peBzzTchCourse");
//			tchCodc.addOrder(Order.asc("code"));
//			opendc.createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType");
//			jccomdc.add(Restrictions.eq("ssoUser.id", studentid));
//			
//		//已完成基础课程-----jcwc
//		if(ctype.equals("jcwc")){
//			jccomdc.add(Restrictions.eq("learnStatus", "COMPLETED"));
//			jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "基础课程"));
//		}	
//		
//		//在学基础课程-----jczx
//		if(ctype.equals("jczx")){
//			jccomdc.add(Restrictions.ne("learnStatus", "COMPLETED"));
//			jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "基础课程"));
//		
//		}
//		//在学提升课程-----tszx
//		if(ctype.equals("tszx")){
//			jccomdc.add(Restrictions.ne("learnStatus", "COMPLETED"));
//			jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "提升课程"));
//		
//		}
//		//完成提升课程-----tswc
//		if(ctype.equals("tswc")){
//			jccomdc.add(Restrictions.eq("learnStatus", "COMPLETED"));
//			jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "提升课程"));
//		}
//			page = this.getGeneralService().getByPage(jccomdc, pageSize, startIndex);
//			this.pagecount = page.getTotalCount();
//			List tempList = page.getItems();
//			pagetotal = pagecount%pageSize == 0?(pagecount/pageSize):(pagecount/pageSize)+1;
//			if(pagenow<2){
//				this.pagedown = -1; //当前页面为1时 不显示上一页;
//				if(pagetotal<2){
//					this.pagenext = -1;
//					this.pagetotal = 1;
//				}else{
//					this.pagenext = pagenow+1;
//				}
//			}else{
//				this.pagedown = pagenow -1;
//				if(pagenow==pagetotal){
//					this.pagenext = -1;
//				}else{
//					this.pagenext = pagenow+1;
//				}
//			}
//			this.setComlist(tempList);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
		
		return "detailcourse";
	}

	//学生工作室首页公告信息
	
		public  long getDaysOfTowDiffDate(Date sdate,Date edate){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdate);
			Long begin =calendar.getTimeInMillis();
			calendar.setTime(edate);
			Long end =calendar.getTimeInMillis()+(1000 * 60 * 60 * 24);
		   long betweenDays = (long) ( ( end- begin ) / ( 1000 * 60 * 60 * 24 ) );
		   return betweenDays;
		}

		
	
	public String getFirstinfo() throws EntityException {
		/*
		 * 调查问卷信息
		 */
		
		this.getLoginStudent();
		
		DetachedCriteria firstdc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = firstdc.createCriteria(
				"enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagType = firstdc.createCriteria(
				"enumConstByFlagType", "enumConstByFlagType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagType.add(Restrictions.or(Restrictions.eq("code", "0"),
				Restrictions.eq("code", "1")));
		firstdc.addOrder(Order.desc("foundDate"));

		List<PeVotePaper> list;
		try {

			list = this.getGeneralService().getList(firstdc);

			if (list.size() > 0) {
				this.setVotePaper(list.get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		/*
//		 * 学生课程学习进度信息
//		 * 已完成课程信息
//		 */	
//		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		String studentid = userSession.getSsoUser().getId();	
//		DetachedCriteria xxdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
//		xxdc.createCriteria("ssoUser","ssoUser");
//		DetachedCriteria xdc = xxdc.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse");
//		xdc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
//		xxdc.add(Restrictions.eq("ssoUser.id", studentid));
//		xxdc.add(Restrictions.eq("learnStatus", "COMPLETED"));
//		xxdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "提升课程"));
//		int tscomnum =this.getGeneralService().getList(xxdc).size();
//		
//		
//		/*
//		 * 学生课程学习进度信息
//		 * 在学课程信息(包括已完成和为完成)
//		 */	
//		DetachedCriteria xxwdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
//		xxwdc.createCriteria("ssoUser","ssoUser");
//		DetachedCriteria xwdc = xxwdc.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse");
//		xwdc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
//		xxwdc.add(Restrictions.eq("ssoUser.id",studentid));
//		xxwdc.add(Restrictions.ne("learnStatus", "COMPLETED"));
//		xxwdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "提升课程"));
//		int tsnum =this.getGeneralService().getList(xxwdc).size();
//		
//		
//		
//		/*
//		 * 学生课程学习进度信息
//		 * 在学课程信息(包括已完成和为完成)
//		 */	
//		DetachedCriteria jcdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
//		jcdc.createCriteria("ssoUser","ssoUser");
//		DetachedCriteria jdc = jcdc.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse");
//		jdc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
//		jcdc.add(Restrictions.eq("ssoUser.id", studentid));
//		jcdc.add(Restrictions.ne("learnStatus", "COMPLETED"));
//		jcdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "基础课程"));
//		
//		int jcnum = this.getGeneralService().getList(jcdc).size();
//		
//		
//		/*
//		 * 学生课程学习进度信息
//		 * 在学课程信息(包括已完成)
//		 */	
//		DetachedCriteria jccomdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
//		jccomdc.createCriteria("ssoUser","ssoUser");
//		jccomdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType");
//		jccomdc.add(Restrictions.eq("ssoUser.id", studentid));
//		jccomdc.add(Restrictions.eq("learnStatus", "COMPLETED"));
//		jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "基础课程"));
//		
//		int jccomnum = this.getGeneralService().getList(jccomdc).size();
//		
//		Map session =  ActionContext.getContext().getSession();
//		session.put("tsnum", this.getGeneralService().getList(xxwdc).size());
//		session.put("tscomnum", this.getGeneralService().getList(xxdc).size());
//		session.put("jcnum" , this.getGeneralService().getList(jcdc).size());
//		session.put("jccomnum" , this.getGeneralService().getList(jccomdc).size());
//		
		
		/*
		 *  首页公告信息
		 */
		List templist = this.pebulletinsList();
			if(templist.size()>7){
				for(int k= 0;k<templist.size();k++){
					if(k>6){
						break;
					}
					this.shortName((PeBulletin)templist.get(k));
					bulletinList.add((PeBulletin)templist.get(k));
				}
			}else{
				for(int k= 0;k<templist.size();k++){
					this.shortName((PeBulletin)templist.get(k));
					bulletinList.add((PeBulletin)templist.get(k));
				}
				for(int k= 0;k<(7-templist.size());k++){
					PeBulletin tub = new PeBulletin();
					tub.setTitle("1");
					bulletinList.add(tub);
				}
			}
		return "studentindex";
	}
	
	public String allPebulletins(){
		bulletinList =this.pebulletinsList();
		return "allPebulletins";
	}
	
	
	/*
	 * 公告信息
	 */
	private List<PeBulletin> pebulletinsList(){
		if(true)
		return new ArrayList();
		List<PeBulletin> xlist =new ArrayList();
		try {
			
			UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			String studentid = userSession.getSsoUser().getId();
			
			DetachedCriteria tradc = DetachedCriteria.forClass(PeTrainee.class);
			tradc.createAlias("peTrainingClass", "peTrainingClass", DetachedCriteria.LEFT_JOIN);
			tradc.createCriteria("ssoUser","ssoUser");
			tradc.add(Restrictions.eq("ssoUser.id",studentid));
			PeTrainee smstu = (PeTrainee)this.getGeneralService().getList(tradc).get(0);
			
			PeTrainingClass ptc =null;// smstu.getPeTrainingClass();
			
			DetachedCriteria dc = DetachedCriteria.forClass(PeBulletin.class);
			
			dc.add(Restrictions.like("scopeString", "<"+ptc.getId()+">", MatchMode.ANYWHERE));
				//有企业 无学期限制
			dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid")
					.createAlias("enumConstByFlagIstop", "enumConstByFlagIstop");
			dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
			
			dc.addOrder(Order.desc("enumConstByFlagIstop.code")).addOrder(
					Order.desc("publishDate"));
			xlist = this.getGeneralService().getDetachList(dc);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlist;
	}
	
	
	
	private void shortName(PeBulletin bulletin){
		String tempname ="";
		if(bulletin.getTitle().length()>20){
			tempname = bulletin.getTitle().substring(0,20)+"...";
			bulletin.setTitle(tempname);
		}
	}

	/**
	 * 分页操作访问的方法
	 * 
	 * @return
	 */

	public String toPageBulletin() {
		this.getLoginStudent();
		// this.theBulletinList();

		return "bulletinView";
	}

	/**
	 * 查看公告详细内容
	 * 
	 * @return
	 */
	public String toInfo() {
		this.getLoginStudent();
		if (this.getBean().getId() != null) {
			try {
				DetachedCriteria dedc = DetachedCriteria.forClass(PeBulletin.class);
				dedc.add(Restrictions.eq("id", this.getBean().getId()));
				this.setBulletin((PeBulletin)this.getGeneralService().getList(dedc).get(0));
				return "info";
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		this.setMsg("无法取得公告!");
		this.setTogo("back");
		return "msg";
	}

	// 取得当前登陆学生peStudent对象
	private PeTrainee getLoginStudent() {
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
//		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List<PeTrainee> student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (student != null && !student.isEmpty()) {
			this.setPeStudent(student.get(0));
			return (PeTrainee) student.get(0);
			
		}
		return null;
	}

	/**
	 * 判断 所包含的范围是否有这个学生
	 * 
	 * @param str
	 * @return
	 */
//	private boolean checkScope(String str) {
//
//		if (!this.test(str, "site", this.getPeBzzStudent().getPeSite().getId())) {
//			return false;
//		}
//		if (!this.test(str, "major", this.getPeStudent().getPeMajor().getId())) {
//			return false;
//		}
//		if (!this.test(str, "edutype", this.getPeStudent().getPeEdutype()
//				.getId())) {
//			return false;
//		}
//		if (!this.test(str, "grade", this.getPeStudent().getPeGrade().getId())) {
//			return false;
//		}
//		return true;
//	}

	/**
	 * 判断字符串是否含有 type 和 id
	 * 
	 * @param str
	 *            公告的scopeString
	 * @param type
	 *            包含的类型 如site major
	 * @param id
	 *            学生所在类型的id
	 * @return
	 */
	private boolean test(String str, String type, String id) {
		int number1 = str.indexOf(type);
		if (number1 < 0) {
			return true;
		}
		str = str.substring(number1, str.length());
		int number2 = str.indexOf("|");
		if (number2 >= 0) {
			str = str.substring(0, number2);
		}
		if (str.indexOf(id) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void initGrid() {

	}

	public void setEntityClass() {
	}

	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/stuPeBulletinView";
	}

	public void setBean(PeBulletin bean) {
		super.superSetBean(bean);
	}

	public PeBulletin getBean() {
		return (PeBulletin) super.superGetBean();
	}

	public List getBulletinList() {
		return bulletinList;
	}

	public void setBulletinList(List bulletinList) {
		this.bulletinList = bulletinList;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<PeVotePaper> getPeVotePaper() {
		return peVotePaper;
	}

	public void setPeVotePaper(List<PeVotePaper> peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public PeVotePaper getVotePaper() {
		return votePaper;
	}

	public void setVotePaper(PeVotePaper votePaper) {
		this.votePaper = votePaper;
	}

	public List<PeBulletin> getPeBulletins() {
		return peBulletins;
	}

	public void setPeBulletins(List<PeBulletin> peBulletins) {
		this.peBulletins = peBulletins;
	}

	public PeBulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(PeBulletin bulletin) {
		this.bulletin = bulletin;
	}

//	public List<TrainingCourseStudent> getUncomlist() {
//		return uncomlist;
//	}
//
//	public List<TrainingCourseStudent> getComlist() {
//		return comlist;
//	}
//
//	public void setUncomlist(List<TrainingCourseStudent> uncomlist) {
//		this.uncomlist = uncomlist;
//	}
//
//	public void setComlist(List<TrainingCourseStudent> comlist) {
//		this.comlist = comlist;
//	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPagenow() {
		return pagenow;
	}

	public int getPagetotal() {
		return pagetotal;
	}

	public int getPagecount() {
		return pagecount;
	}

	public int getPagenext() {
		return pagenext;
	}

	public int getPagedown() {
		return pagedown;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPagenow(int pagenow) {
		this.pagenow = pagenow;
	}

	public void setPagetotal(int pagetotal) {
		this.pagetotal = pagetotal;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public void setPagenext(int pagenext) {
		this.pagenext = pagenext;
	}

	public void setPagedown(int pagedown) {
		this.pagedown = pagedown;
	}

	public PeTrainee getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeTrainee peStudent) {
		this.peStudent = peStudent;
	}

}
