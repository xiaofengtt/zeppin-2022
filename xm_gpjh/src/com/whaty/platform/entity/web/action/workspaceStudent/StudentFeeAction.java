package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class StudentFeeAction extends MyBaseAction<PrFeeDetail> {

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/studentFee";
	}
	
	private List prFeeDetailList;
	private PeStudent peStudent;
	
	//	以下字段用于分页显示
	private String start; 				// 列表开始的位置
	private String limit; 				// 每页要显示的信息个数
	private int curPage;				//当前页
	private int totalPage;				//总页数
	private int pageNo;					//从输入框中获取的页码数
	
	private int isLimit;				//判断请求是否由设定页面信息个数所发出的，1是，0否

	public PeStudent getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public List getPrFeeDetailList() {
		return prFeeDetailList;
	}

	public void setPrFeeDetailList(List prFeeDetailList) {
		this.prFeeDetailList = prFeeDetailList;
	}
	
	public String toDetail() {
		
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = us.getSsoUser();

		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
		dcPeStudent.add(Restrictions.eq("ssoUser", ssoUser));
		PeStudent student = null;
		try {
			student = (PeStudent)(this.getGeneralService().getList(dcPeStudent).get(0));
			this.setPeStudent(student);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.add(Restrictions.eq("peStudent", student));
		dc.addOrder(Order.asc("inputDate"));
		
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
			this.setPrFeeDetailList(this.getGeneralService().getList(dc));
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		/**
		 * 求出总页数
		 */
		double tempTotalPage = this.getPrFeeDetailList().size()/Double.parseDouble(this.getLimit());
		
		/**
		 * 设置总页数
		 */
		if(tempTotalPage <= 1.0){
			this.setTotalPage(1);
		}else if(tempTotalPage > 1.0 && tempTotalPage < 2.0){
			this.setTotalPage(2);
		}else{
			if(this.getPrFeeDetailList().size()%Integer.parseInt(this.getLimit()) > 0){
				this.setTotalPage(this.getPrFeeDetailList().size()/Integer.parseInt(this.getLimit())+1);
			}else
				this.setTotalPage(this.getPrFeeDetailList().size()/Integer.parseInt(this.getLimit()));
		}
		
		try {
			this.setPrFeeDetailList(this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart())).getItems());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toDetail";
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
