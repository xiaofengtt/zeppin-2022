package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class TchPeBulletinViewAction extends MyBaseAction {
	private List bulletinList; // 保存公告
	private PeTeacher peTeacher;//保存教师信息
	
	private int pageLimit; 				// 每页要显示的信息个数
	private int curPage;				//当前页
	private int totalPage;				//总页数
	private int pageNo;					//从输入框中获取的页码数
	
	public String toBulletinView() {
		if (this.getLoginTeacher()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		this.theBulletins();
		this.theBulletinList();
		return "bulletinView";
	}
	
	/**
	 * 取得这个教师相关的通知公告，保存到session中。
	 */
	private void theBulletins(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBulletin.class);
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid")
			.createAlias("enumConstByFlagIstop", "enumConstByFlagIstop")
			.createAlias("peManager", "peManager");
		dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		dc.add(Restrictions.like("scopeString", "teachers", MatchMode.ANYWHERE));
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		dc.addOrder(Order.desc("enumConstByFlagIstop.code"))	
			.addOrder(Order.desc("publishDate"));
		try {
			List<PeBulletin> list = this.getGeneralService().getList(dc);
			ActionContext.getContext().getSession().remove("tchPeBulletin");
			ActionContext.getContext().getSession().put("tchPeBulletin", list);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
	}
	
	private void theBulletinList() {
		this.setPageLimit(10);
		List list = (List)ActionContext.getContext().getSession().get("tchPeBulletin");
		
		/*
		 *设置当前页 
		 */
		if (this.getPageNo()>0){
			this.setCurPage(this.getPageNo());
		}
		if (this.getCurPage()==0) {
			this.setCurPage(1);
		}
		
		/**
		 * 根据页数 取得所要显示的list
		 */
		if (list!=null&&list.size()>0) {
			if (list.size()<=(this.getPageLimit()*this.getCurPage())) {
				List page = new ArrayList();
				for (int i = (this.getPageLimit()*(this.getCurPage()-1)); i < list.size(); i++) {
					page.add(list.get(i));
				}
				this.setBulletinList(page);
			}else {
				List page = new ArrayList();
				for (int i = (this.getPageLimit()*(this.getCurPage()-1)); i < (this.getPageLimit()*this.getCurPage()); i++) {
					page.add(list.get(i));
				}
				this.setBulletinList(page);
			}

			/**
			 * 求出总页数
			 */
			double tempTotalPage = (double)list.size()/(double)this.getPageLimit();
			/**
			 * 设置总页数
			 */
			if(tempTotalPage <= 1.0){
				this.setTotalPage(1);
			}else if(tempTotalPage > 1.0 && tempTotalPage < 2.0){
				this.setTotalPage(2);
			}else{
				if(list.size()%this.getPageLimit() > 0){
					this.setTotalPage(list.size()/this.getPageLimit()+1);
				}else
					this.setTotalPage(list.size()/this.getPageLimit());
			}
		} else {
			this.setTotalPage(1);
		}
	}
	
	/**
	 *分页操作访问的方法
	 * @return
	 */
	public String toPageBulletin() {
		this.getLoginTeacher();
		this.theBulletinList();
		return "bulletinView";
	}
	
	/**
	 * 查看公告详细内容
	 * @return
	 */
	public String toInfo() {
		this.getLoginTeacher();
		if (this.getBean().getId()!=null) {
			try {
				this.setBean((PeBulletin)this.getGeneralService().getById(this.getBean().getId()));
				return "info";
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		this.setMsg("无法取得公告!");
		this.setTogo("back");
		return "msg";
	}
	
	
	//取得当前登陆学生peStudent对象
	private PeTeacher getLoginTeacher(){
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return null;
		}
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(teacherList != null && !teacherList.isEmpty()) {
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
		this.setPeTeacher(peTeacher);
		return peTeacher;
		}
		return null;
	}



	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBulletin.class;
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/stuPeBulletinView";
	}
	
	
	public void setBean(PeBulletin bean) {
		super.superSetBean(bean);
	}
	
	public PeBulletin getBean() {
		return (PeBulletin)super.superGetBean();
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

	public PeTeacher getPeTeacher() {
		return peTeacher;
	}

	public void setPeTeacher(PeTeacher peTeacher) {
		this.peTeacher = peTeacher;
	}
}
